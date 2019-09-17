package lcam.redditorganized.network.main;

import java.util.List;

import io.reactivex.Flowable;
import lcam.redditorganized.models.Post;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApi {

    // posts?userId=1   @Query would append a ? mark
    @GET
    Flowable<List<Post>> getPostsFromUser(
            @Query("userId") int id
    );
}
