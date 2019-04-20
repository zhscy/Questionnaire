package edu.scausxxg.questionnaire;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String sex;
    String satisfaction;

    Spinner collegeSpinner;
    EditText nameEdit;
    EditText birthdayEdit;
    EditText phoneNumberEdit;
    EditText suggestionEdit;
    /**
     * 录入信息展示
     */
    TextView infoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.nameEdit = findViewById(R.id.name);
        this.collegeSpinner = findViewById(R.id.college);
        this.phoneNumberEdit = findViewById(R.id.phoneNumber);
        this.suggestionEdit = findViewById(R.id.suggestion);
        this.infoView = findViewById(R.id.showInfo);


        // 处理出生日期
        birthdayEdit = findViewById(R.id.birthday);
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(birthdayEdit, myCalendar);
            }
        };
        // 显示日期
        birthdayEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        // 获取性别
        RadioGroup sexGroup = findViewById(R.id.sex);
        sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton sexSelect = findViewById(checkedId);
                sex = sexSelect.getText().toString();
            }
        });

        // 获取满意度
        RadioGroup satisfyGroup = findViewById(R.id.satisfaction);
        satisfyGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton satisfySelect = findViewById(checkedId);
                satisfaction = satisfySelect.getText().toString();
            }
        });

    }

    /**
     * 创建菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 处理菜单选项
     *
     * @param menuItem
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.isCheckable()) {
            menuItem.setChecked(true);
        }
        switch (menuItem.getItemId()) {
            case R.id.menu1:
                submit();
                break;
            case R.id.menu2:
                finish();
                break;
        }
        return true;
    }


    /**
     * 提交信息
     */
    private void submit() {
        // 获取信息
        String name = this.nameEdit.getText().toString();
        String birthday = this.birthdayEdit.getText().toString();
        String phoneNumber = this.phoneNumberEdit.getText().toString();
        String college = this.collegeSpinner.getSelectedItem().toString();
        String suggestion = this.suggestionEdit.getText().toString();
        if (this.judge(name, birthday, college, phoneNumber, suggestion)) {
            infoView.setText("你的信息：" + "姓名" + name + "，出生日期" + birthday + "，性别" + sex
                    + "，所在学院" + college + "，手机号为" + phoneNumber + ",对饭堂是否满意为" + satisfaction + ",建议为" + suggestion);
        }

    }

    /**
     * 日期显示设置
     *
     * @param editText
     * @param myCalendar
     */
    private void updateLabel(EditText editText, Calendar myCalendar) {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendar.getTime()));
    }

    /**
     * 判断信息录全与否
     *
     * @param name
     * @param birthday
     * @param college
     * @param phoneNumber
     * @param suggestion
     */
    private boolean judge(String name, String birthday, String college, String phoneNumber, String suggestion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle("警告")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        if (name.equals("") || name == null) {
            builder.setMessage("姓名为空！");
            builder.create().show();
            return false;
        } else if (birthday.equals("") || birthday == null) {
            builder.setMessage("出生日期为空！");
            builder.create().show();
            return false;
        } else if (sex == null) {
            builder.setMessage("性别为空！");
            builder.create().show();
            return false;
        } else if (college.equals("") || college == null) {
            builder.setMessage("院系为空！");
            builder.create().show();
            return false;
        } else if (phoneNumber.equals("") || phoneNumber == null) {
            builder.setMessage("手机号为空！");
            builder.create().show();
            return false;
        } else if (satisfaction == null) {
            builder.setMessage("是否满意为空！");
            builder.create().show();
            return false;
        } else if (suggestion.equals("") || suggestion == null) {
            builder.setMessage("建议为空！");
            builder.create().show();
            return false;
        }
        return true;
    }
}
