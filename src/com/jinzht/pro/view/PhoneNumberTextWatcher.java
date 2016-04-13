package com.jinzht.pro.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Administrator on 2015/11/11.
 */
public class PhoneNumberTextWatcher implements TextWatcher {
    private EditText edTxt;
    private boolean isDelete;

    public PhoneNumberTextWatcher(EditText edTxtPhone) {
        this.edTxt = edTxtPhone;
        edTxt.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    isDelete = true;
                }
                return false;
            }
        });
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    public void afterTextChanged(Editable s) {
        try {
            if (isDelete) {
                isDelete = false;
                return;
            }
            String val = s.toString();
            String a = "";// 0-3
            String b = "";//
            String c = "";//
            if (val != null && val.length() > 0) {
                val = val.replace(" ", "");
                if (val.length() >= 3) {
                    a = val.substring(0, 3);
                } else if (val.length() < 3) {
                    a = val.substring(0, val.length());
                }
                if (val.length() >= 7) {
                    b = val.substring(3, 7);
                    c = val.substring(7, val.length());
                } else if (val.length() > 3 && val.length() < 7) {
                    b = val.substring(3, val.length());
                }
                StringBuffer stringBuffer = new StringBuffer();
                if (a != null && a.length() > 0) {
                    stringBuffer.append(a);
                    if (a.length() == 3) {
                        stringBuffer.append(" ");
                    }
                }
                if (b != null && b.length() > 0) {
                    stringBuffer.append(b);
                    if (b.length() == 4) {
                        stringBuffer.append(" ");
                    }
                }
                if (c != null && c.length() > 0) {
                    stringBuffer.append(c);
                }
                edTxt.removeTextChangedListener(this);
                edTxt.setText(stringBuffer.toString());
                edTxt.setSelection(edTxt.getText().toString().length());
                edTxt.addTextChangedListener(this);
            } else {
                edTxt.removeTextChangedListener(this);
                edTxt.setText("");
                edTxt.addTextChangedListener(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
