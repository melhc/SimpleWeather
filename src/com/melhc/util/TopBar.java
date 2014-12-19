package com.melhc.util;

import com.melhc.simpleweather.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 自定义的顶端topbar布局
 * @author Melhc0623
 *
 */
public class TopBar extends RelativeLayout {
   private Button leftButton,rightButton;
   private TextView  tvText;
   private Drawable  leftBackground,rightBackground;
   private float titleTextSize;
   private int titleTextColor;
   private String title;
   private LayoutParams leftParams,rightParams,centerParams;
	@SuppressWarnings("deprecation")
	public TopBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.Topball);
		leftBackground=typedArray.getDrawable(R.styleable.Topball_leftBackground);
		rightBackground=typedArray.getDrawable(R.styleable.Topball_rightBackground);
		titleTextSize=typedArray.getDimension(R.styleable.Topball_titleTextSize, 0);
		titleTextColor=typedArray.getColor(R.styleable.Topball_titleTextColor, 0);
		title=typedArray.getString(R.styleable.Topball_title);
		typedArray.recycle();
		leftButton=new Button(context);
		rightButton=new Button(context);
		tvText=new TextView(context);
		//该方法已经不推荐使用，仅为了向下兼容版本，使android 2.0的用户也能使用我们的软件
		leftButton.setBackgroundDrawable(leftBackground);
		rightButton.setBackgroundDrawable(rightBackground);
		tvText.setText(title);
		tvText.setTextColor(titleTextColor);
		tvText.setTextSize(titleTextSize);
		tvText.setGravity(Gravity.CENTER);
		
		leftParams=new LayoutParams(30,30);
		leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		leftParams.setMargins(10, 0, 0, 0);
		addView(leftButton, leftParams);
		
		rightParams=new LayoutParams(30,30);
		rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rightParams.setMargins(0, 0, 10, 0);
		addView(rightButton, rightParams);
		
		centerParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		centerParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		addView(tvText, centerParams);
	}

}
