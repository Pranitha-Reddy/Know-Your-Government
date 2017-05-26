package com.example.pranijareddy.knowyourgovernment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Pranijareddy on 4/7/2017.
 */

public class About extends AppCompatActivity

    {
        private TextView title;
        private  TextView name ;
        private   TextView version;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
            title=(TextView) findViewById(R.id.title) ;
            name = (TextView) findViewById(R.id.name);
            version= (TextView) findViewById(R.id.version);
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            //String text = intent.getStringExtra(Intent.EXTRA_TEXT);

        }

    }
        @Override
        public void onPause()
        {
            super.onPause();

        }
}
