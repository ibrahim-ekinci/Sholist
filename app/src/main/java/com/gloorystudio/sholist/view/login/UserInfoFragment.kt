package com.gloorystudio.sholist.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gloorystudio.sholist.Go
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.databinding.FragmentUserInfoBinding
import com.gloorystudio.sholist.viewmodel.login.UserInfoViewModel

class UserInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentUserInfoBinding
    private lateinit var viewModel: UserInfoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_info, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnUnRegister.setOnClickListener {
            UserInfoFragmentDirections.actionUserInfoFragmentToVerificationFragment().Go(it)
        }
        viewModel = ViewModelProvider(this).get(UserInfoViewModel::class.java)
    }

}