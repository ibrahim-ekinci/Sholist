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
import com.gloorystudio.sholist.databinding.FragmentRegisterBinding
import com.gloorystudio.sholist.viewmodel.login.RegisterViewModel


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(this).get( RegisterViewModel::class.java)
        binding.btnRegister.setOnClickListener {btn->
            //Todo Remove Navigate
            RegisterFragmentDirections.actionRegisterFragmentToUserInfoFragment("name","123","test@test.com").Go(btn)

            viewModel.signUp(requireContext(),binding.etRegisterEmail.text.toString(),binding.etRegisterPassword.text.toString())
        }

    }
}