package com.hvdevs.mydailytaxes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hvdevs.mydailytaxes.R
import com.hvdevs.mydailytaxes.constructor.Taxes

class TaxesAdapter(private val list: ArrayList<Taxes>): RecyclerView.Adapter<TaxesAdapter.TaxesViewHolder>() {
    /**Creamos la funcion del clickListener*/
    private lateinit var mListener: OnItemClickListener
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    class TaxesViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tax)
        val price: TextView = itemView.findViewById(R.id.price)

        /**Inicializamos el click y el check*/
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaxesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.taxes_card, parent, false)
        return TaxesViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: TaxesViewHolder, position: Int) {
        val currentItem = list[position]
        holder.name.text = currentItem.name
        holder.price.text = currentItem.price.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}