package com.example.crudprodutos

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.example.crudprodutos.services.ProdutoService
import com.example.crudprodutos.services.RetrofitInitializer
import com.google.firebase.firestore.FirebaseFirestore

class EditProdutoActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance();
    private var idProduto: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_produto)
        idProduto = intent.getStringExtra("idProduto")
        val id = idProduto.toString()

        db.collection("produtos")
            .document(id)
            .get()
            .addOnSuccessListener { result ->
                Log.d("FIREBASE >>>>> ", "${result.id} => ${result.data}")
                val nome = findViewById<EditText>(R.id.inputNome)
                nome.setText(result["nome"].toString())
                val valor = findViewById<EditText>(R.id.inputValor)
                valor.setText(result["preco"].toString())
            }
            .addOnFailureListener { exception ->
                Log.w("FIREBASE >>>>> ", "Error getting documents.", exception)
            }
    }

    fun salvar(view: View?) {
        val txtNome = findViewById<EditText>(R.id.inputNome).text.toString()
        val txtValor = findViewById<EditText>(R.id.inputValor).text.toString()

        val produto = hashMapOf(
            "nome" to txtNome,
            "preco" to txtValor.toDouble()
        )

        db.collection("produtos")
            .document(idProduto.toString())
            .update(produto)
            .addOnSuccessListener { documentReference ->
                Log.d("Produto Editado", "DocumentSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w("Ediçã Error", "Error adding document", e)
            }
        setResult(Activity.RESULT_OK)
        finish()
    }

    fun excluir(view: View?) {
        db.collection("produtos")
            .document(idProduto.toString())
            .delete()
            .addOnSuccessListener { documentReference ->
                Log.d("Produto Excluido", "DocumentSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w("Exclusão Error", "Error adding document", e)
            }
        setResult(666)
        finish()
    }

    fun voltar(view: View?) {
        finish()
    }
}
