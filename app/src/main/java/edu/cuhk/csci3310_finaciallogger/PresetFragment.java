package edu.cuhk.csci3310_finaciallogger;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import edu.cuhk.csci3310_finaciallogger.game.PresetDataReader;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PresetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PresetFragment extends Fragment {

    // TODO: Rename and change types of parameters
    //this only used to instantiate the adapter
    private String[] mPresetItem, mPresetAmount,mPresetCategory;
    private RecyclerView mRecyclerView;
    private PresetListAdapter mAdapter;
    private Context mContext;
    private TextView mEmptyPresetMessage;

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
        mEmptyPresetMessage = getActivity().findViewById(R.id.empty_preset_message);

        if(mPresetItem.length == 0) {
            mEmptyPresetMessage.setVisibility(View.VISIBLE);
        }
        else {
            mEmptyPresetMessage.setVisibility(View.INVISIBLE);
        }

        //quick fix
        //locally converting String[] to ArrayList<String> for easy element removal
        mAdapter = new PresetListAdapter(
                mContext,
                new ArrayList<String>(Arrays.asList(mPresetItem)),
                new ArrayList<String>(Arrays.asList(mPresetAmount)),
                new ArrayList<String>(Arrays.asList(mPresetCategory)),
                mEmptyPresetMessage);
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

    //called to update the adapter
    public void updateDataSet() {
        PresetDataReader reader = PresetDataReader.getInstance();
        reader.loadData(getContext(), getResources());
        HashMap<String, ArrayList<String>> data = reader.getData();
        mAdapter.updateDataSet(data.get("preset_list"), data.get("preset_list_amount"), data.get("preset_list_category"));
        mAdapter.notifyDataSetChanged();
        if(data.get("preset_list").size() == 0) {
            mEmptyPresetMessage.setVisibility(View.VISIBLE);
        }
        else {
            mEmptyPresetMessage.setVisibility(View.INVISIBLE);
        }
    }

}