package com.jastzeonic.pagingdemo

import android.arch.lifecycle.Observer
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jastzeonic.pagingdemo.api.RedditApi
import com.jastzeonic.pagingdemo.paging.SubRedditDataSourceFactory
import com.jastzeonic.pagingdemo.vo.RedditPost
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private val NETWORK_IO = Executors.newFixedThreadPool(5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val call: Call<RedditApi.ListingResponse> = RedditApi.create().getTop("androidDev", 30)

        val redditApi = RedditApi.create()

        val pageSize = 30


        val sourceFactory = SubRedditDataSourceFactory(redditApi, "androidDev", NETWORK_IO)
        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(pageSize * 2)
                .setPageSize(pageSize)
                .build()
        val pagedList = LivePagedListBuilder(sourceFactory, pagedListConfig)
                // provide custom executor for network requests, otherwise it will default to
                // Arch Components' IO pool which is also used for disk access
                .setFetchExecutor(NETWORK_IO)
                .build()

        val adapter = PostsAdapter({
        })

        val list = findViewById<RecyclerView>(R.id.recyclerView)

        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)

        pagedList.observe(this, Observer<PagedList<RedditPost>> {
            adapter.submitList(it)
        })


//                call . enqueue (object : Callback<RedditApi.ListingResponse> {
//            override fun onFailure(call: Call<RedditApi.ListingResponse>?, t: Throwable?) {
//            }
//
//            override fun onResponse(call: Call<RedditApi.ListingResponse>?, response: Response<RedditApi.ListingResponse>?) {
//
//                Log.d("Test", "test：" + response?.body()?.data?.children?.size)
//
//            }
//
//        })


    }
}
