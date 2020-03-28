package com.example.crudprodutos

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudprodutos.adapter.ProdutoAdapter
import com.example.crudprodutos.model.Produto
import com.google.firebase.firestore.FirebaseFirestore

class ProdutosActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance();
    private var listaProdutos: ArrayList<Produto> = ArrayList()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produtos)
        getProdutos()
    }

    fun getProdutos() {
        db.collection("produtos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("FIREBASE >>>>> ", "${document.id} => ${document.data}")
                    listaProdutos.add(
                        Produto(
                            id = document.id,
                            nome = document.data["nome"].toString(),
                            preco = document.data["preco"].toString().toDouble()
                        )
                    )
                }
                viewManager = LinearLayoutManager(this)
                viewAdapter = ProdutoAdapter(listaProdutos)
                recyclerView = findViewById<RecyclerView>(R.id.recyclerViewProdutos).apply {
                    setHasFixedSize(true)
                    layoutManager = viewManager
                    adapter = viewAdapter
                }
            }
            .addOnFailureListener { exception ->
                Log.w("FIREBASE >>>>> ", "Error getting documents.", exception)
            }
    }

    fun addProduto(view: View?) {
        val it = Intent(this, CadastroActivity::class.java).apply {
        }
        startActivityForResult(it, 1)
    }

    fun editProduto(view: View) {
        var intent = Intent(this, EditProdutoActivity::class.java).apply {
            val nome = view.findViewById<TextView>(R.id.txtProduto).text.toString()
            val idProduto = listaProdutos.find { c -> c.nome == nome }?.id
            putExtra("idProduto", idProduto.toString())
        }
        startActivityForResult(intent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                listaProdutos = ArrayList()
                getProdutos()
                Toast.makeText(this, "Produto registrado com sucesso!", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                listaProdutos = ArrayList()
                getProdutos()
                Toast.makeText(this, "Produto editado com sucesso!", Toast.LENGTH_SHORT).show()
            } else if (resultCode == 666) {
                listaProdutos = ArrayList()
                getProdutos()
                Toast.makeText(this, "Produto removido com sucesso!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
