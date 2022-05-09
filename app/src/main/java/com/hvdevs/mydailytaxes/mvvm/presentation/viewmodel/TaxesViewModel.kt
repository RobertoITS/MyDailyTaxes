package com.hvdevs.mydailytaxes.mvvm.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.hvdevs.mydailytaxes.mvvm.domain.TaxesUseCaseInterface
import com.hvdevs.mydailytaxes.resource.Resource
import kotlinx.coroutines.Dispatchers

class TaxesViewModel(useCase: TaxesUseCaseInterface): ViewModel() {
    val fetchTaxesData = liveData(Dispatchers.IO){
        emit(Resource.Loading())

        try {
            val list = useCase.getList()
            emit(list)
        } catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}