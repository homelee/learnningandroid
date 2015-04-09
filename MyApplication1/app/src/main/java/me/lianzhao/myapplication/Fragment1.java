package me.lianzhao.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Fragment1 extends Fragment {

    @InjectView(R.id.leftView)
    View leftView;
    @InjectView(R.id.rightView)
    View rightView;

    private int leftColor;
    protected int rightColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        ButterKnife.inject(this, view);

        leftView.setBackgroundColor(leftColor);
        rightView.setBackgroundColor(rightColor);

        return view;
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);

        TypedArray typedArray = activity.obtainStyledAttributes(attrs, R.styleable.Fragment1);
        try {
            leftColor = typedArray.getColor(R.styleable.Fragment1_leftColor, 0);
            rightColor = typedArray.getColor(R.styleable.Fragment1_rightColor, 0);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
