package com.example.mywiki
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), Adapter.OnItemClicked {
    lateinit var viewModel: ItemViewModel
    private lateinit var adapter: Adapter
    private lateinit var recyclerView: RecyclerView
    private var showMe: ArrayList<Modal> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        fetchData()


      //  getitem()
//        viewModel=ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(ItemViewModel::class.java)
//      viewModel.allItems.observe(this, Observer {List->
//                  List?.let{
//                      adapter.updateList(it)
//                  }
//
//      })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
     super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

//    private fun getitem() {
//        val item: Call<Modal_result> = WikiService.itemInstance.getImages()
//        item.enqueue(object : Callback<Modal_result> {
//            override fun onResponse(call: Call<Modal_result>, response: Response<Modal_result>) {
//                val item: Modal_result? = response.body()
//                if (item != null) {
//                    Log.d("image", item.toString())
//                    adapter=Adapter(showMe,this@MainActivity)
//                    recyclerView.adapter=adapter
//                    recyclerView.layoutManager=LinearLayoutManager(this@MainActivity)
//                }
//
//            }
//            override fun onFailure(call: Call<Modal_result>, t: Throwable) {
//                Log.d("ms", "error in fetchimmage", t)
//            }
//        })
//
//    }

//    override fun onclick(item: Modal) {
//
////        val builder =  CustomTabsIntent.Builder()
////     val customTabsIntent = builder.build()
////       customTabsIntent.launchUrl(this, Uri.parse(item.img_desc
//       ))
    //}
//}

    private fun fetchData() {
        val imageURL =
            "https://commons.wikimedia.org/w/api.php?action=query&prop=imageinfo&iiprop=timestamp%7Cuser%7Curl&generator=categorymembers&gcmtype=file&gcmtitle=Category:Featured_pictures_on_Wikimedia_Commons&format=json&utf8"
        val articleURL =
            "https://en.wikipedia.org/w/api.php?format=json&action=query&generator=random&grnnamespace=0&prop=revisions%7Cimages&rvprop=content&grnlimit=10"
       val category_url=
           "https://en.wikipedia.org/w/api.php?action=query&list=allcategories&acprefix=List+of&formatversion=2&format=json"
        val totalImages: MutableList<String> = ArrayList()
        val totalTitles: MutableList<String> = ArrayList()
        val totalDescriptions: MutableList<String> = ArrayList()
        val totalCategories: MutableList<String> = ArrayList()
        val timg_url: MutableList<String> = ArrayList()

        val queue = Volley.newRequestQueue(this)

        val imageJSON = JsonObjectRequest(Request.Method.GET, imageURL, null, {
            val imageQuery = it.getJSONObject("query")
            val imagePages = imageQuery.getJSONObject("pages")
            val imageKeys: Iterator<String> = imagePages.keys()

            while (imageKeys.hasNext()) {
                val imageKey = imageKeys.next()
                val imageRandomKey: JSONObject = imagePages.getJSONObject(imageKey)
                val imageInfo = imageRandomKey.getJSONArray("imageinfo")

                for (n in 0 until imageInfo.length()) {
                    val imageObj: JSONObject = imageInfo.getJSONObject(n)
                    timg_url+=imageObj.getString("descriptionurl")
                    totalImages += imageObj.getString("url")

                }
            }

        }, {
            Log.d("WIKIPEDIA", "Something Related To Volley Error In ImageJSON :( $it")
        })
        val catJSON = JsonObjectRequest(Request.Method.GET,category_url, null, {
            val cateQuery = it.getJSONObject("query")
      //      val imagePages = imageQuery.getJSONObject("pages")
        //    val imageKeys: Iterator<String> = imagePages.keys()
val catJsonarraay=cateQuery.getJSONArray("allcategories")
//            while (catJsonarraay.hasNext()) {
//               // val imageKey = imageKeys.next()
//                //val imageRandomKey: JSONObject = imagePages.getJSONObject(imageKey)
//               // val imageInfo = imageRandomKey.getJSONArray("imageinfo")

                for (n in 0 until catJsonarraay.length()) {
                    val catObj: JSONObject = catJsonarraay.getJSONObject(n)
                    totalCategories+=catObj.getString("category")
                   // totalImages += imageObj.getString("url")

                }
            //}

        }, {
            Log.d("WIKIPEDIA", "Something Related To Volley Error In CatJSON :( $it")
        })

        val articleJSON = JsonObjectRequest(Request.Method.GET, articleURL, null, {
            val articleQuery = it.getJSONObject("query")
            val articlePages = articleQuery.getJSONObject("pages")
            val articleKeys: Iterator<String> = articlePages.keys()

            while (articleKeys.hasNext()) {
                val articleKey = articleKeys.next()
                val articleRandomKey: JSONObject = articlePages.getJSONObject(articleKey)
                val revisions = articleRandomKey.getJSONArray("revisions")

                for (n in 0 until revisions.length()) {
                    val articleObj: JSONObject = revisions.getJSONObject(n)
                    totalTitles += articleRandomKey.getString("title")
                    totalDescriptions += articleObj.getString("contentmodel")
                }
            }
            for (i in 0 until totalImages.size) {
                showMe.add(Modal(totalImages[i], totalTitles[i], totalDescriptions[i],timg_url[i],totalCategories[i]))
            }

            recyclerView.also { re ->
                re.layoutManager = LinearLayoutManager(this)
                re.adapter = Adapter(showMe,this)
            }

        }, {
            Log.d("WIKIPEDIA", "Something Related To Volley Error In articleJSON :( $it")
        })

        queue.add(imageJSON)
        queue.add(articleJSON)
        queue.add(catJSON)
    }

    override fun onclick(item: Modal) {
        val builder =  CustomTabsIntent.Builder()
     val customTabsIntent = builder.build()
       customTabsIntent.launchUrl(this, Uri.parse(item.img_desc
       ))
    }
}
