package io.techmeskills.an02onl_plannerapp.screen.main.cloud

import com.google.gson.annotations.SerializedName

class CloudNote(

    @SerializedName("title")
    val noteTitle: String,

    @SerializedName("date")
    val date: String
)