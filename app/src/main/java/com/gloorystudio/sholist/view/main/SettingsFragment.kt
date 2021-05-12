package com.gloorystudio.sholist.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.databinding.FragmentSettingsBinding
import com.gloorystudio.sholist.viewmodel.login.LoginViewModel
import com.gloorystudio.sholist.viewmodel.main.SettingsViewModel


class SettingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private lateinit var  binding : FragmentSettingsBinding
    private lateinit var viewModel : SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_settings,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.statusBarColor= ContextCompat.getColor(requireContext(),R.color.green_dark)

        viewModel= ViewModelProvider(this).get(SettingsViewModel ::class.java)

    }


}