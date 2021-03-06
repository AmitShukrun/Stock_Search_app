package edu.sjsu.android.stocksearch;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import java.net.MalformedURLException;
import java.net.URL;

public class AutoCompleteStockName implements TextWatcher {

    // Layout variables
    private AutoCompleteTextView autoTextView;

    // Data variables
    private String input;
    private String[] stockNames = {};
    private ArrayAdapter<String> arrayAdapter;

    // Variables
    Context context;
    Activity mainActivity;

    // Constructor passes main activity context over.
    public AutoCompleteStockName(Context context, Activity mainActivity) {
        this.context = context;
        this.mainActivity = mainActivity;
        autoTextView = mainActivity.findViewById(R.id.editStockName);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!autoTextView.isPerformingCompletion()) {
            input = s.toString();
            if (input.length() >= 1) {
                queryJSON(input);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    // Connect to tiingo's DB

    public void queryJSON(String ticker) {
        String token = context.getResources().getString(R.string.token);
        String url = "https://api.tiingo.com/tiingo/utilities/search?query=" + ticker + "&token=" + token;
        new JsonTask().execute(url);
    }

    // Inner class for querying JSON in background thread.
    private class JsonTask extends AsyncTask<String, Void, String[]> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String[] doInBackground(String... params) {
            String jsonStr = null;
            // Query from website for json info.
            try {
                URL url = new URL(params[0]);
                jsonStr = JSONParser.getJsonFromUrl(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            // Parse json info and return it within an array.
            return JSONParser.getValueArray(jsonStr, "ticker");
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            stockNames = result;
            arrayAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_dropdown_item_1line, stockNames);
            autoTextView.setAdapter(arrayAdapter);
            autoTextView.showDropDown();
        }
    }

    public boolean checkStockNameValid(String stockName) {
        for (String currStockName : stockNames) {
            if (stockName.equals(currStockName)) {
                return true;
            }
        }
        return false;
    }

}
