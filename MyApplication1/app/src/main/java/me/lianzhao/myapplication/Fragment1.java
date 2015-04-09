package me.lianzhao.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment1 extends Fragment {

    private int _leftColor;
    private int _rightColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        View leftView = view.findViewById(R.id.leftView);
        View rightView = view.findViewById(R.id.rightView);
        leftView.setBackgroundColor(_leftColor);
        rightView.setBackgroundColor(_rightColor);

        return view;
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);

        TypedArray typedArray = activity.obtainStyledAttributes(attrs, R.styleable.Fragment1);
        try {
            _leftColor = typedArray.getColor(R.styleable.Fragment1_leftColor, 0);
            _rightColor = typedArray.getColor(R.styleable.Fragment1_rightColor, 0);
        } finally {
            typedArray.recycle();
        }
    }
}
