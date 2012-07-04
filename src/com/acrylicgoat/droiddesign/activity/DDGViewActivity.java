/**
 * Copyright (c) 2012 Acrylic Goat Software
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.droiddesign.activity;

import com.acrylicgoat.droiddesign.R;
import com.acrylicgoat.droiddesign.fragments.DDGViewFragment;
import com.acrylicgoat.droiddesign.util.ContentCache;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
//import android.util.Log;

/**
 * @author ew2
 *
 */
public class DDGViewActivity extends SherlockFragmentActivity
{
    String url;
    DDGViewFragment viewer;
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_fragment);

        Intent launchingIntent = getIntent();
        url = launchingIntent.getData().toString();
        ContentCache.setObject("url", url);

        viewer = (DDGViewFragment) getSupportFragmentManager().findFragmentById(R.id.view_fragment);
        
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                viewer.updateUrl(url); 
            }
        });

        String category = (String)ContentCache.getObject("webName");
        
        ActionBar actionBar = getSupportActionBar();
        //Log.d("DDGViewActivity", "category = " + category);
        actionBar.setTitle(category);
        
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
     * added to handle orientation change.  Not sure why this is needed, but it is.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {        
        super.onConfigurationChanged(newConfig);
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        ContentCache.setObject("url", url);
    }
    
}
