package sechan.intern.lessismore.map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by Sechan on 2017-08-07.
 */

public interface ApiService {
    //public static final String API_URL = "https://openapi.naver.com/v1/map/geocode";

/*
    @GET("v1/map/geocode")
    Call<MapResult> mapResult (@Header("X-Naver-Client-Id") String id, @Header("X-Naver-Client-Secret") String secret, @Query("encoding") String encoding, @Query("coordType") String coordType, @Query("query") String query);

*/
    //주소로 검색



    @GET("v1/search/local.json")
    Call<MapRes> mapRes(@Header("X-Naver-Client-Id") String id, @Header("X-Naver-Client-Secret") String secret, @Query("display") int display, @Query("start") int start, @Query("sort") String sort, @Query("query") String query);

    //상호명검색


}
