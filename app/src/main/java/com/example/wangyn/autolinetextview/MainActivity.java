package com.example.wangyn.autolinetextview;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Drawable drawable = getResources().getDrawable(R.drawable.common_image_m2);
        HotelAutoLineTextView autoLineTextView = (HotelAutoLineTextView)findViewById(R.id.autoLineTextView);
        HotelAutoLineTextView.Builder builder = HotelAutoLineTextView.Builder.getInstance();
        builder.append("Hello",R.style.text_13_26aaf2,drawable).append("world",R.style.text_13_808080)
                .append("Hello", R.style.text_13_26aaf2).append("world",R.style.text_13_808080)
                .append("cool",R.style.text_13_26aaf2).append("world",R.style.text_13_808080)
                .append("hot",R.style.text_13_26aaf2).append("world",R.style.text_13_808080)
                .append("beautiful",R.style.text_13_26aaf2).append("world",R.style.text_13_808080)
                .append(" Hello",R.style.text_13_26aaf2).append("world",R.style.text_13_808080)
                .into(autoLineTextView);
        HotelDrawLinePriceTextView priceView = (HotelDrawLinePriceTextView)findViewById(R.id.priceTextView);
        priceView.setTitle("平日价");
        priceView.setPrice("¥12500");
        priceView.invalidate();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
