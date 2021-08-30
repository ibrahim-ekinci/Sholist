package com.gloorystudio.sholist.view.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gloorystudio.sholist.go
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.adapter.ShoppingListAdapter
import com.gloorystudio.sholist.CurrentData.currentUser
import com.gloorystudio.sholist.base.BaseFragment
import com.gloorystudio.sholist.data.db.entity.Item
import com.gloorystudio.sholist.databinding.DialogAddItemBinding
import com.gloorystudio.sholist.databinding.DialogNewlistBinding
import com.gloorystudio.sholist.databinding.FragmentListBinding
import com.gloorystudio.sholist.getCheckedText
import com.gloorystudio.sholist.model.ShoppingCard
import com.gloorystudio.sholist.viewmodel.main.ListViewModel


class ListFragment :BaseFragment() {

    private lateinit var viewModel: ListViewModel
    private val shoppinglistAdapter = ShoppingListAdapter(arrayListOf())
    var itemList: ArrayList<Item> = ArrayList<Item>()


    private lateinit var binding: FragmentListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        return binding.root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.removeListener()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var shoppingCard: ShoppingCard? = null
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        arguments?.let {
            val myArgs = ListFragmentArgs.fromBundle(it)
            shoppingCard = myArgs.ShoppingCard
            binding.shoppingCard = shoppingCard
        }
        shoppingCard?.let { shoppingCardData ->
            val color = shoppingCardData.color
            Log.d(TAG, "onViewCreated: id:"+shoppingCardData.id)
            when (color) { //Set Bg Color
                1 -> {
                    val color = ContextCompat.getColor(binding.clList.context, R.color.card_bg1)
                    binding.clList.setBackgroundColor(color)
                    binding.topAppBar.setBackgroundColor(color)
                    requireActivity().window.statusBarColor = color
                }
                2 -> {
                    val color = ContextCompat.getColor(binding.clList.context, R.color.card_bg2)
                    binding.clList.setBackgroundColor(color)
                    binding.topAppBar.setBackgroundColor(color)
                    requireActivity().window.statusBarColor = color
                }
                3 -> {
                    val color = ContextCompat.getColor(binding.clList.context, R.color.card_bg3)
                    binding.clList.setBackgroundColor(color)
                    binding.topAppBar.setBackgroundColor(color)
                    requireActivity().window.statusBarColor = color
                }
            }
            binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_item_edit_list -> {
                    binding.ivEdit.performClick()
                }
                R.id.menu_item_user_list -> {
                    binding.ivPeoples.performClick()
                }
                R.id.menu_item_remove -> {
                    removeList(shoppingCard?.id)
                }
                R.id.menu_item_leave -> {

                }

            }
            true
        }
            currentUser?.let { user->
                if (user.id==shoppingCardData.creatorId){
                    binding.topAppBar.menu.findItem(R.id.menu_item_remove).setVisible(true)
                    binding.topAppBar.menu.findItem(R.id.menu_item_leave).setVisible(false)
                    binding.topAppBar.menu.findItem(R.id.menu_item_edit_list).setVisible(false)
                }else{
                    binding.topAppBar.menu.findItem(R.id.menu_item_remove).setVisible(false)
                    binding.topAppBar.menu.findItem(R.id.menu_item_leave).setVisible(true)
                    binding.topAppBar.menu.findItem(R.id.menu_item_edit_list).setVisible(false)
                }
            }
            viewModel.getTemplateItems { itemList->
                 binding.floatingActionButton.setOnClickListener {
                //Todo: show add item dialog
                var counter = 1
                var dialog = Dialog(requireContext())
                val dialogBinding =
                    DialogAddItemBinding.inflate(LayoutInflater.from(requireContext()))
                dialog.setContentView(dialogBinding.root)
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                val items = arrayListOf<String>()
                     if (itemList != null) {
                         for (item in itemList){
                             items.add(item.name)
                         }
                     }
                val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
                (dialogBinding.textInputLayoutItem.editText as? AutoCompleteTextView)?.setAdapter(
                    adapter
                )

                dialogBinding.btnCancel.setOnClickListener { dialog.cancel() }
                dialogBinding.btnAdd.setOnClickListener {
                    val itemName = dialogBinding.autoCompleteTvItemName.text.toString()
                    if (itemName.isEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.please_enter_product_name),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.addItem(requireContext(), itemName, counter){isSuccess->
                            if (isSuccess){
                                dialog.dismiss()
                            }
                        }
                    }

                }
                counter = dialogBinding.tvCount.text.toString().toInt()
                dialogBinding.btnAddCount.setOnClickListener {
                   dialogBinding.tvCount.text = (++counter).toString()
                }
                dialogBinding.btnRemoveCount.setOnClickListener {
                    if (counter>1){
                        dialogBinding.tvCount.text = (--counter).toString()
                    }
                }
                dialog.show()
            }
            }

            binding.ivEdit.setOnClickListener {
                var dialog = Dialog(requireContext())
                val dialogBinding =
                    DialogNewlistBinding.inflate(LayoutInflater.from(requireContext()))
                var color = shoppingCardData.color
                dialog.setContentView(dialogBinding.root)
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialogBinding.tvTitle.text = getString(R.string.edit_list)
                dialogBinding.btnAdd.text = getString(R.string.edit)
                dialogBinding.etListName.setText(shoppingCardData.name)
                dialogBinding.llBlue.setOnClickListener {
                    color = 1
                    dialogBinding.tvBlue.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.card_bg1
                        )
                    )
                    dialogBinding.tvGreen.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.sho_gray
                        )
                    )
                    dialogBinding.tvOrange.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.sho_gray
                        )
                    )
                }
                dialogBinding.llGreen.setOnClickListener {
                    color = 2
                    dialogBinding.tvBlue.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.sho_gray
                        )
                    )
                    dialogBinding.tvGreen.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.card_bg2
                        )
                    )
                    dialogBinding.tvOrange.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.sho_gray
                        )
                    )
                }
                dialogBinding.llOrange.setOnClickListener {
                    color = 3
                    dialogBinding.tvBlue.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.sho_gray
                        )
                    )
                    dialogBinding.tvGreen.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.sho_gray
                        )
                    )
                    dialogBinding.tvOrange.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.card_bg3
                        )
                    )
                }
                when (color) {
                    1 -> dialogBinding.llBlue.performClick()
                    2 -> dialogBinding.llGreen.performClick()
                    3 -> dialogBinding.llOrange.performClick()
                }
                dialogBinding.btnCancel.setOnClickListener {
                    dialog.cancel()
                }
                dialogBinding.btnAdd.setOnClickListener {
                    val name = dialogBinding.etListName.text.toString()
                    if (name.isNotEmpty()) {
                        dialogBinding.textInputLayoutListName.error = null
                        viewModel.editList(requireContext(),name,color)
                    } else {
                        dialogBinding.textInputLayoutListName.error =
                            getString(R.string.please_enter_list_name)
                    }
                }
                dialog.show()
            }
            binding.ivPeoples.setOnClickListener {
                ListFragmentDirections.actionListFragmentToUserListFragment(shoppingCardData).go(it)
            }


            viewModel.shoppingCard = shoppingCardData
            viewModel.refreshItemListData(shoppingCardData.itemList, requireContext())
            binding.rvItemList.layoutManager = LinearLayoutManager(requireContext())
            binding.rvItemList.adapter = shoppinglistAdapter
            observeLiveData()

            shoppinglistAdapter.onClickCB { position, item, checkBox ,itemList->
                checkBox.isEnabled = false
                if (checkBox.isChecked) {
                    shoppinglistAdapter.updateItem(position, item, checkBox.isChecked, checkBox)
                } else {
                    shoppinglistAdapter.updateItem(position, item, checkBox.isChecked, checkBox)
                }
                viewModel.updateItemChecked(requireContext(),item,checkBox.isChecked){isSuccess ->
                    if (isSuccess){
                        shoppingCard?.itemList = itemList
                        binding.tvItemCount.text = shoppingCard?.itemList?.getCheckedText()
                    }
                }
            }

            shoppinglistAdapter.onLongClickCB { position, item, checkBox ->
                val alert = AlertDialog.Builder(requireContext())
                alert.setTitle(getString(R.string.delete_item))
                alert.setMessage(getString(R.string.are_you_want_delete))
                alert.setCancelable(false)
                alert.setPositiveButton(getString(R.string.yes)) { dialogInterface: DialogInterface, i: Int ->
                    viewModel.deleteItem(requireContext(),item)
                }
                alert.setNegativeButton(getString(R.string.no)) {dialogInterface: DialogInterface, i: Int ->

                }
                alert.show()
            }

            shoppinglistAdapter.onClickIV { item, imageView ->
                /*
                var dialog = Dialog(requireContext())
                val dialogBinding =
                    DialogSetIconBinding.inflate(LayoutInflater.from(requireContext()))
                val icons = getIcos()
                dialog.setContentView(dialogBinding.root)
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                dialogBinding.rvIcons.layoutManager = LinearLayoutManager(requireContext())
                val adapter = IconAdapter(icons)
                adapter.onClickIV { icon, iv ->
                    viewModel.setIcon(requireContext(), item, icon)
                }
                dialogBinding.rvIcons.adapter = adapter
                dialogBinding.btnCancel.setOnClickListener {
                    dialog.cancel()
                }
                dialog.show()
                */
            }
        }
    }

    private fun removeList(id: Int?) {
        id?.let {
            val alert = AlertDialog.Builder(requireContext())
            alert.setTitle(getString(R.string.delete_item_shooing_list))
            alert.setMessage(getString(R.string.are_you_want_delete_shooing_list))
            alert.setCancelable(false)
            alert.setPositiveButton(getString(R.string.yes)) { dialogInterface: DialogInterface, i: Int ->
                viewModel.deleteList(requireContext(),it){ isSuccess->
                    if (isSuccess){
                        binding.topAppBar.findNavController().navigateUp()
                    }
                }
            }
            alert.setNegativeButton(getString(R.string.no)) {dialogInterface: DialogInterface, i: Int ->

            }
            alert.show()

        }
    }

    private fun observeLiveData() {
        viewModel.items.observe(viewLifecycleOwner, { items ->
            items?.let {
                shoppinglistAdapter.updateItemList(it)
            }
        }
        )
    }

    private fun getIcos(): ArrayList<Int> {
        var iconList: ArrayList<Int> = ArrayList<Int>()
        iconList.clear()
        iconList.add(R.drawable.shop)
        iconList.add(R.drawable.shoppingbasket)
        iconList.add(R.drawable.apple)
        iconList.add(R.drawable.banana)
        iconList.add(R.drawable.beans)
        iconList.add(R.drawable.beer)
        iconList.add(R.drawable.birthdaycake_2)
        iconList.add(R.drawable.biscuit)
        iconList.add(R.drawable.biscuit_2)
        iconList.add(R.drawable.boiledegg_2)
        iconList.add(R.drawable.bowl)
        iconList.add(R.drawable.bread)
        iconList.add(R.drawable.burger_2)
        iconList.add(R.drawable.burger_3)
        iconList.add(R.drawable.cake_2)
        iconList.add(R.drawable.can)
        iconList.add(R.drawable.candy)
        iconList.add(R.drawable.candy_2)
        iconList.add(R.drawable.candycorn)
        iconList.add(R.drawable.capsicum)
        iconList.add(R.drawable.carrot)
        iconList.add(R.drawable.cheese_2)
        iconList.add(R.drawable.chickenleg)
        iconList.add(R.drawable.chilisauce)
        iconList.add(R.drawable.chinesefood)
        iconList.add(R.drawable.chips_3)
        iconList.add(R.drawable.chocolate)
        iconList.add(R.drawable.coffeecup)
        iconList.add(R.drawable.coffeecup_2)
        iconList.add(R.drawable.cookie_2)
        iconList.add(R.drawable.cookie_3)
        iconList.add(R.drawable.cookie_4)
        iconList.add(R.drawable.croissant)
        iconList.add(R.drawable.cupcake)
        iconList.add(R.drawable.cupcake_2)
        iconList.add(R.drawable.cupcake_3)
        iconList.add(R.drawable.dogfood)
        iconList.add(R.drawable.donut_2)
        iconList.add(R.drawable.eggplant)
        iconList.add(R.drawable.feeder)
        iconList.add(R.drawable.fish_2)
        iconList.add(R.drawable.flan)
        iconList.add(R.drawable.frenchfries_2)
        iconList.add(R.drawable.friedchicken)
        iconList.add(R.drawable.glass)
        iconList.add(R.drawable.grapejuice)
        iconList.add(R.drawable.grapejuice_2)
        iconList.add(R.drawable.grapes)
        iconList.add(R.drawable.hazelnut)
        iconList.add(R.drawable.honey)
        iconList.add(R.drawable.hotdog)
        iconList.add(R.drawable.icecream)
        iconList.add(R.drawable.icecream_2_2)
        iconList.add(R.drawable.icecream_4)
        iconList.add(R.drawable.jug)
        iconList.add(R.drawable.juice)
        iconList.add(R.drawable.ketchup)
        iconList.add(R.drawable.ketchup_2)
        iconList.add(R.drawable.loaf)
        iconList.add(R.drawable.lollipop_2)
        iconList.add(R.drawable.mango_2)
        iconList.add(R.drawable.milk)
        iconList.add(R.drawable.milk_2)
        iconList.add(R.drawable.mitten)
        iconList.add(R.drawable.muffin)
        iconList.add(R.drawable.mushrooms)
        iconList.add(R.drawable.noodles)
        iconList.add(R.drawable.noodles_2)
        iconList.add(R.drawable.oakleaf)
        iconList.add(R.drawable.onion)
        iconList.add(R.drawable.orange)
        iconList.add(R.drawable.orange_2)
        iconList.add(R.drawable.pear)
        iconList.add(R.drawable.pomegranate)
        iconList.add(R.drawable.popsicle)
        iconList.add(R.drawable.popsicle_4)
        iconList.add(R.drawable.redchilipepper)
        iconList.add(R.drawable.roastchicken)
        iconList.add(R.drawable.salt)
        iconList.add(R.drawable.sausage)
        iconList.add(R.drawable.sausage_2)
        iconList.add(R.drawable.sausage_2_2)
        iconList.add(R.drawable.softdrink_2)
        iconList.add(R.drawable.softdrink_4)
        iconList.add(R.drawable.soup_3)
        iconList.add(R.drawable.strawberry)
        iconList.add(R.drawable.sushi)
        iconList.add(R.drawable.teabag)
        iconList.add(R.drawable.teacup_2)
        iconList.add(R.drawable.teacup_3)
        iconList.add(R.drawable.toast)
        iconList.add(R.drawable.tomato_3)
        iconList.add(R.drawable.water)
        iconList.add(R.drawable.water_3)
        iconList.add(R.drawable.watermelon)
        iconList.add(R.drawable.watermelon_3)

        return iconList
    }

}