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
import com.gloorystudio.sholist.data.api.model.auth.SetNames
import com.gloorystudio.sholist.databinding.FragmentUserInfoBinding
import com.gloorystudio.sholist.isNameTrue
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
        observeUserNameErrorLiveData()
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

            if (binding.etUnName.isNameTrue().not()) binding.textInputLayoutName.error =
                requireContext().getString(R.string.invalid_name_and_surname)
            else if(binding.etUnUsername.text.toString().length<3){
                binding.textInputLayoutUsername.error=requireContext().getString(R.string.username_must_be_least_4_char)
            } else{
                viewModel.setName(it,
                    requireContext(), SetNames(
                        email,
                        tempToken,
                        binding.etUnUsername.text.toString(),
                        binding.etUnName.text.toString()
                    )
                )
            }

        }

        binding.etUnName.apply {
            doAfterTextChanged {
                binding.textInputLayoutName.error = null
                if (isNameTrue().not()) binding.textInputLayoutName.error =
                    requireContext().getString(R.string.invalid_name_and_surname)
            }
        }

    }

    private fun observeUserNameErrorLiveData() {
        viewModel.usernameError.observe(viewLifecycleOwner, { error ->
            error?.let {
                if (error) {
                    binding.textInputLayoutUsername.error =
                        requireContext().getString(R.string.current_username)
                } else binding.textInputLayoutUsername.error = null
            }
        })
    }

}