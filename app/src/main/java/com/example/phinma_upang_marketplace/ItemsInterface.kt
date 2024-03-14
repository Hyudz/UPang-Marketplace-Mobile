package com.example.phinma_upang_marketplace

import GetData
import HistoryResponse
import MessageResponse
import OrderHistory
import OrderRequest
import OrderResponse
import PostProduct
import ProductDetail
import ProductHistory
import ProductsRequest
import ProductsFetch
import ProfileRequest
import RemoveRequest
import SellerResponse
import UpdateRequest
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

    @GET("getBuyer") //hindi ko pa alam yung endpoint
    fun getBuyer(@Header("Authorization") token: String): Call<GetData>

    @POST("getSeller") //hindi ko pa alam yung endpoint
    fun getSeller(@Body productRequest: ProductsRequest, @Header("Authorization") token: String): Call<GetData>

    @PUT("updateProfile/{id}")
    fun updateProfile(@Body profileRequest : ProfileRequest, @Header("Authorization") token: String, @Path("id") id : Int): Call<OrderResponse>

    @DELETE("deleteProfile/{id}")
    fun deleteProfile(@Header("Authorization") token: String, @Path("id") id: Int): Call<OrderResponse>

    @GET("buyerProfile")
    fun getHistory(@Header("Authorization") token: String): Call<List<HistoryResponse>>

    @GET("sellerProfile")
    fun getSellerHistory(@Header("Authorization") token: String): Call<List<SellerResponse>>

    @POST("cancelOrder")
    fun cancelOrder(@Body orderId: OrderHistory, @Header("Authorization") token: String): Call<OrderResponse>

    @POST("orderSettled")
    fun settleOrder(@Body product_id: ProductHistory, @Header("Authorization") token: String): Call<OrderResponse>

    @DELETE("deleteProduct/{id}")
    fun deleteProduct(@Header("Authorization") token: String, @Path("id") id: Int): Call<OrderResponse>
<<<<<<< Updated upstream

    @PUT("updateProduct/{id}")
    fun updateProduct(@Body productRequest: PostProduct, @Header("Authorization") token: String, @Path("id") id: Int): Call<OrderResponse>
<<<<<<< HEAD

    @PUT("updateAccount/{id}")
    fun updateAccount(@Body updateRequest: UpdateRequest, @Header("Authorization") token: String, @Path("id") id: String): Call<OrderResponse>
=======
=======
>>>>>>> Stashed changes
>>>>>>> dbb5c4e99703b83ad05a13923564aed1e12901b1
}