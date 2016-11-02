package com.yidingliu.dev.knowldegelibrary.widgets.squareprogressbar;

import android.graphics.Paint.Align;

public class PercentStyle {
	private Align align;
	private float textSize;
	private boolean percentSign;

	public PercentStyle () {
		// TODO Auto-generated constructor stub
	}

	public PercentStyle ( Align align, float textSize, boolean percentSign ) {
		super();
		this.align = align;
		this.textSize = textSize;
		this.percentSign = percentSign;
	}

	public Align getAlign() {
		return align;
	}

	public void setAlign(Align align) {
		this.align = align;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public boolean isPercentSign() {
		return percentSign;
	}

	public void setPercentSign(boolean percentSign) {
		this.percentSign = percentSign;
	}

}