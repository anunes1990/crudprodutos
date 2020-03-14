package com.example.crudprodutos.services

import com.example.crudprodutos.model.Produto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ProdutoService {
    @GET("produtos")
    fun getAllProdutos(): Call<List<Produto>>

    @GET("produtos/{id}")
    fun getProduto(@Path("id") id: String): Call<Produto>

    @POST("produtos/")
    fun postProduto(@Body produto: Produto): Call<ResponseBody>

    @PUT("produtos/{id}")
    fun putProduto(@Path("id") id: String, @Body produto: Produto): Call<ResponseBody>

    @DELETE("produtos/{id}")
    fun deleteProduto(@Path("id") id: String): Call<ResponseBody>
}
