package com.example.unzipped_apk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Activity_Menu extends AppCompatActivity
{
    private MaterialButton menu_BTN_start;
    private TextInputEditText menu_EDT_id;

    public Activity_Menu() {
        super();
    }

//    static /* synthetic */ TextInputEditText access$100(final Activity_Menu activity_Menu) {
//        return activity_Menu.menu_EDT_id;
//    }

    private void findViews() {
        this.menu_BTN_start = (MaterialButton)this.findViewById(R.id.menu_BTN_start);
        this.menu_EDT_id = (TextInputEditText)this.findViewById(R.id.menu_EDT_id);
    }

    public String getJSON(String spec) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader br = null;
        InputStreamReader in = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(spec);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            in = new InputStreamReader(httpURLConnection.getInputStream());
            br = new BufferedReader(in);
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                StringBuilder sb2 = new StringBuilder();
                sb.append(sb2.append(line).append("\n").toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                    br = null;
                }
                if (in != null) {
                    in.close();
                    in = null;
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                    httpURLConnection = null;
                }
            } catch (IOException ex) {
                Log.e("pttt", "getJSON exception: " + ex.getMessage());
            }

        }
        return sb.toString();
    }

    private void initViews() {
        this.menu_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeServerCall();
            }
        });
    }

    private void makeServerCall() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String json = getJSON(getResources().getString(R.string.url));
                Log.d("pttt", json);
                if (json != null) {
                    startGame(menu_EDT_id.getText().toString(), json);
                }
            }
        }).start();
    }

    private void startGame(final String s, String s2) {
//        s2 = "California,Texas,Florida,New York,Illinois,Pennsylvania,Ohio,Washington,Michigan,Arizona";
        s2 = s2.split(",")[Integer.valueOf(String.valueOf(s.charAt(7)))];
        final Intent intent = new Intent(this.getBaseContext(), Activity_Game.class);
        intent.putExtra("EXTRA_ID", s);
        intent.putExtra("EXTRA_STATE", s2);
        this.startActivity(intent);
    }

    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_menu);
        this.findViews();
        this.initViews();
    }
}
