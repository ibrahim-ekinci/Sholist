package com.gloorystudio.sholist

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.findNavController


//for short fragment train
fun NavDirections.Go(view: View) {
    view.let {
        val action = this
        it.findNavController().navigate(action)
    }
}