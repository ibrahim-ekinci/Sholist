package com.gloorystudio.sholist

import android.app.Dialog
import android.content.Context
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
    this?.let {
        for (u in this){
            names="$names, ${u.name}"
            if(names.length>32){
                names.substring(1,32)
                names+="..."
                return names.substring(1)
            }
        }
    }
    return names
}
fun ArrayList<Item>.getCheckedText():String{

    return "${this.getCheckedCount()}/${this.size}"
}
fun getUsername(user:User):String{
    if (user.status==1)return user.username
    else if (user.status==2) return  "${user.username} (Waiting)"
    else return  " "
}

private var loadingDialog: Dialog? = null
fun LoadingDialogShow(context: Context) {
    context?.let { c ->
        loadingDialog = Dialog(c)
        loadingDialog?.let { dialog ->
            dialog.setContentView(R.layout.dialog_loading)
            dialog.setCancelable(false)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()
        }
    }

}

fun LoadingDialogCancel() {
    loadingDialog?.let { dialog -> dialog.cancel() }
}