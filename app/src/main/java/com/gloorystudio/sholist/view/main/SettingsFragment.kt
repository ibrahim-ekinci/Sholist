package com.gloorystudio.sholist.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.base.BaseFragment
import com.gloorystudio.sholist.data.getNightMode
import com.gloorystudio.sholist.data.setNightMode
import com.gloorystudio.sholist.databinding.FragmentSettingsBinding
import com.gloorystudio.sholist.isNameTrue
import com.gloorystudio.sholist.isPasswordTrue
import com.gloorystudio.sholist.viewmodel.main.SettingsViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class SettingsFragment : BaseFragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var viewModel: SettingsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            getNightMode(requireContext())?.let { nightMode ->
                when (nightMode) {
                    AppCompatDelegate.MODE_NIGHT_YES -> {
                        binding.toggleButtonGroup.selectButton(binding.btnNightModeDark)
                    }
                    AppCompatDelegate.MODE_NIGHT_NO -> {
                        binding.toggleButtonGroup.selectButton(binding.btnNightModeLite)
                    }
                    else -> {
                        binding.toggleButtonGroup.selectButton(binding.btnNightModeAuto)
                    }
                }
            }
        }
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.green_dark)

        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)


        binding.btnInfoSave.setOnClickListener {
            val name = binding.etNameSurname.text.toString()
            if (binding.etNameSurname.isNameTrue().not()) {
                binding.textInputLayoutName.error = getString(R.string.invalid_name_and_surname)
            } else {
                binding.textInputLayoutName.error = null
                viewModel.changeName(requireContext(), name)
            }
        }

        binding.btnPwSave.setOnClickListener {
            val oldPassword = binding.etOldPw.text.toString()
            val newPassword = binding.etNewPw.text.toString()
            if (binding.etNewPw.isPasswordTrue().not()) {
                binding.textInputLayoutNewPw.error =
                    getString(R.string.password_must_be_more_then_six)
            } else {
                binding.textInputLayoutNewPw.error = null
                viewModel.changePassword(requireContext(), newPassword, oldPassword)
            }
        }
        binding.btnNightModeAuto.setOnClickListener {
            setNightModeAuto(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
        binding.btnNightModeDark.setOnClickListener {
            setNightModeAuto(AppCompatDelegate.MODE_NIGHT_YES)
        }
        binding.btnNightModeLite.setOnClickListener {
            setNightModeAuto(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setNightModeAuto(mode: Int) {
        lifecycleScope.launch {
            setNightMode(requireContext(), mode)
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }
}