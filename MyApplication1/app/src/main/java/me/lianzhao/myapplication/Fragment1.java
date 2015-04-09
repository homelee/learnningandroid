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
    @InjectView(R.id.middleView)
    View middleView;

    private int leftColor;
    private int rightColor;
    private int middleColor;
    private OnMiddleColorChangedListener onMiddleColorChangedListener;

    public int getMiddleColor() {
        return middleColor;
    }

    @SuppressWarnings("UnusedDeclaration")
    public int getRightColor() {
        return rightColor;
    }

    @SuppressWarnings("UnusedDeclaration")
    public int getLeftColor() {
        return leftColor;
    }

    public void setLeftColor(int leftColor) {
        this.leftColor = leftColor;
        leftView.setBackgroundColor(leftColor);
        setMiddleColor();
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setRightColor(int rightColor) {
        this.rightColor = rightColor;
        rightView.setBackgroundColor(rightColor);
        setMiddleColor();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnMiddleColorChangedListener) {
            onMiddleColorChangedListener = (OnMiddleColorChangedListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        ButterKnife.inject(this, view);

        leftView.setBackgroundColor(leftColor);
        rightView.setBackgroundColor(rightColor);
        middleView.setBackgroundColor(middleColor);

        return view;
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);

        TypedArray typedArray = activity.obtainStyledAttributes(attrs, R.styleable.Fragment1);
        try {
            leftColor = typedArray.getColor(R.styleable.Fragment1_leftColor, 0);
            rightColor = typedArray.getColor(R.styleable.Fragment1_rightColor, 0);
            middleColor = leftColor + rightColor;
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void setMiddleColor() {
        middleColor = leftColor + rightColor;
        middleView.setBackgroundColor(middleColor);
        OnMiddleColorChangedListener listener = onMiddleColorChangedListener;
        if (listener != null) {
            listener.onMiddleColorChanged();
        }
    }

    public interface OnMiddleColorChangedListener {
        void onMiddleColorChanged();
    }
}
