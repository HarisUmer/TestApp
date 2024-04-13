package com.example.myapplication;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class camera extends AppCompatActivity {
    Button b1, b2;
    private static final String[] REQUIRED_PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    private static final int REQUEST_CODE_PERMISSIONS = 10;
    private PreviewView previewView;
    private ImageCapture imageCapture;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    private static final Lock lock = new ReentrantLock();
    PreviewView Prev;
    ImageCapture IC;
    ProcessCameraProvider p1;
    Context cont = this;
    Socket socket;
    final Sender s1 = new Sender();;
    ListenableFuture<ProcessCameraProvider> CameraProviderFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Find the capture button view
        Button captureButton = findViewById(R.id.button);

        Prev = findViewById(R.id.preview);



        try {
            CameraProviderFuture = ProcessCameraProvider.getInstance(this);
            CameraProviderFuture.addListener(() -> {
                try {
                    p1 = CameraProviderFuture.get();
                    if (p1 != null) {
                        startCameraX(p1);
                    } else {
                        // Handle case where ProcessCameraProvider is null
                        Toast.makeText(this, "Unable to initialize camera", Toast.LENGTH_SHORT).show();
                    }
                } catch (ExecutionException | InterruptedException e) {
                    // Handle exceptions
                    e.printStackTrace();
                }
            }, ContextCompat.getMainExecutor(this));
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }


        // Request camera permissions if necessary
        if (allPermissionsGranted()) {
            startCameraX(p1);
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(camera.this, "Capture clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCameraX(p1);
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    private void startCameraX(ProcessCameraProvider p1) {
        if (p1 != null) {
            p1.unbindAll();
            CameraSelector c1 = new CameraSelector.Builder().requireLensFacing(
                    CameraSelector.LENS_FACING_BACK
            ).build();
            Preview newPrev = new Preview.Builder().build();
            newPrev.setSurfaceProvider(Prev.getSurfaceProvider());
            IC = new ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build();

            ImageAnalysis ImangeProcess = new ImageAnalysis.Builder().setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
            ImangeProcess.setAnalyzer(getMainExecutor(), new ImageAnalysis.Analyzer() {
                @Override
                public void analyze(@NonNull ImageProxy image) {


                    Log.d("ImageAnalysis", "Image format: " );
                     new Thread(new Runnable() {
                        @Override
                        public void run() {
                            lock.lock();
                            // Call the sendImage method of Sender class
                            int result = s1.sendImage(image);

                            // Handle the result if needed
                            if (result == 1) {
                                Log.d("ImageAnalysis", "Image sent successfully");
                            } else {
                                Log.d("ImageAnalysis", "Failed to send image");
                            }

                            lock.unlock();
                        }
                    }).start();


                }
            });

            p1.bindToLifecycle(this, c1, newPrev, IC, ImangeProcess);

        } else {
            Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();

        }
    }





};

