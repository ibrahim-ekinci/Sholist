package com.gloorystudio.sholist.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.databinding.FragmentForgetPasswordBinding
import com.gloorystudio.sholist.isEmailTrue
import com.gloorystudio.sholist.viewmodel.login.ForgetPasswordViewModel


class ForgetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgetPasswordBinding
    private lateinit var viewModel: ForgetPasswordViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_forget_password, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForgetPasswordViewModel::class.java)

        binding.btnForgetPw.setOnClickListener {
            if (isEmailCheck())
                viewModel.sendMail(requireContext(), binding.etForgetEmail.text.toString())

        }
        binding.etForgetEmail.apply {
            doAfterTextChanged {
                isEmailCheck()
            }
        }


    }

    fun isEmailCheck(): Boolean {
        if (binding.etForgetEmail.isEmailTrue()) {
            binding.textInputLayoutEmail.error = null
            return true
        } else {
            binding.textInputLayoutEmail.error = getString(R.string.invalid_email_address)
            return false
        }

    }

}