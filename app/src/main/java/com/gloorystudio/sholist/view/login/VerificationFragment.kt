package com.gloorystudio.sholist.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gloorystudio.sholist.Go
import com.gloorystudio.sholist.LoadingDialogShow
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.databinding.FragmentVerificationBinding
import com.gloorystudio.sholist.viewmodel.login.VerificationViewModel


class VerificationFragment : Fragment() {

    private lateinit var binding: FragmentVerificationBinding
    private lateinit var viewModel: VerificationViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_verification, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(this).get(VerificationViewModel ::class.java)
        var email=""
        arguments?.let {
            val myArgs = VerificationFragmentArgs.fromBundle(it)
            email=myArgs.email
        }
        sendMail(email)

        binding.llSendMail.setOnClickListener {
            sendMail(email)
            LoadingDialogShow(requireContext())
        }
        binding.btnLogin.setOnClickListener {
            VerificationFragmentDirections.actionVerificationFragmentToLoginFragment().Go(it)
        }
    }

    private fun sendMail(email:String) {
        viewModel.sendMail(requireContext(),email)
    }

}