package lcam.redditorganized.network.auth;

import io.reactivex.Flowable;
import lcam.redditorganized.models.OAuthToken;
import lcam.redditorganized.util.Constants;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthApi {

    @FormUrlEncoded
    @POST(Constants.ACCESS_TOKEN_URL)
    Flowable<OAuthToken> requestTokenForm(
            @Field("code")String code,
            @Field("client_id")String client_id,
            @Field("redirect_uri")String redirect_uri,
            @Field("grant_type")String grant_type
    );
}
