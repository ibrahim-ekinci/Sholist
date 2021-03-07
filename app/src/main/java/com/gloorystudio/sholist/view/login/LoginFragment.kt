package com.gloorystudio.sholist.view.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.gloorystudio.sholist.Go
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.databinding.FragmentLoginBinding
import com.gloorystudio.sholist.view.main.MainActivity


class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private lateinit var  binding : FragmentLoginBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.llRegister.setOnClickListener {
            LoginFragmentDirections.actionLoginFragmentToRegisterFragment().Go(it)
        }
        binding.textViewForget.setOnClickListener {
            LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment().Go(it)
        }
        binding.btnLogin.setOnClickListener {
            val intent = Intent(requireContext(),MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }



    }
    

}