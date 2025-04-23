package com.example.mywiki

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL=  "https://commons.wikimedia.org"
//private const val BASE_URL2 = "https://en.wikipedia.org/w/api.php?"
interface WikiInterface {

    @GET("api.php")
    fun getImages(
        @Query("action") action: String? = "query",
        @Query("format") format: String? = "json",
        @Query("generator") generator: String? = "random",
        @Query("prop") prop: String? = "pageimages|info",
        @Query("grnlimit") grnlimit: Int? = 10, // This can be left without default value so it must be given always
        @Query("inprop") inprop: String? = "url",
        ) : Call<Modal_result>


}
//interface articleInterface{
//    @GET(value = "format=json&action=query&generator=random&grnnamespace=0&prop=revisions%7Cimages&rvprop=content&grnlimit=10")
//  fun getArticle(@Query("query") query: String,@Query("pages") pages:Int):
//            Call<Modal>
//}
object WikiService {
    val itemInstance:WikiInterface
   // val articleInstance:articleInterface
    init {
         val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
             //.baseUrl((BASE_URL2))
            .build()
        itemInstance=retrofit.create(WikiInterface::class.java)
       // articleInstance=retrofit.create(articleInterface::class.java)
    }


}