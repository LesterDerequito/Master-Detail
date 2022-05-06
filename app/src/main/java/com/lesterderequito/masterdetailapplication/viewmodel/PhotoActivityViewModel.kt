package com.lesterderequito.masterdetailapplication.viewmodel

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lesterderequito.masterdetailapplication.network.PhotoInfo
import com.lesterderequito.masterdetailapplication.network.RetroInstance
import com.lesterderequito.masterdetailapplication.network.RetroService
import com.lesterderequito.masterdetailapplication.paging.PhotoPagingSource
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class PhotoActivityViewModel: ViewModel() {

    private var retroService: RetroService = RetroInstance.getRetroInstance().create(RetroService::class.java)

    fun getListData(): Flow<PagingData<PhotoInfo>> {
        return Pager(
            config = PagingConfig(pageSize = 1, prefetchDistance = 2),
            pagingSourceFactory = {PhotoPagingSource(retroService)}
        ).flow
    }
}
