package com.gloorystudio.sholist.data.api.model.Item

data class Items(
    val cell: Cell,
    val description: String,
    val limits: List<Int>,
    val type: Int
)