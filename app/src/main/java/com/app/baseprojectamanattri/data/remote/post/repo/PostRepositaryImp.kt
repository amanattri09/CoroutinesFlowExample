package com.app.baseprojectamanattri.data.remote.post.repo

import com.app.baseprojectamanattri.data.local.SharedPrefManager
import com.app.baseprojectamanattri.domain.post.models.PostModel
import com.app.baseprojectamanattri.domain.post.repositary.PostRepositary
import com.app.baseprojectamanattri.network.ConnectionHelper
import com.app.baseprojectamanattri.network.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * repo implementation
 */
class PostRepositaryImp @Inject constructor(
    private val apiService: ApiService,
    private val sharedPrefManager: SharedPrefManager,
    private val connectionHelper: ConnectionHelper,
) : PostRepositary {

    private var posts: List<PostModel> = arrayListOf()
        get() = sharedPrefManager.getPosts()

    override fun getPosts(): Flow<List<PostModel>> = flow {
        if (connectionHelper.isConnected()) {
            emit(apiService.listPosts().map {
                it.mapToModel()
            }.saveToPref(sharedPrefManager))
        } else {
            emit(posts)
        }
    }.flowOn(Dispatchers.Default)

    override fun getPost(postId: String): Flow<PostModel> = flow {
        emit(apiService.getPostById(postId).mapToModel())
    }.flowOn(Dispatchers.Default)

}

private fun List<PostModel>.saveToPref(sharedPrefManager: SharedPrefManager): List<PostModel> {
    sharedPrefManager.savePosts(this)
    return this
}
