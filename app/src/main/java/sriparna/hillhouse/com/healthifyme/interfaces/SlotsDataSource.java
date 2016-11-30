package sriparna.hillhouse.com.healthifyme.interfaces;

import java.util.Date;
import java.util.List;

import sriparna.hillhouse.com.healthifyme.models.SlotItem;

/**
 * Created by sriparna on 27/11/16.
 */
public interface SlotsDataSource {
    int numberOfDaysAvailableForBooking(String user, String expert);
    int getNumberOfSlots(int dayNumber, String user, String expert);

    Date getSlotDate(int dayNumber, String user, String expert);
    String getSlotName(int dayNumber, int slotNumber, String user, String expert);

    List<SlotItem> getSlotDetails(int dayNumber, int slotNumber, String user, String expert);
}
