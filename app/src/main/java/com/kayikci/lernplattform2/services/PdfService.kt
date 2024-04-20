package com.kayikci.lernplattform2.services

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.kayikci.lernplattform2.models.Exam
import com.kayikci.lernplattform2.models.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.OutputStream


class PdfService {


    fun createPdfFromList(dataSource: List<Exam>, outputStream: OutputStream) {

        val coroutineScope = CoroutineScope(Dispatchers.Main)

        coroutineScope.launch {


            // Create a new PdfDocument
            val pdfDocument = PdfDocument()

            // Create a Paint object for drawing text
            val paint = Paint()
            paint.color = Color.BLACK

            // Set the page size (A4)
            val pageWidth = 595
            val pageHeight = 842



            // For each item in the RecyclerView, create a new page and draw the text
            for (i in dataSource.indices) {
                // Create a new PageInfo object and a new Page object
                val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, i + 1).create()
                val page = pdfDocument.startPage(pageInfo)

                // Get the Canvas object from the Page and draw the text
                val canvas = page.canvas

                var yPos = 30f // Reset yPos for each new page

                val examText = dataSource[i].toPdfString()
                drawTextWithLineBreaks(canvas, examText, 20f, yPos, paint)

                yPos += paint.textSize * examText.lines().size + 15f



                //yPos += 15f


                val questionList = withContext(Dispatchers.IO) {
                    loadQuestionsByExam(dataSource[i].id!!)
                }


                questionList.forEachIndexed { index, question ->
                    val questionText = question.toPdfString(index + 1)
                    drawTextWithLineBreaks(canvas, questionText, 20f, yPos, paint)
                    yPos += paint.textSize * questionText.lines().size + 15f
                }



                pdfDocument.finishPage(page)

            }
            try {
                pdfDocument.writeTo(outputStream)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            // Close the PdfDocument
            pdfDocument.close()
        }

    }

    private fun drawTextWithLineBreaks(canvas: Canvas, text: String, x: Float, y: Float, paint: Paint) {
        val lines = text.split("\n")
        var currentY = y
        for (line in lines) {
            canvas.drawText(line, x, currentY, paint)
            currentY += paint.textSize
        }
    }

    suspend fun loadQuestionsByExam(id: Long): List<Question> =
        suspendCancellableCoroutine { continuation ->
            val coroutineScope = CoroutineScope(Dispatchers.IO)



            coroutineScope.launch(Dispatchers.IO) {
                val questionService = ServiceBuilder.buildService(QuestionService::class.java)

                try {
                    val response = questionService.getQuestionList(id)

                    if (response.isSuccessful) {
                        println("getQuestionList-API-Request war erfolgreich")


                        continuation.resume(response.body()!!, {})


                    } else if (response.code() == 401) {
                        println("Deine Session ist abgelaufen. Bitte logge dich nochmal ein")
                        continuation.cancel()  // Du kannst auch einen spezifischen Fehler werfen, wenn du möchtest

                    } else {
                        println("getQuestionList-API-Request ist fehlgeschlagen")
                        continuation.cancel()  // Du kannst auch einen spezifischen Fehler werfen, wenn du möchtest

                    }
                } catch (e: Exception) {
                    println("getQuestionList-API-Request ist fehlgeschlagen. Ein Fehler ist aufgetreten")
                    continuation.cancel()  // Du kannst auch einen spezifischen Fehler werfen, wenn du möchtest

                }

            }
        }


}