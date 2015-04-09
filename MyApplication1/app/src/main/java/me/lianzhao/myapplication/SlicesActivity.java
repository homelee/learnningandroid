package me.lianzhao.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SlicesActivity extends ActionBarActivity {

    @InjectView(R.id.ivSlice)
    ImageView ivSlice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_slices);
        ButterKnife.inject(this);
    }

    public void getImage(View view) {
        String requestUri = "";// todo
        new DownloadImageTask().execute(requestUri);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String uri = params[0];
            try {
                // todo
                return "https://www.baidu.com/img/bdlogo.png";
            } catch (Exception ex) {
                return ex.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                URL url = new URL(result);
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ivSlice.setImageBitmap(bitmap);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
