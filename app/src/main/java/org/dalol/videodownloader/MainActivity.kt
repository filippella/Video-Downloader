package org.dalol.videodownloader

import android.os.Bundle
import android.widget.TextView
import org.dalol.videodownloader.base.BaseActivity
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import com.google.gson.GsonBuilder
import org.dalol.videodownloader.api.interceptors.GzipRequestInterceptor
import okhttp3.OkHttpClient
import org.dalol.videodownloader.api.ApiYoutube
import org.dalol.videodownloader.api.interceptors.LoggingInterceptor
import okhttp3.ResponseBody
import java.util.logging.Level.SEVERE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URLDecoder
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import java.util.regex.Pattern
import java.util.zip.GZIPInputStream
import java.util.regex.Pattern.DOTALL




class MainActivity : BaseActivity() {

    lateinit var textLinks: TextView

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        textLinks = findViewById(R.id.text_video_links)

        val client = OkHttpClient.Builder()
                .addNetworkInterceptor(LoggingInterceptor())
                //.addNetworkInterceptor(GzipRequestInterceptor())
                .build()

        val gb = GsonBuilder()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://www.youtube.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        val api = retrofit.create(ApiYoutube::class.java)

//        val videoId = "CpM33xnx2TI"
//        val videoId = "rRC6C8bRkQQ"
        val videoId = "XeThOWsPdmc"
//        val videoId = "y18I_oZHfPs"
//        val videoId = "r7IvAHXaxjE"
        val videoInfo = api.getVideoInfo(videoId)
        videoInfo.enqueue(object : Callback<ResponseBody> {


            override fun onResponse(call: Call<ResponseBody>, rspns: Response<ResponseBody>) {

                val body = rspns.body()

                if (body != null) {
                    try {
                        //val gzipInputStream = GZIPInputStream(body!!.byteStream())
                        val stream = body!!.byteStream()
                        val reader = BufferedReader(InputStreamReader(stream, "UTF-8"))
                        var line : String?
                        val builder = StringBuilder()

                        while (true) {
                            line = reader.readLine() ?: break
                            builder.append(line)
                        }

                        val rawText = builder.toString()

                        val containedUrls = ArrayList<String>()

                        //System.out.println(rawText);
                        val tokenizer = StringTokenizer(rawText, "&")
                        while (tokenizer.hasMoreTokens()) {
                            var keyVal = tokenizer.nextToken()

                            if (keyVal.contains("url_encoded_fmt_stream_map")) {
                                //String decode = URLDecoder.decode(keyVal, "UTF-8").replace("+", "");

                                keyVal = keyVal.replace("url_encoded_fmt_stream_map", "")

                                keyVal = URLDecoder.decode(keyVal, "UTF-8")
                                keyVal = URLDecoder.decode(keyVal, "UTF-8")

//                                val output2 = URLDecoder.decode(output1, "UTF-8")
//                                val output3 = URLDecoder.decode(output2, "UTF-8")

                                println("Bingo")
                                println(keyVal)

                                textLinks.text = keyVal

//                                val matcher = urlPattern.matcher(keyVal)
//                                while (matcher.find()) {
//                                    val matchStart = matcher.start(1)
//                                    val matchEnd = matcher.end()
//
//                                    containedUrls.add(keyVal.substring(matcher.start(0), matcher.end(0)));
//
////                                    println(matchStart)
////                                    println(matchEnd)
//                                    // now you have the offsets of a URL match
//                                }

//                                println("All URL")
//                                println("-------------------------\n\n\n")
//
//                                for (url in containedUrls) {
//                                    println(url +"\n\n")
//                                }
                                //println(containedUrls.toString())

                                //System.out.println(output3);

                                //                                StringTokenizer videoInfoTokens = new StringTokenizer(output3, "&url=");
                                //                                int logic = 0;
                                //                                while (videoInfoTokens.hasMoreTokens()) {
                                //                                    String nextToken = videoInfoTokens.nextToken();
                                //                                    if(logic == 0) {
                                //                                        System.out.print("Key: " + nextToken);
                                //                                        logic = 1;
                                //                                    } else {
                                //                                        System.out.println("\tValue: " + nextToken);
                                //                                        logic = 0;
                                //                                    }
                                //                                }

//                                val urls = output2.split("&url=".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
//                                for (url in urls) {
//                                    println("\n" + url.replace("\"", ""))
//                                }
                            }
                        }
                    } catch (ex: Exception) {
                        Logger.getLogger(MainActivity::class.java!!.getName()).log(Level.SEVERE, null, ex)
                    }

                }
            }

            override fun onFailure(call: Call<ResponseBody>, thrwbl: Throwable) {
                System.err.println("Error " + thrwbl.message)
            }
        })
    }

    private val urlPattern = Pattern.compile(
            "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
            + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
            + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL)
}
