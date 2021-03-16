package com.gloorystudio.sholist.view.main

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.gloorystudio.sholist.Go
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.adapter.ShoppingCardAdapter
import com.gloorystudio.sholist.databinding.DialogNewlistBinding
import com.gloorystudio.sholist.databinding.FragmentMainBinding
import com.gloorystudio.sholist.model.Item
import com.gloorystudio.sholist.model.ShoppingCard
import com.gloorystudio.sholist.model.User
import com.gloorystudio.sholist.viewmodel.ShoppingCardViewModel


class MainFragment : Fragment() {

    private lateinit var viewModel :ShoppingCardViewModel
    private val shoppingCardAdapter=ShoppingCardAdapter(arrayListOf())
    var sList: ArrayList<ShoppingCard> = ArrayList<ShoppingCard>()
    var itemList: ArrayList<Item> = ArrayList<Item>()
    var userList: ArrayList<User> = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setHasOptionsMenu(true)
    }
    private lateinit var  binding : FragmentMainBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_item_info->{

                }
                R.id.menu_item_settings->{
                    MainFragmentDirections.actionMainFragmentToSettingsFragment().Go(binding.topAppBar)
                }
            }
            true
        }

        shoppingCardAdapter.onClickCard{ shoppingCard->
            MainFragmentDirections.actionMainFragmentToListFragment(shoppingCard).Go(binding.topAppBar)
        }


        binding.floatingActionButton.setOnClickListener {
            var dialog =Dialog(requireContext())
            var color=1
            val dialogBinding = DialogNewlistBinding.inflate(LayoutInflater.from(requireContext()))
            dialogBinding.llBlue.setOnClickListener {
                color=1
                dialogBinding.tvBlue.setTextColor(ContextCompat.getColor(requireContext(),R.color.card_bg1))
                dialogBinding.tvGreen.setTextColor(ContextCompat.getColor(requireContext(),R.color.sho_gray))
                dialogBinding.tvOrange.setTextColor(ContextCompat.getColor(requireContext(),R.color.sho_gray))
            }
            dialogBinding.llGreen.setOnClickListener {
                color=2
                dialogBinding.tvBlue.setTextColor(ContextCompat.getColor(requireContext(),R.color.sho_gray))
                dialogBinding.tvGreen.setTextColor(ContextCompat.getColor(requireContext(),R.color.card_bg2))
                dialogBinding.tvOrange.setTextColor(ContextCompat.getColor(requireContext(),R.color.sho_gray))
            }
            dialogBinding.llOrange.setOnClickListener {
                color=3
                dialogBinding.tvBlue.setTextColor(ContextCompat.getColor(requireContext(),R.color.sho_gray))
                dialogBinding.tvGreen.setTextColor(ContextCompat.getColor(requireContext(),R.color.sho_gray))
                dialogBinding.tvOrange.setTextColor(ContextCompat.getColor(requireContext(),R.color.card_bg3))
            }
            dialog.setContentView(dialogBinding.root)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()
        }

        userList.clear()
        itemList.clear()
        sList.clear()

        userList.add(User("1","Halil İbrahim","Ekinci","ibrahim","1"))
        userList.add(User("2","Yunus Emre","Bulut","yunusemre","2"))
        userList.add(User("3","Hilal","Tokgöz","hilal","3"))
        userList.add(User("4","Recep","Yeşilkaya","recep","4"))

        itemList.add(Item("1","Ekmek",2,1,true,R.drawable.ic_jam))
        itemList.add(Item("2","Elma",3,1,true,R.drawable.ic_jam))
        itemList.add(Item("3","Armut",1,2,true,R.drawable.ic_jam))
        itemList.add(Item("4","Muz",1,2,true,R.drawable.ic_jam))
        itemList.add(Item("5","Kivi",1,3,false,R.drawable.ic_jam))
        itemList.add(Item("6","Cips",1,3,false,R.drawable.ic_jam))
        itemList.add(Item("7","Kraker",1,4,false,R.drawable.ic_jam))
        itemList.add(Item("8","Selpak",1,4,false,R.drawable.ic_jam))
        itemList.add(Item("9","Su",2,5,false,R.drawable.ic_jam))
        itemList.add(Item("10","Kola",2,5,false,R.drawable.ic_jam))



        sList.add(ShoppingCard("1","My Shoping List","1",1,userList,itemList))
        sList.add(ShoppingCard("2","My Shoping List1","1",1,userList,itemList))
        sList.add(ShoppingCard("3","My Shoping List2","1",2,userList,itemList))
        sList.add(ShoppingCard("4","My Shoping List3","1",3,userList,itemList))
        sList.add(ShoppingCard("5","My Shoping List4","1",4,userList,itemList))


        if(sList.isEmpty()) binding.llEmptyList.visibility=View.VISIBLE
        else binding.llEmptyList.visibility=View.GONE

        viewModel = ViewModelProvider(this).get(ShoppingCardViewModel::class.java)
        viewModel.refreshShoppingCardsData(sList)
        binding.rvShopingcards.layoutManager=LinearLayoutManager(requireContext())
        binding.rvShopingcards.adapter=shoppingCardAdapter
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.ShoppingCards.observe(viewLifecycleOwner,{cards->
            cards?.let {
                shoppingCardAdapter.updateShopingCard(it)
            }
        })
    }

}