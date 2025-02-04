package br.lavstaritaoperacao.ui.add_service

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.lavstaritaoperacao.R
import br.lavstaritaoperacao.domain.model.Item

class ItemsAdapter (
    private val data: List<Item>,
    private val itemListener: (Item) -> Unit
) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.txtItemName)
        val qtd: TextView = view.findViewById(R.id.qtdItem)
        val containerItem: ConstraintLayout = view.findViewById(R.id.containerItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.itemName.text = item.name
        holder.qtd.text = item.qtd.toString() + " pçs"

        holder.containerItem.setOnClickListener {
            itemListener(item)
        }
    }

    override fun getItemCount(): Int = data.size

}