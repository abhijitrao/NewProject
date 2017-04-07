package com.gennext.newproject.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.Button;
import android.widget.EditText;

import com.gennext.newproject.R;

import java.util.regex.Pattern;

/**
 * Created by Abhijit on 15-Oct-16.
 */

public class FieldValidation {
    public static final int STRING = 1, NAME = 2, EMAIL = 3, MOBILE = 4, PIN_CODE = 5, BUTTON = 6, IFSC = 7, PAN_NUMBER = 8, ALP_NUM = 9;
//    FieldValidation(EditText etReason) {
//        this.etReason = etReason;
//    }

    public static boolean validateAllFields(boolean[] check) {
        for (int i = 0; i < check.length; i++) {
            if (!check[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean validate(Context context, Button button, int type, String defaultValue) {
        return validate(context, null,button, type, false,defaultValue);
    }
    public static boolean validate(Context context, EditText edittext, int type, boolean requiredREGEX) {
        return validate(context, edittext,null, type, requiredREGEX,null);
    }
    public static boolean validate(Context context, EditText edittext, Button button, int type, boolean requiredREGEX,String defaultValue) {
        switch (type) {
            case STRING:
                if (!isString(context, edittext, EMPTY, requiredREGEX)) {
                    return false;
                }
                break;
            case NAME:
                if (!isName(context, edittext, requiredREGEX)) {
                    return false;
                }
                break;
            case EMAIL:
                if (!isEmail(context, edittext, requiredREGEX)) {
                    return false;
                }
                break;
            case MOBILE:
                if (!isPhoneNumber(context, edittext, requiredREGEX)) {
                    return false;
                }
                break;
            case PIN_CODE:
                if (!isPinCode(context, edittext, requiredREGEX)) {
                    return false;
                }
                break;
            case BUTTON:
                if (!isButton(context, button, defaultValue)) {
                    return false;
                }
                break;
            case IFSC:
                if (!isIFSCCode(context, edittext, requiredREGEX)) {
                    return false;
                }
                break;
            case PAN_NUMBER:
                if (!isPANNumber(context, edittext, requiredREGEX)) {
                    return false;
                }
                break;
            case ALP_NUM:
                if (!isAlpNum(context, edittext, requiredREGEX)) {
                    return false;
                }
                break;
        }
        if(edittext!=null) {
            edittext.setCompoundDrawables(null, null, null, null);
            edittext.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    context.getResources().getDrawable(R.drawable.ic_success), null);
        }
        return true;
    }

    public static boolean validateOnly(Context context, EditText edittext, int type, boolean requiredREGEX) {
        return validateOnly(context, edittext,null, type, requiredREGEX,null);
    }

    public static boolean validateOnly(Context context, EditText edittext, Button button, int type, boolean requiredREGEX,String defaultValue) {
        switch (type) {
            case STRING:
                if (!isString(context, edittext, EMPTY, requiredREGEX)) {
                    edittext.setCompoundDrawables(null, null, null, null);
                    return false;
                }
                break;
            case NAME:
                if (!isName(context, edittext, requiredREGEX)) {
                    edittext.setCompoundDrawables(null, null, null, null);
                    return false;
                }
                break;
            case EMAIL:
                if (!isEmail(context, edittext, requiredREGEX)) {
                    edittext.setCompoundDrawables(null, null, null, null);
                    return false;
                }
                break;
            case MOBILE:
                if (!isPhoneNumber(context, edittext, requiredREGEX)) {
                    edittext.setCompoundDrawables(null, null, null, null);
                    return false;
                }
                break;
            case ALP_NUM:
                if (!isAlpNum(context, edittext, requiredREGEX)) {
                    edittext.setCompoundDrawables(null, null, null, null);
                    return false;
                }
                break;
        }
        if(edittext!=null) {
            edittext.setCompoundDrawables(null, null, null, null);
            edittext.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    context.getResources().getDrawable(R.drawable.ic_success), null);
        }
        return true;
    }


    private static boolean isAlpNum(Context context, EditText edittext, boolean requiredREGEX) {
        return isValid(context, edittext, ALPHA_NUM_REGEX, ALP_NUM_MSG, requiredREGEX);
    }

    private static boolean isPANNumber(Context context, EditText edittext, boolean requiredREGEX) {
        return isValid(context, edittext, ALPHA_NUM_REGEX, PAN_MSG, requiredREGEX);
    }

    private static boolean isIFSCCode(Context context, EditText edittext, boolean required) {
        return isValid(context, edittext, IFSC_CODE_REGEX, IFSC_MSG, required);
    }

    private static boolean isButton(Context context, Button button, String defaultValue) {
        String validText=button.getText().toString();
        if(TextUtils.isEmpty(validText) && validText.equals(defaultValue)){
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Context context, EditText editText, String invalidMsg, boolean required) {
        return isValid(context, editText, NAME_REGEX, invalidMsg, required);
    }

    // call this method when you need to check email validation
    public static boolean isEmail(Context context, EditText editText, boolean required) {
        return isValid(context, editText, EMAIL_REGEX, EMAIL_MSG, required);
    }


    // call this method when you need to check phone number validation
    public static boolean isPhoneNumber(Context context, EditText editText, boolean required) {
        return isValid(context, editText, PHONE_REGEX, PHONE_MSG, required);
    }

    public static boolean isPinCode(Context context, EditText editText, boolean required) {
        return isValid(context, editText, PIN_CODE_REGEX, PIN_MSG, required);
    }


    public static boolean isName(Context context, EditText editText, boolean required) {
        return isValid(context, editText, NAME_REGEX, NAME_MSG, required);
    }

    public static boolean isString(Context context, EditText editText, String invalidMsg, boolean required) {
        return hasText(context, editText);
    }

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "^[7-9][0-9]{9}$";

    private static final String PIN_CODE_REGEX = "^[0-9]{6}$";
    private static final String IFSC_CODE_REGEX = "^[A-Za-z]{4}[0-9]{7}$";

    private static final String PHONE_REGEX_2 = "^[0-9][0-9]{6,10}$";

    private static final String AGE_REGEX = "^[7-9][0-9]{9}$";
    private static final String NAME_REGEX = "[a-zA-Z ]{1,100}";
    private static final String ALPHA_NUM_REGEX = "^[a-zA-Z0-9]+$";
    //dd-MM-yyyy,dd/MM/yyyy
    private static final String DATE_REGEX = "^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$";
    //MM-dd-yyyy,MM/dd/yyyy
    private static final String DATE1_REGEX = "^(((0[1-9]|[12][0-9]|30)[-/]?(0[13-9]|1[012])|31[-/]?(0[13578]|1[02])|(0[1-9]|1[0-9]|2[0-8])[-/]?02)[-/]?[0-9]{4}|29[-/]?02[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$";

    //Support exp 2300,23:00,4 am ,4am ,4pm ,4 pm,04:30pm ,04:30 pm ,4:30pm ,4:30 pm,04.30pm,04.30 pm,4.30pm,4.30 pm ,23:59 ,0000 ,00:00
    private static final String TIME_REGEX = "";

    // Error Messages
    private static final String REQUIRED_MSG = "required";
    private static final String EMAIL_MSG = "invalid email";
    private static final String PHONE_MSG = "invalid phone";
    private static final String VEHICLE_MSG = "Please enter your car number without any spaces";
    private static final String PIN_MSG = "Please enter 6 digit pin code";
    private static final String IFSC_MSG = "Please enter valid IFSC code";
    private static final String PAN_MSG = "Please enter valid PAN number";
    private static final String ALP_NUM_MSG = "Please enter alpha numeric value";
    private static final String NAME_MSG = "invalid Name";
    private static final String STATE_MSG = "invalid State";
    private static final String CITY_MSG = "invalid City";
    private static final String AGE_MSG = "invalid Age";
    private static final String DISCOUNT_MSG = "invalid Discount";
    private static final String EMPTY = "Empty";

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(Context context, EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if (!hasText(context, editText)) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            Drawable drawable = context.getResources().getDrawable(R.drawable.ic_fail);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            editText.setError(errMsg, drawable);
            return false;
        }
        ;

        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(Context context, EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);
        // length 0 means there is no text
        if (text.length() == 0) {

            SpannableString s = new SpannableString(EMPTY);
            s.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 0);
            s.setSpan(new RelativeSizeSpan(1.1f), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

//            editText.requestFocus();
            Drawable drawable = context.getResources().getDrawable(R.drawable.ic_fail);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            editText.setError(s, drawable);
            return false;
        }
//        Drawable drawable=context.getResources().getDrawable(R.drawable.ic_success);
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//        editText.setError("done",drawable);
        return true;
    }



}


