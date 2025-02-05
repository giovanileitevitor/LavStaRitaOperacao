package br.lavstaritaoperacao.ui.operation.home_operation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.lavstaritaoperacao.R
import br.lavstaritaoperacao.domain.model.Service

class ServicesAdapter (
    private val data: List<Service>,
    private val itemListener: (Service) -> Unit,
    private val itemLongListener: (Service) -> Unit
) : RecyclerView.Adapter<ServicesAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val client: TextView = view.findViewById(R.id.txtClientName)
        val dateIn: TextView = view.findViewById(R.id.txtData)
        val qtd: TextView = view.findViewById(R.id.qtdItems)
        val containerItem: ConstraintLayout = view.findViewById(R.id.containerItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.client.text = "Cliente: " + item.clientName
        holder.dateIn.text = "Entrada:" + item.dataIn
        holder.qtd.text = item.qtdItems.toString() + " itens"

        holder.containerItem.setOnClickListener {
            itemListener.invoke(item)
        }

        holder.containerItem.setOnLongClickListener {
            itemLongListener(item)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int = data.size

}