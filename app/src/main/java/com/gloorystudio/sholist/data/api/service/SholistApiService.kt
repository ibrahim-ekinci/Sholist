package com.gloorystudio.sholist.data.api.service

import com.gloorystudio.sholist.data.api.model.Item.DeleteItem
import com.gloorystudio.sholist.data.api.model.Item.PatchItem
import com.gloorystudio.sholist.data.api.model.Item.PostItem
import com.gloorystudio.sholist.data.api.model.auth.*
import com.gloorystudio.sholist.data.api.model.invitation.DeleteInvitation
import com.gloorystudio.sholist.data.api.model.invitation.PatchInvitation
import com.gloorystudio.sholist.data.api.model.invitation.PostInvitation
import com.gloorystudio.sholist.data.api.model.response.*
import com.gloorystudio.sholist.data.api.model.shoppingcard.DeleteShoppingCard
import com.gloorystudio.sholist.data.api.model.shoppingcard.GetShoppingCard
import com.gloorystudio.sholist.data.api.model.shoppingcard.PostShoppingCard
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class SholistApiService {

    private val BASE_URL = "https://www.sholist.com/api/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(SholistApi::class.java)


    fun signUp(signUp: SignUp): Single<ApiResponseWithTt> {
        return api.signUp(signUp)
    }

    fun setNames(setNames: SetNames): Single<ApiResponse> {
        return api.setNames(setNames)
    }

    fun signIn(signIn: SignIn): Single<ApiResponseWithJwt> {
        return api.signIn(signIn)
    }
    fun loginWithGoogle(loginWithGoogle: LoginWithGoogle): Single<ApiResponseWithJwtAndTt> {
        return api.loginWithGoogle(loginWithGoogle)
    }

    fun verifyEmail(email: String): Single<ApiResponse> {
        return api.verifyEmail(email)
    }

    fun signOut(jwt: String): Single<ApiResponse> {
        return api.signOut(jwt)
    }

    fun forgotMyPassword(email: String): Single<ApiResponse> {
        return api.forgotMyPassword(email)
    }

    fun getShoppingCard(getShoppingCard: GetShoppingCard): Single<ApiResponseWithShoppingCard> {
        return api.getShoppingCard(getShoppingCard)
    }

    fun getShoppingCardAll(jwt: String): Single<ApiResponseWithShoppingCardList> {
        return api.getShoppingCardAll(jwt)
    }

    fun postShoppingCard(postShoppingCard: PostShoppingCard): Single<ApiResponseWithShoppingCard> {
        return api.postShoppingCard(postShoppingCard)
    }

    fun deleteShoppigCard(deleteShoppigCard: DeleteShoppingCard): Single<ApiResponseWithShoppingCard> {
        return api.deleteShoppigCard(deleteShoppigCard)
    }

    fun postItem(postItem: PostItem): Single<ApiResponseWithShoppingCardAndItemList> {
        return api.postItem(postItem)
    }


    fun deleteItem(deleteItem: DeleteItem): Single<ApiResponse> {
        return api.deleteItem(deleteItem)
    }


    fun patchItem(patchItem: PatchItem): Single<ApiResponseWithItem> {
        return api.patchItem(patchItem)
    }


    fun getInvitation(jwt: String): Single<ApiResponseWithInvitation> {
        return api.getInvitation(jwt)
    }


    fun postInvitation(postInvitation: PostInvitation): Single<ApiResponse> {
        return api.postInvitation(postInvitation)
    }


    fun patchInvitation(patchInvitation: PatchInvitation): Single<ApiResponse> {
        return api.patchInvitation(patchInvitation)
    }


    fun deleteInvitation(deleteInvitation: DeleteInvitation): Single<ApiResponse> {
        return api.deleteInvitation(deleteInvitation)
    }


    fun changePassword(changePassword: ChangePassword): Single<ApiResponse> {
        return api.changePassword(changePassword)
    }


    fun changeName(setNames: SetNames): Single<ApiResponse> {
        return api.changeName(setNames)
    }

}