package edu.cuhk.csci3310_finaciallogger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpinningWheelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpinningWheelFragment extends Fragment {
    ImageView m_Wheel;
    TextView m_ResultText;
    private static final String[] m_Sectors = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SpinningWheelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpinningWheelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SpinningWheelFragment newInstance(String param1, String param2) {
        SpinningWheelFragment fragment = new SpinningWheelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spinning_wheel, container, false);

        m_Wheel = view.findViewById(R.id.spinning_wheel);
        m_ResultText = view.findViewById(R.id.result_text);
        Button spinButton = view.findViewById(R.id.spin_button);

        spinButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                spin(view);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void spin(View view) {
        Random random = new Random();
        int degree = random.nextInt(360);

        RotateAnimation rotateAnimation = new RotateAnimation(0, degree + 720,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setDuration(3000);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                m_ResultText.setText("You got " + getResult(degree) + "!");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        m_Wheel.startAnimation(rotateAnimation);
    }

    public String getResult(int degree) {
        //total degree 360 || 12 segment || 30 degree each segment
        int initialPoint = 0;
        int endPoint = 30;
        int i = 0;
        String res = null;

        do {
            if(degree >= initialPoint && degree < endPoint) {
                res = m_Sectors[i];
            }
            initialPoint += 30;
            endPoint += 30;
            i++;
        } while(res == null);
        return res;
    }
}