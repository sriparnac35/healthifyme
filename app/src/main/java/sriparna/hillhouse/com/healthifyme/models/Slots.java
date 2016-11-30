package sriparna.hillhouse.com.healthifyme.models;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sriparna on 27/11/16.
 */
public class Slots {
    private List<SlotDate> slots = null;

    public Slots(List<SlotDate> slots){
        this.slots = slots;
    }

    private Slots(){

    }

    public List<SlotDate> getSlots(){
        return slots;
    }

    public static Slots build(JSONObject json) throws IOException, ParseException, JSONException {
        if (json == null){
            return null;
        }

        Slots slots = new Slots();
        slots.slots = new LinkedList<>();

        Iterator<String> keys = json.keys();
        while(keys.hasNext()){
            String key = keys.next();
            JSONObject object = json.getJSONObject(key);
            SlotDate slotDate = SlotDate.build(key, object);
            slots.slots.add(slotDate);
        }
        return slots;
    }
}
