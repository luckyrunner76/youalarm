package com.example.ingredient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.Manifest;

import com.example.ingredient.dto.Todo;

public class processedfood_input extends AppCompatActivity {
    ArrayAdapter<CharSequence> adapter = null;
    Spinner spinner = null;
    Todo todo = null;

    // 알림 관련 변수
    private static final String CHANNEL_ID = "food_alarm_id";
    private static final int NOTIFICATION_ID = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;

    // 알림 매니저 및 알림 빌더 변수
    private NotificationManagerCompat notificationManager;
    private NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processedfood_input);

        Button pButton = findViewById(R.id.pbutton);
        EditText et_title = (EditText) findViewById(R.id.pName);
        EditText et_cnt = (EditText) findViewById(R.id.pCnt);
        EditText et_memo = (EditText) findViewById(R.id.pMemo);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setTitle("뒤로가기");
//        }

        adapter = ArrayAdapter.createFromResource(this, R.array.place, android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.processedspinner);
        spinner.setAdapter(adapter);

        EditText et_Date = (EditText) findViewById(R.id.pCreateDate);
        EditText expiry_Date = (EditText) findViewById(R.id.pAlarm);

        Intent type = getIntent();
        String message = getIntent().getStringExtra("type");
        if(message.equals("ADD")){
            pButton.setText("추가하기");
        }else{
            todo = (Todo) getIntent().getSerializableExtra("item");
            et_title.setText(todo.getTitle());
            et_cnt.setText(todo.getCount());
            et_memo.setText(todo.getComment());
            pButton.setText("수정하기");
        }

        // 등록일
        et_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(processedfood_input.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // 소비기한
        expiry_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 기존 코드
                new DatePickerDialog(processedfood_input.this, myDatePicker2, myCalendar2.get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH), myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // 저장버튼
        pButton.setOnClickListener(new View.OnClickListener() {
            // 저장할때 사용되는 메소드(sql을 이용한 것)을 호출하여 사용
            //저장1W
            @Override
            public void onClick(View view) {
                // 저장하시겠습니까? 라는 다이얼로그가 들어가는 것이 좋아보이지만...
                // 일단은 메인 화면으로 돌아가도록 코드 진행
                //추가
                if(message.equals("ADD")) {
                    boolean state = true;
                    String title = et_title.getText().toString();
                    String cnt = et_cnt.getText().toString();
                    Long time = (Long) (myCalendar2.getTimeInMillis() - myCalendar.getTimeInMillis()) / (24 * 60 * 60 * 1000);
                    String where = spinner.getSelectedItem().toString();
                    String comment = et_memo.getText().toString();
                    Todo todo = new Todo(0, state, "no", title, cnt, where, time, comment);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class).putExtra("todo", todo).putExtra("flag", 0);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                //수정
                else{
                    boolean state = true;
                    String title = et_title.getText().toString();
                    String cnt = et_cnt.getText().toString();
                    Long time = (Long) (myCalendar2.getTimeInMillis() - myCalendar.getTimeInMillis()) / (24 * 60 * 60 * 1000);
                    String where = spinner.getSelectedItem().toString();
                    String comment = et_memo.getText().toString();
                    Todo todo_edit = new Todo(todo.getId(), state, "no", title, cnt, where, time, comment);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class).putExtra("todo", todo_edit).putExtra("flag", 1);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                //startActivity(intent);
            }
        });

        // 알림 매니저 초기화
        notificationManager = NotificationManagerCompat.from(this);
    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home: {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 기존의 메인 화면을 최상위로 설정
//                startActivity(intent);
//                return true;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }

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
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED) != PackageManager.PERMISSION_GRANTED) {
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
//        Toast.makeText(this, "알림 표시 코드 테스트 createNotification2", Toast.LENGTH_SHORT).show();
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

        EditText et_date = (EditText) findViewById(R.id.pCreateDate);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat2 = "yyyy/MM/dd";    // 출력형식   2021/07/26
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat2, Locale.KOREA);

        EditText alarm_Date = (EditText) findViewById(R.id.pAlarm);
        alarm_Date.setText(sdf.format(myCalendar2.getTime()));
    }
}