package com.app.baseprojectamanattri.presentation.common.base

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.app.baseprojectamanattri.R
import com.app.baseprojectamanattri.network.ErrorCodes
import com.app.baseprojectamanattri.network.NetworkError
import com.app.baseprojectamanattri.presentation.MyApplication
import com.app.baseprojectamanattri.presentation.common.compoundviews.ErrorView
import com.app.baseprojectamanattri.presentation.common.hideKeyboard
import com.app.baseprojectamanattri.presentation.common.utilities.AlertManager

abstract open class BaseActivity<Binding : ViewBinding> : AppCompatActivity() {

    open val onRetry:(()->Unit)?=null
    lateinit var binding: Binding
    val app: MyApplication
    get() = application as MyApplication

    fun setContentView(binding: Binding) {
        this.binding = binding
        setContentView(binding.root)
    }

    fun showToast(msg: String? = "Something went wrong !!") {
        Toast.makeText(this, msg ?: "Showed null value !!", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }

    fun onLoading(show: Boolean) {
        var progressBar:View=findViewById(R.id.progress_bar)
        progressBar?.visibility=if(show)View.VISIBLE else View.GONE
        var errorView:ErrorView=findViewById(R.id.errorView)
        errorView?.visibility=if(show)View.GONE else View.VISIBLE
        errorView?.onRetry=onRetry
    }

    fun onError(error: Throwable,showErrorView:Boolean){
        if (error is NetworkError) {
            //show if you have any error view
            if (showErrorView) {
                val errorView: View? = findViewById(R.id.errorView)
                errorView?.visibility = View.VISIBLE
            }
            when (error.errorCode) {
                ErrorCodes.SESSION_EXPIRED -> {
                    showToast(getString(R.string.session_expired))
                    app.onLogout()
                }
                else -> AlertManager.showNegativeAlert(
                    this,
                    error.message,
                    getString(R.string.alert)
                )
            }
        } else {
            AlertManager.showNegativeAlert(
                this,
                getString(R.string.please_try_again),
                getString(R.string.alert)
            )
        }
    }

}