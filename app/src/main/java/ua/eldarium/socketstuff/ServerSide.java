package ua.eldarium.socketstuff;

import android.util.Log;
import android.widget.TextView;

import java.io.*;
import java.net.*;

/**
 * Created by asus on 04.07.2016.
 */
public class ServerSide {

    private BufferedReader in = null;
    private PrintWriter out = null;
    private ServerSocket server = null;
    private Socket clientRef = null;

    public ServerSide(int port) {
        try {
            server = new ServerSocket(port);
            clientRef = server.accept();
            in  = new BufferedReader(new InputStreamReader(clientRef.getInputStream()));
            out = new PrintWriter(clientRef.getOutputStream(),true);
        }
        catch (Exception ex){
            Log.e("Server error", ex.getMessage());
        }

    }

    public void start() {
        Log.d("Server", "starting..");
        try {
            waitMsg();
            close();
        }
        catch (IOException ex){
            Log.e("Server error", ex.getMessage());
        }
    }

    private void waitMsg() throws IOException{
        String input;
        while((input=in.readLine())!=null){
            out.println(input);
            Log.e("Server got message", input);
        }
    }

    public void close() throws  IOException{
        in.close();
        out.close();
        clientRef.close();
        server.close();
    }

}
