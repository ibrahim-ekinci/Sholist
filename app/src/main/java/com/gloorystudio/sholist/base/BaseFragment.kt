package com.gloorystudio.sholist.base

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.View
import androidx.fragment.app.Fragment
import com.gloorystudio.sholist.CurrentData
import com.gloorystudio.sholist.data.logout
import com.gloorystudio.sholist.view.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/***
Created by Halil ibrahim Ekinci on 08,Ağustos,2021
info@ibrahimekinci.com
 ***/
open class BaseFragment:Fragment() {

    //inside Fragment
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: "+CurrentData.currentJwt)
        if (CurrentData.currentJwt == null){
            uiScope.launch {
                logout(requireContext())
                val intent = Intent(requireContext(), LoginActivity::class.java)
                requireContext().startActivity(intent)
                requireActivity().finish()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}