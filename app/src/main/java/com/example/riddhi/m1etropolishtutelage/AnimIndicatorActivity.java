package com.example.riddhi.m1etropolishtutelage;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;

import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;


public class AnimIndicatorActivity extends Fragment implements ViewPager.OnPageChangeListener, OnPageClickListener {
    private ArrayList<Page> pageViews;
    ArrayList<HashMap<String, String>> usersList = new ArrayList<HashMap<String, String>>();
    private static String url = "http://ridhitrivedi.16mb.com/Dashboard_listview.php";
    //private static String url = "http://ridhitrivedi.16mb.com/imageget.php";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private InfiniteIndicator mAnimLineIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_anim_indicator, container, false);
        mAnimLineIndicator = (InfiniteIndicator) v.findViewById(R.id.infinite_anim_line);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        new AnimIndicatorActivity.GetContacts3().execute();

        initData();

        testAnimLineIndicator();
        return v;
    }

    private void initData() {
        pageViews = new ArrayList<>();
//        pageViews.add(new Page("A ", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/a.jpg", this));
//        pageViews.add(new Page("B ", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/b.jpg", this));
//        pageViews.add(new Page("C ", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/c.jpg", this));
//        pageViews.add(new Page("D ", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/d.jpg", this));

        pageViews.add(new Page("A", R.drawable.school_bg1, this));
        pageViews.add(new Page("B", R.drawable.backgrng1, this));
        pageViews.add(new Page("C", R.drawable.school_bg3, this));
        pageViews.add(new Page("D", R.drawable.school_bg4, this));

    }

    //To avoid memory leak ,you should release the res
    @Override
    public void onPause() {
        super.onPause();

        mAnimLineIndicator.stop();
    }

    @Override
    public void onResume() {
        super.onResume();

        mAnimLineIndicator.start();
    }


    private void testAnimLineIndicator() {
        //
        IndicatorConfiguration configuration = new IndicatorConfiguration.Builder()
                .imageLoader(new PicassoLoader())
                .isAutoScroll(false)
                .isStopWhileTouch(true)
                .onPageChangeListener(this)
                .position(IndicatorConfiguration.IndicatorPosition.Center_Bottom)
                .build();
        mAnimLineIndicator.init(configuration);
        mAnimLineIndicator.notifyDataChange(pageViews);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        Toast.makeText(this,"page selected"+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageClick(int position, Page page) {
        //Toast.makeText(this, " click page --- " + position, Toast.LENGTH_SHORT).show();
    }

    private class GetContacts3 extends AsyncTask<String, Void, String> {
        private ProgressDialog pDialog1;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            usersList.clear();
            pDialog1 = new ProgressDialog(getActivity());
            pDialog1.setMessage("Please wait...");

            pDialog1.setCancelable(false);
            pDialog1.show();

        }

        @Override
        protected String doInBackground(String... params) {
            int success;
            BufferedReader bufferedReader = null;
            HttpHandler1 sh = new HttpHandler1();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e("viewimage", "Response from url: " + jsonStr);
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("xyz");
                Log.d("jsonis134", "iss" + contacts);


                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);


                    String category_image = c.getString("image");
                    // This Line is new for you.....
                    String title = c.getString("title");
                    String discription = c.getString("discription");
                    String date = c.getString("date");
                    String type = c.getString("type");


                    HashMap<String, String> contact = new HashMap<String, String>();

                   // contact.put("category_image", category_image);
                    // This Line is new for you.....
                    contact.put("title", title);
                    contact.put("discription", discription);
                    contact.put("date", date);
                    contact.put("type", type);
                    contact.put("image", category_image);


                    Log.d("hashmap", "iss" + contact);
                    usersList.add(contact);

                }
                Log.d("userllistis", "iss" + usersList);


            } catch (Exception e) {
                return null;
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Dismiss the progress dialog

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (pDialog1.isShowing())
                        pDialog1.dismiss();

                    Log.d("ordertaking", "is>>>" + usersList);


                    CardAdapter1 ad = new CardAdapter1(getActivity(), usersList);
                    recyclerView.setAdapter(ad);


                }
            });


            /**
             * Updating parsed JSON data into ListView
             * */


        }
    }
}
