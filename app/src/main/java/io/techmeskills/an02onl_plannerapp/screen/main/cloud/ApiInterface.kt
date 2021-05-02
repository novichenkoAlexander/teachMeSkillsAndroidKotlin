package io.techmeskills.an02onl_plannerapp.screen.main.cloud

import io.techmeskills.an02onl_plannerapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @GET("importNotes")//!!!!!!name importNotes
    suspend fun importNotes(
        @Query("userName") userName: String,
        @Query("phoneId") phoneId: String
    ): Response<List<CloudNote>>

    @POST("exportNotes")// exportNotes
    suspend fun exportNotes(@Body body: UploadNotesRequestBody): Response<Any>

    companion object {
        private const val API_URL = "https://us-central1-plannerapi-2d0bf.cloudfunctions.net"

        fun get(): ApiInterface = Retrofit.Builder().baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder().apply {
                    if (BuildConfig.DEBUG) {
                        addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    }
                }.build()
            )
            .build().create(ApiInterface::class.java)
    }

}