package com.example.onlineshopping

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.onlineshopping.database.Item
import com.example.onlineshopping.database.ItemsDatabase
import com.example.onlineshopping.itemsList.ItemsListViewModel
import com.example.onlineshopping.itemsList.ItemsListViewModelFactory
import org.json.JSONException
import org.json.JSONObject

class ProductActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    internal lateinit var dataModelArrayList: ArrayList<DataModel>
    private var adapter: RvAdapter? = null
    lateinit var id: String
    lateinit var name: String
    lateinit var price: String
    lateinit var image: String
    lateinit var cart_img: ImageView
    private lateinit var viewModel: ItemsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        recyclerView = findViewById(R.id.recyclerview)
        cart_img = findViewById(R.id.cart_img)
        val dao = ItemsDatabase.getInstance(application).itemsDao
        val viewModelFactory = ItemsListViewModelFactory(dao)
        viewModel = ViewModelProvider(this, viewModelFactory)[ItemsListViewModel::class.java]


        cart_img.setOnClickListener {
            val intent = Intent(this@ProductActivity, MainActivity::class.java)
            startActivity(intent)
        }



        val URL = " https://www.mocky.io/v2/5def7b172f000063008e0aa2"
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->
                Log.i("responseeeeeeeee", response!!)

                try {
                    dataModelArrayList = ArrayList()
                    val jsonObject = JSONObject(response)
                    val data = jsonObject.getJSONArray("products")
                    for (i in 0 until data.length()) {
                        val json = data.getJSONObject(i)
                        id = json.getString("id")
                        name = json.getString("name")
                        price = json.getString("price")
                        image = json.getString("image")

                        val playerModel = DataModel()

                        playerModel.setNames(name)
                        playerModel.setPrices(price)
                        playerModel.setImages(image)
                        playerModel.setIds(id)

                        dataModelArrayList.add(playerModel)


                    }
                    recyclerView.layoutManager = LinearLayoutManager(this@ProductActivity)
                    adapter = RvAdapter(this, dataModelArrayList)
                    recyclerView.adapter = adapter



                    adapter!!.setOnItemClickListener(object : RvAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val model: DataModel = dataModelArrayList.get(position)

                            val p_id: String = model.getIds()
                            val p_name: String = model.getNames()
                            val p_price: String = model.getPrices()
                            val p_image: String = model.getImages()


                            val newItem = Item(p_name, 1,p_price,p_image)
                            viewModel.addItem(newItem)


                        }


                    })
                    adapter!!.setOnItemClickListener1(object : RvAdapter.onItemClickListener1 {
                        override fun onItemClick1(position: Int) {
                            val intent = Intent(this@ProductActivity,MainActivity::class.java)
                            startActivity(intent)

                        }

                    })


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { }) {

        }

        stringRequest.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        val requestQueue = Volley.newRequestQueue(this@ProductActivity)
        requestQueue.add(stringRequest)

    }

    override fun onBackPressed() {
        val dialog: Dialog
        dialog = Dialog(this@ProductActivity)

        dialog.setContentView(R.layout.alert_dialogbox)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )


        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()

        val title = dialog.findViewById<TextView>(R.id.title)
        val no = dialog.findViewById<RelativeLayout>(R.id.no)
        val yes = dialog.findViewById<RelativeLayout>(R.id.yes)
        title.text = "Are you sure want to exit"
        no.setOnClickListener { dialog.dismiss() }
        yes.setOnClickListener { finishAffinity() }
    }
}
