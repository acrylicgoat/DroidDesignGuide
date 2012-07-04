/**
 * Copyright (c) 2012 Acrylic Goat Software
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.droiddesign.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar;
import android.util.Log;
import android.view.Display;

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
        
        ActionBar actionBar = getSupportActionBar();
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
    protected void onResume() 
    {
        super.onResume();
        String category = (String)ContentCache.getObject("display");
        
        ActionBar actionBar = getSupportActionBar();
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
