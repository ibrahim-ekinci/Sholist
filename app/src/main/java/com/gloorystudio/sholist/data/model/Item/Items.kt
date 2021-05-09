package com.gloorystudio.sholist.data.model.Item

data class Items(
    val cell: Cell,
    val description: String,
    val limits: List<Int>,
    val type: Int
)