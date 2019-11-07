package lcam.redditorganized.network.main;

import javax.inject.Inject;

import io.reactivex.Single;
import lcam.redditorganized.models.SavedList;
import lcam.redditorganized.models.User;

public class MainNetworkClient {

    private final MainApi mainApi;

    @Inject
    public MainNetworkClient(MainApi mainApi) {
        this.mainApi = mainApi;
    }

    Single<User> requestName(){
        return mainApi.getUserName();
    }

    Single<SavedList> requestSavedList(String username){
        return mainApi.getSavedListFromUser(username);
    }
}
