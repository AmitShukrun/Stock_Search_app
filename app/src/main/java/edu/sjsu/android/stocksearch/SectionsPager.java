package edu.sjsu.android.stocksearch;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class SectionsPager extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    private String stockName;
    private String histDate;

    public SectionsPager(Context context, String stockName, String histDate, FragmentManager fm) {
        super(fm);
        mContext = context;
        this.stockName = stockName;
        this.histDate = histDate;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // CurrentStockInfo fragment
        if (position == 0) {
            return CurrentStockInfo.newInstance(stockName);
        }
        // HistoricalData fragment
        else if (position == 1) {
            return HistoricalData.newInstance(stockName, histDate);
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}