package com.hvdevs.mydailytaxes.mvvm.data.network

import com.hvdevs.mydailytaxes.constructor.Taxes
import com.hvdevs.mydailytaxes.resource.Resource

interface TaxesRepoInterface {
    suspend fun getTaxesRepo(): Resource<ArrayList<Taxes>>
}