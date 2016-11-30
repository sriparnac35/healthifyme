package sriparna.hillhouse.com.healthifyme.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import sriparna.hillhouse.com.healthifyme.adapters.SlotsExpandableListAdapter;

/**
 * Created by sriparna on 29/11/16.
 */
public class ExpandableListsFragment extends Fragment implements
        RecyclerViewExpandableItemManager.OnGroupCollapseListener,
        RecyclerViewExpandableItemManager.OnGroupExpandListener, SlotsExpandableListAdapter.Listener{


    public interface DataSource{
        int getNumberOfSections();
        String getTitleForSection(int section);
        String getImageForSection(int section);
        String getSubTitleForSection(int section);

        int getNumberOfItemsForSection(int section);
        String getTitleForItemInSection(int section, int item);

        boolean isItemInSectionEnabled(int section, int item);
    }

    public interface Listener{
        void onItemSelected(int section, int item);
    }

    private DataSource mDataSource = null;
    private Listener mListener = null;
    private RecyclerView mRecyclerView = null;
    private RecyclerViewExpandableItemManager mItemManager = null;
    private RecyclerView.Adapter mRecyclerViewAdapter = null;

    public void setDataSource(DataSource dataSource){
        this.mDataSource = dataSource;
    }

    public void setListener(Listener listener){
        this.mListener = listener;
    }

    public void reload(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRecyclerView = new RecyclerView(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final GeneralItemAnimator animator = new RefactoredDefaultItemAnimator();
        animator.setSupportsChangeAnimations(true);

        mRecyclerView.setItemAnimator(animator);
        mRecyclerView.setHasFixedSize(false);

        mItemManager = new RecyclerViewExpandableItemManager(null);
        mItemManager.setOnGroupExpandListener(this);
        mItemManager.setOnGroupCollapseListener(this);

       mRecyclerViewAdapter =
                mItemManager.createWrappedAdapter(
                        new SlotsExpandableListAdapter(getActivity(), mDataSource, this));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mItemManager.attachRecyclerView(mRecyclerView);

        return mRecyclerView;
    }

    @Override
    public void onGroupSelected(int group) {
        if (mItemManager.isGroupExpanded(group)){
            mItemManager.collapseGroup(group);
        }
        else{
            mItemManager.collapseAll();
            mItemManager.expandGroup(group);
        }
    }

    @Override
    public void onChildSelected(int group, int child) {

    }

    @Override
    public void onGroupCollapse(int groupPosition, boolean fromUser) {
    }

    @Override
    public void onGroupExpand(int groupPosition, boolean fromUser) {

    }
}
