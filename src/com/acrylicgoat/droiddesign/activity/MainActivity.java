/**
 * Copyright (c) 2012 Acrylic Goat Software
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.droiddesign.activity;

import java.util.ArrayList;

import com.acrylicgoat.droiddesign.R;
import com.acrylicgoat.droiddesign.adapters.ListAdapter;
import com.acrylicgoat.droiddesign.util.ContentCache;
import com.actionbarsherlock.app.SherlockListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends SherlockListActivity 
{
    
        ArrayList<String> initialList;
        ListAdapter adapter;
        
        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) 
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.list_view);
            
            initialList = (ArrayList<String>)getLastNonConfigurationInstance();
            if(initialList==null && savedInstanceState != null)
            {
                //Log.d("ViewLenses.onCreate()", "getting saved data");
                initialList = (ArrayList<String>)savedInstanceState.getSerializable("initialList");
            }
            if(initialList == null)
            {
                createList();
            }
           
            fillData(initialList);
            finishedLoadingList();
                
            setListAdapter(adapter);
        }
        
        
        @Override
        protected void onListItemClick(ListView l, View v, int position, long id) 
        {
            String item = (String)getListView().getItemAtPosition(position);
            if(item.equals("Get Started"))
            {
                ContentCache.setObject("category","getstarted");
                ContentCache.setObject("display","Get Started");
            }
            else if(item.equals("Style"))
            {
                ContentCache.setObject("category","style");
                ContentCache.setObject("display","Style");
            }
            else if(item.equals("Patterns"))
            {
                ContentCache.setObject("category","patterns");
                ContentCache.setObject("display","Patterns");
            }
            else if(item.equals("Building Blocks"))
            {
                ContentCache.setObject("category","blocks");
                ContentCache.setObject("display","Building Blocks");
            }
            startActivity(new Intent(this, SublistActivity.class));
        }
        
        @Override
        protected void onSaveInstanceState(Bundle outState)
        {
            super.onSaveInstanceState(outState);
            //Log.d("ViewLenses.onSaveInstanceState()", "saving data");
            outState.putSerializable("initialList", initialList);
            
        }
        
        protected void finishedLoadingList() 
        {
            setListAdapter(adapter);
            getListView().setSelection(0);
            getListView().setSaveEnabled(true);
            getListView().setClickable(true);
        }
        
        /**
         * Loads data into list view
         * @param contentList ArrayList<Content>
         */
        private void fillData(ArrayList<String> contentList) 
        {
            //Log.d("LensViewer", "fillData() called");
            adapter = new ListAdapter(this, contentList);
        }
        
        private void createList()
        {
            if(initialList == null)
            {
                initialList = new ArrayList<String>();
            }
            initialList.add("Get Started");
            initialList.add("Style");
            initialList.add("Patterns");
            initialList.add("Building Blocks");
        }
}