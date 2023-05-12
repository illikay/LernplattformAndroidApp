package com.kayikci.lernplattform2.services

import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.kayikci.lernplattform2.models.Exam
import java.io.IOException
import java.io.OutputStream


class PdfService {
    fun createPdfFromRecyclerView(dataSource: List<Exam>, outputStream: OutputStream) {


        // Create a new PdfDocument
        val pdfDocument = PdfDocument()

        // Create a Paint object for drawing text
        val paint = Paint()
        paint.color = Color.BLACK

        // Set the page size (A4)
        val pageWidth = 595
        val pageHeight = 842

        var yPos = 30f

        // For each item in the RecyclerView, create a new page and draw the text
        for (i in dataSource.indices) {
            // Create a new PageInfo object and a new Page object
            val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, i + 1).create()
            val page = pdfDocument.startPage(pageInfo)

            // Get the Canvas object from the Page and draw the text
            val canvas = page.canvas

            val pruefungsname = dataSource[i].pruefungsName
            val info = dataSource[i].info
            val beschreibung = dataSource[i].beschreibung

            if (pruefungsname != null) {
                canvas.drawText(pruefungsname    , 20f, yPos, paint)
            }
            yPos += 15f
            if (info != null) {
                canvas.drawText(info    , 20f, yPos, paint)
            }
            yPos += 15f
            if (beschreibung != null) {
                canvas.drawText(beschreibung    , 20f, yPos, paint)
            }


            // Finish the page
            pdfDocument.finishPage(page)
        }


        // Write the PDF file to the external storage
        try {
            pdfDocument.writeTo(outputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Close the PdfDocument
        pdfDocument.close()
    }
}