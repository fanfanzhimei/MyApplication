package com.zhi.www.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private List<Item> itemList = new ArrayList<Item>();
    private String mInputProgress = "";

    private TextView mTv0;
    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private TextView mTv4;
    private TextView mTv5;
    private TextView mTv6;
    private TextView mTv7;
    private TextView mTv8;
    private TextView mTv9;
    private TextView mTvClear;
    private TextView mTvDivided;
    private TextView mTvMultiply;
    private TextView mTvMinus;
    private TextView mTvAdd;
    private TextView mTvDot;
    private RelativeLayout mRlDelete;
    private RelativeLayout mRlCalcunator;

    private EditText mEtInput;
    private EditText mEtProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        initViews();
        initEvent();
        hideSystemSoft();
    }

    private void initViews() {
        mTv0 = (TextView) findViewById(R.id.tv_0);
        mTv1 = (TextView) findViewById(R.id.tv_1);
        mTv2 = (TextView) findViewById(R.id.tv_2);
        mTv3 = (TextView) findViewById(R.id.tv_3);
        mTv4 = (TextView) findViewById(R.id.tv_4);
        mTv5 = (TextView) findViewById(R.id.tv_5);
        mTv6 = (TextView) findViewById(R.id.tv_6);
        mTv7 = (TextView) findViewById(R.id.tv_7);
        mTv8 = (TextView) findViewById(R.id.tv_8);
        mTv9 = (TextView) findViewById(R.id.tv_9);
        mTvClear = (TextView) findViewById(R.id.tv_clear);
        mTvDivided = (TextView) findViewById(R.id.tv_divided);
        mTvMultiply = (TextView) findViewById(R.id.tv_multiply);
        mTvMinus = (TextView) findViewById(R.id.tv_minus);
        mTvAdd = (TextView) findViewById(R.id.tv_add);
        mTvDot = (TextView) findViewById(R.id.tv_dot);

        mRlDelete = (RelativeLayout) findViewById(R.id.rl_delete);
        mRlCalcunator = (RelativeLayout) findViewById(R.id.rl_calcunator);
        mEtInput = (EditText) findViewById(R.id.et_input);
        mEtProgress = (EditText) findViewById(R.id.et_progress);
    }

    private void initEvent() {
        mTv0.setOnClickListener(this);
        mTv1.setOnClickListener(this);
        mTv2.setOnClickListener(this);
        mTv3.setOnClickListener(this);
        mTv4.setOnClickListener(this);
        mTv5.setOnClickListener(this);
        mTv6.setOnClickListener(this);
        mTv7.setOnClickListener(this);
        mTv8.setOnClickListener(this);
        mTv9.setOnClickListener(this);
        mTvClear.setOnClickListener(this);
        mTvDivided.setOnClickListener(this);
        mTvMultiply.setOnClickListener(this);
        mTvMinus.setOnClickListener(this);
        mTvAdd.setOnClickListener(this);
        mTvDot.setOnClickListener(this);
        mRlDelete.setOnClickListener(this);
        mRlCalcunator.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_0:
                getPress("0");
                break;
            case R.id.tv_1:
                getPress("1");
                break;
            case R.id.tv_2:
                getPress("2");
                break;
            case R.id.tv_3:
                getPress("3");
                break;
            case R.id.tv_4:
                getPress("4");
                break;
            case R.id.tv_5:
                getPress("5");
                break;
            case R.id.tv_6:
                getPress("6");
                break;
            case R.id.tv_7:
                getPress("7");
                break;
            case R.id.tv_8:
                getPress("8");
                break;
            case R.id.tv_9:
                getPress("9");
                break;
            case R.id.tv_dot:
                Editable editable = mEtInput.getText();
                if (null == editable || (null != editable && editable.toString().trim().equals(""))) {
                    editable.insert(0, "0");
                    mEtInput.setText(editable);
                    mEtInput.setSelection(mEtInput.length());
                    mInputProgress = mInputProgress + mEtInput.getText().toString();
                    mEtProgress.setText(mInputProgress);
                }
                String input = mEtInput.getText().toString();
                if (!input.contains(".")) {
                    getPress(".");
                }
                break;
            case R.id.tv_add:
                getResult("+", Types.ADD);
                break;
            case R.id.tv_minus:
                getResult("-", Types.SUB);
                break;
            case R.id.tv_multiply:
                getResult("*", Types.MUL);
                break;
            case R.id.tv_divided:
                getResult("/", Types.DIV);
                break;
            case R.id.rl_calcunator:  //计算结果
                getResult("=", Types.RESULT);
                if (itemList.size() == 0) {
                    return;
                }
                double result = itemList.get(0).getValue();

                DecimalFormat df = new DecimalFormat("######0.00");
                mEtInput.setText(df.format(result));
                mEtInput.setSelection(mEtInput.getText().length());

                mEtProgress.setText(mEtInput.getText().toString());
                mInputProgress = mEtInput.getText().toString();
                itemList.clear();
                break;
            case R.id.tv_clear:
                mEtInput.setText("");
                mEtProgress.setText("");
                mInputProgress = "";
                itemList.clear();
                break;
            case R.id.rl_delete:
                getDelete();
                break;
        }
    }

    private void getResult(String symbol, int type) {
        addNumber();
        getPress(symbol);
        getCompute(symbol);
        if (null != itemList && itemList.size() > 0) {
            if (itemList.get(itemList.size() - 1).getType() != Types.NUMBER) {
                itemList.remove(itemList.size() - 1);
            }
            itemList.add(new Item(0, type));
            mEtInput.setText("");
        }
    }

    private void addNumber() {
        String input = onlySymbol(mEtInput);
        if(null == input || (null != input && "".equals(input.trim()))){
            return;
        }

        if (input.lastIndexOf("+") != input.length() &&
                input.lastIndexOf("-") != input.length() &&
                input.lastIndexOf("*") != input.length() &&
                input.lastIndexOf("/") != input.length() &&
                input.lastIndexOf("=") != input.length()) {
            itemList.add(new Item(Double.parseDouble(input), Types.NUMBER));
        }
    }

    private void getPress(String symbol) {
        checkLastSymbol(symbol);
        int index = mEtInput.getSelectionStart();
        Editable editable = mEtInput.getText();
        editable.insert(index, symbol);
        if (!("=".equals(symbol))) {
            mInputProgress = mInputProgress + symbol;
        }
        mEtProgress.setText(mInputProgress);
        onlySymbol(mEtProgress);
    }

    private void getCompute(String number) {
        if (null != itemList && itemList.size() >= 3) {
            double firstNum = itemList.get(0).getValue();
            double secondNum = itemList.get(2).getValue();
            int type = itemList.get(1).getType();
            itemList.clear();
            switch (type) {
                case Types.ADD:
                    itemList.add(new Item(firstNum + secondNum, Types.NUMBER));
                    mInputProgress = firstNum + "+" + secondNum;
                    if (!("=".equals(number))) {
                        mInputProgress = mInputProgress + number;
                    }
                    mEtProgress.setText(mInputProgress);
                    break;
                case Types.SUB:
                    itemList.add(new Item(firstNum - secondNum, Types.NUMBER));
                    mInputProgress = firstNum + "-" + secondNum;
                    if (!("=".equals(number))) {
                        mInputProgress = mInputProgress + number;
                    }
                    mEtProgress.setText(mInputProgress);
                    break;
                case Types.MUL:
                    itemList.add(new Item(firstNum * secondNum, Types.NUMBER));

                    mInputProgress = firstNum + "*" + secondNum;
                    if (!("=".equals(number))) {
                        mInputProgress = mInputProgress + number;
                    }
                    mEtProgress.setText(mInputProgress);
                    break;
                case Types.DIV:
                    if ((int) secondNum == 0) {
                        mEtInput.setText("");
                        itemList.clear();
                        Toast.makeText(this, "被除数不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String str = firstNum / secondNum + "";

                    mInputProgress = firstNum + "/" + secondNum;
                    if (!("=".equals(number))) {
                        mInputProgress = mInputProgress + number;
                    }
                    mEtProgress.setText(mInputProgress);
                    if (null == str || (null != str && str.trim().equals(""))) {
                        return;
                    }
                    int n = str.length();
                    while (str.charAt(n - 1) == '0') {
                        n--;
                    }
                    if (str.charAt(n - 1) == '.') {
                        str = str.substring(0, n - 1);
                    } else {
                        str = str.substring(0, n);
                    }
                    itemList.add(new Item(Double.parseDouble(str), Types.NUMBER));
                    break;
            }
        }
    }

    private String onlySymbol(EditText editText){
        Editable editable = editText.getText();
        if(null == editable || ("".equals(editable.toString().trim()))){
            return "";
        }
        String input = editable.toString();
        if ("+".equals(input) || "-".equals(input) || "*".equals(input) || "/".equals(input) || "=".equals(input)) {
            itemList.clear();
            mEtInput.setText("");
            mEtProgress.setText("");
            mInputProgress = "";
            input = null;
        }
        return input;
    }

    /* 插入的是符号 且 【最后一个字符】也是符号   (针对的是过程部分)
     * */
    private void checkLastSymbol(String symbol) {
        if ("+".equals(symbol) || "-".equals(symbol) || "*".equals(symbol) ||
                "/".equals(symbol) || "=".equals(symbol)) {
            Editable editable = mEtProgress.getText();
            if (null != editable && !("".equals(editable.toString().trim()))) {
                String input = editable.toString();
                String lastChar = input.substring(input.length() - 1, input.length());
                if ("+".equals(lastChar) || "-".equals(lastChar) || "*".equals(lastChar) ||
                        "/".equals(lastChar) || "=".equals(lastChar)) {
                    String progress = mEtProgress.getText().toString();
                    mEtProgress.setText(progress.substring(0, progress.length() - 1));
                    mInputProgress = mEtProgress.getText().toString();
                }
            }
        }
    }

    private void getDelete() {
        if (null == mEtInput.getText() || (null != mEtInput.getText() &&
                (((mEtInput.getText()).toString()).trim()).equals(""))) {
            mEtProgress.setText("");
            mInputProgress = "";
            itemList.clear();
            return;
        }
        int index = mEtInput.getSelectionStart();
        Editable editable = mEtInput.getText();
        if (index > 0) {
            editable.delete(index - 1, index);
        }
        mInputProgress = editable.toString();
        mEtProgress.setText(editable);
    }

    private void hideSystemSoft() {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            mEtInput.setInputType(InputType.TYPE_NULL);
        } else {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setSoftInputShownOnFocus;
                setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setSoftInputShownOnFocus.setAccessible(true);
                setSoftInputShownOnFocus.invoke(mEtInput, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}