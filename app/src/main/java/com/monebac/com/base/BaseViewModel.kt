package com.monebac.com.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monebac.com.App
import com.monebac.com.network.ApiException
import com.monebac.com.utils.showToast
import com.monebac.com.wkyk.ui.repository.ResultRepository
import kotlinx.coroutines.*
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

typealias Block<T> = suspend (CoroutineScope) -> T
typealias Error = suspend (Exception) -> Unit
typealias Cancel = suspend (Exception) -> Unit

open class BaseViewModel : ViewModel() {
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    val resultRepository by lazy {
        ResultRepository()
    }

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @param error 错误时执行
     * @param cancel 取消时只需
     * @param showErrorToast 是否弹出错误吐司
     * @return Job
     */
    protected fun launch(
            block: Block<Unit>,
            error: Error? = null,
            cancel: Cancel? = null,
            showErrorToast: Boolean = true
    ): Job {
        return viewModelScope.launch {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        onError(e, showErrorToast)
                        error?.invoke(e)
                    }
                }
            }
        }
    }

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @return Deferred<T>
     */
    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async { block.invoke(this) }
    }

    /**
     * 取消协程
     * @param job 协程job
     */
    protected fun cancelJob(job: Job?) {
        if (job != null && job.isActive && !job.isCompleted && !job.isCancelled) {
            job.cancel()
        }
    }


    /**
     * 统一处理错误
     * @param e 异常
     * @param showErrorToast 是否显示错误吐司
     */
    private fun onError(e: Exception, showErrorToast: Boolean) {
        when (e) {
            is ApiException -> {
                when (e.code) {
                    // 其他api错误
                    -1 -> if (showErrorToast) App.instance.showToast(e.message)
                    // 其他错误
                    else -> if (showErrorToast) App.instance.showToast(e.message)
                }
            }
            // 网络请求失败
            is ConnectException, is SocketTimeoutException, is UnknownHostException, is HttpException ->
                if (showErrorToast) App.instance.showToast("网络请求失败")
            // 数据解析错误
            is JSONException ->
                if (showErrorToast) App.instance.showToast("数据解析错误")
            // 其他错误
            else ->
                if (showErrorToast) App.instance.showToast(e.message ?: return)
        }
    }
}