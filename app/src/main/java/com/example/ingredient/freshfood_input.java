package com.example.ingredient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ingredient.dto.Todo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class freshfood_input extends AppCompatActivity {
    // 스피너 관련 변수
    ArrayAdapter<CharSequence> adapter = null;
    ArrayAdapter<CharSequence> adapter2 = null;
    ArrayAdapter<CharSequence> adapter3 = null;
    ArrayAdapter<CharSequence> adapter4 = null;
    ArrayAdapter<CharSequence> adapter5 = null;
    ArrayAdapter<CharSequence> adapterPlace = null;



    Spinner spinner = null;
    Spinner spinner2 = null;
    Spinner spinner3 = null;
    Spinner spinner4 = null;
    Spinner spinner5 = null;
    Spinner freshSpinner = null;

    // 스피너 관련 변수 끝!


    private RadioGroup mRgLine1;
    private RadioGroup mRgLine2;
    String radio;
    Todo todo = null;

    // 알림 관련 변수
    private static final String CHANNEL_ID = "food_alarm_id";
    private static final int NOTIFICATION_ID = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;

    // 알림 매니저 및 알림 빌더 변수
    private NotificationManagerCompat notificationManager;
    private NotificationCompat.Builder builder;


//    private TextView defaultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freshfood_input);



        adapter = ArrayAdapter.createFromResource(this, R.array.place, android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.freshspinner);
        spinner.setAdapter(adapter);

        adapter2 = ArrayAdapter.createFromResource(this, R.array.fruit_type, android.R.layout.simple_spinner_dropdown_item);
        spinner2 = findViewById(R.id.fruitSpinner);
        spinner2.setAdapter(adapter2);

        adapter3 = ArrayAdapter.createFromResource(this, R.array.vegetable_type, android.R.layout.simple_spinner_dropdown_item);
        spinner3 = findViewById(R.id.vegetableSpinner);
        spinner3.setAdapter(adapter3);

        adapter4 = ArrayAdapter.createFromResource(this, R.array.meat_type, android.R.layout.simple_spinner_dropdown_item);
        spinner4 = findViewById(R.id.meatSpinner);
        spinner4.setAdapter(adapter4);

        adapter5 = ArrayAdapter.createFromResource(this, R.array.seafood_type, android.R.layout.simple_spinner_dropdown_item);
        spinner5 = findViewById(R.id.seafoodSpinner);
        spinner5.setAdapter(adapter5);

        adapterPlace = ArrayAdapter.createFromResource(this, R.array.place, android.R.layout.simple_spinner_dropdown_item);
        freshSpinner = findViewById(R.id.freshspinner);
        freshSpinner.setAdapter(adapterPlace);

        // 날짜 입력되는 부분
        EditText et_Date = (EditText) findViewById(R.id.fCreateDate);
        EditText expiry_Date = (EditText) findViewById(R.id.fAlarm);
        et_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(freshfood_input.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        expiry_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(freshfood_input.this, myDatePicker2, myCalendar2.get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH), myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // 저장버튼
        Button fButton = findViewById(R.id.fbutton);
        EditText et_name = (EditText)findViewById(R.id.etcName);
        EditText et_cnt = (EditText)findViewById(R.id.fCnt);
        EditText et_memo = (EditText)findViewById(R.id.fMemo);

        Intent type = getIntent();
        String message = getIntent().getStringExtra("type");
        if(message.equals("ADD")){
            fButton.setText("추가하기");
        }
        else{
            todo = (Todo)getIntent().getSerializableExtra("item");
            et_cnt.setText(todo.getCount());
            et_memo.setText(todo.getComment());
            fButton.setText("수정하기");
        }

        fButton.setOnClickListener(new View.OnClickListener() {
            // 저장할때 사용되는 메소드(sql을 이용한 것)을 호출하여 사용
            String item;
            String count;
            String place;
            Long time;
            String memo;
            @Override
            public void onClick(View view) {
                // 저장하시겠습니까? 라는 다이얼로그가 들어가는 것이 좋아보이지만...
                // 일단은 메인 화면으로 돌아가도록 코드 진행
                boolean state = false;
                if(message.equals("ADD")){
                    if(radio.equals("1")){
                        item = spinner2.getSelectedItem().toString();
                    }
                    else if(radio.equals("2")){
                        item = spinner3.getSelectedItem().toString();
                    }
                    else if(radio.equals("3")){
                        item = spinner4.getSelectedItem().toString();
                    }
                    else if(radio.equals("4")){
                        item = spinner5.getSelectedItem().toString();
                    }
                    else if(radio.equals("5")){
                        item = et_name.getText().toString();
                    }
                    count = et_cnt.getText().toString();
                    place = freshSpinner.getSelectedItem().toString();
                    time = (Long) (myCalendar2.getTimeInMillis()-myCalendar.getTimeInMillis())/(24*60*60*1000);
                    memo = et_memo.getText().toString();
                    Todo todo = new Todo(0,state,radio,item,count,place,time,memo);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class).putExtra("todo",todo).putExtra("flag",0);
                    setResult(RESULT_OK,intent);
                    finish();
                    // startActivity(intent);
                }
                else{
                    if(radio.equals("1")){
                        item = spinner2.getSelectedItem().toString();
                    }
                    else if(radio.equals("2")){
                        item = spinner3.getSelectedItem().toString();
                    }
                    else if(radio.equals("3")){
                        item = spinner4.getSelectedItem().toString();
                    }
                    else if(radio.equals("4")){
                        item = spinner5.getSelectedItem().toString();
                    }
                    else if(radio.equals("5")){
                        item = et_name.getText().toString();
                    }
                    count = et_cnt.getText().toString();
                    place = freshSpinner.getSelectedItem().toString();
                    time = (Long) (myCalendar2.getTimeInMillis()-myCalendar.getTimeInMillis())/(24*60*60*1000);
                    memo = et_memo.getText().toString();
                    Todo todo_edit = new Todo(0,state,radio,item,count,place,time,memo);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class).putExtra("todo",todo_edit).putExtra("flag",1);
                    setResult(RESULT_OK,intent);
                    finish();
                    // startActivity(intent);
                }
            }
        });

        // 2중 라디오 버튼 중첩 선택 안되게 하는 부분
        mRgLine1 = (RadioGroup) findViewById(R.id.rg_line1);
