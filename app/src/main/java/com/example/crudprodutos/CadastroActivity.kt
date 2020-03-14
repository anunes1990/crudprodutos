package com.example.crudprodutos

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.room.Room
//import com.example.crudprodutos.daos.ProdutoDao
//import com.example.crudprodutos.database.AppDatabase
import com.example.crudprodutos.model.Produto
import com.example.crudprodutos.services.ProdutoService
import com.example.crudprodutos.services.RetrofitInitializer
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class CadastroActivity : AppCompatActivity() {

    val service: ProdutoService = RetrofitInitializer().getRetrofitCofig()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
    }

    fun cadastrar(view: View) {
        val txtNome = findViewById<EditText>(R.id.inputNome)
        val txtValor = findViewById<EditText>(R.id.inputValor)

        val nome = txtNome.text.toString()
        val preco = txtValor.text.toString()

        val id = Random.nextInt(0, 999999999);
        val produto = Produto(id, nome, preco.toDouble())

        service.postProduto(produto).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Erro", t.message)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val it = Intent().apply {
                    }
                } else {
                    Log.e("Erro", response.code().toString())
                }
            }
        })

        val it = Intent().apply {
        }

        setResult(Activity.RESULT_OK, it)
        finish()
    }
}
