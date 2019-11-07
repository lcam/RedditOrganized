package lcam.redditorganized.network.main;

import io.reactivex.Single;
import lcam.redditorganized.models.SavedList;
import lcam.redditorganized.models.User;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MainApi {

    // https://oauth.reddit.com/api/v1/me
    @GET("api/v1/me")
    Single<User> getUserName(
    );

    //https://oauth.reddit.com/user/leonzcamz101/saved
    @GET("user/{id}/saved")
    Single<SavedList> getSavedListFromUser(
            @Path("id") String username
    );

}
