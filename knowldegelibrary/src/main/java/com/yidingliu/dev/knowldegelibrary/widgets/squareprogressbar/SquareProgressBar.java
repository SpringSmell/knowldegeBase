package com.yidingliu.dev.knowldegelibrary.widgets.squareprogressbar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yidingliu.dev.knowldegelibrary.R;


/**
 * The basic {@link SquareProgressBar}. THis class includes all the methods you
 * need to modify your {@link SquareProgressBar}.
 *
 * 像普通控件一样在xml定义后在代码中加入如下代码控制
 *
 *  squareProgressBar = (SquareProgressBar) findViewById(R.id.square_progress_bar);
 *  squareProgressBar.setImage(R.drawable.pic01);
 *  squareProgressBar.setColor("#ff99cc00");
 *  squareProgressBar.setProgress(100);
 *  squareProgressBar.setWidth(8);
 *  squareProgressBar.setOpacity(false);
 *
 *  squareProgressBar.setAutoProgressListener (40, new SquareProgressFinshListener () {
 *
 * @author   modify by hzm
 *
 */
public class SquareProgressBar extends RelativeLayout {

	private ImageView imageView;
	
	private View layout_in_pro;
//	private ImageView 
	
	private final SquareProgressView bar;
	private boolean opacity = false;
	private boolean greyscale;

	SquareProgressFinshListener listener;
	private double progressTick=0f;
	private CountDownTimer mCountDownTimer;

