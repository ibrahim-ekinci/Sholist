package com.gloorystudio.sholist.view.main

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.gloorystudio.sholist.*
import com.gloorystudio.sholist.adapter.InvitationAdapter
import com.gloorystudio.sholist.adapter.ShoppingCardAdapter
import com.gloorystudio.sholist.databinding.DialogInvitationBinding
import com.gloorystudio.sholist.databinding.DialogNewlistBinding
import com.gloorystudio.sholist.databinding.FragmentMainBinding
import com.gloorystudio.sholist.viewmodel.main.MainViewModel


class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private val shoppingCardAdapter = ShoppingCardAdapter(arrayListOf())
    private val invitationAdapter = InvitationAdapter(arrayListOf())
    private lateinit var dialogBindingInvitations: DialogInvitationBinding
    private lateinit var dialogInvitations: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setHasOptionsMenu(true)
    }

    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.green_dark)

        //TODO: DAVET LİSTELERİ ÇEKİLECEK.


        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //For Invitation Dialog
        dialogBindingInvitations =
            DialogInvitationBinding.inflate(LayoutInflater.from(requireContext()))
        viewModel.refreshInvitationsData(requireContext())
        dialogBindingInvitations.rvInvitation.layoutManager = LinearLayoutManager(requireContext())
        dialogInvitations = Dialog(requireContext())
        dialogInvitations.setContentView(dialogBindingInvitations.root)
        invitationAdapter.onClickIvConfirm { invation, iv ->
            viewModel.invitationAccept(requireContext(), true, invation)
        }
        invitationAdapter.onClickIvCancel { invation, iv ->
            viewModel.invitationAccept(requireContext(), false, invation)
        }
        dialogBindingInvitations.rvInvitation.adapter = invitationAdapter
        observeInvitationsLiveData(dialogBindingInvitations)

        //For ShoppingCardList
        viewModel.refreshShoppingCardsData(requireContext())
        binding.rvShopingcards.layoutManager = LinearLayoutManager(requireContext())
        binding.rvShopingcards.adapter = shoppingCardAdapter
        observeShoppingCardsLiveData()

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_item_info -> {

                }
                R.id.menu_item_settings -> {
                    MainFragmentDirections.actionMainFragmentToSettingsFragment()
                        .Go(binding.topAppBar)
                }
                R.id.menu_item_logout ->{
                    viewModel.logOut(requireContext(),requireActivity())
                }
                R.id.menu_item_invitation -> {
                    ShowInvitations()
                }
            }
            true
        }

        shoppingCardAdapter.onClickCard { shoppingCard ->
            MainFragmentDirections.actionMainFragmentToListFragment(shoppingCard)
                .Go(binding.topAppBar)
        }


        binding.floatingActionButton.setOnClickListener {
            var dialog = Dialog(requireContext())
            var color = 1
            val dialogBinding = DialogNewlistBinding.inflate(LayoutInflater.from(requireContext()))
            dialogBinding.llBlue.setOnClickListener {
                color = 1
                dialogBinding.tvBlue.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.card_bg1
                    )
                )
                dialogBinding.tvGreen.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.sho_gray
                    )
                )
                dialogBinding.tvOrange.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.sho_gray
                    )
                )
            }
            dialogBinding.llGreen.setOnClickListener {
                color = 2
                dialogBinding.tvBlue.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.sho_gray
                    )
                )
                dialogBinding.tvGreen.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.card_bg2
                    )
                )
                dialogBinding.tvOrange.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.sho_gray
                    )
                )
            }
            dialogBinding.llOrange.setOnClickListener {
                color = 3
                dialogBinding.tvBlue.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.sho_gray
                    )
                )
                dialogBinding.tvGreen.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.sho_gray
                    )
                )
                dialogBinding.tvOrange.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.card_bg3
                    )
                )
            }
            dialog.setContentView(dialogBinding.root)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialogBinding.btnCancel.setOnClickListener { dialog.cancel() }
            dialogBinding.btnAdd.setOnClickListener {
                val text = dialogBinding.etListName.text.toString()
                if (text.isNotEmpty()) {
                    viewModel.createNewListWithApi(requireContext(), text, color, dialog)
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.please_enter_list_name),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            dialog.show()
            println("jwt-" + currentData.currentJwt)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.removeInvitationListener()
    }

    private fun ShowInvitations() {

        dialogInvitations.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialogBindingInvitations.btnCancel.setOnClickListener {
            dialogInvitations.cancel()
        }

        dialogInvitations.show()

    }

    private fun observeShoppingCardsLiveData() {
        viewModel.ShoppingCards.observe(viewLifecycleOwner, { cards ->
            cards?.let {

                shoppingCardAdapter.updateShopingCard(it)
            }
        })
        viewModel.ShoppingCardsIsEmpty.observe(viewLifecycleOwner, { isEmpty ->
            isEmpty?.let {
                if (it) binding.llEmptyList.visibility = View.VISIBLE
                else binding.llEmptyList.visibility = View.GONE
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
        viewModel.ShoppingCardsLoading.observe(viewLifecycleOwner, { loading ->
            loading?.let {
                if (loading) LoadingDialogShow(requireContext())
                else LoadingDialogCancel()
            }
        })

    }

    private fun observeInvitationsLiveData(dialogBinding: DialogInvitationBinding) {
        viewModel.Invitations.observe(viewLifecycleOwner, { invitations ->
            invitations?.let {
                invitationAdapter.updateInvitation(it)
            }
        })
        viewModel.InvitationsIsEmpty.observe(viewLifecycleOwner, { isEmpty ->
            isEmpty?.let {
                if (isEmpty) {
                    dialogBinding.tvIsempty.visibility = View.VISIBLE
                    binding.topAppBar.menu.findItem(R.id.menu_item_invitation).isVisible = false
                } else {
                    dialogBinding.tvIsempty.visibility = View.GONE
                    binding.topAppBar.menu.findItem(R.id.menu_item_invitation).isVisible = true
                }
            }
        })
    }

}