package com.hvdevs.mydailytaxes.mvvm.domain

import com.hvdevs.mydailytaxes.constructor.Taxes
import com.hvdevs.mydailytaxes.resource.Resource

interface TaxesUseCaseInterface {
    suspend fun getList(): Resource<ArrayList<Taxes>>
}