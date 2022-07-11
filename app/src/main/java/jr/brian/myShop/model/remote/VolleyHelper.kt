package jr.brian.myShop.model.remote

import android.content.Context
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import jr.brian.myShop.model.remote.Constant.BASE_URL
import jr.brian.myShop.model.remote.Constant.CATEGORY_EP
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class VolleyHelper(context: Context) {

    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun getCategories(): Inventory {
        val url = BASE_URL + CATEGORY_EP
        var response = Inventory(arrayListOf(), "", 0)
        val strRequest = object : StringRequest(url, {
            val typeToken = object : TypeToken<Inventory>() {}
            val gson = Gson()
            response = gson.fromJson(it, typeToken.type)
            Log.i("RESPONSE_SUCCESS", response.toString())
        }, { Log.i("RESPONSE_FAIL", it.toString()) }) {
        }
        requestQueue.add(strRequest)
        return response
    }
}

class GsonRequest<T>(
    url: String,
    private val clazz: Class<T>,
    private val headers: MutableMap<String, String>?,
    private val listener: Response.Listener<T>,
    errorListener: Response.ErrorListener
) : Request<T>(Method.GET, url, errorListener) {
    private val gson = Gson()

    override fun getHeaders(): MutableMap<String, String> = headers ?: super.getHeaders()

    override fun deliverResponse(response: T) = listener.onResponse(response)

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            val json = String(
                response?.data ?: ByteArray(0),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
            )
            Response.success(
                gson.fromJson(json, clazz),
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (e: JsonSyntaxException) {
            Response.error(ParseError(e))
        }
    }
}