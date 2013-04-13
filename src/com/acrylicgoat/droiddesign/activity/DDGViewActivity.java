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
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
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
    ActionBar actionBar;
    
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
        
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        //Log.d("DDGViewActivity", "category = " + category);
        actionBar.setTitle(category);
        actionBar.setDisplayHomeAsUpEnabled(true);
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getSupportMenuInflater().inflate(R.menu.actionbar_menu, menu);
        
        SpinnerAdapter mSpinnerAdapter;
        if(Build.VERSION.SDK_INT <= 10) 
        {
            mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.nav_list,android.R.layout.simple_spinner_item);
        }
        else
        {
            mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.nav_list,android.R.layout.simple_spinner_dropdown_item);
        }
        OnNavigationListener mOnNavigationListener = new OnNavigationListener() 
        {
            // Get the same strings provided for the drop-down's ArrayAdapter
            //String[] strings = getResources().getStringArray(R.array.nav_list);

            @Override
            public boolean onNavigationItemSelected(int position, long itemId) 
            {
              switch (position)
              {
                  case 1:
                      Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com/design/index.html"));
                      startActivity(browserIntent);
                      break;
                  case 2:
                      openPlayStore();
                      break;
              }
              
              return true;
            }
          };
          
          actionBar.setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);
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
    
    @Override
    public void onResume()
    {
        actionBar.setSelectedNavigationItem(0);
        super.onResume();
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
