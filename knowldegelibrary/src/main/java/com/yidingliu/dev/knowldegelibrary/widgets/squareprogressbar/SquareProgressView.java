package com.yidingliu.dev.knowldegelibrary.widgets.squareprogressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.text.DecimalFormat;

/*绘制进度条*/
public class SquareProgressView extends View {

	private double progress;
	private final Paint progressBarPaint;
	private final Paint outlinePaint;
	private final Paint textPaint;

	private float widthInDp = 0;
	private float strokewidth = 0;
	private Canvas canvas;

	private boolean outline = false;
	private boolean startline = false;
	private boolean showProgress = false;

	private PercentStyle percentSettings = new PercentStyle(Align.CENTER, 150,
			true);

	public SquareProgressView(Context context) {
		super(context);
		progressBarPaint = new Paint();
		progressBarPaint.setColor(context.getResources().getColor(
				android.R.color.holo_green_light));
		progressBarPaint.setStrokeWidth(convertDpToPx(
				widthInDp, getContext()));
		progressBarPaint.setAntiAlias(true);
		progressBarPaint.setStyle(Style.STROKE);

		outlinePaint = new Paint();
		outlinePaint.setColor(context.getResources().getColor(
				android.R.color.black));
		outlinePaint.setStrokeWidth(1);
		outlinePaint.setAntiAlias(true);
		outlinePaint.setStyle(Style.STROKE);

		textPaint = new Paint();
		textPaint.setColor(context.getResources().getColor(
				android.R.color.black));
		textPaint.setAntiAlias(true);
		textPaint.setStyle(Style.STROKE);
		_90du = (float) Math.PI * radio / 2;
	}

	public SquareProgressView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		progressBarPaint = new Paint();
		progressBarPaint.setColor(context.getResources().getColor(
				android.R.color.holo_green_light));
		progressBarPaint.setStrokeWidth(convertDpToPx(
				widthInDp, getContext()));
		progressBarPaint.setAntiAlias(true);
		progressBarPaint.setStyle(Style.STROKE);

		outlinePaint = new Paint();
		outlinePaint.setColor(context.getResources().getColor(
				android.R.color.black));
		outlinePaint.setStrokeWidth(1);
		outlinePaint.setAntiAlias(true);
		outlinePaint.setStyle(Style.STROKE);

