package com.gloorystudio.sholist.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.databinding.FragmentRegisterBinding
import com.gloorystudio.sholist.isEmailTrue
import com.gloorystudio.sholist.isPasswordTrue
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
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding.btnRegister.setOnClickListener { btn ->
            if (isFieldsFilling()) {
                viewModel.signUp(
                    binding,
                    requireContext(),
                    binding.etRegisterEmail.text.toString(),
                    binding.etRegisterPassword.text.toString()
                )

            }
        }
        binding.etRegisterEmail.apply {
            doAfterTextChanged {
                isFieldsFilling()
            }
        }
        binding.etRegisterPassword.apply {
            doAfterTextChanged {
                isFieldsFilling()
            }
        }

    }

    private fun isFieldsFilling(): Boolean {
        binding.textInputLayoutEmail.error = null
        binding.tiRegisterPasword.error = null
        if (!binding.etRegisterEmail.isEmailTrue()) {
            binding.textInputLayoutEmail.error =
                requireContext().getString(R.string.invalid_email_address)
            return false
        } else if (!binding.etRegisterPassword.isPasswordTrue()) {
            binding.tiRegisterPasword.error =
                requireContext().getString(R.string.password_must_be_more_then_six)
            return false
        } else if(binding.checkboxAccept.isChecked.not()){
            Toast.makeText(requireContext(),requireContext().getString(R.string.please_confirm_term_of_use), Toast.LENGTH_SHORT).show()
            return false
        }
        else return true
    }
}