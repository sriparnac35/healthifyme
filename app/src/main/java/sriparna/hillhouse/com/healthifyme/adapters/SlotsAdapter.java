package sriparna.hillhouse.com.healthifyme.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sriparna.hillhouse.com.healthifyme.fragments.ExpandableListsFragment;
import sriparna.hillhouse.com.healthifyme.interfaces.SlotsDataSource;
import sriparna.hillhouse.com.healthifyme.utils.Utilities;

/**
 * Created by sriparna on 29/11/16.
 */
public class SlotsAdapter extends FragmentStatePagerAdapter {
    private SlotsDataSource mDataSource;

    public SlotsAdapter(FragmentManager fm, SlotsDataSource dataSource) {
        super(fm);
        mDataSource = dataSource;
    }

    @Override
    public Fragment getItem(int position) {
        ExpandableListsFragment fragment = new ExpandableListsFragment();
        fragment.setDataSource(createDataSourceForFragmentInPosition(position));
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Date slotDate = mDataSource.getSlotDate(position, Utilities.getCurrentUser(),
                Utilities.getSelectedExpert());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(slotDate);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        String nameOfDay = DateFormatSymbols.getInstance().getShortWeekdays()[dayOfWeek];
        return String.valueOf(day).concat("  ").concat(nameOfDay);

    }

    private ExpandableListsFragment.DataSource createDataSourceForFragmentInPosition(final int position){
        ExpandableListsFragment.DataSource fragmentDataSource = new ExpandableListsFragment.DataSource() {
            @Override
            public int getNumberOfSections() {
                return mDataSource.getNumberOfSlots(position, Utilities.getCurrentUser(),
                        Utilities.getSelectedExpert());
            }

            @Override
            public String getTitleForSection(int section) {
                return mDataSource.getSlotName(position,section,
                        Utilities.getCurrentUser(), Utilities.getSelectedExpert());
            }

            @Override
            public String getImageForSection(int section) {
                return null;
            }

            @Override
            public String getSubTitleForSection(int section) {
                return null;
            }

            @Override
            public int getNumberOfItemsForSection(int section) {
                return mDataSource.getSlotDetails(position, section,
                        Utilities.getCurrentUser(), Utilities.getSelectedExpert()).size();
            }

            @Override
            public String getTitleForItemInSection(int section, int item) {
                return String.valueOf(mDataSource.getSlotDetails(position, section,
                        Utilities.getCurrentUser(), Utilities.getSelectedExpert()).get(item).getSlotID());
            }

            @Override
            public boolean isItemInSectionEnabled(int section, int item) {
                return true;
            }
        };

        return fragmentDataSource;
    }

    @Override
    public int getCount() {
        return mDataSource.numberOfDaysAvailableForBooking(Utilities.getCurrentUser()
                , Utilities.getSelectedExpert());
    }
}
