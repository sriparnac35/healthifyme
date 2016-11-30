package sriparna.hillhouse.com.healthifyme.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;

import org.w3c.dom.Text;

import sriparna.hillhouse.com.healthifyme.R;
import sriparna.hillhouse.com.healthifyme.fragments.ExpandableListsFragment;

/**
 * Created by sriparna on 29/11/16.
 */
public class SlotsExpandableListAdapter extends AbstractExpandableItemAdapter<GroupViewHolder,ChildViewHolder> {
    public interface Listener{
        void onGroupSelected(int group);
        void onChildSelected(int group, int child);
    }

    private ExpandableListsFragment.DataSource mDataSource ;
    private Listener mListener;
    private Context mContext;

    public SlotsExpandableListAdapter(Context context,
                                      ExpandableListsFragment.DataSource dataSource, Listener listener){
        mDataSource = dataSource;
        mListener = listener;
        mContext = context;
        setHasStableIds(true);
    }

    @Override
    public int getGroupCount() {
        return mDataSource.getNumberOfSections();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return mDataSource.getNumberOfItemsForSection(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 100* groupPosition + childPosition;
    }

    @Override
    public GroupViewHolder onCreateGroupViewHolder(ViewGroup parent,
                                                           @IntRange(from = -8388608L, to = 8388607L)
                                                           int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.slot_section,null);
        return new GroupViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent,
                                                           @IntRange(from = -8388608L, to = 8388607L)
                                                           int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.slot_item,null);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindGroupViewHolder(GroupViewHolder holder, final int groupPosition,
                                      @IntRange(from = -8388608L, to = 8388607L)
                                      int viewType) {
        holder.itemView.setClickable(true);
        Drawable drawable = Drawable.createFromPath(mDataSource.getImageForSection(groupPosition));
        holder.mImageView.setImageDrawable(drawable);

        holder.mTitle.setText(mDataSource.getTitleForSection(groupPosition));
        holder.mSubTitle.setText(mDataSource.getSubTitleForSection(groupPosition));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onGroupSelected(groupPosition);
            }
        });
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder,
                                      final int groupPosition, final int childPosition,
                                      @IntRange(from = -8388608L, to = 8388607L) int viewType) {
        holder.itemView.setClickable(true);
        holder.mLabel.setText(mDataSource.getTitleForItemInSection(groupPosition,childPosition));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onChildSelected(groupPosition, childPosition);
            }
        });
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(GroupViewHolder holder,
                                                   int groupPosition, int x, int y, boolean expand) {
        return false;
    }
}

class GroupViewHolder extends RecyclerView.ViewHolder{
     TextView mTitle ;
     TextView mSubTitle;
     ImageView mImageView;

    public GroupViewHolder(View itemView) {
        super(itemView);

        mTitle = (TextView)itemView.findViewById(R.id.text);
        mSubTitle = (TextView)itemView.findViewById(R.id.sub_text);
        mImageView = (ImageView)itemView.findViewById(R.id.image);
    }
}

class ChildViewHolder extends RecyclerView.ViewHolder{
    TextView mLabel;
    public ChildViewHolder(View itemView) {
        super(itemView);
        mLabel = (TextView)itemView.findViewById(R.id.text);
    }
}
