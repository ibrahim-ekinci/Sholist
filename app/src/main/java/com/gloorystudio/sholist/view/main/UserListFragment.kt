package com.gloorystudio.sholist.view.main

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.adapter.UserListAdapter
import com.gloorystudio.sholist.databinding.DialogAddUserBinding
import com.gloorystudio.sholist.databinding.FragmentUserListBinding
import com.gloorystudio.sholist.model.ShoppingCard
import com.gloorystudio.sholist.model.User

import com.gloorystudio.sholist.viewmodel.UserListViewModel


class UserListFragment : Fragment() {
    private lateinit var viewModel : UserListViewModel
    private val userListAdapter = UserListAdapter(arrayListOf())
    var userList: ArrayList<User> = ArrayList<User>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
     private lateinit var  binding :FragmentUserListBinding

     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_list,container,false)
         return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.statusBarColor= ContextCompat.getColor(requireContext(),R.color.green_dark)


        var shoppingCard: ShoppingCard?=null
        arguments?.let {
            val myArgs = UserListFragmentArgs.fromBundle(it)
            shoppingCard = myArgs.ShoppingCard
           //  binding.shoppingCard =shoppingCard
        }
        shoppingCard?.let { shoppingCardData ->


            viewModel = ViewModelProvider(this).get(UserListViewModel::class.java)
            viewModel.refreshUserData(shoppingCardData.userList)
            binding.rvUsers.layoutManager= LinearLayoutManager(requireContext())
            binding.rvUsers.adapter=userListAdapter
            observeLiveData()





            binding.floatingActionButton.setOnClickListener {
                val dialog = Dialog(requireContext())
                val dialogBinding =DialogAddUserBinding.inflate(LayoutInflater.from(requireContext()))
                dialog.setContentView(dialogBinding.root)
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.show()
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