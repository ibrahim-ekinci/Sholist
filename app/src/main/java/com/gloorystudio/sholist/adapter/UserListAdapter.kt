package com.gloorystudio.sholist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.databinding.ItemUserBinding
import com.gloorystudio.sholist.getUsername


import com.gloorystudio.sholist.model.User

class UserListAdapter(private val userList :ArrayList<User> ):RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    private var actionFragmentList: ((User, ImageView)->Unit)?= null

    fun onClickCB(actionFragmentList:(User, ImageView)->Unit){
        this.actionFragmentList=actionFragmentList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemUserBinding>(inflater, R.layout.item_user,parent,false)
        return UserListViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int)
    =holder.bind(userList[position])

    override fun getItemCount(): Int= userList.size


    fun updateUser(it : List<User>){
        userList.clear()
        userList.addAll(it)
        notifyDataSetChanged()
    }


    inner class UserListViewHolder(var binding: ItemUserBinding):RecyclerView.ViewHolder(binding.root){

        init {
                binding.ivIcon.setOnClickListener {
                    actionFragmentList?.invoke(userList[adapterPosition],binding.ivIcon)
                }
        }

        fun bind(user:User){
            binding.user= user
            val context = binding.cardItem.context
            when(user.status){
                1->{
                    binding.ivIcon.setImageResource(R.drawable.ic_baseline_person_remove_24)
                    binding.ivIcon.setColorFilter(ContextCompat.getColor(context,R.color.red))
                }
                2 -> {

                    binding.ivIcon.setImageResource(R.drawable.ic_baseline_replay_24)
                    binding.ivIcon.setColorFilter(ContextCompat.getColor(context, R.color.sho_gray))
                }

            }
        }
    }


}