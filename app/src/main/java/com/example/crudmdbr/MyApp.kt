package com.example.crudmdbr

import android.app.Application
import com.example.crudmdbr.ui.theme.data.models.Address
import com.example.crudmdbr.ui.theme.data.models.Course
import com.example.crudmdbr.ui.theme.data.models.Student
import com.example.crudmdbr.ui.theme.data.models.Teacher
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class MyApp : Application() {

    companion object {
        val realm: Realm by lazy {
            Realm.open(
                configuration = RealmConfiguration.create(
                    schema = setOf(
                        Address::class,
                        Teacher::class,
                        Course::class,
                        Student::class
                    )
                )
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        realm
    }
}
