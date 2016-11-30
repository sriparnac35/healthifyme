package sriparna.hillhouse.com.healthifyme.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import sriparna.hillhouse.com.healthifyme.utils.Constants;

/**
 * Created by sriparna on 27/11/16.
 */
public final class SlotItem{
    private static final String TOKEN_SLOT_ID = "slot_id";
    private static final String TOKEN_IS_BOOKED = "is_booked";
    private static final String TOKEN_IS_EXPIRED = "is_expired";
    private static final String TOKEN_START_TIME = "start_time";
    private static final String TOKEN_END_TIME = "end_time";

    private static final SimpleDateFormat DATE_FORMAT
            = new SimpleDateFormat(Constants.EXPANDED_DATE_FORMAT);

    private static final int INVALID_SLOT_ID = -1;

    // fields
    private int slotID = INVALID_SLOT_ID;
    private boolean isBooked = false;
    private boolean isExpired = false;
    private Date startTime = null;
    private Date endTime = null;

    //generic constructor
    public SlotItem(int slotID, boolean isBooked, boolean isExpired, Date startTime, Date endTime){
        this.slotID = slotID;
        this.isBooked = isBooked;
        this.isExpired = isExpired;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private SlotItem(){

    }

    public static SlotItem build(JSONObject json) throws IOException, ParseException, JSONException {
        if(json == null){
            return null;
        }
        SlotItem item = new SlotItem();
        Iterator<String> keys = json.keys();

        while(keys.hasNext()){
            String name = keys.next();
            switch (name){
                case TOKEN_SLOT_ID:
                    item.slotID = json.getInt(name);
                    break;

                case TOKEN_IS_BOOKED:
                    item.isBooked = json.getBoolean(name);
                    break;

                case TOKEN_IS_EXPIRED:
                    item.isExpired = json.getBoolean(name);
                    break;

                case TOKEN_START_TIME:
                    item.startTime = DATE_FORMAT.parse(json.getString(name));
                    break;

                case TOKEN_END_TIME:
                    item.endTime = DATE_FORMAT.parse(json.getString(name));
                    break;
                default:
                    break;
            }
        }
        return item;
    }

    // GETTERS
    public int getSlotID() {
        return slotID;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

}
