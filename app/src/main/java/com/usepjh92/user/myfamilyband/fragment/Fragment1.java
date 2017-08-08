package com.usepjh92.user.myfamilyband.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.usepjh92.user.myfamilyband.R;
import com.usepjh92.user.myfamilyband.activity.WriteActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;


public class Fragment1 extends Fragment {

    ImageView img;
    TextView tv;

    final int REQ_PICTURE = 10;
    Uri imgUri;
    String imgPath;
    String upLoadServerURL = "http://neworld.dothome.co.kr/android/Upload.php";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        img = (ImageView) view.findViewById(R.id.pro_img);
        tv = (TextView) view.findViewById(R.id.textView);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteActivity.class);
                startActivity(intent);
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQ_PICTURE);

                } else {

                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), REQ_PICTURE);

                }
            }
        });


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_PICTURE:
                if (resultCode != getActivity().RESULT_OK) return;
                //선택된 이미지의 정보를 가지고 있는 Uri 객체 얻어오기
                imgUri = data.getData();

                try {

                    Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUri);
                    img.setImageBitmap(bm);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        new Thread() {
            @Override
            public void run() {
               // upLoadFile();
            }
        }.start();


        super.onActivityResult(requestCode, resultCode, data);
    }

//    public String getPath(Uri uri) {
//        // uri가 null일경우 null반환
//        if( uri == null ) {
//            return null;
//        }
//        // 미디어스토어에서 유저가 선택한 사진의 URI를 받아온다.
//        String[] projection = { MediaStore.Images.Media.DATA };
//        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
//        if( cursor != null ){
//            int column_index = cursor
//                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        }
//        // URI경로를 반환한다.
//        return uri.getPath();
//    }





}
