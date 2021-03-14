package com.gloorystudio.sholist.adapter

import android.graphics.Paint
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gloorystudio.sholist.Go
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.databinding.ItemShoppingcardBinding
import com.gloorystudio.sholist.model.ShoppingCard
import com.gloorystudio.sholist.view.main.MainFragmentDirections



class ShoppingCardAdapter(private val ShoppingCardList :ArrayList<ShoppingCard>):RecyclerView.Adapter<ShoppingCardAdapter.ShoppingCardViewHolder>(){

    private var actionFragmentList: ((ShoppingCard)->Unit)?= null

    fun onClickCard(actionFragmentList:(ShoppingCard)->Unit){
        this.actionFragmentList=actionFragmentList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemShoppingcardBinding>(inflater,R.layout.item_shoppingcard,parent,false)
        return ShoppingCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingCardViewHolder, position: Int) =
        holder.bind(ShoppingCardList[position],actionFragmentList)


    override fun getItemCount(): Int {
       return ShoppingCardList.size
    }

    fun updateShopingCard(newVariableList: List<ShoppingCard>){
        ShoppingCardList.clear()
        ShoppingCardList.addAll(newVariableList)
        notifyDataSetChanged()
    }

   inner class ShoppingCardViewHolder(var binding: ItemShoppingcardBinding) :RecyclerView.ViewHolder(binding.root) {

        init{
            binding.clCard.setOnClickListener {
                actionFragmentList?.invoke(ShoppingCardList[adapterPosition])
            }
        }

        fun bind(shoppingCard:ShoppingCard,actionFragmentList:((ShoppingCard)->Unit)?){
            binding.shoppingCard= shoppingCard
            when(shoppingCard.color){
                1 ->  binding.clCard.setBackgroundColor(ContextCompat.getColor(binding.clCard.context,R.color.card_bg1))
                2 -> binding.clCard.setBackgroundColor(ContextCompat.getColor(binding.clCard.context,R.color.card_bg2))
                3 -> binding.clCard.setBackgroundColor(ContextCompat.getColor(binding.clCard.context,R.color.card_bg3))
                4 ->{
                    binding.clCard.setBackgroundColor(ContextCompat.getColor(binding.clCard.context,R.color.card_bg4))
                    binding.tvListName.apply { paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG }
                }
            }
        }
    }
}