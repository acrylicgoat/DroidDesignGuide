/**
 * Copyright (c) 2012 Acrylic Goat Software
 * 
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.droiddesign.util;

import java.util.HashMap;

import android.app.Activity;
import android.util.Log;
import android.view.Display;

/**
 * @author ew2
 *
 */
public class DDGUtil
{
    private static HashMap<String,String> map = new HashMap<String,String>();
    
    public static boolean isTabletDevice(Display d, Activity context) 
    {
        if (android.os.Build.VERSION.SDK_INT >= 11) 
        { // honeycomb
            
            if(d.getWidth() < d.getHeight())
            {
                //is portrait so return false
                return false;
            }
            else 
            { 
                 //landscape so check width
                int w = d.getWidth();
                Log.d("DDGUtil", "width = " + w);
                if(w >= 900)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }

        }
        return false;
    }
    
    public static String getCategory(String url)
    {
        if(map.size() < 1)
        {
            createCategoryMap();
        }
        return map.get(url);
    }
    
    private static void createCategoryMap()
    {
        map = new HashMap<String,String>();
        
        
        map.put("http://developer.android.com/design/index.html", "Get Started");
        map.put("http://developer.android.com/design/get-started/creative-vision.html", "Creative Vision" );
        map.put("http://developer.android.com/design/get-started/principles.html", "Design Priciples");
        map.put("http://developer.android.com/design/get-started/ui-overview.html", "UI Overview");
        
    
        map.put("http://developer.android.com/design/style/index.html", "Style");
        map.put("http://developer.android.com/design/style/devices-displays.html", "Devices and Displays");
        map.put("http://developer.android.com/design/style/themes.html", "Themes");
        map.put("http://developer.android.com/design/style/touch-feedback.html", "Touch Feedback");
        map.put("http://developer.android.com/design/style/metrics-grids.html", "Metrics and Grids");
        map.put("http://developer.android.com/design/style/typography.html","Typography");
        map.put("http://developer.android.com/design/style/color.html","Color");
        map.put("http://developer.android.com/design/style/iconography.html","Iconography");
        map.put("http://developer.android.com/design/style/writing.html","Writing Style");
        
    
        map.put("http://developer.android.com/design/patterns/index.html","Patterns");
        map.put("http://developer.android.com/design/patterns/new-4-0.html","New in Android 4.0");
        map.put("http://developer.android.com/design/patterns/gestures.html","Gestures");
        map.put("http://developer.android.com/design/patterns/app-structure.html","App Structure");
        map.put("http://developer.android.com/design/patterns/navigation.html","Navigation");
        map.put("http://developer.android.com/design/patterns/actionbar.html","Action Bar");
        map.put("http://developer.android.com/design/patterns/multi-pane-layouts.html","Multi-pane Layouts");
        map.put("http://developer.android.com/design/patterns/swipe-views.html","Swipe Views");
        map.put("http://developer.android.com/design/patterns/selection.html","Selection");
        map.put("http://developer.android.com/design/patterns/notifications.html","Notifications");
        map.put("http://developer.android.com/design/patterns/compatibility.html","Compatibility");
        map.put("http://developer.android.com/design/patterns/pure-android.html","Pure Android");
        
    
        map.put("http://developer.android.com/design/building-blocks/index.html","Building Blocks");
        map.put("http://developer.android.com/design/building-blocks/tabs.html","Tabs");
        map.put("http://developer.android.com/design/building-blocks/lists.html","Lists");
        map.put("http://developer.android.com/design/building-blocks/grid-lists.html","Grid Lists");
        map.put("http://developer.android.com/design/building-blocks/scrolling.html","Scrolling");
        map.put("http://developer.android.com/design/building-blocks/spinners.html","Spinners");
        map.put("http://developer.android.com/design/building-blocks/buttons.html","Buttons");
        map.put("http://developer.android.com/design/building-blocks/text-fields.html","Text Fields");
        map.put("http://developer.android.com/design/building-blocks/seek-bars.html","Seek Bars");
        map.put("http://developer.android.com/design/building-blocks/progress.html","Progress and Activity");
        map.put("http://developer.android.com/design/building-blocks/switches.html","Switches");
        map.put("http://developer.android.com/design/building-blocks/dialogs.html","Dialogs");
        map.put("http://developer.android.com/design/building-blocks/pickers.html","Pickers");
            
        
        map.put("http://developer.android.com/distribute/index.html","play");
        map.put("http://developer.android.com/distribute/googleplay/about/visibility.html","Visibility");
        map.put("http://developer.android.com/distribute/googleplay/about/monetizing.html","Monetizing");
        map.put("http://developer.android.com/distribute/googleplay/about/distribution.html","Distribution");
   
        map.put("http://developer.android.com/distribute/googleplay/publish/index.html","publishing");
        map.put("http://developer.android.com/distribute/googleplay/publish/register.html","Get Started");
        map.put("http://developer.android.com/distribute/googleplay/publish/console.html","Developer Console");
        map.put("http://developer.android.com/distribute/googleplay/publish/preparing.html","Publishing Checklist");
    
        map.put("http://developer.android.com/distribute/googleplay/promote/index.html","promoting");
        map.put("http://developer.android.com/distribute/googleplay/promote/linking.html","Linking to Your Products");
        map.put("http://developer.android.com/distribute/googleplay/promote/badges.html","Google Play Badges");
        map.put("http://developer.android.com/distribute/promote/device-art.html","Device Art Generator");
        map.put("http://developer.android.com/distribute/googleplay/promote/brand.html","Brand Guidelines");
        
        map.put("http://developer.android.com/distribute/googleplay/quality/index.html","quality");
        map.put("http://developer.android.com/distribute/googleplay/quality/core.html","Core App Quality");
        map.put("http://developer.android.com/distribute/googleplay/quality/tablet.html","Tablet App Quality");
        map.put("http://developer.android.com/distribute/googleplay/strategies/app-quality.html","Improving App Quality");
        
    }

}
