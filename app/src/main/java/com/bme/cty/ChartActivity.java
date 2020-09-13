
package com.bme.cty;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bme.cty.Configuration.OnChartModeChangeListener;
import com.bme.cty.chart.ChartAdapter;
import com.bme.cty.chart.DayChartAdapter;
import com.bme.cty.chart.MonthChartAdapter;
import com.bme.cty.chart.ChartFolder;
import com.bme.cty.chart.YearChartAdapter;
import com.bme.cty.core.WeightDB;
import com.bme.cty.core.WeightDB.OnDBDataChangeListener;
import com.bme.cty.widget.ChartDateSelector;
import com.bme.cty.widget.ChartDateSelector.OnDateSelectedListener;

public class ChartActivity extends Activity implements OnDateSelectedListener,OnChartModeChangeListener,OnDBDataChangeListener {

	private TextView mNoDataTips;	
	private ChartDateSelector mDateSeletor;
	private ChartFolder mChartFolder;
    private ChartAdapter mChartAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);						 
		setContentView(R.layout.activity_chart);		
		
		mNoDataTips = (TextView) findViewById(R.id.NoDataTipView);
		LinearLayout mChartLayout = (LinearLayout)findViewById(R.id.ChartLinearLayout);
        mChartFolder = new ChartFolder(mChartLayout);
		
		mDateSeletor = new ChartDateSelector(findViewById(R.id.WidgetChartDateSelector));		
		mDateSeletor.setChartMode(Configuration.getChartMode());		
		mDateSeletor.setOnDateSelectedListener(this);
		onDateSelected();
					
		Configuration.setOnChartModeChangeListener(this);
	}

	@Override
	public void onResume() {		
		super.onResume();		
		WeightDB.getInstance().setOnDBDataChangeListener(this);
	}
	
	@Override
	public void onPause() {	
	    WeightDB.getInstance().setOnDBDataChangeListener(null);		
		super.onPause();		
	}
	
	@Override
	public void onDateSelected() {		
        switch(mDateSeletor.getChartMode()) {
        case CommonUtil.ChartModeYear:
             mChartAdapter = new YearChartAdapter();
             break;
        case CommonUtil.ChartModeMonth:
             mChartAdapter = new MonthChartAdapter(mDateSeletor.getSelectYear());      
             break;
        case CommonUtil.ChartModeDay:
             mChartAdapter = new DayChartAdapter(mDateSeletor.getSelectYear(),mDateSeletor.getSelectMonth());
             break;
        default:
             break;
        }
        mChartFolder.setChartAdapter(mChartAdapter);
        onChartViewUpdate();
	}

	@Override
    public void onDBDataChanged() {
        onChartViewUpdate();
    }
	
    @Override
    public void onChartModeChanged(int chartmode) {
        mDateSeletor.setChartMode(chartmode);        
        onDateSelected();
    }

    protected void onChartViewUpdate() {       
        mChartFolder.invalidate();        
        mNoDataTips.setVisibility(mChartAdapter.isEmpty()?View.VISIBLE:View.GONE);        
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {        
        if (keyCode == KeyEvent.KEYCODE_BACK) {                              
            if (!CommonUtil.onExitProcess(this)) {
            	return true;
            }
        }        
        return super.onKeyDown(keyCode, event);
    }
}
