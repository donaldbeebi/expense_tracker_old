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
//
//    private final LinkedList<String> mImagePathList;
//    private final LinkedList<String> mNameList;
//    private final LinkedList<String> mGenuesList;
//    private final LinkedList<Integer> mRichnessList;
//
//
//    class PresetViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView flowerImageItemView, richnessImageItemView,richnessImageItemView2,richnessImageItemView3;
//        TextView nameTextView, wikiTextView;
//
//        final PresetListAdapter mAdapter;
//
//        public PresetViewHolder(View itemView, PresetListAdapter adapter) {
//            super(itemView);
//            flowerImageItemView = itemView.findViewById(R.id.image);
//            nameTextView = itemView.findViewById(R.id.nameText);
//            wikiTextView = itemView.findViewById(R.id.wikiText);
//            richnessImageItemView = itemView.findViewById(R.id.richnessImageView);
//            richnessImageItemView2 = itemView.findViewById(R.id.richnessImageView2);
//            richnessImageItemView3 = itemView.findViewById(R.id.richnessImageView3);
//
//
//            this.mAdapter = adapter;
//
//            // Event handling registration, page navigation goes here
//            wikiTextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position=getAdapterPosition();
//                    Intent wikiIntent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/"+mGenuesList.get(position)));
//                    context.startActivity(wikiIntent);
//                }
//            });
//            flowerImageItemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position=getAdapterPosition();
//                    Intent intent=new Intent(context,MainActivity2.class);
//                    String mImagePath = mImagePathList.get(position);
//                    intent.putExtra("flowerImagePath",mImagePath);
//                    intent.putExtra("flowerName",mNameList.get(position));
//                    intent.putExtra("genus",mGenuesList.get(position));
//                    intent.putExtra("richness",mRichnessList.get(position));
//                    intent.putExtra("position",position);
//                    ((Activity)context).startActivityForResult(intent,2);
//
//                }
//            });
//
//
//            // End of ViewHolder initialization
//        }
//    }
//
//    public PresetListAdapter(Context context,
//                             LinkedList<String> imagePathList, LinkedList<String> mNameList, LinkedList<String> mGenusList, LinkedList<Integer> mRichnessList) {
//        mInflater = LayoutInflater.from(context);
//        this.mImagePathList = imagePathList;
//        this.mNameList = mNameList;
//        this.mGenuesList = mGenusList;
//        this.context=context;
//        this.mRichnessList = mRichnessList;
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
//        String mImagePath = mImagePathList.get(position);
//        Uri uri = Uri.parse(mImagePath);
//        // Update the following to display correct information based on the given position
//
//        // Set up View items for this row (position), modify to show correct information read from the CSV
//        holder.flowerImageItemView.setImageURI(uri);
//        holder.nameTextView.setText(mNameList.get(position));
//        holder.wikiTextView.setText(mGenuesList.get(position)+"@wikipedia");
//
//        if (mRichnessList.get(position)==1) {
//            holder.richnessImageItemView2.setVisibility(View.INVISIBLE);
//            holder.richnessImageItemView3.setVisibility(View.INVISIBLE);
//        }
//        if (mRichnessList.get(position)==2) {
//            holder.richnessImageItemView3.setVisibility(View.INVISIBLE);
//        }
//
//
//    }
//
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public int getItemCount() {
//        return mImagePathList.size();
//    }
//
//}
