package com.example.myapplication;

import java.io.*;
import java.net.*;

public class Sender {


    public Sender() {
        // Replace with the receiver's IP address or "localhost" if on the same machine
        String hostname = "localhost";
        int port = 5000; // Port number

        try {
            // Create a socket connection
            Socket socket = new Socket(hostname, port);

            // Get output stream to send data
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // Message to send
            String message = "Hello from the Sender!";

            // Send the message
            out.writeUTF(message);

            //   System.out.println("Message sent to " + hostname + ":" + port);

            // Close the connection
            out.close();
            socket.close();

        } catch (UnknownHostException e) {
         //   System.err.println("Couldn't find host: " + hostname);
        } catch (IOException e) {
            //  System.err.println("Error sending message: " + e.getMessage());
        }
    }
}
