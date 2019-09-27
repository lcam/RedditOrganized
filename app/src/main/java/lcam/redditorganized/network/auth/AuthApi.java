package lcam.redditorganized.network.auth;

import java.util.List;

import io.reactivex.Flowable;
import lcam.redditorganized.models.Post;
import lcam.redditorganized.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AuthApi {

    // @Path would append a / to url
    @GET("users/{id}")
    Flowable<User> getUser(
            @Path("id") int id
    );

    // access_token
    @GET("access_token")
    Flowable<List<Post>> getAccessToken(
            //@Query("userId") int id
    );
}
