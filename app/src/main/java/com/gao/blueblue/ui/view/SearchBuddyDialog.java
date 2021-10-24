package com.gao.blueblue.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.gao.blueblue.R;

/**
 * @program: Blueblue
 * @description: 搜索用户对话框
 * @author: wuzewen
 * @create: 2021-10-23 21:11
 **/
public class SearchBuddyDialog extends Dialog implements View.OnClickListener {

    public SearchBuddyDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_search_buddy);
        bindView();
    }


    public void show(View.OnClickListener cancel, View.OnClickListener ok) {
        btnTrue.setOnClickListener(ok);
        btnFalse.setOnClickListener(cancel);
        show();
    }

    public String inputString;
    private ImageView ivClean;
    private EditText input;
    private Button btnFalse;//取消按钮
    private Button btnTrue;//确定按钮

    private void bindView() {
        input = findViewById(R.id.et_dialog_input);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputString = s.toString().trim();
                if (inputString.length() > 0) {
                    ivClean.setVisibility(View.VISIBLE);
                } else {
                    ivClean.setVisibility(View.GONE);
                }
            }
        });


        ivClean = findViewById(R.id.iv_dialog_input_clean);
        ivClean.setOnClickListener(this);

        btnFalse = findViewById(R.id.bt_double_dialog_cancel);
        btnTrue = findViewById(R.id.bt_double_dialog_intput_sure);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_dialog_input_clean:
                input.setText("");
                break;
        }
    }
}

