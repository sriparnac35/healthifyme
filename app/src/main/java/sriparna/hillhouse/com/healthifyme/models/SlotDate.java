package sriparna.hillhouse.com.healthifyme.models;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import sriparna.hillhouse.com.healthifyme.utils.Constants;

/**
 * Created by sriparna on 27/11/16.
 */
public class SlotDate {
    private Date date = null;
    private List<SlotPeriod> slotPeriods;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(Constants.COMPACT_DATE_FORMAT);

    public SlotDate(Date date, List<SlotPeriod> slotPeriods){
        this.date = date;
        this.slotPeriods = slotPeriods;
    }

    private SlotDate(){

    }

    public Date getDate() {
        return date;
    }

    public List<SlotPeriod> getSlotPeriods() {
        return slotPeriods;
    }

    public static SlotDate build(String date, JSONObject json) throws IOException,
            ParseException, JSONException {
        if (json == null){
            return null;
        }
        SlotDate slotDate = new SlotDate();
        slotDate.date = DATE_FORMAT.parse(date);
        slotDate.slotPeriods = new LinkedList<>();

        Iterator<String> keys = json.keys();
        while(keys.hasNext()){
            String key = keys.next();
            JSONArray jsonObject = json.getJSONArray(key);

            SlotPeriod slotPeriod = SlotPeriod.build(key, jsonObject);
            slotDate.slotPeriods.add(slotPeriod);
        }
        return slotDate;
    }

}
