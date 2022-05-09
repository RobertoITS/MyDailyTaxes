package com.hvdevs.mydailytaxes.mvvm.domain

import com.hvdevs.mydailytaxes.constructor.Taxes
import com.hvdevs.mydailytaxes.mvvm.data.network.TaxesRepoInterface
import com.hvdevs.mydailytaxes.resource.Resource

class TaxesUseCaseImplement(private val repo: TaxesRepoInterface): TaxesUseCaseInterface {
    override suspend fun getList(): Resource<ArrayList<Taxes>> = repo.getTaxesRepo()
}