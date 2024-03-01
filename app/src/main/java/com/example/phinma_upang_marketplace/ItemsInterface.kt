package com.example.phinma_upang_marketplace

import OrderResponse
import PostProduct
import ProductsRequest
import ProductsFetch
import RemoveRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ItemsInterface {
    @GET("product") //GET ALL THE PRODUCTS AND DISPLAY USING RECYCLER VIEW
    fun getItems(@Header("Authorization") token : String): Call<List<ProductsFetch>>

    @POST("orders") //eto yung sa checkout ng item
    fun orderedItems(@Body productRequest: ProductsRequest, @Header("Authorization") token: String): Call<OrderResponse>

    @GET("likes") // same sa first function to pero for likes
    fun getLikes(@Header("Authorization") token: String): Call<List<ProductsFetch>>

    @POST("likes/add") //add an item to likes page
    fun addLike(@Body productRequest: ProductsRequest, @Header("Authorization") token: String): Call<OrderResponse>

    @POST("unlike") //opposite of like
    fun removeLike(@Body removeRequest: RemoveRequest, @Header("Authorization") token: String): Call<OrderResponse>

    @POST("products") //for sellers to post a product
    fun postProduct(@Body productRequest: PostProduct, @Header("Authorization") token: String): Call<Void> //TODO: Change the return type to the response of the post request
}