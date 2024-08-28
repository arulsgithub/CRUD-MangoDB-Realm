package com.example.crudmdbr

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import com.example.crudmdbr.ui.theme.CRUDMDBRTheme
import com.example.crudmdbr.ui.theme.data.models.Course

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CRUDMDBRTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val courses by viewModel.course.collectAsState()

                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                            .padding(16.dp)
                            .padding(top = 30.dp)

                    ) {
                        items(courses){course ->
                            Courses(
                                course = course,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.DarkGray)

                                    .clickable {
                                        viewModel.showCourseDet(course)
                                    }
                            )
                        }
                    }
                    if(viewModel.courseDetails!=null){
                        Dialog(onDismissRequest = viewModel::hideCourseDet) {

                            Column(
                                modifier = Modifier
                                    .widthIn(200.dp, 300.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.Black)
                                    .padding(16.dp)
                            ) {
                                viewModel.courseDetails?.teacher?.address?.let {
                                    Text(text = it.fullname)
                                    Text(text = it.houseNum.toString() +", "+ it.street )
                                    Text(text = it.city +" - "+ it.zipCode)
                                }
                                Button(
                                    onClick = { viewModel.DeleteCourse() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.errorContainer,
                                        contentColor =  MaterialTheme.colorScheme.onErrorContainer
                                    )
                                ) {
                                    Text(text = "Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Courses(
    course: Course,
    modifier: Modifier
){

    Column(
        modifier = modifier
    ) {
        Text(
            text = course.courseName,
            fontWeight = FontWeight.W600,
            fontSize = 20.sp,
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = "Held by ${course?.teacher?.address?.fullname}",
            fontWeight = FontWeight.W600,
            fontSize = 14.sp,
            modifier = Modifier.padding(5.dp)

        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Enrolled Students: ${course.enrolledStudents.joinToString { it.studentName }}",
            fontWeight = FontWeight.W600,
            fontSize = 16.sp,
            modifier = Modifier.padding(5.dp)

        )
    }
}