//        mRgLine1.clearCheck();
//        mRgLine1.setOnCheckedChangeListener(listener1);

        mRgLine2 = (RadioGroup) findViewById(R.id.rg_line2);
//        mRgLine2.clearCheck();
//        mRgLine2.setOnCheckedChangeListener(listener2);

        final TextView defaulttext = (TextView) findViewById(R.id.defalutText);
        final Spinner fruitSpinner = (Spinner) findViewById(R.id.fruitSpinner);
        final Spinner vegetableSpinner = (Spinner) findViewById(R.id.vegetableSpinner);
        final Spinner meatSpinner = (Spinner) findViewById(R.id.meatSpinner);
        final Spinner seafoodSpinner = (Spinner) findViewById(R.id.seafoodSpinner);
        final EditText etcName = (EditText) findViewById(R.id.etcName);


        mRgLine1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i) {
                    case R.id.fruit:
                        defaulttext.setVisibility(View.INVISIBLE);
                        fruitSpinner.setVisibility(View.VISIBLE);
                        vegetableSpinner.setVisibility(View.INVISIBLE);
                        meatSpinner.setVisibility(View.INVISIBLE);
                        seafoodSpinner.setVisibility(View.INVISIBLE);
                        et_name.setVisibility(View.INVISIBLE);
                        radio = "1";
                        break;

                    case R.id.vegetable:
                        defaulttext.setVisibility(View.INVISIBLE);
                        fruitSpinner.setVisibility(View.INVISIBLE);
                        vegetableSpinner.setVisibility(View.VISIBLE);
                        meatSpinner.setVisibility(View.INVISIBLE);
                        seafoodSpinner.setVisibility(View.INVISIBLE);
                        et_name.setVisibility(View.INVISIBLE);
                        radio = "2";
                        break;

                    case R.id.meat:
                        defaulttext.setVisibility(View.INVISIBLE);
                        fruitSpinner.setVisibility(View.INVISIBLE);
                        vegetableSpinner.setVisibility(View.INVISIBLE);
                        meatSpinner.setVisibility(View.VISIBLE);
                        seafoodSpinner.setVisibility(View.INVISIBLE);
                        et_name.setVisibility(View.INVISIBLE);
                        radio = "3";
                        break;
                }

                if (i != -1) { // 체크표시 해제
                    mRgLine2.clearCheck();
                }
            }
        });

        mRgLine2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i) {
                    case R.id.seafood:
                        defaulttext.setVisibility(View.INVISIBLE);
                        fruitSpinner.setVisibility(View.INVISIBLE);
                        vegetableSpinner.setVisibility(View.INVISIBLE);
                        meatSpinner.setVisibility(View.INVISIBLE);
                        seafoodSpinner.setVisibility(View.VISIBLE);
                        et_name.setVisibility(View.INVISIBLE);
                        radio = "4";
                        break;

                    case R.id.etc:
                        defaulttext.setVisibility(View.INVISIBLE);
                        fruitSpinner.setVisibility(View.INVISIBLE);
                        vegetableSpinner.setVisibility(View.INVISIBLE);
                        meatSpinner.setVisibility(View.INVISIBLE);
                        seafoodSpinner.setVisibility(View.INVISIBLE);
                        et_name.setVisibility(View.VISIBLE);
                        radio = "5";
                        break;
                }

                if (i != -1) { // 체크 표시 해제
                    mRgLine1.clearCheck();
                }
            }
        });

        // 스피너 값 가져오기(품목, 보관장소) => DB에서 날짜 가져와서 DatePicker에 세팅

