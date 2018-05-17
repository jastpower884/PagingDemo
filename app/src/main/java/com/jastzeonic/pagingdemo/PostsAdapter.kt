/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jastzeonic.pagingdemo

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.jastzeonic.pagingdemo.vo.RedditPost

/**
 * A simple adapter implementation that shows Reddit posts.
 */
class PostsAdapter()
    : PagedListAdapter<RedditPost, RecyclerView.ViewHolder>(POST_COMPARATOR) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RedditPostViewHolder).bind(getItem(position))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            val item = getItem(position)
            (holder as RedditPostViewHolder).updateScore(item)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RedditPostViewHolder.create(parent)
    }

    override fun getItemCount(): Int {

        val count = super.getItemCount()

        return  count
    }


    companion object {
        private val PAYLOAD_SCORE = Any()
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<RedditPost>() {
            override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean =
                    oldItem.name == newItem.name

            override fun getChangePayload(oldItem: RedditPost, newItem: RedditPost): Any? {
                return if (sameExceptScore(oldItem, newItem)) {
                    PAYLOAD_SCORE
                } else {
                    null
                }
            }
        }

        private fun sameExceptScore(oldItem: RedditPost, newItem: RedditPost): Boolean {
            // DON'T do this copy in a real app, it is just convenient here for the demo :)
            // because reddit randomizes scores, we want to pass it as a payload to minimize
            // UI updates between refreshes
            return oldItem.copy(score = newItem.score) == newItem
        }
    }
}
