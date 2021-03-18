package com.gloorystudio.sholist.view.main

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gloorystudio.sholist.Go
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.adapter.ShoppingListAdapter
import com.gloorystudio.sholist.databinding.DialogAddItemBinding
import com.gloorystudio.sholist.databinding.FragmentListBinding
import com.gloorystudio.sholist.model.Item
import com.gloorystudio.sholist.model.ShoppingCard
import com.gloorystudio.sholist.viewmodel.ListViewModel


class ListFragment : Fragment() {

    private lateinit var viewModel :ListViewModel
    private val shoppinglistAdapter=ShoppingListAdapter(arrayListOf())
    var itemList: ArrayList<Item> = ArrayList<Item>()


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
        var shoppingCard: ShoppingCard?=null
        arguments?.let {
            val myArgs =ListFragmentArgs.fromBundle(it)
            shoppingCard = myArgs.ShoppingCard
            binding.shoppingCard =shoppingCard
        }
        shoppingCard?.let {  shoppingCardData->
        val color = shoppingCardData.color
            when(color){ //Set Bg Color
                1-> {
                    val color=ContextCompat.getColor(binding.clList.context,R.color.card_bg1)
                    binding.clList.setBackgroundColor(color)
                    requireActivity().window.statusBarColor=color
                }
                2-> {
                    val color=ContextCompat.getColor(binding.clList.context,R.color.card_bg2)
                    binding.clList.setBackgroundColor(color)
                    requireActivity().window.statusBarColor=color
                }
                3->{
                    val color=ContextCompat.getColor(binding.clList.context,R.color.card_bg3)
                    binding.clList.setBackgroundColor(color)
                    requireActivity().window.statusBarColor=color
                }
            }
            binding.floatingActionButton.setOnClickListener {
                //Todo: show add item dialog

                var dialog = Dialog(requireContext())
                val dialogBinding = DialogAddItemBinding.inflate(LayoutInflater.from(requireContext()))
                dialog.setContentView(dialogBinding.root)
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                val items = listOf("Material", "Design", "Components", "Android", "Design", "Components", "Android", "Design", "Components", "Android", "Design", "Components", "Android", "Design", "Components", "Android", "Design", "Components", "Android", "Design", "Components", "Android", "Design", "Components", "Android", "Design", "Components", "Android")
                val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
                (dialogBinding.textInputLayoutItem.editText as? AutoCompleteTextView)?.setAdapter(adapter)


                dialog.show()
            }
            binding.ivPeoples.setOnClickListener {
                ListFragmentDirections.actionListFragmentToUserListFragment(shoppingCardData).Go(it)
            }




            viewModel = ViewModelProvider(this).get(ListViewModel :: class.java)
            viewModel.refreshItemListData(shoppingCardData.itemList)
            binding.rvItemList.layoutManager=LinearLayoutManager(requireContext())
            binding.rvItemList.adapter=shoppinglistAdapter
            observeLiveData()

            shoppinglistAdapter.onClickCB {item,checkBox->
                if (checkBox.isChecked){

                }else{

                }
            }


        }

    }

    private fun observeLiveData(){
        viewModel.items.observe(viewLifecycleOwner,{items->
            items?.let {
                shoppinglistAdapter.updateItem(it)
            }
        }
        )
    }


}