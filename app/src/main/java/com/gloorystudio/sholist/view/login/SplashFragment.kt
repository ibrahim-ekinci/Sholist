package com.gloorystudio.sholist.view.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gloorystudio.sholist.CurrentData
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.data.getJwt
import com.gloorystudio.sholist.data.getNightMode
import com.gloorystudio.sholist.data.getUserData
import com.gloorystudio.sholist.go
import com.gloorystudio.sholist.view.main.MainActivity
import kotlinx.android.synthetic.main.fragment_splash.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        runBlocking {
            getNightMode(requireContext())?.let { nightMode ->
                AppCompatDelegate.setDefaultNightMode(nightMode)
            }
        }
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            val jwt = getJwt(requireActivity())
            if (jwt != null) {
                CurrentData.currentJwt = getJwt(requireContext())
                CurrentData.currentUser = getUserData(requireContext())
                val intent = Intent(requireContext(), MainActivity::class.java)
                requireActivity().startActivity(intent)
                requireActivity().finish()
                println("jwt:$jwt")
            } else {
                SplashFragmentDirections.actionSplashFragmentToLoginFragment().go(ivLogo)
            }

        }
    }
}