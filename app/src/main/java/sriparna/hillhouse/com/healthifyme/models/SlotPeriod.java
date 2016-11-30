package sriparna.hillhouse.com.healthifyme.models;


import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by sriparna on 27/11/16.
 */
public class SlotPeriod {
    private String slotName = "";
    private List<SlotItem> slots = null;

    public SlotPeriod(String name, List<SlotItem> slots){
        this.slotName = name;
        this.slots = slots;
    }

    private SlotPeriod(){

    }

    public String getSlotName() {
        return slotName;
    }

    public List<SlotItem> getSlots() {
        return slots;
    }

    public static SlotPeriod build(String slotName ,JSONArray jsonArray) throws IOException,
            ParseException, JSONException {
        SlotPeriod slot = new SlotPeriod();
        slot.slotName = slotName;
        slot.slots = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject object = jsonArray.getJSONObject(i);
            SlotItem item = SlotItem.build(object);
            slot.slots.add(item);
        }
        return slot;
    }

}