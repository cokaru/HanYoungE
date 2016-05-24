package com.cokaru.multidic.multidic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by sungj on 2016-04-09.
 */
public class WebViewFragment extends Fragment
{
    private WebView  webView = null;
    private String url;
    private String word;
    private MainActivity.eSearchType searchType;



   public static WebViewFragment newInstance(int type,String searchUrl,String word)
    {
        WebViewFragment  webViewFragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putInt("dictionaryType", type);
        args.putString("searchUrl", searchUrl);
        args.putString("word", word);

        webViewFragment.setArguments(args);
        return webViewFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        searchType = MainActivity.eSearchType.fromInteger(getArguments().getInt("dictionaryType"));
        url = getArguments().getString("searchUrl");
        word = getArguments().getString("word");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dictionary_item, container, false);
        webView = (WebView)view.findViewById(R.id.webview);

        String searchUrl = url + word;
        updateWebView(webView, searchUrl);
        return view;
    }

    public  void updateWebView(String _word)
    {
        word = _word;
        String searchUrl = url + _word;
        updateWebView(webView, searchUrl);
    }

    private void updateWebView(WebView webView ,String searchUrl)
    {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setLoadsImagesAutomatically(true);
       // webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl(searchUrl);
    }

    private class WebViewClientClass extends  WebViewClient
    {
        public boolean shouldOverrideUrlLoading(WebView view,String url)
        {
            view.loadUrl(url);
            return true;
        }
    }



}
