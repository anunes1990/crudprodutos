package com.example.crudprodutos.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    fun getRetrofitCofig(): ProdutoService {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(
            ProdutoService::class.java
        )
    }


}