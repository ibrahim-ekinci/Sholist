package com.gloorystudio.sholist.viewmodel.main

import androidx.lifecycle.ViewModel
import com.gloorystudio.sholist.data.api.service.SholistApiService
import io.reactivex.disposables.CompositeDisposable

class SettingsViewModel:ViewModel() {
    private val apiService = SholistApiService()
    private val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}