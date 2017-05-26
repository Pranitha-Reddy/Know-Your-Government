package com.example.pranijareddy.knowyourgovernment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.pranijareddy.knowyourgovernment.R.layout.officialdetail;
import static com.example.pranijareddy.knowyourgovernment.R.layout.photo_activity;

/**
 * Created by Pranijareddy on 4/7/2017.
 */

public class PhotoActivity extends AppCompatActivity {

    private ImageView imageView;
    String url=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(photo_activity);
        ConstraintLayout vi=(ConstraintLayout) findViewById(R.id.lay);
        imageView = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        TextView tv=(TextView)findViewById(R.id.location);
        if(intent.hasExtra("location")){
            String l=intent.getStringExtra("location");
            tv.setText(l);
        }
        if (intent.hasExtra("activity")) {
           final Official c = (Official) intent.getSerializableExtra("activity");
            TextView Office = (TextView) findViewById(R.id.off);
            Office.setText(c.getOffice());
            TextView name = (TextView) findViewById(R.id.na);
            name.setText(c.getName());

            if(c.getParty().contentEquals("Republican")){
                vi.setBackgroundColor(Color.RED);
            }

            else if(c.getParty().contentEquals("Democratic")||c.getParty().contentEquals("Democrat")){
                vi.setBackgroundColor(Color.BLUE);
            }

            if (c.getPurl() != null) {
                url=c.getPurl();
                Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
// Here we try https if the http image attempt failed
                        final String changedUrl = url.replace("http:", "https:");
                        picasso.load(changedUrl)
                                .error(R.drawable.brokenimage)
                                .placeholder(R.drawable.placeholder)
                                .fit()
                                .into(imageView);
                    }
                }).build();
                picasso.load(url)
                        .error(R.drawable.brokenimage)
                        .placeholder(R.drawable.placeholder)
                        .fit()
                        .into(imageView);
            } else {
                Picasso.with(this).load(url)
                        .error(R.drawable.brokenimage)
                        .placeholder(R.drawable.missingimage)
                        .fit()
                        .into(imageView);
            }




        }

}}
