/*=====================================================================
□ INFORMATION
  ○ Data : 08.03.2018
  ○ Mail : eun1310434@naver.com
  ○ Blog : https://blog.naver.com/eun1310434
  ○ Reference : Do it android app Programming

□ FUNCTION
  ○ 날짜와 시간을 한꺼번에 선택하는 복합위젯 클래스 정의
  ○ 날짜 선택시 전시 설정
  ○ 시간 선택시 전시 설정
  ○ 인터페이스를 활용한 리스너!!!!!
     - 기존의 "날짜변경 리스너 + 시간변경 리스너"
     - 복합으로 정의 하여
     - 날짜나 시간이 바뀔 때 호출되는 리스너 새로 정의

□ STUDY
  ○
=====================================================================*/
package com.eun1310434.datetimepicker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import java.util.Calendar;

public class DateTimePicker extends LinearLayout {

	//인터페이스를 활용한 날짜나 시간이 바뀔 때 호출되는 리스너 새로 정의
	//innerClass
	public interface OnDateTimeChangedListener {
		void onDateTimeChanged(DateTimePicker view, int year, int monthOfYear, int dayOfYear, int hourOfDay, int minute);
	}

	//리스너 객체
	private OnDateTimeChangedListener listener; //
	
	//날짜선택 위젯
	private DatePicker datePicker;

	//시간선택 위젯
	private TimePicker timePicker;

	//체크박스
	private CheckBox enableTimeCheckBox;
	private CheckBox enableDateCheckBox;

	public DateTimePicker(Context context) {
		super(context);
		init(context);
	}

	public DateTimePicker(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		// XML 레이아웃을 인플레이션함
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.datetimepicker, (ViewGroup) getRootView(), true);
		//inflater.inflate(R.layout.datetimepicker, this, true);
		
		// 시간 정보 참조
		Calendar calendar = Calendar.getInstance();
		final int curYear = calendar.get(Calendar.YEAR);
		final int curMonth = calendar.get(Calendar.MONTH);
		final int curDay = calendar.get(Calendar.DAY_OF_MONTH);
		final int curHour = calendar.get(Calendar.HOUR_OF_DAY);
		final int curMinute = calendar.get(Calendar.MINUTE);

		// 날짜선택 위젯 초기화 및 이벤트 처리
		datePicker = (DatePicker)findViewById(R.id.datePicker);
		datePicker.init(curYear, curMonth, curDay, new OnDateChangedListener() {
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				if(listener != null){
					// OnDateChangedListener를 통해 전달받은 객체를
					// 새로 정의한 리스너(통합관리)로 이벤트 전달
					// -> OnDateTimeChangedListener를 선언한 곳의 메소드는 동일하게 변경(메인에 영향)
					listener.onDateTimeChanged(
							DateTimePicker.this,
							year, monthOfYear, dayOfMonth,
							timePicker.getCurrentHour(), timePicker.getCurrentMinute());
					// getHour(), getMinute() 메소드는 API 23부터 지원함
				}
			}
		});

		// 날짜 체크박스 이벤트 처리
		enableDateCheckBox  = (CheckBox)findViewById(R.id.enableDateCheckBox);
		enableDateCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				datePicker.setEnabled(isChecked);
				datePicker.setVisibility((enableDateCheckBox).isChecked()?View.VISIBLE:View.GONE);
			}
		});

		// 날짜 선택 위젯 보이기 설정
		datePicker.setEnabled(enableDateCheckBox.isChecked());
		datePicker.setVisibility((enableDateCheckBox.isChecked()?View.VISIBLE:View.GONE));

		// 시간 선택 체크박스 이벤트 처리
		enableTimeCheckBox = (CheckBox)findViewById(R.id.enableTimeCheckBox);
		enableTimeCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				timePicker.setEnabled(isChecked);
				timePicker.setVisibility((enableTimeCheckBox).isChecked()?View.VISIBLE:View.GONE);
			}
		});

		// 시간선택 위젯 초기화 및 이벤트 처리
		timePicker = (TimePicker)findViewById(R.id.timePicker);
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				if(listener != null) {
					// onTimeChanged를 통해 전달받은 객체를
					// 새로 정의한 리스너(통합관리)로 이벤트 전달
					// -> OnDateTimeChangedListener를 선언한 곳의 메소드는 동일하게 변경 (메인에 영향)
					listener.onDateTimeChanged(
							DateTimePicker.this,
							datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
							hourOfDay, minute);
				}
			}
		});

		// 시간 선택 위젯 보이기 설정
		timePicker.setCurrentHour(curHour);
		timePicker.setCurrentMinute(curMinute);
		timePicker.setEnabled(enableTimeCheckBox.isChecked());
		timePicker.setVisibility((enableTimeCheckBox.isChecked()?View.VISIBLE:View.GONE));


	}
	
	public void setOnDateTimeChangedListener(OnDateTimeChangedListener dateTimeListener){
		// 해당 클래스를 활용하는 곳에서 setOnDateTimeChangedListener를 선언시
		// OnDateTimeChangedListener 생성
		this.listener = dateTimeListener;
	}
	
	public void updateDateTime(int year, int monthOfYear, int dayOfMonth, int currentHour, int currentMinute){
		datePicker.updateDate(year, monthOfYear, dayOfMonth);
		timePicker.setCurrentHour(currentHour);
		timePicker.setCurrentMinute(currentMinute);
	}
	
	public void updateDate(int year, int monthOfYear, int dayOfMonth){
		datePicker.updateDate(year, monthOfYear, dayOfMonth);
	}
	
	public void setIs24HourView(final boolean is24HourView){
		timePicker.setIs24HourView(is24HourView);
	}
	
	public int getYear() {
		return datePicker.getYear();
	}
	
	public int getMonth() {
		return datePicker.getMonth();
	}
	
	public int getDayOfMonth() {
		return datePicker.getDayOfMonth();
	}
	
	public int getCurrentHour() {
		return timePicker.getCurrentHour();
	}
	
	public int getCurrentMinute() {
		return timePicker.getCurrentMinute();
	}
	
	public boolean enableTime() {
		return enableTimeCheckBox.isChecked();
	}

	public boolean enableDate() {
		return enableDateCheckBox.isChecked();
	}
}
