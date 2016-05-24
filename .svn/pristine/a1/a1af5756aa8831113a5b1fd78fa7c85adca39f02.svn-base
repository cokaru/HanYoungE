package com.cokaru.multidic.multidic;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


import com.cokaru.multidic.multidic.view.SlidingTabLayout;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    public enum eSearchType
    {
        SEARCH_TYPE_KOREAN,
        SEARCH_TYPE_ENGLISH,
        SEARCH_TYPE_IMAGES,
        SEARCH_TYPE_HISTORY;

        public static eSearchType fromInteger(int _value)
        {
            switch (_value)
            {
                case 0: return SEARCH_TYPE_KOREAN;
                case 1: return SEARCH_TYPE_ENGLISH;
                case 2: return SEARCH_TYPE_IMAGES;
                case 3: return SEARCH_TYPE_HISTORY;
            }

            return SEARCH_TYPE_KOREAN;
        }
    }

    private static final int MAX_SEARCH_TYPE_COUNT = 3;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private DictionaryPagerAdapter dictionaryPagerAdapter;

    private Fragment findFragmentByPosition(int position)
    {
        if(dictionaryPagerAdapter == null) return null;
        return getSupportFragmentManager().findFragmentByTag(
                "android:switcher:"+viewPager.getId()+":"+dictionaryPagerAdapter.getItemId(position));
    }

    private void updateAllDictionary(String word)
    {
        if(dictionaryPagerAdapter == null || viewPager == null) return;

        for(int index=0; index < MAX_SEARCH_TYPE_COUNT ; index++)
        {
            WebViewFragment fragment = (WebViewFragment)findFragmentByPosition(index);
            if(fragment != null)
                dictionaryPagerAdapter.updateSearch(fragment, word);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        dictionaryPagerAdapter = new DictionaryPagerAdapter(getSupportFragmentManager(),this.getBaseContext());
        viewPager.setAdapter(dictionaryPagerAdapter);

        slidingTabLayout = (SlidingTabLayout)findViewById(R.id.sliding_tabs);
        slidingTabLayout.setViewPager(viewPager);

        EditText editText = (EditText)findViewById(R.id.searchEditText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId)
                {
                    case EditorInfo.IME_ACTION_SEARCH:
                    {
                        String _word = v.getText().toString();
                        if(!_word.isEmpty())
                        {
                            updateAllDictionary(_word);
                            //WebViewFragment fragment = (WebViewFragment)findFragmentByPosition(viewPager.getCurrentItem());
                            //dictionaryPagerAdapter.updateSearch(fragment, _word);
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }


    public static class DictionaryPagerAdapter extends FragmentPagerAdapter
    {

        private String  searchWord = "placebo";
        private Context mainContext;
        private HashMap<Integer,WebViewFragment> hashMap =  new HashMap<Integer,WebViewFragment>();

        public DictionaryPagerAdapter(FragmentManager _fragMgr,Context _context)
        {
            super(_fragMgr);
            mainContext = _context;
        }

        @Override
        public int getCount()
        {
            return MAX_SEARCH_TYPE_COUNT ;
        }


        public Fragment getItem(int position)
        {
            WebViewFragment fragment = null;

            switch (position)
            {
                case 0:
                    fragment = WebViewFragment.newInstance(0, mainContext.getResources().getString(R.string.daum_dic_url),searchWord);
                    break;
                case 1:
                    fragment = WebViewFragment.newInstance(1, mainContext.getResources().getString(R.string.oxford_dic_url),searchWord);
                    break;
                case 2:
                    fragment = WebViewFragment.newInstance(2, mainContext.getResources().getString(R.string.bing_img_url),searchWord);
                    break;
                case 3:
                    fragment = WebViewFragment.newInstance(3, mainContext.getResources().getString(R.string.google_url),searchWord);
                    break;
            }

            hashMap.put(position,fragment);
            return fragment;
        }


        public int getItemPosition(Object object)
        {
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            eSearchType searchType  =  eSearchType.fromInteger(position);
            switch (searchType)
            {
                case SEARCH_TYPE_KOREAN:    return "한글로";
                case SEARCH_TYPE_ENGLISH:   return "영어로";
                case SEARCH_TYPE_IMAGES:    return "이미지로";
                case SEARCH_TYPE_HISTORY:   return "끄적끄적";
            }
            return null;
        }


        public void updateSearch(WebViewFragment fragment,String word)
        {
            searchWord = word;
            fragment.updateWebView(searchWord);
        }

    }
}
