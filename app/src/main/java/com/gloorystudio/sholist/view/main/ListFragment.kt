package com.gloorystudio.sholist.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.databinding.FragmentListBinding
import com.gloorystudio.sholist.model.ShoppingCard


class ListFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private lateinit var  binding : FragmentListBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_list,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var shoppingCard:ShoppingCard?=null
        arguments?.let {
            val myArgs =ListFragmentArgs.fromBundle(it)
            shoppingCard = myArgs.ShoppingCard
        }
        shoppingCard?.let {  shoppingCardData->
        val color = shoppingCardData.color
            when(color){
                1-> binding.clList.setBackgroundColor(ContextCompat.getColor(binding.clList.context,R.color.card_bg1))
                2-> binding.clList.setBackgroundColor(ContextCompat.getColor(binding.clList.context,R.color.card_bg2))
                3-> binding.clList.setBackgroundColor(ContextCompat.getColor(binding.clList.context,R.color.card_bg3))
            }



        }

    }


}