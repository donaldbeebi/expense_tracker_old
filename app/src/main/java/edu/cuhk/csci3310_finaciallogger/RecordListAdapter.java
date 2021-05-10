package edu.cuhk.csci3310_finaciallogger;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.RecordViewHolder> {

    String[] mRecordItem,mRecordAmount,mRecordCategory,mRecordDate;
    private LayoutInflater mInflater;
    private Context mContext;

    class RecordViewHolder extends RecyclerView.ViewHolder{
        final RecordListAdapter mAdapter;
        TextView itemTextView, amountTextView,dateTextView,categoryTextView;
        public RecordViewHolder(View itemView, RecordListAdapter adapter){
            super(itemView);
            itemTextView=itemView.findViewById(R.id.recordItem);
            dateTextView=itemView.findViewById(R.id.recordDate);
            amountTextView=itemView.findViewById(R.id.recordAmount);
            categoryTextView=itemView.findViewById(R.id.recordCategory);
            this.mAdapter=adapter;


        }

    }



    public RecordListAdapter(Context context,
                             String[] recordItem,String[] recordAmount,String[] recordDate,String[] recordCategory) {
        mInflater = LayoutInflater.from(context);
        this.mRecordItem = recordItem;
        this.mRecordCategory = recordCategory;
        this.mRecordAmount = recordAmount;
        this.mRecordDate = recordDate;
        this.mContext= context;

    }


    public RecordListAdapter.RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.recordlist_item, parent, false);
        return new RecordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordListAdapter.RecordViewHolder holder, int position) {
        // Update the following to display correct information based on the given position

        // Set up View items for this row (position), modify to show correct information read from the CSV
        holder.itemTextView.setText(mRecordItem[position]);
        holder.dateTextView.setText(mRecordDate[position]);
        holder.categoryTextView.setText(mRecordCategory[position]);
        holder.amountTextView.setText(mRecordAmount[position]);

    }

    @Override
    public int getItemCount() {
        return mRecordItem.length;
    }
}
