package ua.eldarium.socketstuff;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int portNumber=7777;
    private ServerSide server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final TextView tv =  (TextView)findViewById(R.id.textServer);

        Button createServer = (Button) findViewById(R.id.createServer);
        createServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("");
                if(server==null)
                    server = new ServerSide(portNumber);
                server.start();
            }
        });

        Button sendMsg = (Button) findViewById(R.id.sendSocket);

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClientSide client = new ClientSide("localhost",portNumber);
                client.sendMsg("asdfgh");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.action_settings: {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                final EditText portNum = new EditText(MainActivity.this);
                portNum.setText(String.valueOf(portNumber));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                portNum.setLayoutParams(params);
                dialogBuilder.setView(portNum);

                dialogBuilder.setTitle(R.string.set_port);
                dialogBuilder.setNeutralButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String portString = String.valueOf(portNum.getText());
                        Integer portInt;
                        try {
                            portInt = Integer.parseInt(portString);
                            if (portInt > 0 && portInt < 65536) {
                                portNumber = portInt;
                                Toast.makeText(MainActivity.this, R.string.set_port_success, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this,R.string.set_port_wrong_input,Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.e("set port", e.getMessage());
                            Toast.makeText(MainActivity.this, R.string.set_port_wrong_input, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            }

        }

        return super.onOptionsItemSelected(item);
    }
}
