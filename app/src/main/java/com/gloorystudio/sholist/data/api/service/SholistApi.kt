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
import retrofit2.http.*

interface SholistApi {


    /**Auth Start*/

    //Kayıt Ol
    @POST("signUp")
    fun signUp(@Body signUp: SignUp): Single<ApiResponse>

    //Benzersiz Kullanıcı Adı Belirle
    @POST("setNames")
    fun setNames(@Body setNames: SetNames): Single<ApiResponse>

    //Giriş Yap
    @POST("signIn")
    fun signIn(@Body signIn: SignIn): Single<ApiResponseWithJwt>

    //E-posta Onayı İçin Eposta Gönder
    @POST("verifyEmail")
    fun verifyEmail(@Field("email") email: String): Single<ApiResponse>

    //Çıkış Yap
    @POST("signOut")
    fun signOut(@Field("jwt") jwt: String): Single<ApiResponse>

    //Parolamı Unuttum - Kurtarma E-postası İste
    @POST("forgotMyPassword")
    fun forgotMyPassword(@Field("email") email: String): Single<ApiResponse>

    /**AuthEnd */


    //Alışveriş Listesi Getir
    @GET("shoppingCard")
    fun getShoppingCard(@Body getShoppingCard: GetShoppingCard): Single<ApiResponseWithShoppingCard>

    //Sahibi Olunan Bütün Alışveriş Listesilerini Getir
    @GET("shoppingCardAll")
    fun getShoppingCardAll(@Field("jwt") jwt: String): Single<ApiResponseWithShoppingCardList>

    //Alışveriş Listesi Oluştur
    @POST("shoppingCard")
    fun postShoppingCard(@Body postShoppingCard: PostShoppingCard): Single<ApiResponseWithShoppingCard>

    //Alışveriş Listesini Sil
    @DELETE("shoppingCard")
    fun deleteShoppigCard(@Body deleteShoppigCard: DeleteShoppingCard): Single<ApiResponseWithShoppingCard>

    //Alışveriş Listesine Ürün Ekle
    @POST("item")
    fun postItem(@Body postItem: PostItem): Single<ApiResponseWithShoppingCardAndItemList>

    //Alışveriş Listesinden Ürün Silme
    @DELETE("item")
    fun deleteItem(@Body deleteItem: DeleteItem): Single<ApiResponse>

    //Ürün Alındı/Alınmadı Olarak İşaretleme
    @PATCH("item")
    fun patchItem(@Body patchItem: PatchItem): Single<ApiResponseWithItem>

    //Alışveriş Listesine Eklenmek İçin Gönderilmiş İstekleri Görüntüleme
    @GET("invitation")
    fun getInvitation(@Field("jwt") jwt: String): Single<ApiResponseWithInvitation>

    //Alışveriş Listesine Üye Ekleme İsteği Gönderme
    @POST("invitation")
    fun postInvitation(@Body postInvitation: PostInvitation): Single<ApiResponse>

    //Alışveriş Listesi Ekleme isteği Onaylama/Reddetme
    @PATCH("invitation")
    fun patchInvitation(@Body patchInvitation: PatchInvitation): Single<ApiResponse>

    //Alışveriş Listesinden Üye Çıkarma
    @DELETE("invitation")
    fun deleteInvitation(@Body deleteInvitation: DeleteInvitation): Single<ApiResponse>

    //Parola Değiştirme
    @POST("changePassword")
    fun changePassword(@Body changePassword: ChangePassword): Single<ApiResponse>

    //İsim Değiştirme
    @POST("user")
    fun changeName(@Body setNames: SetNames): Single<ApiResponse>

}