package sriparna.hillhouse.com.healthifyme.api;

import com.google.gson.JsonElement;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sriparna on 27/11/16.
 */
public interface RequestHelper {
    String END_POINT = "http://108.healthifyme.com/";

    @GET("api/v1/booking/slots/all")
    public Call<ResponseBody> getAllSlots(@Query("username") String user,
                                          @Query("api_key") String token,
                                          @Query("vc") int vc,
                                          @Query("expert_username") String expert);


}
