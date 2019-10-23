package lcam.redditorganized.util;

public class Constants {

    public static final String REDDIT_BASE_URL = "https://www.reddit.com/api/v1/";

    public static final String REDDIT_API_URL = "https://oauth.reddit.com";


    public static final String AUTH_URL =
            "https://www.reddit.com/api/v1/authorize.compact?client_id=%s" +
                    "&response_type=code&state=%s&redirect_uri=%s&" +
                    "duration=permanent&scope=identity history";

    public static final String CLIENT_ID = "eMjgbTd6OM2fkw";

    public static final String REDIRECT_URI =
            "http://www.example.com/my_redirect";

    public static final String STATE = "MY_RANDOM_STRING_1";

    public static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";

    public static final String ACCESS_TOKEN_URL =
            "https://www.reddit.com/api/v1/access_token";
}