	/**
	 * New SquareProgressBar.
	 * 
	 * @param context
	 *            the {@link Context} 
	 * @param attrs
	 *            an {@link AttributeSet} 
	 * @param defStyle
	 *            a defined style.
	 * @since 1.0.0
	 */
	public SquareProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mInflater.inflate ( R.layout.progressbarview, this, true );
		bar = (SquareProgressView) findViewById ( R.id.squareProgressBar1 );
		bar.bringToFront(); 	
	}	

	/**
	 * New SquareProgressBar.
	 * 
	 * @param context
	 *            the {@link Context}
	 * @param attrs
	 *            an {@link AttributeSet}
	 * @since 1.0.0
	 */
	public SquareProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mInflater.inflate(R.layout.progressbarview, this, true);
		bar = (SquareProgressView) findViewById(R.id.squareProgressBar1);
		bar.bringToFront();
	}

	/**
	 * New SquareProgressBar.
	 * 
	 * @param context
	 * @since 1.0.0
	 */
	public SquareProgressBar(Context context) {
		super(context);
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mInflater.inflate(R.layout.progressbarview, this, true);
		bar = (SquareProgressView) findViewById(R.id.squareProgressBar1);
		bar.bringToFront();
	}
	
	/**
	 * Sets the image of the {@link SquareProgressBar}. Must be a valid
	 * ressourceId.
	 * 
	 * @param image
	 *            the image as a ressourceId
	 * @since 1.0 	
	 */
	public void setImage(int image) {
		imageView = (ImageView) findViewById(R.id.imageView1);
		imageView.setImageResource(image);
	}

	/**
	 * Sets the progress of the {@link SquareProgressBar}. If opacity is
	 * selected then here it sets it. See {@link #setOpacity(boolean)} for more
	 * information. 
	 * 
	 * @param progress
	 *            the progress
	 * @since 1.0.0
	 */
	public void setProgress(double progress) {
		bar.setProgress(progress);
		if (opacity) {
			setOpacity((int) progress);
		} else {
			setOpacity(100);
		}
	}
    //============================================自动进度条 hzm=======================================================


	/**
	 * @param time  设置进度条消失时间（单位秒）
	 * @param autoProgressListener 设置进度条消失时的回调
	 */
	public void setAutoProgressListener ( int time, SquareProgressFinshListener autoProgressListener ){

		listener=autoProgressListener;
		progressTick=  bar.getProgress ()/(time*10);

		mCountDownTimer = new CountDownTimer( time * 1000, 100) {//倒计时为30秒。每100毫秒 onTick一次
			@Override
			public void onTick(long millisUntilFinished) {

				setProgress(bar.getProgress ()-progressTick);
				//Log.e ("hzm","getProgress:"+bar.getProgress ());
			}
			@Override
			public void onFinish() {
				setProgress(0);
				listener.callback();
				//Log.e ("hzm","onFinish");
			}
		};
	}

	/**
	 * @param mBoolean 进度条开始计时 。需要先调用setAutoProgressListener()
	 */
	public void setAutoProgresStart(boolean mBoolean){

		if(mBoolean && mCountDownTimer!=null)  mCountDownTimer.start ();

	}

	/**
	 * @param mBoolean 取消进度条的倒计时
	 */
	public void cancelAutoProgres(boolean mBoolean){

		if(mBoolean && mCountDownTimer!=null)  mCountDownTimer.cancel ();

	}


	//============================================自动进度条 hzm=======================================================
	/* 设置矩形四个角的弧度*/
	public void  setRadio(float radio){
		bar.setRadio(radio);
	}

	/**
	 * Sets the colour of the {@link SquareProgressBar} to a predefined android
	 * holo color. <br/>
	 * <b>Examples:</b>
	 * <ul>
	 * <li>holo_blue_bright</li>
	 * <li>holo_blue_dark</li>
	 * <li>holo_blue_light</li>
	 * <li>holo_green_dark</li>
	 * <li>holo_green_light</li>
	 * <li>holo_orange_dark</li>
	 * <li>holo_orange_light</li>
	 * <li>holo_purple</li>
	 * <li>holo_red_dark</li>
	 * <li>holo_red_light</li>
	 * </ul>
	 * 
	 * @param androidHoloColor  设置进度条的颜色
	 * @since 1.0.0
	 */
	public void setHoloColor(int androidHoloColor) {
		bar.setColor(getContext().getResources().getColor(androidHoloColor));
	}

	/**
	 * Sets the colour of the {@link SquareProgressBar}. YOu can give it a
	 * hex-color string like <i>#C9C9C9</i>.
	 * 
	 * @param colorString
	 *            the colour of the {@link SquareProgressBar}
	 * @since 1.1.0
	 */
	public void setColor(String colorString) {
		bar.setColor(Color.parseColor(colorString));
	}

	//add by hzm
	public void setColor(int colorint) {
		bar.setColor(colorint);
	}
	/**
	 * This sets the colour of the {@link SquareProgressBar} with a RGB colour.
	 * 
	 * @param r
	 *            red
	 * @param g
	 *            green
	 * @param b
	 *            blue�
	 * @since 1.1.0
	 */
	public void setColorRGB(int r, int g, int b) {
		bar.setColor(Color.rgb(r, g, b));
	}

	/**
	 * This sets the width of the {@link SquareProgressBar}.
	 * 
	 * @param width 设置进度条的宽度
	 *            in Dp
	 * @since 1.1.0
	 */
	public void setWidth(int width) {
		int padding = convertDpToPx(width, getContext());
		
//		layout_in_pro = findViewById(R.id.layout_in_pro);
//		layout_in_pro.setPadding(padding, padding, padding, padding);
		bar.setWidthInDp(width);
	}

	private int convertDpToPx(float dp, Context context) {
		return (int) TypedValue.applyDimension ( TypedValue.COMPLEX_UNIT_DIP, dp,
		                                         context.getResources().getDisplayMetrics() );
	}

	/**
	 * This sets the alpha of the image in the view. Actually I need to use the
	 * deprecated method here as the new one is only available for the API-level
	 * 16. And the min API level o this library is 14.
	 * 
	 * Use this only as private method.
	 * 
	 * @param progress 调整图像的不透明度使其跟随进度
	 *            the progress
	 */
	private void setOpacity(int progress) {
		imageView.setAlpha((int) (2.55 * progress));
	}

	/**
	 * Switches the opacity state of the image. This forces the
	 * SquareProgressBar to redraw with the current progress. As bigger the
	 * progress is, then more of the image comes to view. If the progress is 0,
	 * then you can't see the image at all. If the progress is 100, the image is
	 * shown full.
	 *  调整图像的可以调整不透明度
	 * @param opacity
	 *            true if opacity should be enabled.
	 * @since 1.2.0
	 */
	public void setOpacity(boolean opacity) {
		this.opacity = opacity;
		setProgress(bar.getProgress());
	}

	/**
	 * You can set the image to b/w with this method. Works fine with the
	 * opacity.
	 * 
	 * @param greyscale 设置图像的灰度值
	 *            true if the grayscale should be activated.
	 * @since 1.2.0 / but never used in the example application
	 */
	public void setImageGrayscale(boolean greyscale) {
		this.greyscale = greyscale;
		if (greyscale) {
			ColorMatrix matrix = new ColorMatrix();
			matrix.setSaturation(0);
			imageView.setColorFilter(new ColorMatrixColorFilter(matrix));
		} else {
			imageView.setColorFilter(null);
		}
	}

	/**
	 * If opacity is enabled.
	 * 
	 * @return true if opacity is enabled. 返回是否调整了图像不透明度
	 */
	public boolean isOpacity() {
		return opacity;
	}

	/**
	 * If greyscale is enabled.
	 * 
	 * @return true if greyscale is enabled.返回是否调整了图像灰度
	 */
	public boolean isGreyscale() {
		return greyscale;
	}

	/**
	 * Draws an outline of the progressbar. Looks quite cool in some situations.
	 * 
	 * @param drawOutline 设置进度条的外边框
	 *            true if it should or not.
	 * @since 1.3.0
	 */
	public void drawOutline(boolean drawOutline) {
		bar.setOutline(drawOutline);
	}

	/**
	 * If outline is enabled or not.
	 * 
	 * @return true if outline is enabled.
	 */
	public boolean isOutline() {
		return bar.isOutline();
	}

	/**
	 * Draws the startline. this is the line where the progressbar starts the
	 * drawing around the image.
	 * 
	 * @param drawStartline
	 *            true if it should or not.
	 * @since 1.3.0
	 */
	public void drawStartline(boolean drawStartline) {
		bar.setStartline(drawStartline);
	}

	/**
	 * If the startline is enabled.
	 * 
	 * @return true if startline is enabled or not.
	 */
	public boolean isStartline() {
		return bar.isStartline();
	}

	/**
	 * Defines if the percent text should be shown or not. To modify the text
	 * checkout {@link #setPercentStyle(PercentStyle)}.
	 * 
	 * @param showProgress
	 *            true if it should or not.
	 * @since 1.3.0
	 */
	public void showProgress(boolean showProgress) {
		bar.setShowProgress(showProgress);
	}

	/**
	 * If the progress text inside of the image is enabled.
	 * 
	 * @return true if it is or not.
	 */
	public boolean isShowProgress() {
		return bar.isShowProgress();
	}

	/**
	 * Sets a custom percent style to the text inside the image. Make sure you
	 * set {@link #showProgress(boolean)} to true. Otherwise it doesn't shows.
	 * The default settings are:</br>
	 * <table>
	 * <tr>
	 * <th>Text align</td>
	 * <td>CENTER</td>
	 * </tr>
	 * <tr>
	 * <th>Text size</td>
	 * <td>150 [dp]</td>
	 * </tr>
	 * <tr>
	 * <th>Display percentsign</td>
	 * <td>true</td>
	 * </tr>
	 * </table>
	 * 
	 * @param percentStyle
	 */
	public void setPercentStyle(PercentStyle percentStyle) {
		bar.setPercentStyle(percentStyle);
	}

	/**
	 * Returns the {@link PercentStyle} of the percent text. Maybe returns the
	 * default value, check {@link #setPercentStyle(PercentStyle)} fo that.
	 * 
	 * @return the percent style of the moment.
	 */
	public PercentStyle getPercentStyle() {
		return bar.getPercentStyle();
	}

}
