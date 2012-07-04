/**
 * Copyright (c) 2012 Acrylic Goat Software
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.droiddesign.fragments;

import com.acrylicgoat.droiddesign.R;
import com.acrylicgoat.droiddesign.util.ContentCache;
import com.actionbarsherlock.app.SherlockFragment;

import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;

/**
 * @author ew2
 *
 */
public class DDGViewFragment extends SherlockFragment {
    private WebView viewer = null;
    //DDGViewActivity activity = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
    {
        viewer = (WebView) inflater.inflate(R.layout.web_view, container, false);
        viewer.getSettings().setUseWideViewPort(true);
        viewer.getSettings().setSupportZoom(true);
        viewer.getSettings().setJavaScriptEnabled(true);
        viewer.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        //viewer.setInitialScale(90);
        viewer.getSettings().setDefaultFontSize(17);
        return viewer;
    }
    
    public void updateUrl(final String newUrl) 
    {
        if (viewer != null) 
        {
            ContentCache.setObject("url", newUrl);

            viewer.loadUrl(newUrl); 
        }
    }
    
    @Override
    public void onResume() 
    {
        super.onResume();
        String url = (String)ContentCache.getObject("url");
        if(url != null)
        {
            updateUrl(url);
              
        }
    }
    

}
