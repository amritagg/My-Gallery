package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
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

        if(item.getItemId() == R.id.delete){
            deleteImage();
            return true;
        }else return super.onOptionsItemSelected(item);
    }

    private void deleteImage() {

        int currentID = viewPager.getCurrentItem();
        String url_string = image_uris.get(currentID);
        Uri uri = Uri.parse(url_string);
        int _id = getContentResolver().delete(
                uri,
                null,
                null
        );
        if(_id != 0){
            Toast.makeText(this, "Image deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }

}
