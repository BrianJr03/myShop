package jr.brian.myShop.model.remote

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jr.brian.myShop.model.remote.Constant.BASE_URL
import jr.brian.myShop.model.remote.Constant.CATEGORY_EP
import jr.brian.myShop.model.remote.Constant.ERROR_TAG
import jr.brian.myShop.model.remote.Constant.GET_SUB_CATEGORY_BY_ID_EP
import jr.brian.myShop.model.remote.Constant.SIGN_IN_EP
import jr.brian.myShop.model.remote.Constant.SIGN_UP_EP
import org.json.JSONObject

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

    fun getSubCategories(categoryId: String, callback: OperationalCallback) {
        val url = BASE_URL + GET_SUB_CATEGORY_BY_ID_EP + categoryId
        val strRequest = object : StringRequest(url, {
            val typeToken = object : TypeToken<Sub>() {}
            val gson = Gson()
            val response: Sub = gson.fromJson(it, typeToken.type)
            callback.onSuccess(response)
            Log.i("RESPONSE_SUCCESS", response.toString())
        }, { Log.i("RESPONSE_FAIL", it.toString()) }) {
        }
        requestQueue.add(strRequest)
    }

    fun signUpUser(user: User, callback: OperationalCallback): String {
        val url = BASE_URL + SIGN_UP_EP
        val data = JSONObject()
        var message: String? = null

        data.put("full_name", user.fullName)
        data.put("mobile_no", user.mobileNo)
        data.put("email_id", user.emailId)
        data.put("password", user.password)

        val request = JsonObjectRequest(
            Request.Method.POST, url, data,
            { response: JSONObject ->
                message = response.getString("message")
                val status = response.getInt("status")
                if (status == 0) {
                    callback.onSuccess(message.toString())
                } else {
                    callback.onFailure(message.toString())
                }

            }, { error: VolleyError ->
                error.printStackTrace()
                Log.i(ERROR_TAG, "${error.printStackTrace()}")
                callback.onFailure(message.toString())
            })
        requestQueue.add(request)
        return message.toString()
    }

    fun signInUser(user: User, callback: OperationalCallback): String {
        val url = BASE_URL + SIGN_IN_EP
        val data = JSONObject()
        var message: String? = null

        data.put("email_id", user.emailId)
        data.put("password", user.password)

        val request = JsonObjectRequest(
            Request.Method.POST, url, data,
            { response: JSONObject ->
                message = response.getString("message")
                val status = response.getInt("status")
                if (status == 0) {
                    val responseUser = response.getJSONObject("user")
                    user.userId = responseUser.getString("user_id")
                    user.fullName = responseUser.getString("full_name")
                    user.mobileNo = responseUser.getString("mobile_no")
                    callback.onSuccess(message.toString())
                } else {
                    callback.onFailure(message.toString())
                }
            }, { error: VolleyError ->
                error.printStackTrace()
                Log.i(ERROR_TAG,"${error.printStackTrace()}")
                callback.onFailure(message.toString())
            })
        requestQueue.add(request)
        return message.toString()
    }
}