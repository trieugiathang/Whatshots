/**
 * Copyright 2009 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.view.MainHome;

import com.markupartist.android.widget.ActionBar;

import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/**
 *  Mo ta muc dich cua lop (interface)
 *  @author: HocTB
 *  @version: 1.0
 *  @since: Aug 20, 2009
 */
public class EventInfoTab extends Activity {
	View menu;
    View app;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = LayoutInflater.from(this);
		app = inflater.inflate(com.viettel.R.layout.layout_tab_info_event, null);
		setContentView(app);
		
		final View[] children = new View[] { app };

        // Scroll to app (view[1]) when layout finished.
        int scrollToViewIdx = 1;
       // scrollView.initViews(children, scrollToViewIdx, ne
	}
}
