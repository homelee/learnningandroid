package me.lianzhao.myapplication;

import android.animation.TimeAnimator;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.TextureView;

import net.chilicat.m3u8.Element;
import net.chilicat.m3u8.ParseException;
import net.chilicat.m3u8.Playlist;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class HlsPlayerActivity extends ActionBarActivity {

    @InjectView(R.id.playbackView)
    TextureView playbackView;


    private TimeAnimator mTimeAnimator = new TimeAnimator();

    // A utility that wraps up the underlying input and output buffer processing operations
    // into an east to use API.
    private MediaCodecWrapper mCodecWrapper;
    private MediaExtractor mExtractor = new MediaExtractor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hls_player);
        ButterKnife.inject(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTimeAnimator != null && mTimeAnimator.isRunning()) {
            mTimeAnimator.end();
        }

        if (mCodecWrapper != null) {
            mCodecWrapper.stopAndRelease();
            mExtractor.release();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_hls_player, menu);
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

    public void startPlayback() {

        // Construct a URI that points to the video resource that we want to play
//        Uri videoUri = Uri.parse("android.resource://"
//                + getPackageName() + "/"
//                + R.raw.vid_bigbuckbunny);


        try {

            String uri = getFirstPlaybackUri("http://walterebert.com/playground/video/hls/sintel-trailer.m3u8");

            //Uri videoUri = Uri.parse("http://walterebert.com/playground/video/hls/ts/480x2701.ts");
            Uri videoUri = Uri.parse(uri);

            // BEGIN_INCLUDE(initialize_extractor)
            mExtractor.setDataSource(this, videoUri, null);
            int nTracks = mExtractor.getTrackCount();

            // Begin by unselecting all of the tracks in the extractor, so we won't see
            // any tracks that we haven't explicitly selected.
            for (int i = 0; i < nTracks; ++i) {
                mExtractor.unselectTrack(i);
            }


            // Find the first video track in the stream. In a real-world application
            // it's possible that the stream would contain multiple tracks, but this
            // sample assumes that we just want to play the first one.
            for (int i = 0; i < nTracks; ++i) {
                // Try to create a video codec for this track. This call will return null if the
                // track is not a video track, or not a recognized video format. Once it returns
                // a valid MediaCodecWrapper, we can break out of the loop.
                mCodecWrapper = MediaCodecWrapper.fromVideoFormat(mExtractor.getTrackFormat(i),
                        new Surface(playbackView.getSurfaceTexture()));
                if (mCodecWrapper != null) {
                    mExtractor.selectTrack(i);
                    break;
                }
            }
            // END_INCLUDE(initialize_extractor)


            // By using a {@link TimeAnimator}, we can sync our media rendering commands with
            // the system display frame rendering. The animator ticks as the {@link Choreographer}
            // recieves VSYNC events.
            mTimeAnimator.setTimeListener(new TimeAnimator.TimeListener() {
                @Override
                public void onTimeUpdate(final TimeAnimator animation,
                                         final long totalTime,
                                         final long deltaTime) {

                    boolean isEos = ((mExtractor.getSampleFlags() & MediaCodec
                            .BUFFER_FLAG_END_OF_STREAM) == MediaCodec.BUFFER_FLAG_END_OF_STREAM);

                    // BEGIN_INCLUDE(write_sample)
                    if (!isEos) {
                        // Try to submit the sample to the codec and if successful advance the
                        // extractor to the next available sample to read.
                        boolean result = mCodecWrapper.writeSample(mExtractor, false,
                                mExtractor.getSampleTime(), mExtractor.getSampleFlags());

                        if (result) {
                            // Advancing the extractor is a blocking operation and it MUST be
                            // executed outside the main thread in real applications.
                            mExtractor.advance();
                        }
                    }
                    // END_INCLUDE(write_sample)

                    // Examine the sample at the head of the queue to see if its ready to be
                    // rendered and is not zero sized End-of-Stream record.
                    MediaCodec.BufferInfo out_bufferInfo = new MediaCodec.BufferInfo();
                    mCodecWrapper.peekSample(out_bufferInfo);

                    // BEGIN_INCLUDE(render_sample)
                    if (out_bufferInfo.size <= 0 && isEos) {
                        mTimeAnimator.end();
                        mCodecWrapper.stopAndRelease();
                        mExtractor.release();
                    } else if (out_bufferInfo.presentationTimeUs / 1000 < totalTime) {
                        // Pop the sample off the queue and send it to {@link Surface}
                        mCodecWrapper.popSample(true);
                    }
                    // END_INCLUDE(render_sample)

                }
            });

            // We're all set. Kick off the animator to process buffers and render video frames as
            // they become available
            mTimeAnimator.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getFirstPlaybackUri(String hlsUri) throws IOException, ParseException {

        URL url = new URL(hlsUri);
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        Playlist playlist = Playlist.parse(inputStream);
        List<Element> elements = playlist.getElements();
        Element element = elements.get(0);
        URI elementURI = element.getURI();
        if (element.isMedia()) {
            return getAbsoluteUri(hlsUri, elementURI);
        }
        return getFirstPlaybackUri(getAbsoluteUri(hlsUri, elementURI));
    }

    private String getAbsoluteUri(String hlsUri, URI uri) {
        String result;
        if (uri.isAbsolute()) {
            result = uri.toString();
        } else {
            String substring = hlsUri.substring(0, hlsUri.lastIndexOf('/'));
            result = substring + "/" + uri.toString();
        }
        return result;
    }
}
