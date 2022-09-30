package com.app.baseprojectamanattri.presentation.views.postdetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.app.baseprojectamanattri.R
import com.app.baseprojectamanattri.databinding.PostDetailActivityBinding
import com.app.baseprojectamanattri.domain.post.models.PostModel
import com.app.baseprojectamanattri.presentation.common.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailActivity : BaseActivity<PostDetailActivityBinding>() {

    companion object {
        private const val EXTRAS_POST_MODEL = "extras_post_model"

        fun start(activity: Activity, postModel: PostModel) {
            val intent = Intent(activity, PostDetailActivity::class.java)
            intent.putExtra(EXTRAS_POST_MODEL, postModel)
            activity.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(PostDetailActivityBinding.inflate(layoutInflater))
        var postModel = intent.getParcelableExtra<PostModel>(EXTRAS_POST_MODEL)
        if (postModel != null) {
            setViews(postModel)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setViews(postModel: PostModel) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PostDetailFragment.newInstance(postModel))
            .commitNow()
    }


}