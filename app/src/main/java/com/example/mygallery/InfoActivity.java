package com.example.mygallery;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;
import org.jetbrains.annotations.NotNull;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class InfoActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
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
            int pathColumn = cursor.getColumnIndex(MediaStore.Images.Media.RELATIVE_PATH);

            if (cursor.moveToFirst()) {
                String name = cursor.getString(nameColumn);
                int size = cursor.getInt(sizeColumn);
                int height = cursor.getInt(heightColumn);
                int width = cursor.getInt(widthColumn);
                long dateTaken = cursor.getLong(dataTakenColumn);
                String path_string = cursor.getString(pathColumn);

                String dimension = height + "X" + width;
                date_taken_view.setText(timeConverter(dateTaken));
                name_view.setText(name);
                size_view.setText(sizeConverter(size));
                dimension_view.setText(dimension);
                path_view.setText(path_string + name);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NotNull
    private String sizeConverter(int size){
        float size_in_kb = (float) size/1024;
        float size_in_mb = (float) size_in_kb/1024;

        String kb_string = new DecimalFormat("##.##").format(size_in_kb);
        String mb_string = new DecimalFormat("##.##").format(size_in_mb);
        if( (int) size_in_kb/1024 == 0){
            return kb_string + "KB";
        }
        return mb_string + "MB";
    }

    @NotNull
    private String timeConverter(long time){
        Date date = new Date(time);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatted = new SimpleDateFormat("LLL dd, yyyy hh:mm:ss a");
        formatted.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        return formatted.format(date);
    }

}