package com.gloorystudio.sholist.data.api

import com.gloorystudio.sholist.data.model.Item.DeleteItem
import com.gloorystudio.sholist.data.model.Item.PatchItem
import com.gloorystudio.sholist.data.model.Item.PostItem
import com.gloorystudio.sholist.data.model.auth.*
import com.gloorystudio.sholist.data.model.invitation.DeleteInvitation
import com.gloorystudio.sholist.data.model.invitation.PatchInvitation
import com.gloorystudio.sholist.data.model.invitation.PostInvitation
import com.gloorystudio.sholist.data.model.response.*
import com.gloorystudio.sholist.data.model.shoppingcard.DeleteShoppingCard
import com.gloorystudio.sholist.data.model.shoppingcard.GetShoppingCard
import com.gloorystudio.sholist.data.model.shoppingcard.GetShoppingCardAll
import com.gloorystudio.sholist.data.model.shoppingcard.PostShoppingCard
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface SholistApi {


    /**Auth Start*/

    //Kayıt Ol
    @POST("signUp")
    suspend fun signUp(@Body signUp:SignUp): Response<ApiResponse>

    //Benzersiz Kullanıcı Adı Belirle
    @POST("setNames")
    suspend fun setNames(@Body setNames:SetNames):Response<ApiResponse>

    //Giriş Yap
    @POST("signIn")
    suspend fun signIn(@Body signIn:SignIn):Response<ApiResponseWithJwt>

    //E-posta Onayı İçin Eposta Gönder
    @POST("verifyEmail")
    suspend fun verifyEmail(@Body verifyEmail: VerifyEmail):Response<ApiResponse>

    //Çıkış Yap
    @POST("signOut")
    suspend fun signOut(@Field("jwt") jwt:String):Response<ApiResponse>

    //Parolamı Unuttum - Kurtarma E-postası İste
    @POST("forgotMyPassword")
    suspend fun forgotMyPassword(@Field("email") email:String):Response<ApiResponse>

    /**AuthEnd */


    //Alışveriş Listesi Getir
    @GET("shoppingCard")
    suspend fun getShoppingCard(@Body getShoppingCard: GetShoppingCard):Response<ApiResponseWithShoppingCard>

    //Sahibi Olunan Bütün Alışveriş Listesilerini Getir
    @GET("shoppingCardAll")
    suspend fun getShoppingCardAll(@Body getShoppingCardAll:GetShoppingCardAll):Response<ApiResponseWithShoppingCardList>

    //Alışveriş Listesi Oluştur
    @POST("shoppingCard")
    suspend fun postShoppingCard(@Body postShoppingCard:PostShoppingCard):Response<ApiResponseWithShoppingCard>

    //Alışveriş Listesini Sil
    @DELETE("shoppingCard")
    suspend fun delete(@Body deleteShoppigCard:DeleteShoppingCard):Response<ApiResponseWithShoppingCard>

    //Alışveriş Listesine Ürün Ekle
    @POST("item")
    suspend fun postItem(@Body postItem: PostItem):Response<ApiResponseWithShoppingCardAndItemList>

    //Alışveriş Listesinden Ürün Silme
    @DELETE("item")
    suspend fun deleteItem(@Body deleteItem:DeleteItem):Response<ApiResponse>

    //Ürün Alındı/Alınmadı Olarak İşaretleme
    @PATCH("item")
    suspend fun patchItem(@Body patchItem: PatchItem):Response<ApiResponseWithItem>

    //Alışveriş Listesine Eklenmek İçin Gönderilmiş İstekleri Görüntüleme
    @GET("invitation")
    suspend fun getInvitation(@Field("jwt")jwt:String):Response<ApiResponseWithInvitation>

    //Alışveriş Listesine Üye Ekleme İsteği Gönderme
    @POST("invitation")
    suspend fun postInvitation(@Body postInvitation: PostInvitation):Response<ApiResponse>

    //Alışveriş Listesi Ekleme isteği Onaylama/Reddetme
    @PATCH("invitation")
    suspend fun patchInvitation(@Body patchInvitation: PatchInvitation):Response<ApiResponse>

    //Alışveriş Listesinden Üye Çıkarma
    @DELETE("invitation")
    suspend fun deleteInvitation(@Body deleteInvitation: DeleteInvitation):Response<ApiResponse>

    //Parola Değiştirme
    @POST("changePassword")
    suspend fun changePassword(@Body changePassword:ChangePassword):Response<ApiResponse>

    //İsim Değiştirme
    @POST("user")
    suspend fun changeName(@Body setNames: SetNames):Response<ApiResponse>

}