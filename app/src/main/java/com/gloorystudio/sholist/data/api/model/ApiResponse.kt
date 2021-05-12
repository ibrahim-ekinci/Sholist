package com.gloorystudio.sholist.data.api.model.response

import com.gloorystudio.sholist.model.Invitation
import com.gloorystudio.sholist.model.Item
import com.gloorystudio.sholist.model.ShoppingCard


interface  IApiResponse{
    val message: String?
    val state: String
}

data class ApiResponse(
    override val message: String?,
    override val state: String
):IApiResponse

data class ApiResponseWithJwt(
    override val message: String?,
    override val state: String,
    val jwt:String
):IApiResponse

data class ApiResponseWithShoppingCard(
    override val message: String?,
    override val state: String,
    val shoppingCard: ShoppingCard
):IApiResponse

data class ApiResponseWithShoppingCardList(
    override val message: String?,
    override val state: String,
    val shoppingCard: List<ShoppingCard>
):IApiResponse

data class ApiResponseWithShoppingCardAndItemList(
    override val message: String?,
    override val state: String,
    val shoppingCard: ShoppingCard,
    val itemList:List<Item>
):IApiResponse

data class ApiResponseWithItem(
    override val message: String?,
    override val state: String,
    val item:Item
):IApiResponse

data class ApiResponseWithInvitation(
    override val message: String,
    override val state: String,
    val requests:List<Invitation>
):IApiResponse
