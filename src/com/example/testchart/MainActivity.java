package com.example.testchart;

import java.util.ArrayList;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity implements OnChartGestureListener, OnChartValueSelectedListener{
	private LineChart mChart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mChart = (LineChart) findViewById(R.id.chart1);
		mChart.setOnChartGestureListener(this);
		mChart.setOnChartValueSelectedListener(this);
		mChart.setDrawGridBackground(false);
		// no description text
		mChart.setDescription("");
		mChart.setNoDataTextDescription("You need to provide data for the chart.");

		// enable value highlighting
		mChart.setHighlightEnabled(true);
		mChart.setExtraBottomOffset(0f);
		mChart.setExtraLeftOffset(0f);

		// enable touch gestures
		mChart.setTouchEnabled(true);

		// enable scaling and dragging
		mChart.setDragEnabled(true);
		mChart.setScaleEnabled(false);

		// if disabled, scaling can be done on x- and y-axis separately
		mChart.setPinchZoom(true);
		// set an alternative background color
		// mChart.setBackgroundColor(Color.GRAY);

		// create a custom MarkerView (extend MarkerView) and specify the layout
		// to use for it
		MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

		// set the marker to the chart
		mChart.setMarkerView(mv);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setXOffset(0f);

		YAxis leftAxis = mChart.getAxisLeft();
		leftAxis.removeAllLimitLines();  
		leftAxis.setAxisMaxValue(100f);
		leftAxis.setAxisMinValue(0f);
		leftAxis.setStartAtZero(true);
		leftAxis.setDrawGridLines(false);
		leftAxis.setYOffset(0f);
		leftAxis.setDrawAxisLine(true);
		leftAxis.setAxisLineColor(Color.WHITE);
		leftAxis.setTextColor(Color.WHITE);
		leftAxis.setDrawLimitLinesBehindData(true);

		mChart.getAxisRight().setEnabled(false);

		// add data
		setData(11, 100);

		// mChart.setVisibleXRange(20);
		// mChart.setVisibleYRange(20f, AxisDependency.LEFT);
		// mChart.centerViewTo(20, 50, AxisDependency.LEFT);

		mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
		//mChart.animateY(3000, Easing.EasingOption.EaseInCubic);
		//mChart.animateXY(3000, 3000);
		
		// mChart.invalidate();

		// get the legend (only possible after setting data)
		Legend l = mChart.getLegend();

		// modify the legend ...
		// l.setPosition(LegendPosition.LEFT_OF_CHART);
		l.setForm(LegendForm.LINE);
		l.setTextColor(Color.WHITE);
		l.setPosition(LegendPosition.BELOW_CHART_CENTER);

		// // dont forget to refresh the drawing
		// mChart.invalidate();
		for (DataSet<?> set : mChart.getData().getDataSets())
			set.setDrawValues(!set.isDrawValuesEnabled());

		mChart.invalidate();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
	}


	private void setData(int count, float range) {

		ArrayList<String> xVals = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			xVals.add((i+1) + "");
		}

		ArrayList<Entry> yVals = new ArrayList<Entry>();

		for (int i = 0; i < count; i++) {

			float mult = (range + 1);
			float val = (float) (Math.random() * mult) + 3; 
			yVals.add(new Entry(val, i));
		}

		// create a dataset and give it a type
		LineDataSet set1 = new LineDataSet(yVals, "全站每题作答正确率统计图");
		// set1.setFillAlpha(110);
		// set1.setFillColor(Color.RED);
		set1.disableDashedLine();
		set1.setValueTextSize(9f);
		set1.setHighLightColor(Color.WHITE);
		set1.setValueTextColor(Color.WHITE);
		// set the line to be drawn like this "- - - - - -"
		set1.setValueTextColor(Color.WHITE);
		//TODO
		set1.setColor(Color.WHITE);
		set1.setCircleColor(Color.WHITE);
		set1.setLineWidth(1f);
		set1.setCircleSize(3f);
		set1.setDrawCircleHole(false);
		// set1.setDrawFilled(true);
		// set1.setShader(new LinearGradient(0, 0, 0, mChart.getHeight(),
		// Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR));

		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		dataSets.add(set1); // add the datasets

		// create a data object with the datasets
		LineData data = new LineData(xVals, dataSets);

		// set data
		mChart.setData(data);
	}

	@Override
	public void onChartLongPressed(MotionEvent me) {
		Log.i("LongPress", "Chart longpressed.");
	}

	@Override
	public void onChartDoubleTapped(MotionEvent me) {
		Log.i("DoubleTap", "Chart double-tapped.");
	}

	@Override
	public void onChartSingleTapped(MotionEvent me) {
		Log.i("SingleTap", "Chart single-tapped.");
	}

	@Override
	public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX,
			float velocityY) {
		Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: "
				+ velocityY);
	}

	@Override
	public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
		Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
	}

	@Override
	public void onChartTranslate(MotionEvent me, float dX, float dY) {
		Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
	}

	@Override
	public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
		Log.i("Entry selected", e.toString());
		Log.i("", "low: " + mChart.getLowestVisibleXIndex() + ", high: "
				+ mChart.getHighestVisibleXIndex());
	}

	@Override
	public void onNothingSelected() {
		Log.i("Nothing selected", "Nothing selected.");
	}

}
