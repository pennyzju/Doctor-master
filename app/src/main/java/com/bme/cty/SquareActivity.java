
package com.bme.cty;

import com.bme.cty.core.WeightDB;
import com.bme.cty.core.WeightDB.Weight;
import com.bme.cty.widget.SquareItemWidget;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class SquareActivity extends Activity {

    public static final String BP_HIGH  = "偏高";
    public static final String BP_NICE  = "正常";
    public static final String BP_LOW  = "偏低";

	public static final String BMI_THIN_WEIGHT  = "偏瘦";
	public static final String BMI_NICE_WEIGHT  = "完美";
	public static final String BMI_FAT_WEIGHT   = "偏胖";
	public static final String BMI_BIG_WEIGHT  = "肥胖";

    private SquareItemWidget mBpItem;
    private SquareItemWidget mWeightItem;
	private SquareItemWidget mBMIItem;
	private SquareItemWidget mBMRItem;
	private SquareItemWidget mBpResultItem;
    private SquareItemWidget mDestBp;
    private SquareItemWidget mWeightResultItem;
	private SquareItemWidget mDestWeight;
	private TextView mPersonalTips;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);		 
		setContentView(R.layout.activity_square);
		
		mPersonalTips = (TextView)findViewById(R.id.PersionalTips);
        mBpItem = new SquareItemWidget(findViewById(R.id.SquareBpItem),
                getString(R.string.square_bp));
        mWeightItem = new SquareItemWidget(findViewById(R.id.SquareWeightItem),
				getString(R.string.square_weight));		
		mBMIItem = new SquareItemWidget(findViewById(R.id.SquareBMIItem),
				getString(R.string.square_bmi));		
		mBMRItem = new SquareItemWidget(findViewById(R.id.SquareBMRItem),
				getString(R.string.square_bmr));
        mBpResultItem = new SquareItemWidget(findViewById(R.id.SquareBpResult),
                getString(R.string.square_bp_result));
        mDestBp = new SquareItemWidget(findViewById(R.id.SquareDestBp),
                getString(R.string.square_dest_bp));
		mWeightResultItem = new SquareItemWidget(findViewById(R.id.SquareWeightResult),
				getString(R.string.square_weight_result));
		mDestWeight = new SquareItemWidget(findViewById(R.id.SquareDestWeight),
				getString(R.string.square_dest_weight));
	}
	
	public void onResume() {		
		super.onResume();
		onSquareUpdate();
	}
	
	protected void onSquareUpdate() {
	    
	    //判断是否有体重数据
        Weight weight = WeightDB.getInstance().get(0);
        if (weight == null) {
            mPersonalTips.setText(getString(R.string.no_data));
            mPersonalTips.setVisibility(View.VISIBLE);
            mBpItem.setItemValue("");
            mWeightItem.setItemValue("");
            mBMIItem.setItemValue("");
            mBMRItem.setItemValue("");
            mDestBp.setItemValue("");
            mBpResultItem.setItemValue("");
            mDestWeight.setItemValue("");
            mWeightResultItem.setItemValue("");
            return;
        }
        
        //判断个人信息是否填写好了
        double height = Configuration.getUserHeight();
        int age = Configuration.getUserAge();       
        if (height <= 0.0 || age <= 0) {
            mPersonalTips.setText(getString(R.string.personalTips));
            mPersonalTips.setVisibility(View.VISIBLE);
        }
        else {
            mPersonalTips.setVisibility(View.GONE);
        }
        
        //显示BMI指标分析结果
        WeightData weightdata = new WeightData(weight);     
        mWeightItem.setItemValue(weightdata.getWeightStr());
        String bmi = weightdata.getBMIValue();
        if (!"".equals(bmi)) {         
            mBMIItem.setItemValue(bmi);         
            double BMI = Double.valueOf(bmi);               
            if (BMI < 18.5) {
                mWeightResultItem.setItemValue(BMI_THIN_WEIGHT);
            }
            else if (BMI >= 18.5 && BMI < 24) {
                mWeightResultItem.setItemValue(BMI_NICE_WEIGHT);
            }
            else if (BMI >= 24 && BMI < 28) {
                mWeightResultItem.setItemValue(BMI_FAT_WEIGHT);
            }
            else {
                mWeightResultItem.setItemValue(BMI_BIG_WEIGHT);
            }
        }
                
        //显示目标体重
        if (height != 0) {
            double HEIGHT = Double.valueOf(height);
            String destWeight = CommonUtil.calculateMinWeight(HEIGHT) + "kg" + 
                    "～" + CommonUtil.calculateMaxWeight(HEIGHT) + "kg";
            mDestWeight.setItemValue(destWeight);                                   
        }
                    
        //显示BMR
        int bmr_age = Configuration.getUserAge();           
        double bmr_height = Configuration.getUserHeight();
        int bmr_sex = Configuration.getUserSex();
        if (bmr_age!=0 && !"".equals(weight) && bmr_height!= 0) {
            double bmr_weight = Double.valueOf(weightdata.getWeightValue());
            String bmrStr = CommonUtil.calculateBMR(bmr_sex, bmr_weight, bmr_height, bmr_age) + "千卡";
            mBMRItem.setItemValue(bmrStr);
        }       
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
