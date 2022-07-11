package jr.brian.myShop.model.remote

import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jr.brian.myShop.model.remote.Constant.BASE_URL
import jr.brian.myShop.model.remote.Constant.CATEGORY_EP

class VolleyHelper(context: Context) {

    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun getCategories(callback: OperationalCallback) {
        val url = BASE_URL + CATEGORY_EP
        val strRequest = object : StringRequest(url, {
            val typeToken = object : TypeToken<Inventory>() {}
            val gson = Gson()
            val response: Inventory = gson.fromJson(it, typeToken.type)
            callback.onSuccess(response)
            Log.i("RESPONSE_SUCCESS", response.toString())
        }, { Log.i("RESPONSE_FAIL", it.toString()) }) {
        }
        requestQueue.add(strRequest)
    }
}