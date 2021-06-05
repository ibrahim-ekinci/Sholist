package com.gloorystudio.sholist.data.api.model.response

import com.gloorystudio.sholist.data.api.model.Item.Items
import com.gloorystudio.sholist.model.Invitation
import com.gloorystudio.sholist.data.db.entity.Item
import com.gloorystudio.sholist.model.ShoppingCard
import com.gloorystudio.sholist.data.db.entity.User
import com.gloorystudio.sholist.model.DefItem


interface IApiResponse {
    val state: String
    val message: String?
    val code: Int?
}

data class ApiResponse(
    override val state: String,
    override val message: String?,
    override val code: Int?
) : IApiResponse

data class ApiResponseWithJwt(
    override val state: String,
    override val message: String?,
    override val code: Int?,
    val member: User?,
    val jwt: String?,
    val tempToken:String?
) : IApiResponse
data class ApiResponseWithTt(
    override val state: String,
    override val message: String?,
    override val code: Int?,
    val tempToken:String?
) : IApiResponse
data class ApiResponseWithJwtAndTt(
    override val state: String,
    override val message: String?,
    override val code: Int?,
    val member: User?,
    val registered:Boolean,
    val jwt: String?,
    val tempToken:String?
) : IApiResponse
data class ApiResponseWithShoppingCard(

    override val state: String,
    override val message: String?,
    override val code: Int?,
    val shoppingCard: ShoppingCard
) : IApiResponse

data class ApiResponseWithShoppingCardList(
    override val state: String,
    override val message: String?,
    override val code: Int?,
    val shoppingCard: List<ShoppingCard>
) : IApiResponse

data class ApiResponseWithShoppingCardAndItemList(

    override val state: String,
    override val message: String?,
    override val code: Int?,
    val shoppingCard: ShoppingCard,

    val itemList: List<Item>
) : IApiResponse

data class ApiResponseWithItem(
    override val state: String,
    override val message: String?,
    override val code: Int?,
    val item: Item
) : IApiResponse

data class ApiResponseWithInvitation(
    override val state: String,
    override val message: String,
    override val code: Int?,
    val requests: List<Invitation>
) : IApiResponse
data class ApiResponseWithVersion(
    override val state: String,
    override val message: String,
    override val code: Int?,
    val version: Int?
) : IApiResponse
data class ApiResponseWithItemList(
    override val state: String,
    override val message: String?,
    override val code: Int?,
    val items: List<DefItem>
) : IApiResponse