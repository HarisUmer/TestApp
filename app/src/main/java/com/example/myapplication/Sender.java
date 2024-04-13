package com.example.myapplication;

import android.graphics.ImageFormat;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageProxy;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Sender {

    public Socket socket  = null;
    private static final Lock lock = new ReentrantLock();

    public int  Sender11( @NonNull ImageProxy image) {
        // Replace with the receiver's IP address or "localhost" if on the same machine
        try {

            String hostname = "192.168.0.115";
            int port = 55110; // Port number
            if(this.socket == null) {
                this.socket = new Socket(hostname, port);
            }
            byte[] b1 = imageProxyToByteArray(image);
            OutputStream out = socket.getOutputStream();

            OutputStream outputStream = socket.getOutputStream();

            // Send the image data
            Log.d("ByteArray", "b1: " + b1.length);
            byte[] sizeBytes = ByteBuffer.allocate(4).putInt(b1.length).array();
            outputStream.write(sizeBytes);
            outputStream.write(b1);
            outputStream.flush();

            Log.d("Massage tag", "Message Sent");

            // Close the connection

            return 1;

        } catch (UnknownHostException e) {
            Log.d("socket" ,"cant find host");
            return 0;
         //   System.err.println("Couldn't find host: " + hostname);
        } catch (IOException e) {
            Log.d("socket" ,"Error Sending message");
            return -1;
            //  System.err.println("Error sending message: " + e.getMessage());
        }
    }

    public  int sendImage(ImageProxy image) {
        return this.Sender11(image);
    }

    public byte[] imageProxyToByteArray(ImageProxy imageProxy) {
        // Check if the format is YUV_420_888 (which is usually the case)
        if (imageProxy.getFormat() == ImageFormat.YUV_420_888) {
            ByteBuffer yBuffer = imageProxy.getPlanes()[0].getBuffer();
            ByteBuffer vuBuffer = imageProxy.getPlanes()[2].getBuffer();

            int ySize = yBuffer.remaining();
            int vuSize = vuBuffer.remaining();

            // Create a new byte array that will hold the YUV data
            byte[] nv21 = new byte[ySize + vuSize];

            // Transfer the data from the byte buffers to the byte array
            yBuffer.get(nv21, 0, ySize);
            vuBuffer.get(nv21, ySize, vuSize);

            return nv21;
        } else {
            // Handle other image formats if necessary
            throw new UnsupportedOperationException("Unsupported image format: " + imageProxy.getFormat());
        }
    }
}
