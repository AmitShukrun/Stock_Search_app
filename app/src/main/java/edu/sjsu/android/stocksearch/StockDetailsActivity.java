package edu.sjsu.android.stocksearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import org.json.JSONObject;

public class StockDetailsActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
            Intent intent = new Intent(StockDetailsActivity.this, MainActivity.class);
            startActivity(intent);
    }

    // Context variables
    private Context context;

    // Data variables
    private String stockName;
    private String histDate;

    // Zero-arg constructor
    public StockDetailsActivity() {}

    // Layout variables
    private FloatingActionButton fab;
    private ViewPager viewPager;
    private TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);

        context = getApplicationContext();

        // Retrieve intent bundled data.
        if (getIntent().hasExtra("stockName")) {
            stockName = getIntent().getStringExtra("stockName");
            histDate = getIntent().getStringExtra("histDate");
        }

        // Default activity + UI setup
        SectionsPager sectionsPagerAdapter = new SectionsPager(this, stockName, histDate, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        fab = findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesJSON favJson = new SharedPreferencesJSON(context);
                JSONObject favJsonObj = favJson.getSharedPreferencesJSON();
                if (!favJsonObj.has(stockName)) {
                    favJson.addToSharedPreferencesJSON(stockName);
                    fab.setBackgroundTintList(context.getResources().getColorStateList(R.color.green, null));
                    Snackbar.make(view, "Successfully added to favorites!", Snackbar.LENGTH_SHORT).show();
                }
                else {
                    favJson.removeFromSharedPreferencesJSON(stockName);
                    fab.setBackgroundTintList(context.getResources().getColorStateList(R.color.red, null));
                    Snackbar.make(view, stockName + " has been removed from favorites.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}