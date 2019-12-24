package com.tsivileva.okhttpexample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.tsivileva.okhttpexample.OkAsyncExample.Companion.BUNDL_KEY
import com.tsivileva.okhttpexample.OkAsyncExample.Companion.GET_CODE
import com.tsivileva.okhttpexample.OkAsyncExample.Companion.POST_CODE
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    companion object {

        class MyHandler(mContext: Context) {

            var handler = Handler(Looper.getMainLooper(), Handler.Callback {

                val code=it.what
                val body = it.data.getString(BUNDL_KEY)

                if (body!= null){
                    when(code){
                        GET_CODE-> Toast.makeText(mContext, "GET: $body", Toast.LENGTH_LONG).show()
                        POST_CODE->Toast.makeText(mContext, "POST: $body", Toast.LENGTH_LONG).show()
                    }

                }

                true
            })
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        get_button.setOnClickListener {
            try {
                val body=OkAsyncExample(this).getByURL(url_edit_text.text.toString())
                Toast.makeText(this, "$body", Toast.LENGTH_LONG).show()
            } catch (ex: Exception) {
                Toast.makeText(this, "${ex.message}", Toast.LENGTH_LONG).show()
            }
        }

        /*кнопка POST*/
        put_button.setOnClickListener {
            OkAsyncExample(this).uploadOnServer()
        }


    }
}



