package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewImageActivity extends AppCompatActivity {

    ViewPager viewPager;
    ArrayList<String> image_uris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");

        assert bundle != null;
        int current = bundle.getInt("current_position");

        image_uris = bundle.getStringArrayList("image_uris");
        viewPager = findViewById(R.id.view_pager);
        ViewPageImageAdapter adapter = new ViewPageImageAdapter(this, image_uris);

        viewPager.setAdapter(adapter);
        viewPager.setPageMargin(100);
        viewPager.setCurrentItem(current);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

         if(item.getItemId() == R.id.info){
            int position = viewPager.getCurrentItem();
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtra("Uri_String", image_uris.get(position));
            startActivity(intent);
            return true;
         } else if(item.getItemId() == R.id.delete){
             deleteImage();
         }return super.onOptionsItemSelected(item);
    }

    private void deleteImage() {

        int currentID = viewPager.getCurrentItem();
        String url_string = image_uris.get(currentID);
        final Uri uri = Uri.parse(url_string);

        new AlertDialog.Builder(this)
                .setTitle("Delete Image")
                .setMessage("Do You really want to delete the image")
                .setPositiveButton("YES!!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "The file " + uri + " is deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("NO!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "The file " + uri + " will not be deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .create()
                .show();
    }

}
