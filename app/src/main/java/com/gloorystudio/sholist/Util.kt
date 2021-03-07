package com.gloorystudio.sholist

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.gloorystudio.sholist.model.Item
import com.gloorystudio.sholist.model.User


//for short fragment train
fun NavDirections.Go(view: View) {
    view.let {
        val action = this
        it.findNavController().navigate(action)
    }
}
fun ArrayList<Item>.getCheckedCount():Int{
    var counter=0
    for (i in this){
        if (i.checked) counter++
    }
    return counter
}
fun ArrayList<User>.getUserNames():String{
    var names=""
    for (u in this){
       names="$names, ${u.name}"
        if(names.length>32){
            names.substring(1,32)
            names+="..."
            return names.substring(1)
        }
    }
    return names
}
fun ArrayList<Item>.getCheckedText():String{

    return "${this.getCheckedCount()}/${this.size}"
}