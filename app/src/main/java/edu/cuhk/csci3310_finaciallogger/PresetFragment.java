package edu.cuhk.csci3310_finaciallogger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PresetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PresetFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private String[] mPresetItem, mPresetAmount,mPresetCategory;
    private RecyclerView mRecyclerView;
    private PresetListAdapter mAdapter;
    private Context mContext;

    public PresetFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PresetFragment newInstance(String[] mPresetItem,String[] mPresetAmount,String[] mPresetCategory) {
        PresetFragment fragment = new PresetFragment();
        Bundle args = new Bundle();
        args.putStringArray("presetItemList", mPresetItem);
        args.putStringArray("presetAmountList", mPresetAmount);
        args.putStringArray("presetCategoryList", mPresetCategory);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.d(LOG_TAG,"Arguments not null");
            if (mPresetItem==null){
                Log.d(LOG_TAG,"getting items");
            }
            mPresetItem = getArguments().getStringArray(String.valueOf(mPresetItem));
            mPresetAmount = getArguments().getStringArray(String.valueOf(mPresetAmount));
            mPresetCategory = getArguments().getStringArray(String.valueOf(mPresetCategory));

        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_preset, container, false);


        mRecyclerView = view.findViewById(R.id.PresetRecyclerview);
        mPresetItem = this.getArguments().getStringArray("presetItemList");
        mPresetAmount = this.getArguments().getStringArray("presetAmountList");
        mPresetCategory = this.getArguments().getStringArray("presetCategoryList");

        if (mPresetItem==null){
            Log.d(LOG_TAG,"noPresetItem");
        }

        mAdapter = new PresetListAdapter(mContext,mPresetItem,mPresetAmount,mPresetCategory);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

}