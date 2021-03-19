package com.gloorystudio.sholist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.databinding.ItemShoppingitemBinding
import com.gloorystudio.sholist.model.Item


class ShoppingListAdapter(private val itemList :ArrayList<Item>):RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>() {

    private var actionFragmentList: ((Item,CheckBox)->Unit)?= null
    private var actionFragmentImageView: ((Item,ImageView)->Unit)?= null

    fun onClickCB(actionFragmentList:(Item,CheckBox)->Unit){
        this.actionFragmentList=actionFragmentList
    }
    fun onClickIV(actionFragmentImageView:(Item,ImageView)->Unit){
        this.actionFragmentImageView=actionFragmentImageView
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemShoppingitemBinding>(inflater, R.layout.item_shoppingitem,parent,false)
        return ShoppingListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) =holder.bind(itemList[position],actionFragmentList,actionFragmentImageView)


    override fun getItemCount(): Int {
       return itemList.size
    }

    fun updateItem(it: List<Item>) {
        itemList.clear()
        itemList.addAll(it)
        notifyDataSetChanged()
    }


    inner class ShoppingListViewHolder (var binding: ItemShoppingitemBinding):RecyclerView.ViewHolder(binding.root){

        init {
            binding.cbItem.setOnClickListener {
                actionFragmentList?.invoke(itemList[adapterPosition],binding.cbItem)
            }

            binding.cardItem.setOnClickListener {
                binding.cbItem.performClick()
            }
            binding.ivIcon.setOnClickListener {
                actionFragmentImageView?.invoke(itemList[adapterPosition],binding.ivIcon)
            }


        }
        fun bind(item: Item, actionFragmentList: ((Item, CheckBox) -> Unit)?, actionFragmentImageView: ((Item, ImageView) -> Unit)?){
            binding.item=item


        }
    }
}