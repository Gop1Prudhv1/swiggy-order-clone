package com.gopi.mytest.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.gopi.mytest.myapplication.databinding.AadlnItemBinding
import com.gopi.mytest.myapplication.databinding.AdapterItemBinding
import com.gopi.mytest.myapplication.models.AddOn
import com.gopi.mytest.myapplication.models.VEG


fun String.addRupeeSymbol(): String {
    return "₹$this"
}

class ItemAdapter(
    private val context: Context,
    private val items: List<AddOn>,
    val onButtonClicked: (item: AddOn, price: Int, view: LinearLayout, btn: RadioButton) -> Unit
    ) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    inner class ViewHolder(binding: AdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val titleTxt = binding.titleText
        val containerLl = binding.itemsLl
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item =
            AdapterItemBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTxt.text = items[position].name
        items[position].items?.forEach {
            val subItem = AadlnItemBinding.inflate(LayoutInflater.from(context))
            subItem.typeImg.setImageResource(if (it.type == VEG) R.drawable.veg else R.drawable.non_veg)
            subItem.radioBtn.text = it.name
            subItem.price.text = it.price.toString().addRupeeSymbol()
            // setting an initial tag to revive the information about the number of clicks on a button
            // will play around with this in the itemFragment to identify the click to make the btn
            // either checked or unchecked
            subItem.radioBtn.tag = 1
            subItem.radioBtn.setOnClickListener { view ->
                onButtonClicked(items[position], it.price, holder.containerLl, view as RadioButton)
            }
            holder.containerLl.addView(subItem.root)
        }
    }

    override fun getItemCount(): Int {
        return items.size ?: 0
    }

}
