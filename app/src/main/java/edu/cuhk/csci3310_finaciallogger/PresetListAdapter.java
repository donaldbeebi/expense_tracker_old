package edu.cuhk.csci3310_finaciallogger;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PresetListAdapter extends RecyclerView.Adapter<PresetListAdapter.PresetViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    ArrayList<String> mPresetItem,mPresetAmount,mPresetCategory;
    DecimalFormat mFormatter;
    TextView mEmptyPresetMessage;
    private String FILE_PATH = "/data/data/edu.cuhk.csci3310_finaciallogger/files/preset";
    private String FILE_PATH_dup = "/data/data/edu.cuhk.csci3310_finaciallogger/files/preset_dup";
    private String RECORD_FILE_PATH = "/data/data/edu.cuhk.csci3310_finaciallogger/files/record";
    private String RECORD_FILE_PATH_dup = "/data/data/edu.cuhk.csci3310_finaciallogger/files/record_dup";

    class PresetViewHolder extends RecyclerView.ViewHolder {
        final PresetListAdapter mAdapter;
        TextView mPresetCategoryTextView;
        TextView mPresetTitleTextView;
        TextView mPresetAmountTextView;
        Button mPresetItemButton;
        ImageView mDeleteButton;

        public PresetViewHolder(View itemView, PresetListAdapter adapter) {
            super(itemView);
            mPresetTitleTextView = itemView.findViewById(R.id.PresetTitle);
            mPresetAmountTextView = itemView.findViewById(R.id.PresetAmount);
            mPresetCategoryTextView = itemView.findViewById(R.id.PresetCategory);

            mDeleteButton = itemView.findViewById(R.id.deleteButton);
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    FileInputStream is = null;
                    FileInputStream isd = null;
                    try {
                        isd = new FileInputStream(FILE_PATH_dup);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    BufferedReader br = new BufferedReader(new InputStreamReader(isd, Charset.forName("UTF-8")));
                    FileOutputStream fos = null;
                    String line = "";
                    try {
                        fos = mContext.openFileOutput("preset", mContext.MODE_PRIVATE);
                        int count = 0;
                        while (((line = br.readLine()) != null)) {
                            if (count==Integer.valueOf(position)){
                                count++;
                                continue;
                            }
                            String output = line + "\n";
                            fos.write(output.getBytes());
                            count++;
                        }
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    //overwrite duplicate file
                    try {
                        is = new FileInputStream(FILE_PATH);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    BufferedReader brd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    FileOutputStream fosd = null;
                    try {
                        fosd = mContext.openFileOutput("preset_dup", mContext.MODE_PRIVATE);
                        while (((line = brd.readLine()) != null)) {
                            String output = line + "\n";
                            fosd.write(output.getBytes());
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //quick fix
                    //local update -> no need to restart activity to see the deletion
                    mPresetItem.remove(getLayoutPosition());
                    mPresetAmount.remove(getLayoutPosition());
                    mPresetCategory.remove(getLayoutPosition());

                    //updating the message
                    if(mPresetItem.isEmpty()) mEmptyPresetMessage.setVisibility(View.VISIBLE);

                    notifyItemRemoved(getLayoutPosition());

                }
            });

            mPresetItemButton = itemView.findViewById(R.id.PresetItemSelectButton);
            mPresetItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, InputActivity.class);
                    intent.putExtra("input", mPresetTitleTextView.getText());
                    intent.putExtra("PresetAmount", mPresetAmountTextView.getText());
                    intent.putExtra("PresetCategory", mPresetCategoryTextView.getText());
                    mContext.startActivity(intent);
                }

            });
            this.mAdapter = adapter;
        }
    }

    public PresetListAdapter(Context context, ArrayList<String> presetItem, ArrayList<String> presetAmount, ArrayList<String> presetCategory, TextView emptyPresetMessage) {
        mInflater = LayoutInflater.from(context);
        mPresetCategory = presetCategory;
        mPresetAmount = presetAmount;
        mPresetItem = presetItem;
        mContext = context;
        mEmptyPresetMessage = emptyPresetMessage;
        mFormatter = new DecimalFormat("#,##0.0");
    }

    @NonNull
    @Override
    public PresetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.presetlist_item, parent, false);
        return new PresetViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PresetViewHolder holder, final int position) {
        holder.mPresetTitleTextView.setText(mPresetItem.get(position));
        holder.mPresetAmountTextView.setText("$" + mFormatter.format(Integer.parseInt(mPresetAmount.get(position))));
        holder.mPresetCategoryTextView.setText(mPresetCategory.get(position));
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mPresetCategory.size();
    }

    //custom function called when the preset fragment is resumed
    //this is to update the data set after adding a preset
    public void updateDataSet (ArrayList<String> presetItem, ArrayList<String> presetAmount, ArrayList<String> presetCategory) {
        mPresetCategory = presetCategory;
        mPresetAmount = presetAmount;
        mPresetItem = presetItem;
    }
}
