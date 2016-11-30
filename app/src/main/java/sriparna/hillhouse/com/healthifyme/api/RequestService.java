package sriparna.hillhouse.com.healthifyme.api;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by sriparna on 27/11/16.
 */
public class RequestService {
    private Retrofit retrofit = null;
    private RequestHelper requestHelper = null;

    private static String USER_TOKEN = "a4aeb4e27f27b5786828f6cdf00d8d2cb44fe6d7";

    public RequestService(){
        prepare();
    }

    private void prepare(){
        retrofit = new Retrofit.Builder()
                .baseUrl(RequestHelper.END_POINT)
                .client(new OkHttpClient())
                .build();

        requestHelper = retrofit.create(RequestHelper.class);


    }

    public void getAllSlots(String user, int vc, String expert, Callback callback){
         Call<ResponseBody> call = requestHelper.getAllSlots(user, getUserToken(),vc, expert);
         call.enqueue(callback);
    }

    private String getUserToken(){
        return USER_TOKEN;
    }
}
