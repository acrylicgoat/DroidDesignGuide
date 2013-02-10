/**
 * Copyright (c) 2013 Acrylic Goat Software
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.droiddesign.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * @author ed
 *
 */
public class LandscapeListAdapter extends ArrayAdapter<String>
{
    /** Current context */
    private Context context;
    /** List of Content objects to display*/
    private ArrayList<String> contentList;
    
    
    public LandscapeListAdapter(Context context, ArrayList <String> contentList)
    {
        super(context, android.R.layout.simple_list_item_activated_1, contentList);
        this.context = context;
        this.contentList = contentList;
        
    }

}
