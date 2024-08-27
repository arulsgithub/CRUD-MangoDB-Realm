package com.example.crudmdbr.ui.theme.data.models

import io.realm.kotlin.ext.backlinks
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Student: RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var studentName: String = ""
    val enrolledStudent: RealmResults<Course> by backlinks(Course::enrolledStudents)
}