		textPaint = new Paint();
		textPaint.setColor(context.getResources().getColor(
				android.R.color.black));
		textPaint.setAntiAlias(true);
		_90du = (float) Math.PI * radio / 2;
		textPaint.setStyle(Style.STROKE);
	}

	public SquareProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		progressBarPaint = new Paint();
		progressBarPaint.setColor(context.getResources().getColor(
				android.R.color.holo_green_light));
		progressBarPaint.setStrokeWidth(convertDpToPx(
				widthInDp, getContext()));
		progressBarPaint.setAntiAlias(true);
		progressBarPaint.setStyle(Style.STROKE);

		outlinePaint = new Paint();
		outlinePaint.setColor(context.getResources().getColor(
				android.R.color.black));
		outlinePaint.setStrokeWidth(1);
		outlinePaint.setAntiAlias(true);
		outlinePaint.setStyle(Style.STROKE);

		textPaint = new Paint();
		textPaint.setColor(context.getResources().getColor(
				android.R.color.black));
		textPaint.setAntiAlias(true);
		textPaint.setStyle(Style.STROKE);
		_90du = (float) Math.PI * radio / 2;
	}


	float radio = 10; // 四周圆半径
	float _90du; // 90度的圆周长
	float distance_spacing = 1f; //填充圆弧和path之间的空隙

	RectF rextF = new RectF(0, 0, 0, 0);

	float halfOfTheImageWLine;
	float ImageHLine;

	@Override
	protected void onDraw(Canvas canvas) {
		this.canvas = canvas;
		super.onDraw(canvas);
		strokewidth = convertDpToPx(widthInDp, getContext());

		if (outline) {
			drawOutline();
		}

		if (isStartline()) {
			drawStartline();
		}

		if (showProgress) {
			drawPercent(percentSettings);
		}
		
		halfOfTheImageWLine = getWidth() / 2 - radio - strokewidth / 2;
		ImageHLine = getHeight() - 2 * radio - strokewidth;
		
		float scope = (float) (4*halfOfTheImageWLine +2*ImageHLine +4*_90du);
		float percent = (scope / 100f) * Float.valueOf(String.valueOf(progress)); // 当前距离

		Path path = new Path();
		
		if (percent > halfOfTheImageWLine) {
			paintFirstHalfOfTheTop(canvas); // changed 绘制上边
			float second = percent - halfOfTheImageWLine;

			if (second > (ImageHLine + _90du * 2)) {
				paintLeftSide(canvas);// changed 绘制左边

				float third = second - (ImageHLine + _90du * 2);
				if (third > halfOfTheImageWLine * 2) {
					paintBottomSide(canvas); // changed 绘制底边
					float forth = third - halfOfTheImageWLine * 2;

					if (forth > ImageHLine + _90du * 2) {
						// paintLeftSide(canvas);
						paintRightSide(canvas);

						float fifth = forth - (ImageHLine + _90du * 2);
						
						if (fifth == halfOfTheImageWLine) {
							paintSecondHalfOfTheTop(canvas);
						} else {

							path.moveTo(getWidth()-strokewidth / 2-radio+distance_spacing, strokewidth / 2);
							path.lineTo(getWidth() - fifth - strokewidth/2-radio,strokewidth / 2);
							canvas.drawPath(path, progressBarPaint);
						}
					} else {

						float circle_x1 = getWidth() - 2 * radio - strokewidth
								/ 2;
						float circle_x2 = getWidth() - strokewidth / 2;

						// 绘制右边 动态
						if (forth > _90du + ImageHLine) {

							// 右下半圆+右直线 +绘制右上半圆动态

							// 右下半圆
							rextF.set(circle_x1, getHeight() - strokewidth / 2
									- radio * 2, circle_x2, getHeight()
									- strokewidth / 2);
							canvas.drawArc(rextF, 0, 90, false,
									progressBarPaint);

							// 右直线
							path.moveTo(getWidth() - strokewidth / 2,
									strokewidth / 2 + radio-distance_spacing);
							path.lineTo(getWidth() - strokewidth / 2,
									getHeight() - radio - strokewidth / 2+distance_spacing);
							canvas.drawPath(path, progressBarPaint);

							// 右上半圆动态
							forth = forth - (_90du + ImageHLine);
							float per = forth / _90du;

							rextF.set(circle_x1, strokewidth / 2, circle_x2,
									strokewidth / 2 + 2 * radio);
							canvas.drawArc(rextF, 270 + 90 * (1 - per),
									90 * per, false, progressBarPaint);

						} else if (forth > _90du) {

							// 右下半圆
							rextF.set(circle_x1, getHeight() - strokewidth / 2
									- radio * 2, circle_x2, getHeight()
									- strokewidth / 2);
							canvas.drawArc(rextF, 0, 90, false,
									progressBarPaint);

							forth = forth- _90du;
							// 右边直线 动态
							path.moveTo(getWidth() - strokewidth / 2,
									getHeight() - strokewidth / 2 - radio+distance_spacing);
							path.lineTo(getWidth() - strokewidth / 2,
									getHeight() - strokewidth / 2 - radio
											- forth);
							canvas.drawPath(path, progressBarPaint);

						} else {
							// 绘制右下半圆动态
							float per = forth / _90du;
							rextF.set(circle_x1, getHeight() - strokewidth / 2
									- radio * 2, circle_x2, getHeight()
									- strokewidth / 2);
							canvas.drawArc(rextF, 90 - 90 * per, 90 * per,
									false, progressBarPaint);
						}
					}

				} else {
					// path.moveTo(getWidth() - strokewidth,
					// getHeight() - (strokewidth / 2));
					// path.lineTo(getWidth() - third, getHeight()
					// - (strokewidth / 2));
					// canvas.drawPath(path, progressBarPaint);

					// change 绘制底边 从左到右边
					path.moveTo(strokewidth / 2 + radio, getHeight()
							- (strokewidth / 2));

					path.lineTo(third + strokewidth / 2 + radio, getHeight()
							- (strokewidth / 2));
					canvas.drawPath(path, progressBarPaint);
				}
			} else {
				// 绘制左边 动态
				if (second > _90du + ImageHLine) {

					// 左上半圆+左直线 +绘制左下半圆动态

					// 左上半圆
					rextF.set(strokewidth / 2, strokewidth / 2, strokewidth / 2
							+ radio * 2, strokewidth / 2 + radio * 2);
					canvas.drawArc(rextF, 180, 90, false, progressBarPaint);

					// 左直线
					path.moveTo((strokewidth / 2), strokewidth / 2 + radio-distance_spacing);
					path.lineTo((strokewidth / 2), getHeight() - radio
							- strokewidth / 2+distance_spacing);
					canvas.drawPath(path, progressBarPaint);

					// 左下半圆动态
					second = second - (_90du + ImageHLine);
					float per = second / _90du;

					rextF.set(strokewidth / 2, getHeight() - strokewidth / 2
							- 2 * radio, strokewidth + radio * 2, getHeight()
							- strokewidth / 2);
					canvas.drawArc(rextF, 90 + 90 * (1 - per), 90 * per, false,
							progressBarPaint);

				} else if (second > _90du) {

					// 左上半圆
					rextF.set(strokewidth / 2, strokewidth / 2, strokewidth / 2
							+ radio * 2, strokewidth / 2 + radio * 2);
					canvas.drawArc(rextF, 180, 90, false, progressBarPaint);
					second = second - _90du;

					// 左边直线 动态
					path.moveTo((strokewidth / 2), strokewidth / 2 + radio-distance_spacing);
					path.lineTo((strokewidth / 2), strokewidth / 2 + second
							+ radio);
					canvas.drawPath(path, progressBarPaint);

				} else {

					// 绘制左上半圆动态
					float per = second / _90du;
					rextF.set(strokewidth / 2, strokewidth / 2, strokewidth / 2
							+ radio * 2, strokewidth / 2 + radio * 2);
					canvas.drawArc(rextF, 270 - 90 * per, 90 * per, false,
							progressBarPaint);
				}
			}
		} else {
			// changed 左上角
			path.moveTo(getWidth() / 2, strokewidth / 2);
			path.lineTo(getWidth() / 2 - percent, strokewidth / 2);
			canvas.drawPath(path, progressBarPaint);
		}
	}

	private void drawStartline() {
		Path outlinePath = new Path();
		outlinePath.moveTo(getWidth() / 2, 0);
		outlinePath.lineTo(getWidth() / 2, strokewidth);
		canvas.drawPath(outlinePath, outlinePaint);
	}

	private void drawOutline() {
		Path outlinePath = new Path();
		outlinePath.moveTo(0, 0);
		outlinePath.lineTo(getWidth(), 0);
		outlinePath.lineTo(getWidth(), getHeight());
		outlinePath.lineTo(0, getHeight());
		outlinePath.lineTo(0, 0);
		canvas.drawPath(outlinePath, outlinePaint);
	}

	
	public void paintFirstHalfOfTheTop(Canvas canvas) {
		Path path = new Path();
		path.moveTo(getWidth() / 2, strokewidth / 2);
		path.lineTo(radio+strokewidth / 2-distance_spacing, strokewidth / 2);
		canvas.drawPath(path, progressBarPaint);
	}

	// 右下半圆+右直线 +绘制右上半圆动态
	public void paintRightSide(Canvas canvas) {

		Path path = new Path();

		float circle_x1 = getWidth() - 2 * radio - strokewidth / 2;
		float circle_x2 = getWidth() - strokewidth / 2;

		// 右下半圆
		rextF.set(circle_x1, getHeight() - strokewidth / 2 - radio * 2,
				circle_x2, getHeight() - strokewidth / 2);
		canvas.drawArc(rextF, 0, 90, false, progressBarPaint);

		// 右直线
		path.moveTo(getWidth() - strokewidth / 2, strokewidth / 2 + radio-distance_spacing);
		path.lineTo(getWidth() - strokewidth / 2, getHeight() - radio
				- strokewidth / 2+distance_spacing);
		canvas.drawPath(path, progressBarPaint);

		// 右上半圆动态
		rextF.set(circle_x1, strokewidth / 2, circle_x2, strokewidth / 2 + 2
				* radio);
		canvas.drawArc(rextF, 270, 90, false, progressBarPaint);
	}

	public void paintBottomSide(Canvas canvas) {
		Path path = new Path();
		path.moveTo(getWidth() - strokewidth / 2 - radio+distance_spacing, getHeight()
				- (strokewidth / 2));
		path.lineTo(radio + strokewidth / 2-distance_spacing, getHeight() - (strokewidth / 2));
		canvas.drawPath(path, progressBarPaint);
	}

	// 左上半圆+左直线 +绘制左下半圆
	public void paintLeftSide(Canvas canvas) {

		Path path = new Path();

		// 左上半圆
		rextF.set(strokewidth / 2, strokewidth / 2, strokewidth / 2
				+ radio * 2, strokewidth / 2 + radio * 2);
		canvas.drawArc(rextF, 180, 90, false, progressBarPaint);

		// 左直线
		path.moveTo((strokewidth / 2), strokewidth / 2 + radio-distance_spacing);
		path.lineTo((strokewidth / 2), getHeight() - radio - strokewidth / 2+distance_spacing);
		canvas.drawPath(path, progressBarPaint);

		// 左下半圆
		rextF.set(strokewidth / 2, getHeight() - strokewidth / 2 - 2 * radio,
				strokewidth + radio * 2, getHeight() - strokewidth / 2);
		canvas.drawArc(rextF, 90, 90, false, progressBarPaint);
	}

	public void paintSecondHalfOfTheTop(Canvas canvas) {
		Path path = new Path();
		path.moveTo(getWidth()-strokewidth/2-radio+distance_spacing, (strokewidth / 2));
		path.lineTo(getWidth() / 2, (strokewidth / 2));
		canvas.drawPath(path, progressBarPaint);
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
		this.invalidate();
	}

	public void setColor(int color) {
		progressBarPaint.setColor(color);
		this.invalidate();
	}
	
	public void setRadio(float radio) {
		this.radio = radio;
		if(radio <= 3){
			distance_spacing = strokewidth/2;
		}
		else{
			distance_spacing = 1f;
		}
		this.invalidate();
	}

	/**
	 * @return the border
	 */
	public float getWidthInDp() {
		return widthInDp;
	}

	/**
	 * @return the border
	 */
	public void setWidthInDp(int width) {
		this.widthInDp = width;
		progressBarPaint.setStrokeWidth(convertDpToPx(
				widthInDp, getContext()));
		this.invalidate();
	}

	public boolean isOutline() {
		return outline;
	}

	public void setOutline(boolean outline) {
		this.outline = outline;
		this.invalidate();
	}

	public boolean isStartline() {
		return startline;
	}

	public void setStartline(boolean startline) {
		this.startline = startline;
		this.invalidate();
	}

	private void drawPercent(PercentStyle setting) {
		textPaint.setTextAlign(setting.getAlign());
		if (setting.getTextSize() == 0) {
			textPaint.setTextSize((getHeight() / 10) * 4);
		} else {
			textPaint.setTextSize(setting.getTextSize());
		}

		String percentString = new DecimalFormat("###").format(getProgress());
		if (setting.isPercentSign()) {
			percentString = percentString + "%";
		}

		canvas.drawText(percentString, getWidth() / 2,
				(int) ((getHeight() / 2) - ((textPaint.descent() + textPaint
						.ascent()) / 2)), textPaint);
	}

	public boolean isShowProgress() {
		return showProgress;
	}

	public void setShowProgress(boolean showProgress) {
		this.showProgress = showProgress;
		this.invalidate();
	}

	public void setPercentStyle(PercentStyle percentSettings) {
		this.percentSettings = percentSettings;
		this.invalidate();
	}

	public PercentStyle getPercentStyle() {
		return percentSettings;
	}

	private int convertDpToPx(float dp, Context context) {
		return (int) TypedValue.applyDimension ( TypedValue.COMPLEX_UNIT_DIP, dp,
		                                         context.getResources().getDisplayMetrics() );
	}

}
