package com.example.gamsung;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class CheckableLinearLayout extends LinearLayout implements Checkable {

    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    //현재 Checked 상태 리턴
    @Override
    public boolean isChecked() {
        CheckBox cb = (CheckBox) findViewById(R.id.checkbox) ;

        return cb.isChecked() ;
    }

//    현재 Checked 상태를 바꿈. (UI에 반영)
    @Override
    public void toggle() {
        CheckBox cb = (CheckBox) findViewById(R.id.checkbox) ;

        setChecked(cb.isChecked() ? false : true) ;
    }

//    Checked 상태를 checked 변수대로 설정.
    @Override
    public void setChecked(boolean checked) {
        CheckBox cb = (CheckBox) findViewById(R.id.checkbox) ;

        if (cb.isChecked() != checked) {
            cb.setChecked(checked) ;
        }

    }
}