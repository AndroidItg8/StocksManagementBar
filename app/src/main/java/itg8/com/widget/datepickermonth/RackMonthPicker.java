package itg8.com.widget.datepickermonth;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import itg8.com.stockmanagement.R;
import itg8.com.widget.datepickermonth.listener.DateMonthDialogListener;
import itg8.com.widget.datepickermonth.listener.OnCancelMonthDialogListener;

/**
 * Created by kristiawan on 31/12/16.
 */

public class RackMonthPicker {

    private AlertDialog mAlertDialog;
    private RackMonthPicker.Builder builder;
    private Context context;
    private Button mPositiveButton;
    private Button mNegativeButton;
    private DateMonthDialogListener dateMonthDialogListener;
    private OnCancelMonthDialogListener onCancelMonthDialogListener;
    private boolean isBuild = false;
    private static final String TAG = "RackMonthPicker";

    public RackMonthPicker(Context context) {
        this.context = context;
        builder = new Builder();
    }

    public RackMonthPicker(Activity activity) {
        this.context = activity;
        builder = new Builder();
    }

    public void show() {
        if (isBuild) {
            mAlertDialog.show();
            builder.setDefault();
        } else {
            builder.build();
            isBuild = true;
        }
    }

    /**
     * set action callback when positive button clicked
     *
     * @param dateMonthDialogListener
     * @return
     */
    public RackMonthPicker setPositiveButton(DateMonthDialogListener dateMonthDialogListener) {
        this.dateMonthDialogListener = dateMonthDialogListener;
        mPositiveButton.setOnClickListener(builder.positiveButtonClick());
        return this;
    }

    /**
     * set action callback when negative button clicked
     *
     * @param onCancelMonthDialogListener
     * @return
     */
    public RackMonthPicker setNegativeButton(OnCancelMonthDialogListener onCancelMonthDialogListener) {
        this.onCancelMonthDialogListener = onCancelMonthDialogListener;
        mNegativeButton.setOnClickListener(builder.negativeButtonClick());
        return this;
    }

    /**
     * change text positive button
     *
     * @param text
     * @return
     */
    public RackMonthPicker setPositiveText(String text) {
        mPositiveButton.setText(text);
        return this;
    }

    /**
     * change text negative button
     *
     * @param text
     * @return
     */
    public RackMonthPicker setNegativeText(String text) {
        mNegativeButton.setText(text);
        return this;
    }

    /**
     * set localization show month
     *
     * @param locale
     * @return
     */
    public RackMonthPicker setLocale(Locale locale) {
        builder.setLocale(locale);
        return this;
    }

    /**
     * change default selected month (1 - 12)
     *
     * @param month
     * @return
     */
    public RackMonthPicker setSelectedMonth(int month) {
        builder.setSelectedMonth(month);
        return this;
    }

    /**
     * change default selected year
     *
     * @param year
     * @return
     */
    public RackMonthPicker setSelectedYear(int year) {
        builder.setSelectedYear(year);
        return this;
    }

    /**
     * change color theme
     *
     * @param color
     * @return
     */
    public RackMonthPicker setColorTheme(int color) {
        builder.setColorTheme(color);
        return this;
    }

    public RackMonthPicker setMonthType(MonthType monthType) {
        builder.setMonthType(monthType);
        return this;
    }

    public void dismiss() {
        mAlertDialog.dismiss();
    }

    private class Builder implements MonthAdapter.OnSelectedListener, View.OnClickListener {

        private MonthAdapter monthAdapter;
        private TextView mTitleView;
        private TextView mYear;
        private int year;
        private int month;
        private AlertDialog.Builder alertBuilder;
        private View contentView;

        private Builder() {
            alertBuilder = new AlertDialog.Builder(context);

            contentView = LayoutInflater.from(context).inflate(R.layout.dialogue_date_picker, null);
            contentView.setFocusable(true);
            contentView.setFocusableInTouchMode(true);

            mTitleView = (TextView) contentView.findViewById(R.id.title);
            mYear = (TextView) contentView.findViewById(R.id.text_year);
            mYear.setOnClickListener(this);

            ImageView next = (ImageView) contentView.findViewById(R.id.btn_next);
            next.setOnClickListener(nextButtonClick());

            ImageView previous = (ImageView) contentView.findViewById(R.id.btn_previous);
            previous.setOnClickListener(previousButtonClick());

            mPositiveButton = (Button) contentView.findViewById(R.id.btn_p);
            mNegativeButton = (Button) contentView.findViewById(R.id.btn_n);

            monthAdapter = new MonthAdapter(context, this);

            RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(monthAdapter);

            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);

            setColorTheme(getColorByThemeAttr(context, android.R.attr.colorPrimary, R.color.colorPrimary));
        }

