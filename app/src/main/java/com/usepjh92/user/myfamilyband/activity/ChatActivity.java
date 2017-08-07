package com.usepjh92.user.myfamilyband.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.usepjh92.user.myfamilyband.R;
import com.usepjh92.user.myfamilyband.item.MsgItem;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    LinearLayout linearLayout;

    ArrayList<MsgItem> msgItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        linearLayout = (LinearLayout)findViewById(R.id.add_menu);

    }


    public void clickExit(View v){
        finish();
    }

    public void clickPlus(View v){

        linearLayout.setVisibility(View.VISIBLE);

    }

    public void clickSend(View v){

    }

    public void clickGallery(View v){

    }

    public void clickMovie(View v){

    }

    public void clickCamera(View v){

    }


}
