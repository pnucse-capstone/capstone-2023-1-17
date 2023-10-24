package com.example.frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.frontend.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var adapter: ProductAdapter
    var datas = mutableListOf<ProductData>()
    private lateinit var binding: ActivityMainBinding

    lateinit var json: String

    companion object {
        var requestQueue: RequestQueue?=null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.rvAddButton.setOnClickListener {
            val intent = Intent(this, AdditionActivity::class.java)
            startActivity(intent)
        }

        binding.title.setOnClickListener {
            initRecycler()
        }

        initHttpRequest()
        setContentView(binding.root)
        initRecycler()
    }

    fun initHttpRequest() {
        requestQueue = Volley.newRequestQueue(applicationContext)
    }

    fun httpRequestMakeRV() {
        val request = object: StringRequest(
            Request.Method.GET,
            getString(R.string.SERVER_URL) + "/postList",
            Response.Listener<String> {
                json = it

                datas.apply {
                    for(i: Int in 0..JSONArray(json).length()-1) {
                        val jsonObject = JSONArray(json).getJSONObject(i)

                        // 1~3은 서버 내장 데이터.
                        if(i <= 3) {
                            add(ProductData(
                                id = jsonObject.getInt("id"),
                                img = "pproduct" + jsonObject.getInt("id").toString(),
                                name = String(jsonObject.getString("title").toByteArray(Charsets.ISO_8859_1), Charsets.UTF_8),
                                inform = String(jsonObject.getString("content").toByteArray(Charsets.ISO_8859_1), Charsets.UTF_8),
                                price = jsonObject.getInt("price"),
                                xSize = 250.0,
                                ySize = 250.0,
                                zSize = 250.0,
                            ))
                        }
                        else {
                            add(ProductData(
                                id = jsonObject.getInt("id"),
                                img = "ic_launcher_foreground",
                                name = String(jsonObject.getString("title").toByteArray(Charsets.ISO_8859_1), Charsets.UTF_8),
                                inform = String(jsonObject.getString("content").toByteArray(Charsets.ISO_8859_1), Charsets.UTF_8),
                                price = jsonObject.getInt("price"),
                                xSize = 250.0,
                                ySize = 250.0,
                                zSize = 250.0,
                            ))
                        }
                    }

                    adapter.datas = datas
                    adapter.notifyDataSetChanged()
                }
            },
            Response.ErrorListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                return params
            }
        }
        request.setShouldCache(false)
        requestQueue?.add(request)
    }

    private fun initRecycler() {
        adapter = ProductAdapter(this)
        val ProductList: RecyclerView = findViewById(R.id.ProductList)
        ProductList.adapter = adapter
        datas = mutableListOf<ProductData>()

        // HTTP 요청 - 전체 파일을 받아 리사이클러 뷰 생성
        httpRequestMakeRV()


        // 안드로이드 테스트 용 내장 json 접근
        /*
        json = assets.open("product_datas.json").reader().readText()
        val productCount = JSONObject(json).getInt("count")
        val productDataArray = JSONObject(json).getJSONArray("product")

        datas.apply {
            for(i: Int in 0..productCount-1) {
                val jsonObject = productDataArray.getJSONObject(i)

                add(ProductData(
                    id = jsonObject.getInt("id"),
                    img = "ic_launcher_foreground",
                    name = jsonObject.getString("name"),
                    inform = jsonObject.getString("inform"),
                    price = jsonObject.getInt("price"),
                    xSize = jsonObject.getDouble("xSize"),
                    ySize = jsonObject.getDouble("ySize"),
                    zSize = jsonObject.getDouble("zSize"),
                    ))
            }

            adapter.datas = datas
            adapter.notifyDataSetChanged()
        }
        */
    }
}