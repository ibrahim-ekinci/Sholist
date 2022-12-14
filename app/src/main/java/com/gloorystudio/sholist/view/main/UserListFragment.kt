package com.gloorystudio.sholist.view.main

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gloorystudio.sholist.CurrentData.currentUser
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.adapter.UserListAdapter
import com.gloorystudio.sholist.base.BaseFragment
import com.gloorystudio.sholist.data.db.entity.User
import com.gloorystudio.sholist.databinding.DialogAddUserBinding
import com.gloorystudio.sholist.databinding.FragmentUserListBinding
import com.gloorystudio.sholist.model.ShoppingCard
import com.gloorystudio.sholist.viewmodel.main.UserListViewModel


class UserListFragment : BaseFragment() {
    private lateinit var viewModel: UserListViewModel
    private val userListAdapter = UserListAdapter(arrayListOf())
    var userList: ArrayList<User> = ArrayList<User>()


    private lateinit var binding: FragmentUserListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.green_dark)


        var shoppingCard: ShoppingCard? = null
        arguments?.let {
            val myArgs = UserListFragmentArgs.fromBundle(it)
            shoppingCard = myArgs.ShoppingCard
            //  binding.shoppingCard =shoppingCard
        }
        val isAdmin = shoppingCard?.creatorId == currentUser?.id
        shoppingCard?.let { shoppingCardData ->


            viewModel = ViewModelProvider(this).get(UserListViewModel::class.java)
            viewModel.refreshUserData(shoppingCardData, requireContext())
            binding.rvUsers.layoutManager = LinearLayoutManager(requireContext())
            userListAdapter.isAdmin = isAdmin
            userListAdapter.onClickCB { user, iv ->
                if (user.status == true) {
                    viewModel.removeUser(requireContext(), user)
                }
            }
            binding.rvUsers.adapter = userListAdapter
            observeLiveData()




            if (isAdmin) {
                binding.floatingActionButton.setOnClickListener {
                    val dialog = Dialog(requireContext())
                    val dialogBinding =
                        DialogAddUserBinding.inflate(LayoutInflater.from(requireContext()))
                    dialog.setContentView(dialogBinding.root)
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    dialogBinding.btnAdd.setOnClickListener {
                        val text = dialogBinding.etUsername.text.toString()
                        if (text.isNotEmpty()) {
                            viewModel.addNewUser(requireContext(), text, dialog)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.please_enter_username),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    dialogBinding.btnCancel.setOnClickListener { dialog.cancel() }
                    dialog.show()
                }
            } else {
                binding.floatingActionButton.visibility = View.GONE
            }


        }


    }

    private fun observeLiveData() {
        viewModel.users.observe(viewLifecycleOwner, { users ->
            users?.let {
                userListAdapter.updateUser(it)
            }
        })
    }
}