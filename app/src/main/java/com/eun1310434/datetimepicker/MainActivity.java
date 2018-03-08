/*=====================================================================
□ Infomation
  ○ Data : 08.03.2018
  ○ Mail : eun1310434@naver.com
  ○ Blog : https://blog.naver.com/eun1310434
  ○ Reference : Do it android app Programming

□ Function
  ○ 날짜와 시간을 한꺼번에 선택하는 복합위젯
  ○ 설정에 따라 날짜/시간 선택 가능

□ Study
  ○
=====================================================================*/
package com.eun1310434.datetimepicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
    TextView textView;
    DateTimePicker picker;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView); //현재시간 보여줌

        picker = (DateTimePicker)findViewById(R.id.picker);
        calendar = Calendar.getInstance();//오늘날짜 선택

        // 이벤트 처리
        picker.setOnDateTimeChangedListener(new DateTimePicker.OnDateTimeChangedListener() {
            //DateTimePicker.OnDateTimeChangedListener()를
            // MainActivity에서 정의하여
            // 리스너가 작동시 결과값을 MainActivity에서 새롭게 사용할 수 있다.
            // 1) Date를 재 설정시 리스너를 붙러와 변경 -> listener.onDateTimeChanged -> Main에서 결과값을 받아 재설정
            // 2) Time을 재 설정시 리스너를 불러와서 변경 -> listener.onDateTimeChanged -> Main에서 결과값을 받아 재설정

            public void onDateTimeChanged(
                    DateTimePicker view,
                    int year, int monthOfYear, int dayOfYear,
                    int hourOfDay, int minute) {

                //날짜와 시간이 변동시 각각의 값이
                // 파라메터(view, year, monthOfYear, dayOfYear,hourOfDay,minute )를 통하여 정의 되며,
                //변경된것을 메인의 화면에 영향을 주기 위함
                calendar.set(year, monthOfYear, dayOfYear, hourOfDay, minute);

                // 바뀐 시간 텍스트뷰에 표시
                textView.setText(dateFormat.format(calendar.getTime()));
            }
        });

        // 현재 시간 텍스트뷰에 표시
        calendar.set(
                picker.getYear(),
                picker.getMonth(),
                picker.getDayOfMonth(),
                picker.getCurrentHour(),
                picker.getCurrentMinute());

        textView.setText(dateFormat.format(calendar.getTime()));

    }

}
