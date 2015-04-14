package me.lianzhao.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class VideoViewActivity extends ActionBarActivity {

    @InjectView(R.id.vv)
    VideoView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        ButterKnife.inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_video_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_play) {
            startPlayback();
            item.setEnabled(false);
        }
        return true;
    }

    private void startPlayback() {
        String uri = "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8";
        Uri videoUri = Uri.parse(uri);
        vv.setVideoURI(videoUri);
        vv.setMediaController(new MediaController(this));
        vv.requestFocus();
        vv.start();
    }

    private void stopPlayback() {
        vv.stopPlayback();
    }
}
