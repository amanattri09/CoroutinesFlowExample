package com.app.baseprojectamanattri.presentation.sample

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.baseprojectamanattri.data.entities.ApiResponse
import com.app.baseprojectamanattri.domain.post.interactor.PostUserCase
import com.app.baseprojectamanattri.domain.post.models.PostModel
import com.app.baseprojectamanattri.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SampleActivityViewModel @ViewModelInject constructor(private val postUserCase: PostUserCase) :
    BaseViewModel() {

    private lateinit var postsArray: List<PostModel>
    val posts = MutableLiveData<ApiResponse<List<PostModel>>>()

    fun fetchPost() {
        posts.postValue(ApiResponse.loading(null))
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                postsArray = postUserCase.getPosts()!!
            }
            if (!(postsArray?.isEmpty())) {
                posts.postValue(ApiResponse.success(postsArray))
            } else {
                posts.postValue(ApiResponse.error("", null))
            }
        }
    }

    fun fetchPostByRx() {

    }


}