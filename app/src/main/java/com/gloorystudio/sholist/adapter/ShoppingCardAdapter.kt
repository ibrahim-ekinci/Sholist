package com.gloorystudio.sholist.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.databinding.ItemShoppingcardBinding
import com.gloorystudio.sholist.model.ShoppingCard

class ShoppingCardAdapter(private val ShoppingCardList :ArrayList<ShoppingCard>):RecyclerView.Adapter<ShoppingCardAdapter.ShoppingCardViewHolder>(){

    
    class ShoppingCardViewHolder(var binding: ItemShoppingcardBinding) :RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemShoppingcardBinding>(inflater,R.layout.item_shoppingcard,parent,false)
        return ShoppingCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingCardViewHolder, position: Int) {
        holder.binding.shoppingCard= ShoppingCardList[position]
        when(ShoppingCardList[position].color){
            1 ->  holder.binding.clCard.setBackgroundColor(ContextCompat.getColor(holder.binding.clCard.context,R.color.card_bg1))
            2 -> holder.binding.clCard.setBackgroundColor(ContextCompat.getColor(holder.binding.clCard.context,R.color.card_bg2))
            3 -> holder.binding.clCard.setBackgroundColor(ContextCompat.getColor(holder.binding.clCard.context,R.color.card_bg3))
            4 ->{
                holder.binding.clCard.setBackgroundColor(ContextCompat.getColor(holder.binding.clCard.context,R.color.card_bg4))
                holder.binding.tvListName.apply { paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG }
            }
        }
        holder.binding.clCard.setOnClickListener {
            println("test ")
        }
    }

    override fun getItemCount(): Int {
       return ShoppingCardList.size
    }

    fun updateShopingCard(newVariableList: List<ShoppingCard>){
        ShoppingCardList.clear()
        ShoppingCardList.addAll(newVariableList)
        notifyDataSetChanged()
    }
}