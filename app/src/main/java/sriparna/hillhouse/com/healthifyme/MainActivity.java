package sriparna.hillhouse.com.healthifyme;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

import sriparna.hillhouse.com.healthifyme.adapters.SlotsAdapter;
import sriparna.hillhouse.com.healthifyme.datasource.SlotsFetcher;
import sriparna.hillhouse.com.healthifyme.interfaces.SlotsDataSource;
import sriparna.hillhouse.com.healthifyme.interfaces.SlotsListener;
import sriparna.hillhouse.com.healthifyme.utils.Utilities;

public class MainActivity extends AppCompatActivity {
    private TextView mMonthName = null;
    private ViewPager mViewPager = null;
    private TabLayout mTabs = null;

    private SlotsFetcher mDataSource = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMonthName = (TextView)findViewById(R.id.month_name);
        mViewPager = (ViewPager)findViewById(R.id.view_pager);
        mTabs = (TabLayout)findViewById(R.id.tabs);

        mTabs.setupWithViewPager(mViewPager);
        mTabs.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabs.setTabMode(TabLayout.MODE_SCROLLABLE);

        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                Date date = mDataSource.getSlotDate(tabPosition,
                        Utilities.getCurrentUser(), Utilities.getSelectedExpert());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int monthNumber = calendar.get(Calendar.MONTH);
                String month = DateFormatSymbols.getInstance().getMonths()[monthNumber];
                mMonthName.setText(month);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mDataSource = new SlotsFetcher();
        mDataSource.fetch(new SlotsListener() {
            @Override
            public void onDataChanged() {
                mViewPager.setAdapter(new SlotsAdapter(getSupportFragmentManager(), mDataSource));
            }
        });



    }
}
