package io.techmeskills.an02onl_plannerapp.screen.main.cloud

import com.google.gson.annotations.SerializedName

class CloudUser(

    @SerializedName("id")
    val userId: Long,

    @SerializedName("name")
    val userName: String
)