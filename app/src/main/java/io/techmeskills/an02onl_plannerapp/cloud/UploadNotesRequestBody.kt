package io.techmeskills.an02onl_plannerapp.cloud

import com.google.gson.annotations.SerializedName

class UploadNotesRequestBody(

    @SerializedName("user")
    val user: CloudUser,

    @SerializedName("phoneId")
    val phoneId: String,

    @SerializedName("notes")
    val notes: List<CloudNote>
)