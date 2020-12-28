package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    request code for permission
    private final int REQUEST_PERMISSION_CODE = 5;

    ArrayList<String> imageUris = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        calling permissions method
        checkPermissionStatus();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void startGallery(){

        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.HEIGHT,
                MediaStore.Images.Media.WIDTH,
                MediaStore.Images.Media.DATE_TAKEN
        };

        try (Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.Media.DATE_TAKEN + " DESC"
        )) {
            assert cursor != null;
            int idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID);

            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumn);

                Uri contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                String image_string = contentUri.toString();
                imageUris.add(image_string);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        GridView recyclerView = findViewById(R.id.recyclerView);
        ImageAdapter imageAdapter = new ImageAdapter(getApplicationContext(), imageUris);
        recyclerView.setAdapter(imageAdapter);

        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putStringArrayList("image_uris", imageUris);
                bundle.putInt("current_position", position);

                Intent intent = new Intent(getApplicationContext(), ViewImageActivity.class);
                intent.putExtra("bundle", bundle);

                startActivity(intent);
            }
        });

    }

//    list of permissions required
    String[] PERMISSION_LIST = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

//  Method for permission handling
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void checkPermissionStatus() {

//      check weather the permission is given or not
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            startGallery();
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                if not permission is denied than show the message to user showing him the benefits of the permission.
                Toast.makeText(this, "Permission is needed to show the Media..", Toast.LENGTH_SHORT).show();
            }
//            request permission
            requestPermissions(PERMISSION_LIST, REQUEST_PERMISSION_CODE);
        }
    }

//    handling the permissions
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_PERMISSION_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startGallery();
            }else{
                Toast.makeText(this,"Permissions not granted!!", Toast.LENGTH_SHORT).show();
            }

        }else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
