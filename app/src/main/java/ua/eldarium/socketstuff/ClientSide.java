package ua.eldarium.socketstuff;

import android.util.Log;

import java.io.*;
import java.net.*;

/**
 * Created by asus on 04.07.2016.
 */
public class ClientSide {

    private Socket serverRef;
    private BufferedReader in;
    private PrintWriter out;


    public ClientSide(String hostname, int port){
        try {
            serverRef = new Socket(hostname, port);
            in = new BufferedReader(new InputStreamReader(serverRef.getInputStream()));
            out = new PrintWriter(serverRef.getOutputStream(),true);
        }
        catch (Exception ex){
            Log.e("Client error", ex.getMessage());
        }
    }

    public String sendMsg(String msg){
        out.println(msg);
        try {
            return in.readLine();
        }
        catch (Exception ex){
            Log.e("Client error", ex.getMessage());
            return null;
        }
    }
}
