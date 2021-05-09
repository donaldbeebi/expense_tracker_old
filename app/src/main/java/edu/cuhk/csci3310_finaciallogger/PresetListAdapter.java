//package edu.cuhk.csci3310_finaciallogger;
//
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.LinkedList;
//
//public class PresetListAdapter extends RecyclerView.Adapter<PresetListAdapter.PresetViewHolder> {
//    private Context context;
//    private LayoutInflater mInflater;
//    String[] mPresetItem,mPresetAmount,mPresetCategory;
//
//
//    class PresetViewHolder extends RecyclerView.ViewHolder {
//
//
//        final PresetListAdapter mAdapter;
//        TextView presetItemTextView,presetAmountTextView,presetCategoryTextView;
//        public PresetViewHolder(View itemView, PresetListAdapter adapter) {
//                super(itemView);
//                presetItemTextView=itemView.findViewById(R.id.PresetItem);
//                presetAmountTextView=itemView.findViewById(R.id.PresetAmount);
//                presetCategoryTextView=itemView.findViewById(R.id.PresetCategory);
//                this.mAdapter=adapter;
//
//               }
//    }
//
//    public PresetListAdapter(Context context,
//                             String[] mPresetItem,String[] mPresetAmount,String[] mPresetCategory) {
//        mInflater = LayoutInflater.from(context);
//        this.mPresetCategory=mPresetCategory;
//        this.mPresetAmount=mPresetAmount;
//        this.mPresetItem=mPresetItem;
//        this.context=context;
//    }
//
//    @NonNull
//    @Override
//    public PresetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View mItemView = mInflater.inflate(R.layout.presetlist_item, parent, false);
//        return new PresetViewHolder(mItemView, this);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PresetViewHolder holder, final int position) {
//        // Update the following to display correct information based on the given position
//
//        // Set up View items for this row (position), modify to show correct information read from the CSV
//        holder.presetItemTextView.setText(mPresetItem[position]);
//        holder.presetAmountTextView.setText(mPresetAmount[position]);
//        holder.presetCategoryTextView.setText(mPresetCategory[position]);
//
//    }
//
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public int getItemCount() {
//        return mPresetCategory.length;
//    }
//
//}
