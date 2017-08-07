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
                upLoadFile();
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


    public void upLoadFile() {

        final String lineEnd = "\r\n";
        final String twoHyphens = "--";
        final String boundary = "*****";

        imgPath = imgUri.toString();
//        imgPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).
//                getAbsolutePath() + "/Screenshots/Screenshot_20170624-184948.png";

//        if (imgPath.contains("content://")) {
        //갤러리 or 사진앱으로 선택했을시...
        //Cursor cursor = getActivity().getContentResolver().query(imgUri, null, null, null, null);
  //"_data"
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(imgUri, projection, null, null, null);
        if (imgPath.contains("content://")) {
            if (cursor != null && cursor.getCount() != 0) {
                if (cursor != null) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    imgPath = cursor.getString(column_index);

//                cursor.moveToFirst();
//                String[] cnt = cursor.getColumnNames();
//                Log.i("cnt", Arrays.toString(cnt));
//                Log.i("name", cursor.getString(0));
//                Log.i("name", cursor.getString(1));
//                Log.i("name", cursor.getString(2));
//                Log.i("name", cursor.getString(3));
//                Log.i("name", cursor.getString(4));
//                Log.i("name", cursor.getString(5));
                }
            }
        } else if (imgPath.contains("file://")) {
            //파일매니저로 선택했을시
            imgPath = imgUri.getPath();
        }

        Log.e("imgpath", imgPath);

        new Thread() {

            @Override
            public void run() {

                try {
                    URL url = new URL(upLoadServerURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);

                    //파일 전송의 헤더 영역 속성 설정...
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

                    //파일전송의 body 영역에 들어갈 Data 작성 및 Output
                    OutputStream os = conn.getOutputStream();
                    DataOutputStream dos = new DataOutputStream(os);

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition:form-data;name=uploaded_file;filename=" + imgPath + lineEnd);
                    dos.writeBytes(lineEnd);
                    //파일전송의 body 에 들어갈 실제 이미지의 byte 데이터 output
                    FileInputStream fis = new FileInputStream(imgPath);
                    int availableByte = fis.available(); // 보낼 이미지의 총 바이트수 얻어오기...
                    Log.i("avilable", availableByte + "");
                    //데이터의 전송은 1024 byte(1kb) 단위로 나누어서 보냄..
                    int bufferSize = Math.min(availableByte, 1024);
                    byte[] buffer = new byte[bufferSize];

                    //이미지 파일에서 buffer 배열로 이미지의 바이트데이터를 읽어오기
                    //현재 읽을 번째의 0번째부터 버퍼사이즈만큼 읽어오기
                    int readByte = fis.read(buffer, 0, bufferSize); //리턴값 읽어온 바이트 수.. 없으면 -1

                    while (readByte > 0) {
                        Log.i("bufferSize", buffer.length + "");
                        dos.write(buffer, 0, bufferSize); //파일 전송에 body 에 들어갈 Data 작성..
                        availableByte = fis.available(); // 읽어간 값을 제외한 나머지 바이트 수...
                        bufferSize = Math.min(availableByte, 1024);
                        readByte = fis.read(buffer, 0, bufferSize);

                    }

                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.flush();
                    dos.close();
                    Log.i("complete", "aaaaaa");
                    //서버로 부터 파일업로드가 잘 되었는지 응답받기...
                    InputStream is = conn.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    StringBuffer sb = new StringBuffer();
                    String line = reader.readLine();

                    while (line != null) {
                        Log.i("line", line);
                        line = reader.readLine();
                    }
                } catch (MalformedURLException e) {
                    Log.i("URL", "URL ERROR");
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.i("io", "io ERROR");
                    e.printStackTrace();
                }

            }//run method...

        }.start();


    }


}