//        final Spinner FreshSpinner = (Spinner) findViewById(R.id.freshspinner);
//        String place;
//        freshSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                place = freshSpinner.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        notificationManager = NotificationManagerCompat.from(this);
    }

    // 좌측 상단 되돌아가기
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:{
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // 날짜 선택 및 화면에 보여주기
    Calendar myCalendar = Calendar.getInstance();
    Calendar myCalendar2 = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    DatePickerDialog.OnDateSetListener myDatePicker2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar2.set(Calendar.YEAR, year);
            myCalendar2.set(Calendar.MONTH, month);
            myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel2();
            setAlarm(year, month, dayOfMonth);
        }
    };

    public void setAlarm(int year, int month, int dayOfMonth) {
        // 1일 전 날짜 계산
        Calendar alarmDate = Calendar.getInstance();
        alarmDate.set(year, month, dayOfMonth);
        alarmDate.add(Calendar.DAY_OF_MONTH, -1);

        // 현재 시간
        Calendar currentTime = Calendar.getInstance();

        // 현재 시간과 1일 전 날짜 비교
        if (currentTime.after(alarmDate) || currentTime.equals(alarmDate)) {
            // 알림 설정을 하기 전에 권한 요청을 추가
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_BOOT_COMPLETED) != PackageManager.PERMISSION_GRANTED) {
                // 권한이 없으면 요청
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED}, PERMISSION_REQUEST_CODE);
                return;
            }
            // 알림 설정
            createNotification(alarmDate.getTimeInMillis());
        } else {
        }
    }

    private void createNotification(long triggerTime) {
        // 알림 채널 생성
        String channelId = CHANNEL_ID;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel existingChannel = notificationManager.getNotificationChannel(channelId);
            if (existingChannel == null) {
                CharSequence channelName = "food alarm";
                String channelDescription = "Notification of impending expiration date";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
                channel.setDescription(channelDescription);

                // 알림 채널 등록
                notificationManager.createNotificationChannel(channel);
            } else {
                // 알림 채널이 이미 있는 경우
            }
        }

        // 알림 매니저 및 알림 빌더 초기화
        notificationManager = NotificationManagerCompat.from(this);
        builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.notification_icon_fridge)
                .setContentTitle("유알림")
                .setContentText("소비기한이 임박한 식재료가 있습니다!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // 알림 표시
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED) != PackageManager.PERMISSION_GRANTED) {
            // TODO: 권한 요청 또는 처리 로직 추가
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 허용된 경우 알림을 다시 생성하고 표시할 수 있습니다.
                setAlarm(myCalendar2.get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH), myCalendar2.get(Calendar.DAY_OF_MONTH));
            } else {
            }
        }
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2021/07/26
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_date = (EditText) findViewById(R.id.fCreateDate);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat2 = "yyyy/MM/dd";    // 출력형식   2021/07/26
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat2, Locale.KOREA);

        EditText alarm_Date = (EditText) findViewById(R.id.fAlarm);
        alarm_Date.setText(sdf.format(myCalendar2.getTime()));
    }
}