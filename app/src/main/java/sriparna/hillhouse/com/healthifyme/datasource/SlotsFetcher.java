package sriparna.hillhouse.com.healthifyme.datasource;

import android.util.JsonReader;

import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sriparna.hillhouse.com.healthifyme.api.RequestService;
import sriparna.hillhouse.com.healthifyme.interfaces.SlotsDataSource;
import sriparna.hillhouse.com.healthifyme.interfaces.SlotsListener;
import sriparna.hillhouse.com.healthifyme.models.SlotDate;
import sriparna.hillhouse.com.healthifyme.models.SlotItem;
import sriparna.hillhouse.com.healthifyme.models.Slots;
import sriparna.hillhouse.com.healthifyme.utils.Utilities;

/**
 * Created by sriparna on 27/11/16.
 */
public class SlotsFetcher implements SlotsDataSource {
    public WeakReference<SlotsListener> mListener = null;
    private RequestService mRequestService = new RequestService();
    private Slots mSlot = null;

    public void fetch(final SlotsListener listener){
        mRequestService
                .getAllSlots(Utilities.getCurrentUser(),
                        Utilities.getVC(),
                        Utilities.getSelectedExpert(), new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()){
                                    if (response == null || response.body() == null){
                                        return;
                                    }
                                    try {
                                        String responseString = response.body().string();
                                        JSONObject jsonObject = new JSONObject(responseString);
                                        Iterator<String> keys = jsonObject.keys();

                                        while(keys.hasNext()){
                                            String key = keys.next();
                                            JSONObject slotJson = jsonObject.getJSONObject(key);
                                            Slots slots = Slots.build(slotJson);
                                            mSlot = slots;
                                            break;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    catch (IOException e){
                                        e.printStackTrace();
                                    }
                                    catch (ParseException e){
                                        e.printStackTrace();
                                    }
                                    finally {
                                        listener.onDataChanged();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                logFailure(t.getMessage());
                            }
                        });
    }

    @Override
    public int numberOfDaysAvailableForBooking(String user, String expert) {
        if (mSlot == null) {
            return 0;
        }
        return mSlot.getSlots().size();
    }

    @Override
    public int getNumberOfSlots(int dayNumber, String user, String expert) {
        if (mSlot == null) {
            return 0;
        }
        if (dayNumber < 0 || dayNumber >= mSlot.getSlots().size()){
            return 0;
        }
        return mSlot.getSlots().get(dayNumber).getSlotPeriods().size();
    }

    @Override
    public List<SlotItem> getSlotDetails(int dayNumber, int slotNumber, String user, String expert){
        if (mSlot == null) {
            return null;
        }
        if (dayNumber < 0 || dayNumber >= mSlot.getSlots().size()){
            return null;
        }

        SlotDate slotDate =  mSlot.getSlots().get(dayNumber);
        if (slotNumber < 0 || slotNumber >= slotDate.getSlotPeriods().size()){
            return null;
        }
        return slotDate.getSlotPeriods().get(slotNumber).getSlots();
    }

    @Override
    public String getSlotName(int dayNumber, int slotNumber, String user, String expert){
        if (mSlot == null){
            return null;
        }
        if (dayNumber < 0 || dayNumber >= mSlot.getSlots().size()){
            return null;
        }

        SlotDate slotDate =  mSlot.getSlots().get(dayNumber);
        if (slotNumber < 0 || slotNumber >= slotDate.getSlotPeriods().size()){
            return null;
        }
        return slotDate.getSlotPeriods().get(slotNumber).getSlotName();
    }

    @Override
    public Date getSlotDate(int dayNumber, String user, String expert){
        if (mSlot == null){
            return null;
        }
        if (dayNumber < 0 || dayNumber >= mSlot.getSlots().size()){
            return null;
        }

        SlotDate slotDate =  mSlot.getSlots().get(dayNumber);
        return slotDate.getDate();
    }

    private void logFailure(String errorMessage){

    }

}
