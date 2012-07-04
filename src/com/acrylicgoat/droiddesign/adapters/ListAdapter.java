/**
 * Copyright (c) 2012 Acrylic Goat Software
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.droiddesign.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.widget.ArrayAdapter;

public class ListAdapter extends ArrayAdapter<String>
{
    /** Current context */
    private Context context;
    /** List of Content objects to display*/
    private ArrayList<String> contentList;
    
    public ListAdapter(Context context, ArrayList <String> contentList)
    {
        super(context, android.R.layout.simple_list_item_1, contentList);
        this.context = context;
        this.contentList = contentList;
        
    }

}
