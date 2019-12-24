package com.tsivileva.okhttpexample

import android.content.Context
import android.os.Bundle
import android.os.Message

import android.util.Log
import okhttp3.*
import java.io.IOException

class OkAsyncExample(val mContext: Context) {
    companion object {
        val GET_CODE = 1
        val POST_CODE = 2
        val BUNDL_KEY = "body"
        val URL = "https://my-json-server.typicode.com/LOLyta1/ServerRepo/comments"
    }


    fun sendMessageInMainThread(msgCode: Int, bundlKey: String, bundlValue: String) {
        var message = Message.obtain(MainActivity.Companion.MyHandler(mContext).handler, msgCode)
        var bundle = Bundle()
        bundle.putString(bundlKey, bundlValue)
        message.data = bundle
        message.sendToTarget()
    }


    /*метод получения содержимого(body) через http запрос*/
    fun getByURL(mUrl: String) {
        val client = OkHttpClient()
        var request = Request.Builder().url(mUrl).build()
        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, ex: IOException) {
                ex.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.byteString().toString()
                sendMessageInMainThread(GET_CODE, BUNDL_KEY, responseBody)
            }
        })
    }


    /*метод отправки на сервер */
    fun uploadOnServer() {
        val client = OkHttpClient()
        val body: FormBody =FormBody.Builder().
                                    add("id", "3").
                                    add("body", "comments3").
                                    add("postID", "1")
                                    .build()

        val request = Request.Builder().url(URL).post(body).build()
        val call = client.newCall(request)

        call.enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    var text ="respons si successful: ${response.isSuccessful}, body: ${response.body?.byteString().toString()}"
                    sendMessageInMainThread(POST_CODE, BUNDL_KEY, text)

                }

                override fun onFailure(call: Call, e: IOException) {
                    Log.d("myLog", "OkAsyncExample.uploadOnServer() ОШИБКА: ${e.message}")
                }
        })
    }
}
