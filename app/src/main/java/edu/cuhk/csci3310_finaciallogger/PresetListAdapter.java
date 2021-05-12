package edu.cuhk.csci3310_finaciallogger;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedList;

public class PresetListAdapter extends RecyclerView.Adapter<PresetListAdapter.PresetViewHolder> {
    private Context context;
    private LayoutInflater mInflater;
    String[] mPresetItem,mPresetAmount,mPresetCategory;
    private String FILE_PATH = "/data/data/edu.cuhk.csci3310_finaciallogger/files/preset";
    private String FILE_PATH_dup = "/data/data/edu.cuhk.csci3310_finaciallogger/files/preset_dup";
    private String RECORD_FILE_PATH = "/data/data/edu.cuhk.csci3310_finaciallogger/files/record";
    private String RECORD_FILE_PATH_dup = "/data/data/edu.cuhk.csci3310_finaciallogger/files/record_dup";



    class PresetViewHolder extends RecyclerView.ViewHolder {


        final PresetListAdapter mAdapter;
        TextView presetAmountTextView, presetCategoryTextView;
        Button presetItemButton;
        FloatingActionButton deleteButton;

        public PresetViewHolder(View itemView, PresetListAdapter adapter) {
            super(itemView);
            presetItemButton = itemView.findViewById(R.id.PresetItem);
            presetAmountTextView = itemView.findViewById(R.id.PresetAmount);
            presetCategoryTextView = itemView.findViewById(R.id.PresetCategory);
            this.mAdapter = adapter;
            presetItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, InputActivity.class);
                    intent.putExtra("input", presetItemButton.getText());
                    intent.putExtra("PresetAmount", presetAmountTextView.getText());
                    intent.putExtra("PresetCategory", presetCategoryTextView.getText());
                    ((Activity)context).finish();
                    context.startActivity(intent);
                }

            });
            deleteButton=itemView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
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
                        fos = context.openFileOutput("preset",context.MODE_PRIVATE);
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
                        fosd = context.openFileOutput("preset_dup", context.MODE_PRIVATE);
                        while (((line = brd.readLine()) != null)) {
                            String output = line + "\n";
                            fosd.write(output.getBytes());
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Intent intent=new Intent(context,GameActivity.class);
                    ((Activity)context).startActivity(intent);


                }
            });


        }
    }

    public PresetListAdapter(Context context,
                             String[] mPresetItem,String[] mPresetAmount,String[] mPresetCategory) {
        mInflater = LayoutInflater.from(context);
        this.mPresetCategory=mPresetCategory;
        this.mPresetAmount=mPresetAmount;
        this.mPresetItem=mPresetItem;
        this.context=context;

    }

    @NonNull
    @Override
    public PresetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.presetlist_item, parent, false);
        return new PresetViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PresetViewHolder holder, final int position) {
        holder.presetItemButton.setText(mPresetItem[position]);
        holder.presetAmountTextView.setText(mPresetAmount[position]);
        holder.presetCategoryTextView.setText(mPresetCategory[position]);

    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mPresetCategory.length;
    }

}
