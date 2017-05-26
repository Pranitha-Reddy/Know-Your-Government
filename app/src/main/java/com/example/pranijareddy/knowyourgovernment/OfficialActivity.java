package com.example.pranijareddy.knowyourgovernment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.pranijareddy.knowyourgovernment.R.id.url;
import static com.example.pranijareddy.knowyourgovernment.R.layout.officialdetail;

/**
 * Created by Pranijareddy on 4/7/2017.
 */

public class OfficialActivity extends AppCompatActivity{
    private ImageView imageView;
    String url=null;

    String gid = "";
    String fid = "";
    String tid = "";
    String yid = "";
    String l="";
    private  Official c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(officialdetail);
  ConstraintLayout vi=(ConstraintLayout) findViewById(R.id.layout);
        imageView = (ImageView) findViewById(R.id.pic);
        Intent intent = getIntent();
        TextView loc=(TextView)findViewById(R.id.location);
        if(intent.hasExtra("location")){
            l=intent.getStringExtra("location");
            loc.setText(l);
        }
        if (intent.hasExtra(Official.class.getName())) {
             c = (Official) intent.getSerializableExtra(Official.class.getName());
            TextView Office = (TextView) findViewById(R.id.office);
            Office.setText(c.getOffice());
            TextView name = (TextView) findViewById(R.id.name);
            name.setText(c.getName());
            TextView party = (TextView) findViewById(R.id.party);
            party.setText("("+c.getParty()+")");
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
            TextView add = (TextView) findViewById(R.id.Address);
            if(c.getAddress().isEmpty()){
                add.setText(R.string.NoData);
            }else{

            add.setText(c.getAddress());}
            Linkify.addLinks(add, Linkify.MAP_ADDRESSES);
            add.setLinkTextColor(Color.WHITE);
            TextView phone = (TextView) findViewById(R.id.phone);
            if(c.getNumber().isEmpty()){  phone.setText(R.string.NoData);}
            else{
            phone.setText(c.getNumber());}

            TextView email = (TextView) findViewById(R.id.email);
            if(c.getEmail().isEmpty()){  email.setText(R.string.NoData);}
            else{  email.setText(c.getEmail());}
            TextView web = (TextView) findViewById(R.id.url);
            if(c.getUrl().isEmpty()){web.setText(R.string.NoData);}
            else{web.setText(c.getUrl());}
            Linkify.addLinks(((TextView) findViewById(R.id.url)), Linkify.WEB_URLS);
            web.setLinkTextColor(Color.WHITE);
            Linkify.addLinks(((TextView) findViewById(R.id.phone)), Linkify.PHONE_NUMBERS);
            phone.setLinkTextColor(Color.WHITE);
            Linkify.addLinks(((TextView) findViewById(R.id.email)), Linkify.EMAIL_ADDRESSES);
            email.setLinkTextColor(Color.WHITE);
            ImageView google=(ImageView) findViewById(R.id.google);
            ImageView fb=(ImageView) findViewById(R.id.fb);
            ImageView twitter=(ImageView) findViewById(R.id.twitter);
            ImageView youtube=(ImageView) findViewById(R.id.youtube);
             if(c.getGid().isEmpty()){google.setVisibility(vi.INVISIBLE);
             }
             else{google.setVisibility(vi.VISIBLE);
             gid=c.getGid();}
            if(c.getFid().isEmpty()){fb.setVisibility(vi.INVISIBLE);
            } else{fb.setVisibility(vi.VISIBLE);
            fid=c.getFid();}
            if(c.getTid().isEmpty()){twitter.setVisibility(vi.INVISIBLE);
            } else{twitter.setVisibility(vi.VISIBLE);
            tid=c.getTid();}
            if(c.getYid().isEmpty()){youtube.setVisibility(vi.INVISIBLE);
            } else{youtube.setVisibility(vi.VISIBLE);
            yid=c.getYid();}

        }





        }
    public void googlePlusClicked(View v) {

        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setClassName("com.google.android.apps.plus",
                    "com.google.android.apps.plus.phone.UrlGatewayActivity");
            intent.putExtra("customAppUri", gid);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://plus.google.com/" + gid)));
        }
    }
    public void facebookClicked(View v) {
        String FACEBOOK_URL = "https://www.facebook.com/" + fid;
        String urlToUse;
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                urlToUse = "fb://page/" +FACEBOOK_URL ;
            }
        } catch (PackageManager.NameNotFoundException e) {
            urlToUse = FACEBOOK_URL; //normal web url
        }
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse(urlToUse));
        startActivity(facebookIntent);
    }
    public void twitterClicked(View v) {
        Intent intent = null;

        try {
// get the Twitter app if possible
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + tid));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
// no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + tid));
        }
        startActivity(intent);
    }
    public void youTubeClicked(View v) {

        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + yid));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/" + yid)));
        }
    }
    public void photoClicked(View V){
        Intent intent = new Intent(OfficialActivity.this, PhotoActivity.class);
        intent.putExtra("location",l);
        intent.putExtra("activity",c);
        startActivity(intent);
    }
}
