package com.gloorystudio.sholist.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.databinding.ItemShoppingitemBinding
import com.gloorystudio.sholist.data.db.entity.Item


class ShoppingListAdapter(private val itemList :ArrayList<Item>):RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>() {

    private var actionFragmentList: ((Int,Item, CheckBox)->Unit)?= null
    private var actionFragmentImageView: ((Item, ImageView)->Unit)?= null

    fun onClickCB(actionFragmentList:(Int,Item, CheckBox)->Unit){
        this.actionFragmentList=actionFragmentList
    }
    fun onClickIV(actionFragmentImageView:(Item, ImageView)->Unit){
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

    fun updateItemList(it: List<Item>) {
        itemList.clear()
        itemList.addAll(it)
        itemList.sortBy { it->it.checked}
        notifyDataSetChanged()
    }

    fun updateItem(position: Int,item: Item,isChecked:Boolean,checkBox:CheckBox) {
        val newItem = Item(item.id,item.shoppingListId,item.name,item.count,isChecked,item.img)
        itemList.removeAt(position)
        notifyItemRemoved(position)
        if (isChecked){//alındı olarak işaretlendi
            itemList.add(newItem)
            notifyItemInserted(itemList.size)
        }//Alınma işlemi geri alındı
        else {
            itemList.reverse()
            itemList.add(newItem)
            itemList.reverse()
            notifyItemInserted(0)
        }
        checkBox.isEnabled=true
    }


    inner class ShoppingListViewHolder (var binding: ItemShoppingitemBinding):RecyclerView.ViewHolder(binding.root){

        init {
            binding.cbItem.setOnClickListener {
                actionFragmentList?.invoke(adapterPosition,itemList[adapterPosition],binding.cbItem)
            }

            binding.cardItem.setOnClickListener {
                binding.cbItem.performClick()
            }
            binding.ivIcon.setOnClickListener {
                actionFragmentImageView?.invoke(itemList[adapterPosition],binding.ivIcon)
            }
        }
        fun bind(item: Item, actionFragmentList: ((Int,Item, CheckBox) -> Unit)?, actionFragmentImageView: ((Item, ImageView) -> Unit)?){
            binding.item=item
            if (itemList[adapterPosition].checked){
                binding.cbItem.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }else{
                binding.cbItem.paintFlags= Paint.ANTI_ALIAS_FLAG
            }
        }
    }
}