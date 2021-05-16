package com.gloorystudio.sholist

import android.app.Dialog
import android.content.Context
import android.util.Base64
import android.util.Patterns
import android.view.View
import android.widget.TextView
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.gloorystudio.sholist.data.db.entity.Item
import com.gloorystudio.sholist.data.db.entity.ShoppingList
import com.gloorystudio.sholist.data.db.entity.ShoppingListWithItemsAndUsers
import com.gloorystudio.sholist.data.db.entity.User
import com.gloorystudio.sholist.model.ShoppingCard
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


//for short fragment train
fun NavDirections.Go(view: View) {
    view.let {
        val action = this
        it.findNavController().navigate(action)
    }
}

fun ArrayList<Item>.getCheckedCount(): Int {
    var counter = 0
    for (i in this) {
        if (i.checked) counter++
    }
    return counter
}

fun ArrayList<User>.getUserNames(): String {
    var names = ""
    this.let {
        for (u in this) {
            names = "$names, ${u.name}"
            if (names.length > 32) {
                names.substring(1, 32)
                names += "..."
                return names.substring(1)
            }
        }
    }
    return names
}

fun ArrayList<Item>.getCheckedText(): String {

    return "${this.getCheckedCount()}/${this.size}"
}

fun getUsername(user: User): String {
    if (user.status == true) return user.username!!
    else if (user.status == false) return "${user.username} (Waiting)"
    else return " "
}

private var loadingDialog: Dialog? = null
fun LoadingDialogShow(context: Context) {
    context.let { c ->
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

fun TextView.isEmailTrue(): Boolean {
    val email = this.text.toString()
    return email != "" && email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun TextView.isPasswordTrue(): Boolean {
    val pass = this.text.toString()
    return pass.length > 6
}

fun TextView.isNameTrue(): Boolean {
    val name = this.text.toString()
    return (name.contains(" ") && name.length > 4)
}

fun String.encrypt(password: String): String {
    val secretKeySpec = SecretKeySpec(password.toByteArray(), "AES")
    val iv = ByteArray(16)
    val charArray = password.toCharArray()
    for (i in 0 until charArray.size) {
        iv[i] = charArray[i].code.toByte()
    }
    val ivParameterSpec = IvParameterSpec(iv)

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)

    val encryptedValue = cipher.doFinal(this.toByteArray())
    return Base64.encodeToString(encryptedValue, Base64.DEFAULT)
}

fun String.decrypt(password: String): String {
    val secretKeySpec = SecretKeySpec(password.toByteArray(), "AES")
    val iv = ByteArray(16)
    val charArray = password.toCharArray()
    for (i in 0 until charArray.size) {
        iv[i] = charArray[i].code.toByte()
    }
    val ivParameterSpec = IvParameterSpec(iv)

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)

    val decryptedByteValue = cipher.doFinal(Base64.decode(this, Base64.DEFAULT))
    return String(decryptedByteValue)
}


fun ShoppingCard.toEntity(): ShoppingListWithItemsAndUsers {
    val shoppingList = ShoppingList(this.id, this.name, this.creatorId, this.color)
    return ShoppingListWithItemsAndUsers(shoppingList, this.itemList, this.userList)
}
fun ArrayList<ShoppingCard>.toEntity():ArrayList<ShoppingListWithItemsAndUsers>{
    val newList:ArrayList<ShoppingListWithItemsAndUsers> = arrayListOf()
    for (shoppingCard in this){
        newList.add(shoppingCard.toEntity())
    }
    return newList
}

fun ShoppingListWithItemsAndUsers.toModel(): ShoppingCard {
    for (i in items){
        println(i.name)
    }

    return ShoppingCard(
        shoppingList.id,
        shoppingList.name,
        shoppingList.creatorId,
        shoppingList.color,
        ArrayList(users),
        ArrayList(items)
    )
}
fun List<ShoppingListWithItemsAndUsers>.toModel():ArrayList<ShoppingCard>{
    val newList :ArrayList<ShoppingCard> = arrayListOf()
    for (shoppingListWithItemsAndUsers in this){
        newList.add(shoppingListWithItemsAndUsers.toModel())
    }
    return newList
}

