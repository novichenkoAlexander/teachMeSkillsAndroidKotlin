package io.techmeskills.an02onl_plannerapp.cloud

import com.google.gson.annotations.SerializedName

class CloudNote(

    @SerializedName("title")
    val noteTitle: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("time")
    val time: String
)