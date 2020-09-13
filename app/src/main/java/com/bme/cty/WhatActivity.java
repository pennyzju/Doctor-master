
package com.bme.cty;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

public class WhatActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        
	    getActionBar().setDisplayHomeAsUpEnabled(true);        
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_bg));        		
		setContentView(R.layout.activity_what);
		
		String what = getIntent().getExtras().getString("What");		
		if (what == null) {
		    return;
		}
		
		WebView  what_content = (WebView) findViewById(R.id.WhatWebView);
		what_content.getSettings().setDefaultTextEncodingName("gbk");
		if ("BMI".equals(what)) {
		    getActionBar().setTitle(getString(R.string.more_what_bmi));
		    what_content.loadUrl("file:///android_asset/" + "bmi.html");
		}
		else if ("BMR".equals(what)) {
		    getActionBar().setTitle(getString(R.string.more_what_bmr));
		    what_content.loadUrl("file:///android_asset/" + "bmr.html");
		}
		else if("BP".equals(what)){
			getActionBar().setTitle(getString(R.string.more_what_bp));
			what_content.loadUrl("file:///android_asset/" + "bp.htm");
		}
		else {}
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {        
        switch (item.getItemId()) {
        case android.R.id.home:
             finish();
        default:
             break;
        }
        return true;
    }
}
