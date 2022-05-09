package com.hvdevs.mydailytaxes.mvvm.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hvdevs.mydailytaxes.mvvm.domain.TaxesUseCaseInterface

class TaxesViewModelFactory(private val useCase: TaxesUseCaseInterface): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(TaxesUseCaseInterface::class.java).newInstance(useCase)
    }
}