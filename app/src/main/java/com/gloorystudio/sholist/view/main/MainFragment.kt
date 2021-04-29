package com.gloorystudio.sholist.view.main

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.gloorystudio.sholist.*
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
    private val invitationAdapter=InvitationAdapter(arrayListOf())


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




        





        viewModel = ViewModelProvider(this).get(ShoppingCardViewModel::class.java)
        viewModel.refreshShoppingCardsData()
        binding.rvShopingcards.layoutManager=LinearLayoutManager(requireContext())
        binding.rvShopingcards.adapter=shoppingCardAdapter
        observeShppingCardsLiveData()
    }

    private fun ShowInvitations() {
        var dialog =Dialog(requireContext())
        val dialogBinding = DialogInvitationBinding.inflate(LayoutInflater.from(requireContext()))

        //TODO: DAVET LİSTELERİ ÇEKİLECEK.



        viewModel.refreshInvitationsData()
        dialogBinding.rvInvitation.layoutManager=LinearLayoutManager(requireContext())
        dialogBinding.rvInvitation.adapter=invitationAdapter
        observeInvitationsLiveData(dialogBinding)

        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

    }

    private fun observeShppingCardsLiveData() {
        viewModel.ShoppingCards.observe(viewLifecycleOwner,{cards->
            cards?.let {

                shoppingCardAdapter.updateShopingCard(it)
            }
        })
        viewModel.ShoppingCardsIsEmpty.observe(viewLifecycleOwner,{isEmpty->
            isEmpty?.let {
                if(it) binding.llEmptyList.visibility=View.VISIBLE
                else binding.llEmptyList.visibility=View.GONE
            }
        })
        viewModel.ShoppingCardsError.observe(viewLifecycleOwner, { error ->
            error?.let {
                if (it) {
                    //TODO: Eror yazdırılabilir.
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.ShoppingCardsLoading.observe(viewLifecycleOwner,{loading->
            loading?.let {
                if (loading) LoadingDialogShow(requireContext())
                else LoadingDialogCancel()
            }
        })

    }
    private fun observeInvitationsLiveData(dialogBinding: DialogInvitationBinding){
        viewModel.Invitations.observe(viewLifecycleOwner,{invitations->
            invitations?.let {
                invitationAdapter.updateInvitation(it)
            }
        })
        viewModel.InvitationsIsEmpty.observe(viewLifecycleOwner,{isEmpty->
            isEmpty?.let {
                if (isEmpty)dialogBinding.tvIsempty.visibility =  View.VISIBLE
                else dialogBinding.tvIsempty.visibility=View.GONE
            }
        })
    }

}