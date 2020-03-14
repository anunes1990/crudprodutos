package com.example.crudprodutos

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.example.crudprodutos.model.Produto
import com.example.crudprodutos.services.ProdutoService
import com.example.crudprodutos.services.RetrofitInitializer
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProdutoActivity : AppCompatActivity() {
    val service: ProdutoService = RetrofitInitializer().getRetrofitCofig()
    private var idProduto: String? = ""
    private var produto: Produto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_produto)
        idProduto = intent.getStringExtra("idProduto")
        val id = idProduto.toString()
        service.getProduto(id).enqueue(object : Callback<Produto?> {
            override fun onFailure(call: Call<Produto?>, t: Throwable) {
                Log.e("Erro", t.message)
            }

            override fun onResponse(call: Call<Produto?>, response: Response<Produto?>) {
                if (response.isSuccessful) {
                    produto = response.body()
                    val nome = findViewById<EditText>(R.id.inputNome)
                    nome.setText(produto?.nome)
                    val valor = findViewById<EditText>(R.id.inputValor)
                    valor.setText(produto?.preco?.toString())
                } else {
                    Log.e("Erro", response.code().toString())
                }
            }
        })
    }

    fun salvar(view: View?) {
        val txtNome = findViewById<EditText>(R.id.inputNome).text.toString()
        produto?.nome = txtNome
        val txtValor = findViewById<EditText>(R.id.inputValor).text.toString()
        produto?.preco = txtValor.toDouble()
        service.putProduto(idProduto.toString(), produto as Produto)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("Erro", t.message)
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                    } else {
                        Log.e("Erro", response.code().toString())
                    }
                }
            })
        setResult(Activity.RESULT_OK)
        finish()
    }

    fun excluir(view: View?) {
        service.deleteProduto(idProduto.toString()).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Erro", t.message)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                } else {
                    Log.e("Erro", response.code().toString())
                }
            }
        })
        setResult(666)
        finish()
    }

    fun voltar(view: View?) {
        finish()
    }
}
