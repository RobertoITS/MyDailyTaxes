package com.hvdevs.mydailytaxes.mvvm.data.network

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hvdevs.mydailytaxes.constructor.Taxes
import com.hvdevs.mydailytaxes.resource.Resource
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

class TaxesRepoImplement: TaxesRepoInterface {
    private var taxesList: ArrayList<Taxes> = arrayListOf()

    override suspend fun getTaxesRepo(): Resource<ArrayList<Taxes>>{

        val dbSnapshot: DataSnapshot? = FirebaseDatabase
            .getInstance().getReference("myTaxes/users/userId/taxes/${LocalDate.now().month}-${LocalDate.now().year}").get().await()
        if (dbSnapshot!!.exists()){
            for (dc in dbSnapshot.children){
                val tax = dc.getValue(Taxes::class.java)
                taxesList.add(tax!!)
            }
        }
        return Resource.Success(taxesList)
    }
}