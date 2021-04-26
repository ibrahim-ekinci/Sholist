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
import com.gloorystudio.sholist.adapter.InvitationAdapter
import com.gloorystudio.sholist.adapter.ShoppingCardAdapter
import com.gloorystudio.sholist.databinding.DialogInvitationBinding
import com.gloorystudio.sholist.databinding.DialogNewlistBinding
import com.gloorystudio.sholist.databinding.FragmentMainBinding
import com.gloorystudio.sholist.model.Invitation
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


        requireActivity().window.statusBarColor=ContextCompat.getColor(requireContext(),R.color.green_dark)



        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_item_info->{

                }
                R.id.menu_item_settings->{
                    MainFragmentDirections.actionMainFragmentToSettingsFragment().Go(binding.topAppBar)
                }
                R.id.menu_item_invitation->{
                    ShowInvitations()
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

        userList.add(User("1","asd@asd.com.tr",true,"Halil İbrahim","Ekinci","ibrahim","1",1))
        userList.add(User("2","asd@asd.com.tr",true,"Yunus Emre","Bulut","yunusemre","2",1))
        userList.add(User("3","asd@asd.com.tr",true,"Hilal","Tokgöz","hilal","3",2))
        userList.add(User("4","asd@asd.com.tr",true,"Recep","Yeşilkaya","recep","4",2))

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

    private fun ShowInvitations() {
        var dialog =Dialog(requireContext())
        val dialogBinding = DialogInvitationBinding.inflate(LayoutInflater.from(requireContext()))

        //TODO: DAVET LİSTELERİ ÇEKİLECEK.
        var invitationList: ArrayList<Invitation> = ArrayList<Invitation>()
        invitationList.add(Invitation("1","Alınacaklar","İbrahim","26.25.2020"))
        invitationList.add(Invitation("2","Bim","Yunus Emre","26.25.2020"))
        invitationList.add(Invitation("3","A-101","Recep","26.25.2020"))
        invitationList.add(Invitation("4","Dükkan","Hilal","26.25.2020"))

        val invitationAdapter=InvitationAdapter(invitationList)
        dialogBinding.rvInvitation.layoutManager=LinearLayoutManager(requireContext())
        dialogBinding.rvInvitation.adapter=invitationAdapter

        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

    }

    private fun observeLiveData() {
        viewModel.ShoppingCards.observe(viewLifecycleOwner,{cards->
            cards?.let {
                shoppingCardAdapter.updateShopingCard(it)
            }
        })

    }

}