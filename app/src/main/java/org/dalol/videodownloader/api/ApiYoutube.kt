package org.dalol.videodownloader.api

import retrofit2.http.GET
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.Query


/**
 * Created by filippo on 21/01/2018.
 */
interface ApiYoutube {

//    @Headers("Accept-Language: en-us,en;q=0.5", "Connection: Keep-Alive", "Accept-Encoding: gzip", "Host: www.youtube.com", "Content-Type: application/x-www-form-urlencoded")
    @Headers(
//            "Accept-Language: en-us,en;q=0.5",
            "Connection: close",
//            "Accept-Encoding: gzip",
            "Accept: */*",
            "Host: www.youtube.com"
//            "Content-Type: application/x-www-form-urlencoded"
    )
    @GET("/get_video_info?el=detailpage&sts=17561")
    fun getVideoInfo(@Query("video_id") videoId: String): Call<ResponseBody>
}