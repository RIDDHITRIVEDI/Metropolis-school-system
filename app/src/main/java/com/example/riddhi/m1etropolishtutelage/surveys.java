package com.example.riddhi.m1etropolishtutelage;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Vishmay on 27-Mar-17.
 */

public class surveys extends Fragment {
    PieChart pieChart;
    private static final String REGISTER_URL2 = "http://ridhitrivedi.16mb.com/count.php";
    String yes, no, total;
    Spinner spn_year;
    ArrayList<String> val = new ArrayList<>();
    Globalvariable globalVariable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.survey, container, false);

        globalVariable = (Globalvariable) getApplicationContext();
        globalVariable.setName("home");
        pieChart = (PieChart) v.findViewById(R.id.chart);
        spn_year = (Spinner) v.findViewById(R.id.spn_year);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pie();
        val.add("2017");
        val.add("2016");
        val.add("2015");
        val.add("2014");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, val);
        spn_year.setAdapter(adapter);
        spn_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                piechart(val.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void piechart(final String year) {
        class UploadImage1 extends AsyncTask<Bitmap, Void, String> {

            ProgressDialog progressDialog;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                yes = "";
                no = "";
                total = "";
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                int success;
                BufferedReader bufferedReader = null;
                try {
                    WebRequest webreq = new WebRequest();
                    HashMap<String, String> recnum = new HashMap<String, String>();
                    recnum.put("Year", year);

                    JSONObject json = webreq.makeWebServiceCall(REGISTER_URL2, webreq.POSTRequest, recnum);
                    yes = json.getString("Yes");
                    no = json.getString("No");
                    total = json.getString("Total");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                if (total.matches("0")) {
                    week("No Data Available");
                } else if (total == null) {
                    Toast.makeText(getActivity(), "Please Check Your Internet", Toast.LENGTH_LONG).show();
                } else if (total.matches("")) {
                    Toast.makeText(getActivity(), "Please Check Your Internet", Toast.LENGTH_LONG).show();
                } else {
                    week(year);
                }
                Log.d("mainactivity123", "issss>>>" + total + "<<<<<<<");
            }
        }
        new UploadImage1().execute();
    }


    public void week(String s1) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();
        float total1 = Float.parseFloat(total);
        if (!yes.matches("0")) {
            float i1 = Float.parseFloat(yes);
            float per = (100 * i1) / total1;
            entries.add(new PieEntry(per, "Yes"));
            labels.add("Yes");
        }
        if (!no.matches("0")) {
            float i2 = Float.parseFloat(no);
            float pe2 = (100 * i2) / total1;
            entries.add(new PieEntry(pe2, "No"));
            labels.add("No");
        }

        PieDataSet dataset = new PieDataSet(entries, "Student Admission");
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.MATERIAL_COLORS)
            colors.add(c);


        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);


        colors.add(ColorTemplate.getHoloBlue());

        dataset.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataset);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        //data.setValueTypeface(mTfLight);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);
        pieChart.animateXY(5000, 5000);
        pieChart.invalidate();
        pieChart.setCenterText(generateCenterSpannableText(s1));
    }

    public void pie() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        // pieChart.setCenterTextTypeface(mTfLight);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        // pieChart.setUnit(" €");
        // pieChart.setDrawUnitsInChart(true);

        // add a selection listener


        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // pieChart.spin(2000, 0, 360);


        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setWordWrapEnabled(true);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);

        l.setTextColor(Color.parseColor("#c7cfe7"));

        l.setTextSize(12f);
        l.setYOffset(7f);
        // entry label styling
        pieChart.setEntryLabelColor(Color.WHITE);
        //  pieChart.setEntryLabelTypeface(mTfRegular);
        pieChart.setEntryLabelTextSize(10f);

    }

    private SpannableString generateCenterSpannableText(String s1) {

        SpannableString s = new SpannableString(" " +
                s1);
        s.setSpan(new RelativeSizeSpan(3.0f), 0, 1, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 1, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 1, s.length(), 0);
        s.setSpan(new RelativeSizeSpan(2.0f), 1, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), 1, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 1, s.length(), 0);
        return s;
    }
}
