package com.gloorystudio.sholist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.databinding.ItemInvitationBinding
import com.gloorystudio.sholist.model.Invitation

class InvitationAdapter(private val invitationList :ArrayList<Invitation>): RecyclerView.Adapter<InvitationAdapter.InvitationViewHolder>() {

    private var actionDialogListConfirm:((Invitation,ImageView)->Unit)?=null
    private var actionDialogListCancel:((Invitation,ImageView)->Unit)?=null

    fun onClickIvConfirm(actionDialogListConfirm:((Invitation,ImageView)->Unit)){
        this.actionDialogListConfirm=actionDialogListConfirm
    }

    fun onClickIvCancel(actionDialogListCancel:((Invitation,ImageView)->Unit)){
        this.actionDialogListCancel=actionDialogListCancel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvitationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =DataBindingUtil.inflate<ItemInvitationBinding>(inflater, R.layout.item_invitation,parent,false)
        return InvitationViewHolder(view)
    }

    override fun onBindViewHolder(holder: InvitationViewHolder, position: Int)
    =holder.bind(invitationList[position])

    override fun getItemCount(): Int =invitationList.size

    fun updateInvitation(it:List<Invitation>){
        invitationList.clear()
        invitationList.addAll(it)
        notifyDataSetChanged()
    }



    inner class InvitationViewHolder(var binding:ItemInvitationBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.ivConfirm.setOnClickListener {
                actionDialogListConfirm?.invoke(invitationList[adapterPosition],binding.ivConfirm)
            }
            binding.ivCancel.setOnClickListener {
                actionDialogListCancel?.invoke(invitationList[adapterPosition],binding.ivCancel)
            }
        }


        fun bind(invitation: Invitation) {
            binding.invitation=invitation
        }


    }

}