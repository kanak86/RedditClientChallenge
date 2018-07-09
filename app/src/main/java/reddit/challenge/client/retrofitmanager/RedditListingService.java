package reddit.challenge.client.retrofitmanager;

import java.util.Map;

import retrofit2.http.QueryMap;
import reddit.challenge.client.datamanagers.TopListings;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by apoorvakanaksiwach on 2/4/18.
 */

public interface RedditListingService {

    /**
     * To directly a get a list of 50 top listings in one shot
     * @return
     */
    @GET("top/.json?limit=50")
    Call<TopListings> getTop50();

    /**
     * To send the pagination params limit, after, count
     * Could have also used multiple @Query but making to
     * keep code more generic using @QueryMap is better.
     * @param queryParams
     * @return
     */
    @GET("top/.json")
    Call<TopListings> getTopWithLimit(@QueryMap Map<String, String> queryParams);
}
