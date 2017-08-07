package com.usepjh92.user.myfamilyband.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.usepjh92.user.myfamilyband.R;
import com.usepjh92.user.myfamilyband.item.WriteItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class WriteActivity extends AppCompatActivity {

    EditText editDesc, editTitle;

    ArrayList<WriteItem> writeItems = new ArrayList<>();
    String insertUrl = "http://neworld.dothome.co.kr/android/InsertWrite.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        editDesc = (EditText) findViewById(R.id.edit_write);
        editTitle = (EditText) findViewById(R.id.edit_title);

    }

    public void clickExit(View v) {
        finish();
    }

    public void clickCom(View v) {

        new Thread() {

            @Override
            public void run() {

                String title = editTitle.getText().toString();
                String desc = editDesc.getText().toString();

                try {
                    title = URLEncoder.encode(title, "utf-8");
                    desc = URLEncoder.encode(desc, "utf-8");

                    URL url = new URL(insertUrl);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);

                    String data = "title=" + title + "&maindesc=" + desc;

                    OutputStream os = conn.getOutputStream();
                    os.write(data.getBytes());
                    os.flush();
                    os.close();

                    InputStream is = conn.getInputStream();

                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    final StringBuffer buffer = new StringBuffer();
                    String line = new String();

                    while (line != null) {
                        buffer.append(line);
                        line = reader.readLine();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(WriteActivity.this, buffer.toString(), Toast.LENGTH_SHORT).show();

                            editTitle.setText("");
                            editDesc.setText("");
                        }
                    });


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }.start();

        finish();
    }


}
