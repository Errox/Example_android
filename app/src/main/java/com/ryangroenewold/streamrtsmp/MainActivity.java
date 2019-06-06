package com.ryangroenewold.streamrtsmp;

import android.Manifest;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pedro.encoder.input.video.CameraOpenException;
import com.pedro.rtplibrary.rtsp.RtspCamera1;
import com.pedro.rtplibrary.rtsp.RtspCamera2;
import com.pedro.rtplibrary.view.OpenGlView;
import com.pedro.rtsp.rtp.packets.AacPacket;
import com.pedro.rtsp.rtp.packets.BasePacket;
import com.pedro.rtsp.rtp.sockets.BaseRtpSocket;
import android.media.MediaCodec;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.pedro.rtsp.rtcp.BaseSenderReport;
import com.pedro.rtsp.rtp.packets.AacPacket;
import com.pedro.rtsp.rtp.packets.AudioPacketCallback;
import com.pedro.rtsp.rtp.packets.BasePacket;
import com.pedro.rtsp.rtp.packets.H264Packet;
import com.pedro.rtsp.rtp.packets.H265Packet;
import com.pedro.rtsp.rtp.packets.VideoPacketCallback;
import com.pedro.rtsp.rtp.sockets.BaseRtpSocket;
import com.pedro.rtsp.rtsp.Protocol;
import com.pedro.rtsp.rtsp.RtpFrame;
import com.pedro.rtsp.utils.ConnectCheckerRtsp;
import com.pedro.rtsp.utils.RtpConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements ConnectCheckerRtsp, View.OnClickListener, SurfaceHolder.Callback {

    ImageButton sendButton;
    EditText messageEditText;

    private static final String TAG = MainActivity.class.getSimpleName();

    private String URL;
    private RtspCamera1 rtspCamera1;

    private static final int CAMERA_REQUEST_CODE = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SurfaceView surfaceView = findViewById(R.id.surfaceView2);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);

        URL = "rtsp://145.49.52.101:8080/live/Yeet";

        surfaceView.getHolder().addCallback(this);
        rtspCamera1 = new RtspCamera1(surfaceView, this);
        Log.i(TAG, "onCreate: BITRATE" + rtspCamera1.getBitrate());

            if (rtspCamera1.prepareAudio() && rtspCamera1.prepareVideo()) {
                rtspCamera1.startStream(URL);
            } else {
                //If the camera audio and video feed can't be established
                //Try to switch to camera 2 if this happebns.
                //Always start feed at camera1
            }

    }

    @Override
    public void onConnectionSuccessRtsp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Connection success", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onConnectionFailedRtsp(final String reason) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Connection failed. " + reason, Toast.LENGTH_LONG).show();
                rtspCamera1.stopStream();
            }
        });
    }

    @Override
    public void onDisconnectRtsp() {

    }

    @Override
    public void onAuthErrorRtsp() {

    }

    @Override
    public void onAuthSuccessRtsp() {

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onClick(View v) {

    }
}
