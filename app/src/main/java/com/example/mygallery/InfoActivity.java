package com.example.mygallery;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();

        String uri_string = intent.getStringExtra("Uri_String");
        Uri uri = Uri.parse(uri_string);

        TextView date_taken_view = findViewById(R.id.date_taken);
        TextView name_view = findViewById(R.id.name);
        TextView size_view = findViewById(R.id.size);
        TextView dimension_view = findViewById(R.id.dimensions);
        TextView path_view = findViewById(R.id.path);

        try (Cursor cursor = getContentResolver().query(
                uri,
                null,
                null,
                null,
                null
        )) {
            assert cursor != null;
            int nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
            int sizeColumn = cursor.getColumnIndex(MediaStore.Images.Media.SIZE);
            int heightColumn = cursor.getColumnIndex(MediaStore.Images.Media.HEIGHT);
            int widthColumn = cursor.getColumnIndex(MediaStore.Images.Media.WIDTH);
            int dataTakenColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);

            if (cursor.moveToFirst()) {
                String name = cursor.getString(nameColumn);
                int size = cursor.getInt(sizeColumn);
                int height = cursor.getInt(heightColumn);
                int width = cursor.getInt(widthColumn);
                String dataTaken = cursor.getString(dataTakenColumn);

                String dimension = height + "X" + width;
                date_taken_view.setText(dataTaken);
                name_view.setText(name);
                size_view.setText(size);
                dimension_view.setText(dimension);
                path_view.setText((CharSequence)uri);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}