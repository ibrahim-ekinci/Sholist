package com.gloorystudio.sholist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.databinding.ItemIconBinding
import com.gloorystudio.sholist.model.User


class IconAdapter(private val iconList:ArrayList<Int>):RecyclerView.Adapter<IconAdapter.IconViewHolder>() {

    private var actionFragmentList: ((Int, ImageView)->Unit)?= null

    fun onClickIV(actionFragmentList:(Int, ImageView)->Unit){
        this.actionFragmentList=actionFragmentList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemIconBinding>(inflater, R.layout.item_icon,parent,false)
        return IconViewHolder(view)
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int)
    =holder.bind(iconList[position])

    override fun getItemCount(): Int=iconList.size


    inner class IconViewHolder(var binding: ItemIconBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.ivIcon.setOnClickListener {
                actionFragmentList?.invoke(iconList[adapterPosition],binding.ivIcon)
            }
        }

        fun bind(icon:Int){
            binding.icon=icon
        }



    }

}