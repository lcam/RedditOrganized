package lcam.redditorganized.util;

public class Constants {
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static final String REDDIT_BASE_URL = "https://www.reddit.com/api/v1";


    public static final String AUTH_URL =
            "https://www.reddit.com/api/v1/authorize.compact?client_id=%s" +
                    "&response_type=code&state=%s&redirect_uri=%s&" +
                    "duration=permanent&scope=identity";

    public static final String CLIENT_ID = "eMjgbTd6OM2fkw";

    public static final String REDIRECT_URI =
            "http://www.example.com/my_redirect";

    public static final String STATE = "MY_RANDOM_STRING_1";

    public static final String ACCESS_TOKEN_URL =
            "https://www.reddit.com/api/v1/access_token";
}
