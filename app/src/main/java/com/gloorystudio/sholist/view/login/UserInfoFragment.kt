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
import com.gloorystudio.sholist.data.api.model.auth.SetNames
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
        viewModel = ViewModelProvider(this).get(UserInfoViewModel::class.java)
        var name = ""
        var email = ""
        var tempToken = ""
        arguments?.let {
            val myArgs = UserInfoFragmentArgs.fromBundle(it)
            name = myArgs.name
            email = myArgs.email
            tempToken = myArgs.tempToken
        }
        binding.etUnName.setText(name)

        binding.btnUnRegister.setOnClickListener {
            //todo:Remove navigate
            UserInfoFragmentDirections.actionUserInfoFragmentToVerificationFragment(email).Go(it)

            viewModel.setName(requireContext(), SetNames(
                email,
                tempToken,
                binding.etUnUsername.text.toString(),
                binding.etUnName.text.toString()
            ))
        }

    }

}