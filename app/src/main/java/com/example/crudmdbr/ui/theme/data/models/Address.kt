package com.example.crudmdbr.ui.theme.data.models

import io.realm.kotlin.types.EmbeddedRealmObject

class Address: EmbeddedRealmObject {

    var fullname: String = ""
    var street: String = ""
    var houseNum: Int = 0
    var city: String = ""
    var zipCode: String = ""
    var teacher: Teacher? = null

}