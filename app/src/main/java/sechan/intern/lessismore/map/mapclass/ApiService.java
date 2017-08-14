package sechan.intern.lessismore.map.mapclass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by Sechan on 2017-08-07.
 */

public interface ApiService {

    @GET("v1/search/local.json")
    Call<MapResult> mapRes(@Header("X-Naver-Client-Id") String id, @Header("X-Naver-Client-Secret") String secret, @Query("display") int display, @Query("start") int start, @Query("sort") String sort, @Query("query") String query);
    //상호명검색


}
