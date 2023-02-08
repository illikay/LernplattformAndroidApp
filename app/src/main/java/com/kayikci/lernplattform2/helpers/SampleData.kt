package com.kayikci.lernplattform2.helpers

/*
import com.kayikci.lernplattform2.models.Exam
import java.util.*

object SampleData {

	val EXAMS = ArrayList<Exam>()

	private var COUNT = 5

	private var dummy_description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce accumsan quis justo quis hendrerit. Curabitur a ante neque. Fusce nec mauris sodales, auctor sem at, luctus eros. Praesent aliquam nibh neque. Duis ut suscipit justo, id consectetur orci. Curabitur ultricies nunc eu enim dignissim, sed laoreet odio blandit."

	init {
		// Add some sample items
		val newDestination1 = Exam()
		newDestination1.id = 1
		newDestination1.pruefungsName= "New Delhi"
		newDestination1.beschreibung = dummy_description
		newDestination1.info = "India"
		EXAMS.add(newDestination1)

		val newDestination2 = Exam()
		newDestination2.id = 2
		newDestination2.pruefungsName = "Bangkok"
		newDestination2.beschreibung = dummy_description
		newDestination2.info = "Thailand"
		EXAMS.add(newDestination2)

		val newDestination3 = Exam()
		newDestination3.id = 3
		newDestination3.pruefungsName = "New York"
		newDestination3.beschreibung = dummy_description
		newDestination3.info = "USA"
		EXAMS.add(newDestination3)

	}

	fun addDestination(item: Exam) {
		item.id = COUNT
		EXAMS.add(item)
		COUNT += 1
	}

	fun getDestinationById(id: Int): Exam? {
		for (i in EXAMS.indices) {
			if (EXAMS[i].id == id) {
				return EXAMS[i]
			}
		}

		return null
	}

	fun deleteDestination(id: Int) {
		var destinationToRemove: Exam? = null

		for (i in EXAMS.indices) {
			if (EXAMS[i].id == id) {
				destinationToRemove = EXAMS[i]
			}
		}

		if (destinationToRemove != null) {
			EXAMS.remove(destinationToRemove)
		}
	}

	fun updateDestination(destination: Exam) {
		for (i in EXAMS.indices) {
			if (EXAMS[i].id == destination.id) {
				val destinationToUpdate = EXAMS[i]

				destinationToUpdate.pruefungsName = destination.pruefungsName
				destinationToUpdate.beschreibung = destination.beschreibung
				destinationToUpdate.info = destination.info
			}
		}
	}
}
*/
