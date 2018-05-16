package com.jastzeonic.pagingdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jastzeonic.pagingdemo.api.RedditApi
import com.jastzeonic.pagingdemo.vo.RedditPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val call: Call<RedditApi.ListingResponse> = RedditApi.create().getTop("androidDev", 30)
        call.enqueue(object : Callback<RedditApi.ListingResponse> {
            override fun onFailure(call: Call<RedditApi.ListingResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<RedditApi.ListingResponse>?, response: Response<RedditApi.ListingResponse>?) {

                Log.d("Test", "test：" + response?.body()?.data?.children?.size)

            }

        })


    }
}
