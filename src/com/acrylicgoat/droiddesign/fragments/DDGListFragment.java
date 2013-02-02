/**
 * Copyright (c) 2012 Acrylic Goat Software
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.droiddesign.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import com.acrylicgoat.droiddesign.adapters.LandscapeListAdapter;
import com.acrylicgoat.droiddesign.adapters.ListAdapter;
import com.acrylicgoat.droiddesign.util.ContentCache;
import com.acrylicgoat.droiddesign.util.DDGUtil;
import com.acrylicgoat.droiddesign.R;
import com.actionbarsherlock.app.SherlockListFragment;

import android.graphics.Color;
import android.os.Bundle;
import com.actionbarsherlock.app.ActionBar;
import android.app.Activity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
//import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * @author ew2
 *
 */
public class DDGListFragment extends SherlockListFragment
{
    private OnDDGSelectedListener ddgSelectedListener;
    ListAdapter adapter;
    String category;
    HashMap<String, String> map;
    ArrayList<String> initialList;
    private int curPosition = -1;
    boolean dualFragments = false;
    ActionBar aBar;
    SherlockFragmentActivity sherActivity;
    LandscapeListAdapter landAdapter;
    View prevView;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) 
    {

//        if(curPosition != -1)
//        {
//            //Log.d("DDGListFragment", "curPosition = " + curPosition);
//            if(dualFragments)
//            {
//                l.getChildAt(curPosition).setBackgroundResource(R.color.transparent);
//            }
//            adapter.notifyDataSetChanged();
//        }
//        
//        curPosition = position;
//        String item = (String)l.getItemAtPosition(position);
//        selectPosition();
//        if(dualFragments)
//        {
//            l.getChildAt(position).setBackgroundResource(R.color.color_list_background_selected);
//            if(aBar != null)
//            {
//                aBar.setSubtitle(item);
//            }
//        }
//        adapter.notifyDataSetChanged();
//        String url = getURL(item);
//        ddgSelectedListener.onDDGSelected(url);
//      if(curPosition != -1 && prevView != null)
//      {
//          //Log.d("DDGListFragment", "curPosition = " + curPosition);
//          if(dualFragments)
//          {
//             prevView.setBackgroundResource(R.color.transparent);
//          }
//          adapter.notifyDataSetChanged();
//          
//      }
      
      v.setSelected(true);
      curPosition = position;
      prevView = v;
      String item = (String)l.getItemAtPosition(position);
      selectPosition();
      if(dualFragments)
      {
          //v.setBackgroundResource(R.color.color_list_background_selected);
         //l.getChildAt(position).setBackgroundResource(R.color.color_list_background_selected);
          //View view = (View)l.getAdapter().getItem(position);
          //view.setBackgroundResource(R.color.color_list_background_selected);
          if(aBar != null)
          {
              aBar.setSubtitle(item);
          }
          landAdapter.notifyDataSetChanged();
      }
      else
      {
          adapter.notifyDataSetChanged();
      }
      
      String url = getURL(item);
      ddgSelectedListener.onDDGSelected(url);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        category = (String)ContentCache.getObject("category");
        if (savedInstanceState != null)
        {
            curPosition = savedInstanceState.getInt("listPosition");
        }
        if(category == null)
        {
            category = (String)savedInstanceState.getSerializable("category");
            if(category == null)
            {
                category = "";
            }
            map = (HashMap<String,String>)savedInstanceState.getSerializable("map");
        }
        else
        {
            createURLMap();
        }
        createList();
        //adapter = new ListAdapter(getActivity().getApplicationContext(), initialList);
        //setListAdapter(adapter);
        
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) 
    {
        super.onActivityCreated(savedInstanceState);
        ListView lv = getListView();
        lv.setCacheColorHint(Color.TRANSPARENT);
        //lv.setSelector(R.drawable.list_background);
        //lv.setBackgroundResource(R.color.color_list_background);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        
        if (DDGUtil.isTabletDevice(getActivity().getWindowManager().getDefaultDisplay(),getActivity()))
        {
            String url = getURL(category);
            ddgSelectedListener.onDDGSelected(url);
            dualFragments = true;
        }
        aBar = sherActivity.getSupportActionBar();
        
        if(dualFragments)
        {
            landAdapter = new LandscapeListAdapter(getActivity().getApplicationContext(), initialList);
            setListAdapter(landAdapter);
            
        }
        else
        {
            adapter = new ListAdapter(getActivity().getApplicationContext(), initialList);
            setListAdapter(adapter);
            lv.setBackgroundResource(R.color.color_list_background);
        }
        
    }
    
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        //Log.d("ViewLenses.onSaveInstanceState()", "saving data");
        ContentCache.setObject("category", category);
        ContentCache.setObject("categoryList", initialList);
        ContentCache.setObject("categoryMap", map);
        outState.putInt("listPostion", curPosition);
        
    }
    
    public void onPause()
    {
        super.onPause();
        ContentCache.setObject("category", category);
        ContentCache.setObject("categoryList", initialList);
        ContentCache.setObject("categoryMap", map);
        
    }

    public interface OnDDGSelectedListener 
    {
        public void onDDGSelected(String tutUrl);
    }

    @Override
    public void onAttach(Activity activity) 
    {
        super.onAttach(activity);
        sherActivity = (SherlockFragmentActivity)activity; 
        try 
        {
            ddgSelectedListener = (OnDDGSelectedListener) activity;
        } 
        catch (ClassCastException e) 
        {
            throw new ClassCastException(activity.toString() + " must implement OnDDGSelectedListener");
        }
    }
    
    public void selectPosition() 
    {
        // Only if we're showing both fragments should the item be "highlighted"
        if (dualFragments) 
        {
            //Log.d("DDGListFragment.selectPosition()","using dualFragments" );
            //Log.d("DDGListFragment.selectPosition()","position = " + curPosition);
            ListView lv = getListView();
            lv.setItemChecked(curPosition, true);
        }

    }
    
    private void createURLMap()
    {
        map = new HashMap<String,String>();
        
        if(category.equals("getstarted"))
        {
            map.put("getstarted", "http://developer.android.com/design/index.html");
            map.put("Creative Vision","http://developer.android.com/design/get-started/creative-vision.html" );
            map.put("Design Priciples", "http://developer.android.com/design/get-started/principles.html");
            map.put("UI Overview", "http://developer.android.com/design/get-started/ui-overview.html");
            
        }
        else if(category.equals("style"))
        {
            map.put("style", "http://developer.android.com/design/style/index.html");
            map.put("Devices and Displays", "http://developer.android.com/design/style/devices-displays.html");
            map.put("Themes", "http://developer.android.com/design/style/themes.html");
            map.put("Touch Feedback", "http://developer.android.com/design/style/touch-feedback.html");
            map.put("Metrics and Grids", "http://developer.android.com/design/style/metrics-grids.html");
            map.put("Typography", "http://developer.android.com/design/style/typography.html");
            map.put("Color", "http://developer.android.com/design/style/color.html");
            map.put("Iconography", "http://developer.android.com/design/style/iconography.html");
            map.put("Writing Style", "http://developer.android.com/design/style/writing.html");
            
        }
        else if(category.equals("patterns"))
        {
            map.put("patterns", "http://developer.android.com/design/patterns/index.html");
            map.put("New in Android", "http://developer.android.com/design/patterns/new.html");
            map.put("Gestures","http://developer.android.com/design/patterns/gestures.html");
            map.put("App Structure", "http://developer.android.com/design/patterns/app-structure.html");
            map.put("Navigation", "http://developer.android.com/design/patterns/navigation.html");
            map.put("Action Bar", "http://developer.android.com/design/patterns/actionbar.html");
            map.put("Multi-pane Layouts", "http://developer.android.com/design/patterns/multi-pane-layouts.html");
            map.put("Swipe Views", "http://developer.android.com/design/patterns/swipe-views.html");
            map.put("Selection", "http://developer.android.com/design/patterns/selection.html");
            map.put("Confirming & Acknowledging", "http://developer.android.com/design/patterns/confirming-acknowledging.html");
            map.put("Notifications", "http://developer.android.com/design/patterns/notifications.html");
            map.put("Widgets", "http://developer.android.com/design/patterns/widgets.html");
            map.put("Settings", "http://developer.android.com/design/patterns/settings.html");
            map.put("Help", "http://developer.android.com/design/patterns/help.html");
            map.put("Compatibility", "http://developer.android.com/design/patterns/compatibility.html");
            map.put("Accessibility", "http://developer.android.com/design/patterns/accessibility.html");
            map.put("Pure Android", "http://developer.android.com/design/patterns/pure-android.html");
            
        }
        else if(category.equals("blocks"))
        {
            map.put("blocks", "http://developer.android.com/design/building-blocks/index.html");
            map.put("Tabs", "http://developer.android.com/design/building-blocks/tabs.html");
            map.put("Lists", "http://developer.android.com/design/building-blocks/lists.html");
            map.put("Grid Lists", "http://developer.android.com/design/building-blocks/grid-lists.html");
            map.put("Scrolling", "http://developer.android.com/design/building-blocks/scrolling.html");
            map.put("Spinners", "http://developer.android.com/design/building-blocks/spinners.html");
            map.put("Buttons", "http://developer.android.com/design/building-blocks/buttons.html");
            map.put("Text Fields", "http://developer.android.com/design/building-blocks/text-fields.html");
            map.put("Seek Bars", "http://developer.android.com/design/building-blocks/seek-bars.html");
            map.put("Progress and Activity", "http://developer.android.com/design/building-blocks/progress.html");
            map.put("Switches", "http://developer.android.com/design/building-blocks/switches.html");
            map.put("Dialogs", "http://developer.android.com/design/building-blocks/dialogs.html");
            map.put("Pickers", "http://developer.android.com/design/building-blocks/pickers.html");
            
        }
        else if(category.equals("play"))
        {
            map.put("play", "http://developer.android.com/distribute/index.html");
            map.put("Visibility", "http://developer.android.com/distribute/googleplay/about/visibility.html");
            map.put("Monetizing", "http://developer.android.com/distribute/googleplay/about/monetizing.html");
            map.put("Distribution", "http://developer.android.com/distribute/googleplay/about/distribution.html");
            
        }
        else if(category.equals("publishing"))
        {
            map.put("publishing", "http://developer.android.com/distribute/googleplay/publish/index.html");
            map.put("Get Started", "http://developer.android.com/distribute/googleplay/publish/register.html");
            map.put("Developer Console", "http://developer.android.com/distribute/googleplay/publish/console.html");
            map.put("Publishing Checklist", "http://developer.android.com/distribute/googleplay/publish/preparing.html");
            
        }
        else if(category.equals("promoting"))
        {
            map.put("promoting", "http://developer.android.com/distribute/googleplay/promote/index.html");
            map.put("Linking to Your Products", "http://developer.android.com/distribute/googleplay/promote/linking.html");
            map.put("Google Play Badges", "http://developer.android.com/distribute/googleplay/promote/badges.html");
            map.put("Device Art Generator", "http://developer.android.com/distribute/promote/device-art.html");
            map.put("Brand Guidelines", "http://developer.android.com/distribute/googleplay/promote/brand.html");
            
        }
        else if(category.equals("quality"))
        {
            map.put("quality", "http://developer.android.com/distribute/googleplay/quality/index.html");
            map.put("Core App Quality", "http://developer.android.com/distribute/googleplay/quality/core.html");
            map.put("Tablet App Quality", "http://developer.android.com/distribute/googleplay/quality/tablet.html");
            map.put("Improving App Quality", "http://developer.android.com/distribute/googleplay/strategies/app-quality.html");
            
        }
    }
    
    private void createList()
    {
        
        initialList = new ArrayList<String>();
        
        if(category.equals("getstarted"))
        {
            initialList.add("Creative Vision");
            initialList.add("Design Priciples");
            initialList.add("UI Overview");
        }
        else if(category.equals("style"))
        {
            initialList.add("Devices and Displays");
            initialList.add("Themes");
            initialList.add("Touch Feedback");
            initialList.add("Metrics and Grids");
            initialList.add("Typography");
            initialList.add("Color");
            initialList.add("Iconography");
            initialList.add("Writing Style");
        }
        else if(category.equals("patterns"))
        {
            initialList.add("New in Android");
            initialList.add("Gestures");
            initialList.add("App Structure");
            initialList.add("Navigation");
            initialList.add("Action Bar");
            initialList.add("Multi-pane Layouts");
            initialList.add("Swipe Views");
            initialList.add("Selection");
            initialList.add("Confirming & Acknowledging");
            initialList.add("Notifications");
            initialList.add("Widgets");
            initialList.add("Settings");
            initialList.add("Help");
            initialList.add("Compatibility");
            initialList.add("Accessibility");
            initialList.add("Pure Android");
            
        }
        else if(category.equals("blocks"))
        {
            initialList.add("Tabs");
            initialList.add("Lists");
            initialList.add("Grid Lists");
            initialList.add("Scrolling");
            initialList.add("Spinners");
            initialList.add("Buttons");
            initialList.add("Text Fields");
            initialList.add("Seek Bars");
            initialList.add("Progress and Activity");
            initialList.add("Switches");
            initialList.add("Dialogs");
            initialList.add("Pickers");
            
        }
        else if(category.equals("play"))
        {
            initialList.add("Visibility");
            initialList.add("Monetizing");
            initialList.add("Distribution");
        }
        else if(category.equals("publishing"))
        {
            initialList.add("Get Started");
            initialList.add("Developer Console");
            initialList.add("Publishing Checklist");
        }
        else if(category.equals("promoting"))
        {
            initialList.add("Linking to Your Products");
            initialList.add("Google Play Badges");
            initialList.add("Device Art Generator");
            initialList.add("Brand Guidelines");
        }
        else if(category.equals("quality"))
        {
            initialList.add("Core App Quality");
            initialList.add("Tablet App Quality");
            initialList.add("Improving App Quality");
        }
        
    }
    
    private String getURL(String key)
    {
        return (String)map.get(key);
    }

}
