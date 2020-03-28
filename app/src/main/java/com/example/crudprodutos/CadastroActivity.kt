package com.example.crudprodutos

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore

class CadastroActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
    }

    fun cadastrar(view: View) {
        val txtNome = findViewById<EditText>(R.id.inputNome)
        val txtValor = findViewById<EditText>(R.id.inputValor)

        val nome = txtNome.text.toString()
        val preco = txtValor.text.toString()

        val produto = hashMapOf(
            "nome" to nome,
            "preco" to preco.toDouble()
        )

        db.collection("produtos")
            .add(produto)
            .addOnSuccessListener { documentReference ->
                Log.d("Novo Produto", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Novo Produto Errado", "Error adding document", e)
            }

        val it = Intent().apply {
        }

        setResult(Activity.RESULT_OK, it)
        finish()
    }
}