        private int getColorByThemeAttr(Context context, int attr, int defaultColor) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = context.getTheme();
            boolean got = theme.resolveAttribute(attr, typedValue, true);
            return got ? typedValue.data : defaultColor;
        }

        //set default config
        private void setDefault() {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);

            monthAdapter.setSelectedItem(month);
            mTitleView.setText(monthAdapter.getShortMonth() + ", " + year);
            monthAdapter.notifyDataSetChanged();
            mYear.setText(year + "");
        }

        public void setLocale(Locale locale) {
            monthAdapter.setLocale(locale);
        }

        public void setSelectedMonth(int index) {
            monthAdapter.setSelectedItem(index);
            mTitleView.setText(monthAdapter.getShortMonth() + ", " + year);
        }

        public void setSelectedYear(int year) {
            this.year = year;
            mYear.setText(year + "");
            mTitleView.setText(monthAdapter.getShortMonth() + ", " + year);
        }

        public void setColorTheme(int color) {
            LinearLayout linearToolbar = (LinearLayout) contentView.findViewById(R.id.linear_toolbar);
            linearToolbar.setBackgroundColor(color);

            monthAdapter.setBackgroundMonth(color);
            mPositiveButton.setTextColor(Color.WHITE);
            mNegativeButton.setTextColor(Color.WHITE);
        }

        public void setMonthType(MonthType monthType){
            monthAdapter.setMonthType(monthType);
        }

        public void build() {
            monthAdapter.setSelectedItem(month);
            mTitleView.setText(monthAdapter.getShortMonth() + ", " + year);
            mYear.setText(year + "");

            mAlertDialog = alertBuilder.create();
            mAlertDialog.show();
            mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                    WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            mAlertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_STATE);
            mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mAlertDialog.getWindow().setBackgroundDrawableResource(R.drawable.material_dialog_window);
            mAlertDialog.getWindow().setContentView(contentView);
        }

        public View.OnClickListener nextButtonClick() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    year++;
                    mYear.setText(year + "");
                    mTitleView.setText(monthAdapter.getShortMonth() + ", " + year);
                }
            };
        }

        public View.OnClickListener previousButtonClick() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    year--;
                    mYear.setText(year + "");
                    mTitleView.setText(monthAdapter.getShortMonth() + ", " + year);
                }
            };
        }

        public View.OnClickListener positiveButtonClick() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dateMonthDialogListener.onDateMonth(
                            monthAdapter.getMonth(),
                            monthAdapter.getStartDate(),
                            monthAdapter.getEndDate(),
                            year, mTitleView.getText().toString());

                    mAlertDialog.dismiss();
                }
            };
        }

        public View.OnClickListener negativeButtonClick() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCancelMonthDialogListener.onCancel(mAlertDialog);
                }
            };
        }

        @Override
        public void onContentSelected() {
            mTitleView.setText(monthAdapter.getShortMonth() + ", " + year);
        }

        /**
         * Added Now for Clicked Listener  to Month Picker
         * @param v
         */
        @Override
        public void onClick(View v) {
openCalenderYearOnly();
//            DatePickerDialog datePickerDialog = null;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                datePickerDialog= new DatePickerDialog(v.getContext());
//                datePickerDialog.getDatePicker().getTouchables().get(0).performClick();
//                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        Log.d(TAG, "onDateSet: "+year);
//                    }
//                });
//
//                datePickerDialog.show();
//            }



        }
    }

    private void openCalenderYearOnly() {
        String mDatePicker = "mDatePicker";
        DatePickerDialog datePickerDialog = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            datePickerDialog = new DatePickerDialog(context);

            DatePicker obj = datePickerDialog.getDatePicker();
            try {
                Field[] datePickerDialogFields = obj.getClass().getDeclaredFields();
                for (Field datePickerDialogField : datePickerDialogFields) {
                    if (datePickerDialogField.getName().equalsIgnoreCase(mDatePicker)) {
                        datePickerDialogField.setAccessible(true);
                        DatePicker datePicker = (DatePicker) datePickerDialogField.get(obj);
                        DatePicker monPickerPicker = (DatePicker) datePickerDialogField.get(obj);

                        Field datePickerFields[] = datePickerDialogField.getType().getDeclaredFields();
                        for (Field datePickerField : datePickerFields) {
//datePickerField
//mMonthSpinner

                            if ((mDatePicker.equalsIgnoreCase(datePickerField.getName())) || mDatePicker.equalsIgnoreCase((datePickerField.getName()))) {
                                datePickerField.setAccessible(true);
                                Object dayPicker = new Object();
                                dayPicker = datePickerField.get(datePicker);
                                ((View) dayPicker).setVisibility(View.GONE);
                            }
                            if (mDatePicker.equalsIgnoreCase((datePickerField.getName())) || mDatePicker.equalsIgnoreCase(datePickerField
                                    .getName())) {
                                datePickerField.setAccessible(true);
                                Object dayPicker = new Object();
                                dayPicker = datePickerField.get(datePicker);
                                ((View) dayPicker).setVisibility(View.GONE);
                            }
                        }
                        datePicker.setCalendarViewShown(false);

                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.d(TAG, "openCalenderYearOnly: "+ex.getMessage());
            }
        }
    }
}