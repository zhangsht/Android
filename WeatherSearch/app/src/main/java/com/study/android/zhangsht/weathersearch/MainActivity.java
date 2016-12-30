package com.study.android.zhangsht.weathersearch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private boolean flag = false;
    private EditText inputCity;
    private Button searchButton;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewVer;
    private static final int UPDATE_CONTENT = 0;
    private static final String url = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx/getWeather";
    private IndexAdapter indexAdapter;
    private WeatherAdapter adapter;

    private TextView getCity;
    private TextView updateTime;
    private TextView temMaxValue;
    private TextView temRange;
    private TextView moisture;
    private TextView airQuality;
    private TextView wind;
    private TextView indexTitle;
    private TextView indexCon;
    private TextView date;
    private TextView weatherDes;
    private TextView temperature;
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case UPDATE_CONTENT:
                    flag = true;
                    List<String> list = (ArrayList<String>)message.obj;
                    if (list.size() <= 39) {
                        Toast.makeText(getApplicationContext(), "查询城市不支持或城市拼写出错", Toast.LENGTH_SHORT).show();
                    } else {
                        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.report);
                        linearLayout.setBackgroundColor(getResources().getColor(R.color.backColor));
                        String cityName = list.get(1);
                        getCity.setText(cityName);
                        String updatetime = list.get(3).substring(11);
                        updateTime.setText(updatetime + " 更新");
                        if (list.get(4).indexOf('暂') == -1) {
                            String temprature = list.get(4).substring(list.get(4).indexOf('温') + 2, list.get(4).indexOf('；'));
                            temMaxValue.setText(temprature);
                            String temrange = list.get(8).substring(0);
                            temRange.setText(temrange);
                            String shidu = list.get(4).substring(list.get(4).indexOf('湿'));
                            moisture.setText(shidu);
                            String airEqu = list.get(5).substring(list.get(5).indexOf('空') , list.get(5).lastIndexOf('。'));
                            airQuality.setText(airEqu);
                            String feng = list.get(4).substring(list.get(4).indexOf('力') + 2, list.get(4).indexOf('级') + 1);
                            wind.setText(feng);

                            String [] indexs = list.get(6).split("。");

                            LinearLayoutManager layoutManagerVer = new LinearLayoutManager(MainActivity.this);
                            layoutManagerVer.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerViewVer.setLayoutManager(layoutManagerVer);
                            ArrayList<Index> indices = new ArrayList<Index>();
                            Index index = new Index(indexs[0].substring(0, 5), indexs[0].substring(6));
                            indices.add(index);
                            Index index1 = new Index(indexs[1].substring(0, 4), indexs[1].substring(5));
                            indices.add(index1);
                            Index index2 = new Index(indexs[2].substring(0, 4), indexs[2].substring(5));
                            indices.add(index2);
                            Index index3 = new Index(indexs[3].substring(0, 4), indexs[3].substring(5));
                            indices.add(index3);
                            Index index4 = new Index(indexs[4].substring(0, 4), indexs[4].substring(5));
                            indices.add(index4);
                            Index index5 = new Index(indexs[5].substring(0, 6), indexs[5].substring(7));
                            indices.add(index5);

                            indexAdapter = new IndexAdapter(MainActivity.this, indices);
                            recyclerViewVer.setAdapter(indexAdapter);
                        } else {
                            recyclerViewVer = (RecyclerView)findViewById(R.id.indexVer);
                            temMaxValue.setText("暂无");
                            recyclerViewVer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f));
                        }



                        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(layoutManager);

                        ArrayList<Weather> weatherList = new ArrayList<Weather>();

                        String first = list.get(7).substring(0, list.get(7).indexOf(' '));
                        String firstDes = list.get(7).substring(list.get(7).indexOf(' '));
                        String firstCon = list.get(8);
                        Weather weather = new Weather(first, firstDes, firstCon);
                        weatherList.add(weather);

                        String second = list.get(12).substring(0, list.get(12).indexOf(' '));
                        String secondDes = list.get(12).substring(list.get(12).indexOf(' '));
                        String secondCon = list.get(13);
                        Weather weather1 = new Weather(second, secondDes, secondCon);
                        weatherList.add(weather1);

                        String third = list.get(17).substring(0, list.get(17).indexOf(' '));
                        String thirdDes = list.get(17).substring(list.get(17).indexOf(' '));
                        String thirdCon = list.get(18);
                        Weather weather2 = new Weather(third, thirdDes, thirdCon);
                        weatherList.add(weather2);

                        String four = list.get(22).substring(0, list.get(22).indexOf(' '));
                        String fourDes = list.get(22).substring(list.get(22).indexOf(' '));
                        String fourCon = list.get(23);
                        Weather weather3 = new Weather(four, fourDes, fourCon);
                        weatherList.add(weather3);

                        String five = list.get(27).substring(0, list.get(27).indexOf(' '));
                        String fiveDes = list.get(27).substring(list.get(27).indexOf(' '));
                        String fiveCon = list.get(28);
                        Weather weather4 = new Weather(five, fiveDes, fiveCon);
                        weatherList.add(weather4);

                        String six = list.get(32).substring(0, list.get(32).indexOf(' '));
                        String sixDes = list.get(32).substring(list.get(32).indexOf(' '));
                        String sixCon = list.get(33);
                        Weather weather5 = new Weather(six, sixDes, sixCon);
                        weatherList.add(weather5);

                        String seven = list.get(37).substring(0, list.get(37).indexOf(' '));
                        String sevenDes = list.get(37).substring(list.get(37).indexOf(' '));
                        String sevenCon = list.get(38);
                        Weather weather6 = new Weather(seven, sevenDes, sevenCon);
                        weatherList.add(weather6);
                        adapter = new WeatherAdapter(MainActivity.this, weatherList);
                        recyclerView.setAdapter(adapter);
                    }
                    break;
                default: break;
            }
        }
    };

    private void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    Log.i("runnable", "start");
                    connection = (HttpURLConnection)((new URL(url.toString()).openConnection()));
                    connection.setRequestMethod("POST");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);

                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    String request = inputCity.getText().toString();
                    request = URLEncoder.encode(request, "utf-8");
                    out.writeBytes("theCityCode=" + request + "&theUserID=d97d3c321cf44da0b8135ce20464c4ec");

                    if (connection.getResponseCode() == 200) {
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder response = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        Message message = new Message();
                        message.what = UPDATE_CONTENT;
                        message.obj = parseXMLWithPull(response.toString());
                        handler.sendMessage(message);
                    } else {
                        Toast.makeText(getApplicationContext(), "服务访问出错!", Toast.LENGTH_SHORT).show();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.i("error", "open URL");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("error", "IO");
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private List<String> parseXMLWithPull(String xml) throws XmlPullParserException, IOException {
        List<String> list = new ArrayList<String>();
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(xml));

        int eventType  = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if ("string".equals(parser.getName())) {
                        String str = parser.nextText();
                        Log.i("key", str);
                        list.add(str);
                    }
                    break;
                case XmlPullParser.END_DOCUMENT:
                    break;
                default:
                    break;
            }
            eventType = parser.next();
        }
        return list;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputCity = (EditText)findViewById(R.id.inputCity);
        searchButton = (Button)findViewById(R.id.searchButton);
        getCity = (TextView)findViewById(R.id.getCity);
        updateTime = (TextView)findViewById(R.id.updateTime);
        temMaxValue = (TextView)findViewById(R.id.temMaxValue);
        temRange = (TextView)findViewById(R.id.temRange);
        moisture = (TextView)findViewById(R.id.moisture);
        airQuality = (TextView)findViewById(R.id.airQuality);
        wind = (TextView)findViewById(R.id.wind);
        indexTitle = (TextView)findViewById(R.id.indexTitle);
        indexCon = (TextView)findViewById(R.id.indexCon);
        date = (TextView)findViewById(R.id.date);
        weatherDes = (TextView)findViewById(R.id.weatherDes);
        temperature = (TextView)findViewById(R.id.temperature);
        recyclerViewVer = (RecyclerView)findViewById(R.id.indexVer);
        recyclerView = (RecyclerView)findViewById(R.id.weatherHor);



        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    clearText();
                    sendRequestWithHttpURLConnection();
                } else {
                    Toast.makeText(getApplicationContext(), "当前没有网络可用!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void clearText() {
        if (flag) {
            adapter.clear();
            indexAdapter.clear();
            getCity.setText("");
            updateTime.setText("");
            temMaxValue.setText("");
            temRange.setText("");
            moisture.setText("");
            airQuality.setText("");
            wind.setText("");
        }
    }
}
