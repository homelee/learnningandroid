package me.lianzhao.myapplication;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class FragmentsActivity extends ActionBarActivity implements Fragment1.OnMiddleColorChangedListener {

    @InjectView(R.id.tvMiddle)
    TextView tvMiddle;
    private Fragment1 fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);
        ButterKnife.inject(this);

        FragmentManager fragmentManager = getFragmentManager();
        fragment1 = (Fragment1) fragmentManager.findFragmentById(R.id.fragment1);
    }

    public void setLeftToRed(View view) {
        fragment1.setLeftColor(Color.RED);
    }

    public void setLeftToGreen(View view) {
        fragment1.setLeftColor(Color.GREEN);
    }

    public void setLeftToBlue(View view) {
        fragment1.setLeftColor(Color.BLUE);
    }

    @Override
    public void onMiddleColorChanged() {
        tvMiddle.setText(Integer.toString(fragment1.getMiddleColor()));
    }
}
