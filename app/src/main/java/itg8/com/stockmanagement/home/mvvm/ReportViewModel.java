package itg8.com.stockmanagement.home.mvvm;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import itg8.com.stockmanagement.R;
import itg8.com.stockmanagement.common.FragmentSupportBaseObservable;
import itg8.com.stockmanagement.common.genericRv.GenericAdapter;
import itg8.com.stockmanagement.home.model.ReportModel;
import itg8.com.stockmanagement.widget.datepickermonth.RackMonthPicker;
import itg8.com.stockmanagement.widget.datepickermonth.listener.DateMonthDialogListener;
import itg8.com.stockmanagement.widget.datepickermonth.listener.OnCancelMonthDialogListener;

public class ReportViewModel extends FragmentSupportBaseObservable implements OnChartValueSelectedListener {
    private static final String TAG = "ReportViewModel";


    public ObservableBoolean checkIncome;
    public ObservableBoolean checkStock;
    public ObservableBoolean checkDispachted;
    public ObservableBoolean isMonthy;
    public ObservableList<ReportModel> list;
    private PieChart mChart;
    private ObservableArrayList<String> parties;
    private DatePickerDialog datePickerDialog;
    public GenericAdapter<ReportModel, ReportItemViewModel> genericAdapter;


    public ReportViewModel(PieChart pieChart, Fragment fragment) {
        super((fragment));
        this.mChart = pieChart;
        list=new ObservableArrayList<>();
        generateTempItem();
        checkIncome = new ObservableBoolean(true);
        checkStock = new ObservableBoolean(false);
        checkDispachted = new ObservableBoolean(false);
        isMonthy = new ObservableBoolean(false);
        parties= new ObservableArrayList<>();
        genericRv();
        putValueinList();
        initializePieChart();


    }

    private void generateTempItem() {
        for(int i=0; i<=10; i++){
            ReportModel model  = new ReportModel();
                    model.setTemp(String.valueOf(i));
            list.add(model);
        }
    }

    private void genericRv() {
        ReportItemViewModel itemModel = new ReportItemViewModel();
        genericAdapter = new GenericAdapter<>(list, itemModel);
    }

    private void putValueinList() {
        String  partities= "stock";
        for (int i=0; i<=45; i++){
            parties.add(partities);
        }
    }



    @BindingAdapter(value = {"customAdapter"}, requireAll = false)
    public static void productRecyclerview(RecyclerView recyclerView, GenericAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }


    private void initializePieChart() {
        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        //  mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(false);
        mChart.setHighlightPerTapEnabled(false);

        // mChart.setUnit(" €");
//         mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);
        mChart.setExtraLeftOffset(20f);


//        setData(5, 100);

//        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
//         mChart.spin(2000, 0, 360);


        Legend l = mChart.getLegend();


        l.setForm(Legend.LegendForm.CIRCLE);
        // set what type of form/shape should be used legend.setXEntrySpace(8f);
        // legend.setYEntrySpace(6f);
        // legend.setXOffset(0f);
//        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
//        l.setPosition(Legend.LegendOrientation.VERTICAL)
//        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setFormToTextSpace(4f);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(20f);
        l.setYEntrySpace(5f);
        l.setYOffset(0f);
        l.setXOffset(0f);
        l.setWordWrapEnabled(true);

        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        mChart.setEntryLabelColor(Color.TRANSPARENT);
        mChart.setEntryLabelTextSize(5f);
        mChart.setPivotX(0);
        mChart.offsetLeftAndRight(0);
        mChart.setExtraOffsets(0,0,0,0);
        mChart.getCircleBox().offset(0,0);
        mChart.setPaddingRelative(0, 4, 4, 4);

        setData(4,45F);

    }

    private void setData(int count, float range) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) ((Math.random() * range) + range / 5),
                    parties.get(i % parties.size()),
                    getFragment().getResources().getDrawable(R.drawable.ic_menu_share)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(mChart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    public void onClickedDate(View view) {
        openDialogue(view);

    }

    private void openDialogue(final View view) {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(view.getContext());
        builderSingle.setTitle(" Select Date ");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Daily ");
        arrayAdapter.add("Monthly");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    isMonthy.set(false);
                    openDatePickerDaily(view);
                    datePickerDialog.show();
                } else if (which == 1) {
                    isMonthy.set(true);
                    openMonthlyDateDialogue(view);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        datePickerDialog= new DatePickerDialog(view.getContext());
//                        datePickerDialog.getDatePicker().getTouchables().get(0).performClick();
//                        datePickerDialog.show();
//                    }


                }
            }
        });
        builderSingle.show();
    }



    private void openDatePickerDaily(View view) {
        Calendar calendar = Calendar.getInstance();
    datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }


    private void openMonthlyDateDialogue(View view) {
        new RackMonthPicker(view.getContext())
                .setLocale(Locale.ENGLISH)
                .setPositiveButton(new DateMonthDialogListener() {
                    @Override
                    public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                        Log.d(TAG, "onDateMonth: ");
                    }
                })
                .setNegativeButton(new OnCancelMonthDialogListener() {
                    @Override
                    public void onCancel(androidx.appcompat.app.AlertDialog dialog) {
                        Log.d(TAG, "onCancel: ");
                    }
                }).show();
    }


    public void onCheckChanged(RadioGroup group, int id) {
        int rbtnId = group.getCheckedRadioButtonId();
        switch (rbtnId) {
            case R.id.rd_dispatched:
                setComplete(checkDispachted, checkIncome, checkStock);

                break;
            case R.id.rd_income:
                setComplete(checkIncome, checkStock, checkDispachted);

                break;

            case R.id.rd_stock:
                setComplete(checkStock, checkDispachted, checkIncome);
                break;
        }
    }

    private void setComplete(ObservableBoolean checked, ObservableBoolean unchecked, ObservableBoolean uncheckeds) {
        checked.set(true);
        unchecked.set(false);
        uncheckeds.set(false);
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }



}
