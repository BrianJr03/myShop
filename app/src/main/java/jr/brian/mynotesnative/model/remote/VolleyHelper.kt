package jr.brian.mynotesnative.model.remote

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import jr.brian.mynotesnative.R
import jr.brian.mynotesnative.model.remote.Constant.BASE_URL
import jr.brian.mynotesnative.model.remote.Constant.SIGN_IN_END_POINT
import jr.brian.mynotesnative.model.remote.Constant.SIGN_UP_END_POINT
import org.json.JSONObject

class VolleyHelper(context: Context) {

    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)
    private val signInUrl = BASE_URL + SIGN_IN_END_POINT
    private val signUpUrl = BASE_URL + SIGN_UP_END_POINT

    private fun auth(
        view: View,
        data: JSONObject,
        callback: OperationalCallback,
        url: String,
        tag: String
    ) :String {
        val cpb = view.findViewById<ProgressBar>(R.id.progress_bar_signUp)
        cpb.visibility = View.VISIBLE
        var msg = ""
        val request = JsonObjectRequest(Request.Method.POST, url, data, { response: JSONObject ->
            msg = response.getString("message")
            Log.i(tag, msg)
            cpb.visibility = View.GONE
            callback.onSuccess("Success")
        }, { error: VolleyError -> callback.onFailure(error.message.toString()) })
        requestQueue.add(request)
        return msg
    }

    fun signUpUser(view: View, data: JSONObject, callback: OperationalCallback): String {
        return auth(view, data, callback, signUpUrl, "RESPONSE_SIGN_UP")
    }

    fun signInUser(view: View, data: JSONObject, callback: OperationalCallback): String {
        return auth(view, data, callback, signInUrl, "RESPONSE_SIGN_IN")
    }
}