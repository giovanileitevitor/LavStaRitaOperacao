package br.lavstaritaoperacao.ui.operation.home_operation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.lavstaritaoperacao.R
import br.lavstaritaoperacao.domain.model.Service
import br.lavstaritaoperacao.domain.model.StatusService

class ServicesAdapter (
    private val context: Context,
    private val data: List<Service>,
    private val itemListener: (Service) -> Unit,
    private val itemLongListener: (Service) -> Unit
) : RecyclerView.Adapter<ServicesAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val client: TextView = view.findViewById(R.id.txtClientName)
        val dateIn: TextView = view.findViewById(R.id.txtData)
        val qtd: TextView = view.findViewById(R.id.qtdItems)
        val price: TextView = view.findViewById(R.id.txtPrice)
        val containerItem: ConstraintLayout = view.findViewById(R.id.containerItem)
        val statusContainer: View = view.findViewById(R.id.statusBlock)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.client.text = "Cliente: " + item.clientName
        holder.dateIn.text = "Entrada: " + item.dataIn
        holder.qtd.text = item.qtdItems.toString() + " itens"
        holder.price.text = "Preço: " + item.price.toString()
        holder.containerItem.bringToFront()
        item.statusService.let {
            if(it == StatusService.DONE || it == StatusService.OTHER){
                holder.statusContainer.setBackgroundDrawable(getDrawable(context, R.drawable.status_corner_complete))
            }else{
                holder.statusContainer.setBackgroundDrawable(getDrawable(context, R.drawable.status_corner_pending))
            }
        }


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