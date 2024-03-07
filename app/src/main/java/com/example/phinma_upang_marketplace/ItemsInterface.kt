package com.example.phinma_upang_marketplace

<<<<<<< HEAD
import GetData
import HistoryResponse
import OrderRequest
=======
import HistoryResponse
>>>>>>> e8f22e59d0ab84885296a5facde11f79f9f0407f
import OrderResponse
import PostProduct
import ProductsRequest
import ProductsFetch
import ProfileRequest
import RemoveRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ItemsInterface {
    @GET("product") //GET ALL THE PRODUCTS AND DISPLAY USING RECYCLER VIEW
    fun getItems(@Header("Authorization") token : String): Call<List<ProductsFetch>>

    @POST("purchase") //eto yung sa checkout ng item
    fun orderedItems(@Body orderRequest: OrderRequest, @Header("Authorization") token: String): Call<OrderResponse>

    @GET("likes") // same sa first function to pero for likes
    fun getLikes(@Header("Authorization") token: String): Call<List<ProductsFetch>>

    @GET("cart") //nakalimutan ko nanaman endpoint
    fun getCart(@Header("Authorization") token: String): Call<List<ProductsFetch>>

    @POST("likes/add") //add an item to likes page
    fun addLike(@Body productRequest: ProductsRequest, @Header("Authorization") token: String): Call<OrderResponse>

    @POST("unlike") //opposite of like
    fun removeLike(@Body removeRequest: RemoveRequest, @Header("Authorization") token: String): Call<OrderResponse>

    @POST("products") //for sellers to post a product
    fun postProduct(@Body productRequest: PostProduct, @Header("Authorization") token: String): Call<Void> //TODO: Change the return type to the response of the post request

    @POST("add_to_cart")
    fun addToCart(@Body productRequest: ProductsRequest, @Header("Authorization") token: String): Call<OrderResponse>

    @POST("remove_from_cart")
    fun removeFromCart(@Body removeRequest: RemoveRequest, @Header("Authorization") token: String): Call<OrderResponse>

<<<<<<< HEAD
    @GET("buyerProfile")
    fun getHistory(@Header("Authorization") token: String): Call<HistoryResponse>

    @GET("getBuyer") //hindi ko pa alam yung endpoint
    fun getBuyer(@Header("Authorization") token: String): Call<GetData>

    @POST("getSeller") //hindi ko pa alam yung endpoint
    fun getSeller(@Body productRequest: ProductsRequest, @Header("Authorization") token: String): Call<GetData>

    @PUT("updateProfile/{id}")
    fun updateProfile(@Body profileRequest : ProfileRequest, @Header("Authorization") token: String, @Path("id") id : Int): Call<OrderResponse>

    @DELETE("deleteProfile/{id}")
    fun deleteProfile(@Header("Authorization") token: String, @Path("id") id: Int): Call<OrderResponse>
=======
    @GET("history")
    fun getHistory(@Header("Authorization") token: String): Call<HistoryResponse>
>>>>>>> e8f22e59d0ab84885296a5facde11f79f9f0407f
}