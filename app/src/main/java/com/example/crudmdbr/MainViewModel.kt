package com.example.crudmdbr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudmdbr.ui.theme.data.models.Address
import com.example.crudmdbr.ui.theme.data.models.Course
import com.example.crudmdbr.ui.theme.data.models.Student
import com.example.crudmdbr.ui.theme.data.models.Teacher
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val realm = MyApp.realm

    val course = realm
        .query<Course>()
        .asFlow()
        .map { result->
            result.list.toList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    var courseDetails: Course? by mutableStateOf(null)
        private set

   /* init {
        createSampleEntries()
    }*/

    fun showCourseDet(course: Course){
        courseDetails=course
    }
    fun hideCourseDet(){
        courseDetails=null
    }


    private fun createSampleEntries() {
        viewModelScope.launch {
            realm.write {
                val address1 = Address().apply {
                    fullname = "Arulmurugan"
                    street = "Kamaraj st"
                    houseNum = 29
                    city = "Chennai"
                    zipCode = "600025"
                }
                val address2 = Address().apply {
                    fullname = "Amritha"
                    street = "Thamarai st"
                    houseNum = 30
                    city = "Chennai"
                    zipCode = "600025"
                }
                val address3 = Address().apply {
                    fullname = "Killi"
                    street = "Elango st"
                    houseNum = 24
                    city = "Chennai"
                    zipCode = "600018"
                }
                val address4 = Address().apply {
                    fullname = "Kanishka"
                    street = "Shanmugam st"
                    houseNum = 55
                    city = "Chennai"
                    zipCode = "600011"
                }
                val address5 = Address().apply {
                    fullname = "Adnan"
                    street = "Shakeel st"
                    houseNum = 64
                    city = "Chennai"
                    zipCode = "600029"
                }

                val course1 = Course().apply {
                    courseName = "Learn Kotlin Jetpack Compose"
                }
                val course2 = Course().apply {
                    courseName = "Java Advanced"
                }
                val course3 = Course().apply {
                    courseName = "MERN Stack"
                }
                val course4 = Course().apply {
                    courseName = "Flutter-Lets create your first mobile app"
                }
                val course5 = Course().apply {
                    courseName = "BlockChain Technologies"
                }

                val teacher1 = Teacher().apply {
                    address = address1
                    courses = realmListOf(course1)
                }
                val teacher2 = Teacher().apply {
                    address = address2
                    courses = realmListOf(course2)
                }
                val teacher3 = Teacher().apply {
                    address = address3
                    courses = realmListOf(course3)
                }
                val teacher4 = Teacher().apply {
                    address = address4
                    courses = realmListOf(course4)
                }
                val teacher5 = Teacher().apply {
                    address = address5
                    courses = realmListOf(course5)
                }
                course1.teacher = teacher1
                course2.teacher = teacher2
                course3.teacher = teacher3
                course4.teacher = teacher4
                course5.teacher = teacher5

                address1.teacher = teacher1
                address2.teacher = teacher2
                address3.teacher = teacher3
                address4.teacher = teacher4
                address5.teacher = teacher5

                val student1 = Student().apply {
                    studentName = "Kavin"
                }
                val student2 = Student().apply {
                    studentName = "Vijay"
                }
                val student3 = Student().apply {
                    studentName = "John Dhurai"
                }
                val student4 = Student().apply {
                    studentName = "Siraj"
                }

                course1.enrolledStudents.addAll(listOf(student1,student3))
                course2.enrolledStudents.addAll(listOf(student3,student4))
                course3.enrolledStudents.addAll(listOf(student2,student4))
                course4.enrolledStudents.addAll(listOf(student2,student1))
                course5.enrolledStudents.addAll(listOf(student2,student1))

                copyToRealm(teacher1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(teacher2, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(teacher3, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(teacher4, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(teacher5, updatePolicy = UpdatePolicy.ALL)

                copyToRealm(course1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(course2, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(course3, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(course4, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(course5, updatePolicy = UpdatePolicy.ALL)

                copyToRealm(student1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(student2, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(student3, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(student4, updatePolicy = UpdatePolicy.ALL)

            }
        }
    }
    fun DeleteCourse(){
        viewModelScope.launch {
            realm.write {
                var course = courseDetails ?: return@write
                var latest = findLatest(course) ?: return@write
                delete(latest)
                courseDetails=null
            }
        }
    }
}