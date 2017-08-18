package com.wb.droidvideostreamer;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnShowHideVideo;
    boolean flagToShowHide = true;
    // Put in your Video URL here
    private String VideoURL = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov";
    // Declare some variables
    private ProgressDialog pDialog;
    VideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout from video_main.xml
        setContentView(R.layout.activity_main);
        // Find your VideoView in your video_main.xml layout
        videoview = (VideoView) findViewById(R.id.VideoView);

        //initializing button and setting listener on it
        btnShowHideVideo = (Button) findViewById(R.id.btn_show_hide_video);
        btnShowHideVideo.setOnClickListener(this);

        // Execute StreamVideo AsyncTask
        new StreamVideo().execute();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_show_hide_video:

                //logic to show hide video
                if (flagToShowHide) {
                    flagToShowHide = false;
                    videoview.setAlpha(0f);
                } else {
                    flagToShowHide = true;
                    videoview.setAlpha(1f);
                }
                break;
        }//switch
    }//onClick

    // StreamVideo AsyncTask
    class StreamVideo extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressbar
            pDialog = new ProgressDialog(MainActivity.this);
            // Set progressbar title
            pDialog.setTitle("Android Video Streaming Tutorial");
            // Set progressbar message
            pDialog.setMessage("Buffering...");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            // Show progressbar
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            try {
                // Start the MediaController
                MediaController mediacontroller = new MediaController(
                        MainActivity.this);
                mediacontroller.setAnchorView(videoview);
                // Get the URL from String VideoURL
                Uri video = Uri.parse(VideoURL);
                videoview.setMediaController(mediacontroller);
                videoview.setVideoURI(video);
                videoview.setAlpha(1f);
                //  videoview.set

                videoview.requestFocus();
                videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    // Close the progress bar and play the video
                    public void onPrepared(MediaPlayer mp) {
                        pDialog.dismiss();
                        videoview.start();
                    }
                });
            } catch (Exception e) {
                pDialog.dismiss();
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

        }//onPostExecute

    }//StreamVideo

}//MainActivity
