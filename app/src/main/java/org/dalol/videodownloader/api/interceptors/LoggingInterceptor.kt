package org.dalol.videodownloader.api.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.*

/**
 * Created by filippo on 21/01/2018.
 */

class LoggingInterceptor: Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val val1 = getJHD()
        val val2 = getJHD()

        val newRequest = request.newBuilder ()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36")
                .build();


        //        long t1 = System.nanoTime();
        //        logger.info(String.format("Sending request %s on %s%n%s",
        //                request.url(), chain.connection(), request.headers()));

        //        long t2 = System.nanoTime();
        //        logger.info(String.format("Received response for %s in %.1fms%n%s",
        //                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return chain.proceed(newRequest)
    }

    private fun getJHD(): String? {
        return UUID.randomUUID().toString().substring(0, 5)
    }
}