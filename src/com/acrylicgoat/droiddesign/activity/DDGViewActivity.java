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
import android.net.Uri;
import android.os.Bundle;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
//import android.util.Log;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

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
        actionBar.setDisplayHomeAsUpEnabled(true);
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getSupportMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        int itemId = item.getItemId();
        switch (itemId)
        {
            case R.id.ddg:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com/design/index.html"));
                startActivity(browserIntent);
                break;
            case android.R.id.home:
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
               mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(mainIntent);
               break;
            case R.id.rate:
                //displayRateDialog(getApplicationContext());
                openPlayStore();
                break;
            default:
                break;
        }

        
        return true;
    }
    
    private void openPlayStore()
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.acrylicgoat.droiddesign")));
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
