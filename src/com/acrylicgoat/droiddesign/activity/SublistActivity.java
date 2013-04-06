/**
 * Copyright (c) 2012 Acrylic Goat Software
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.droiddesign.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.util.Log;
import android.view.Display;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.acrylicgoat.droiddesign.R;
import com.acrylicgoat.droiddesign.fragments.*;
import com.acrylicgoat.droiddesign.util.ContentCache;
import com.acrylicgoat.droiddesign.util.DDGUtil;

/**
 * @author ew2
 *
 */
public class SublistActivity extends SherlockFragmentActivity implements DDGListFragment.OnDDGSelectedListener
{
    private String category;
    ActionBar actionBar;
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        Display d = getWindowManager().getDefaultDisplay();
        if(DDGUtil.isTabletDevice(d, this))
        {
            setContentView(R.layout.list_tablet_fragment);
        }
        else
        {
            setContentView(R.layout.list_fragment);
        }
         
        category = (String)ContentCache.getObject("display");
        if(category==null && savedInstanceState != null)
        {
            //Log.d("ViewLenses.onCreate()", "getting saved data");
            category = (String)savedInstanceState.getSerializable("display");
        }
        
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        //Log.d("SublistActivity", "category = " + category);
        actionBar.setTitle(category);
        actionBar.setDisplayShowHomeEnabled(true);
        ContentCache.setObject("display", category);
        
        
    }
    
    @Override
    public void onDDGSelected(final String tutUrl) 
    {
        final DDGViewFragment viewer = (DDGViewFragment) getSupportFragmentManager().findFragmentById(R.id.view_fragment);
        
        ContentCache.setObject("url", tutUrl);
        ContentCache.setObject("webName",DDGUtil.getCategory(tutUrl));

        if (viewer == null || !viewer.isInLayout()) 
        {
            Intent showContent = new Intent(getApplicationContext(),DDGViewActivity.class);
            showContent.setData(Uri.parse(tutUrl));
            startActivity(showContent);
        } 
        else 
        {
            runOnUiThread(new Runnable(){
                public void run()
                {
                    viewer.updateUrl(tutUrl); 
                }
            });
            
        }
        
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
    
    private void openPlayStore()
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.acrylicgoat.droiddesign")));
    }
    
    @Override
    protected void onResume() 
    {
        super.onResume();
        String category = (String)ContentCache.getObject("display");
        
        //ActionBar actionBar = getSupportActionBar();
        actionBar.setSelectedNavigationItem(0);
        actionBar.setTitle(category);
    }
    
    @Override
    protected void onPause() 
    {
        super.onPause();
        ContentCache.setObject("display", category);
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        ContentCache.setObject("display", category);
        outState.putSerializable("display", category);
    }
    
    
}
