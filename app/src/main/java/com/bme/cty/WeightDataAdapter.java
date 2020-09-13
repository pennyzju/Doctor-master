
package com.bme.cty;

import com.bme.cty.R;
import com.bme.cty.core.WeightDB;
import com.bme.cty.core.WeightDB.Weight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeightDataAdapter extends BaseAdapter {

	private Context mContext;
	private String mCondition = null;
	
	protected class ViewHolder {
	    TextView mWeightWeek;
	    TextView mWeightData;
	    TextView mWeightBMI;
	    TextView mWeightValue;	    
	}
	
	public WeightDataAdapter(Context context,String condition) {				
		mContext = context;
		mCondition = condition;
	}
	
	@Override
	public int getCount() {
		return WeightDB.getInstance().size(mCondition);
	}

	@Override
	public Object getItem(int position) {		
		return WeightDB.getInstance().get(position,mCondition);
	}

	@Override
	public long getItemId(int position) {		
		return ((Weight)getItem(position)).key;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {				
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);          
            convertView = (LinearLayout)inflater.inflate(R.layout.widget_weight_item, null);
            ViewHolder holder = new ViewHolder();
            holder.mWeightWeek  = (TextView)convertView.findViewById(R.id.WeightWeek);
            holder.mWeightData  = (TextView)convertView.findViewById(R.id.WeightData);
            holder.mWeightBMI   = (TextView)convertView.findViewById(R.id.WeightBMI);
            holder.mWeightValue = (TextView)convertView.findViewById(R.id.WeightValue);            
            convertView.setTag(holder);
		}				

		Weight weight = (Weight)getItem(position);
		if (weight != null) {
		    WeightData data = new WeightData(weight);
		    ViewHolder holder = (ViewHolder)convertView.getTag();
		    holder.mWeightWeek.setText(data.getWeekStr());
		    holder.mWeightData.setText(data.getDateStr());
		    holder.mWeightBMI.setText(data.getBMIStr());
		    holder.mWeightValue.setText(data.getWeightStr());					    
		}		
		
		return convertView;
	}

}
