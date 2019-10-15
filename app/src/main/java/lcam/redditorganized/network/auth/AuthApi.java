package lcam.redditorganized.network.auth;

import io.reactivex.Flowable;
import lcam.redditorganized.models.OAuthToken;
import lcam.redditorganized.models.User;
import lcam.redditorganized.util.Constants;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthApi {

    // @Path would append a / to url
    @GET("users/{id}")
    Flowable<User> getUser(
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST(Constants.ACCESS_TOKEN_URL)
    Call<OAuthToken> requestTokenForm(
            @Field("code")String code,
            @Field("client_id")String client_id,
            @Field("redirect_uri")String redirect_uri,
            @Field("grant_type")String grant_type
    );
}
