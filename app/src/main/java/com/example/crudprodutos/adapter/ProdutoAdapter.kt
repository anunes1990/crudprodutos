package com.example.crudprodutos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.crudprodutos.model.Produto;
import androidx.recyclerview.widget.RecyclerView;
import com.example.crudprodutos.R;

class ProdutoAdapter(private var listaProdutos: ArrayList<Produto>) :
    RecyclerView.Adapter<ProdutoAdapter.ProdutosHolder>() {

    class ProdutosHolder : RecyclerView.ViewHolder {
        var txtProduto: TextView
        var txtPreco: TextView

        constructor(view: View) : super(view) {
            txtProduto = view.findViewById(R.id.txtProduto)
            txtPreco = view.findViewById(R.id.txtPreco)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProdutoAdapter.ProdutosHolder {
        val view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.produto, parent, false) as View
        return ProdutosHolder(view)
    }

    override fun onBindViewHolder(holder: ProdutosHolder, position: Int) {
        holder.txtProduto.text = listaProdutos.get(position).nome
        holder.txtPreco.text = listaProdutos.get(position).preco.toString()
    }

    override fun getItemCount() = listaProdutos.size

}