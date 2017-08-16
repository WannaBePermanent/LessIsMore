package sechan.intern.lessismore.map;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sechan.intern.lessismore.R;
import sechan.intern.lessismore.map.mapclass.ApiService;
import sechan.intern.lessismore.map.mapclass.MapItem;
import sechan.intern.lessismore.map.mapclass.MapResult;

/**
 * Created by Sechan on 2017-08-07.
 */

public class MapListActivity extends AppCompatActivity {
    Retrofit retrofit;
    ApiService service;
    Button btnSearch;
    EditText editSearch;
    RecyclerView mapRV;
    MapAdapter mapAdapter;

    // API ID 비번 설정
    static final String clientId = "DDDCTG3Meo6SYF6duEr6";
    static final String clientSecret = "D5pIBQtrBL";
    // ID PW 설정 끝

    //Map API
    static final String encoding = "utf-8";
    static final String coordType = "latlng";
    //Map API용, 지역 검색은 사용안함

    //지역 API
    static final int display = 10;
    static final int start = 1;
    static final String sort = "random";
    MapResult map;
    //지역 API용 끝

    String query;
    ArrayList<MapItem> mapList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ;
        setContentView(R.layout.layout_map_dialog);
        retrofit = new Retrofit.Builder().baseUrl("https://openapi.naver.com").addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(ApiService.class);
        btnSearch = (Button) findViewById(R.id.btn_search);
        editSearch = (EditText) findViewById(R.id.edit_search);
        mapRV = (RecyclerView) findViewById(R.id.rv_map);
        mapRV.setLayoutManager(new LinearLayoutManager(this));
        mapAdapter = new MapAdapter(mapList, this);
        mapRV.setAdapter(mapAdapter);
        editSearch.setText("");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = editSearch.getText().toString();
                Call<MapResult> call = service.mapRes(clientId, clientSecret, display, start, sort, query);
                call.enqueue(new Callback<MapResult>() {
                    @Override
                    public void onResponse(Call<MapResult> call, Response<MapResult> response) {
                        if (response.isSuccessful()) {
                            map = response.body();
                            mapList.clear();
                            mapList.addAll(map.getItems());
                            if (map.getTotal() > 0) {
                                mapAdapter.notifyDataSetChanged();
                            }

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<MapResult> call, Throwable t) {

                    }


                });


            }
        });


    }


}
