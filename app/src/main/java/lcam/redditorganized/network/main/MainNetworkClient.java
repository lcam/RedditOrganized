package lcam.redditorganized.network.main;

import javax.inject.Inject;

import io.reactivex.Flowable;
import lcam.redditorganized.models.SavedList;
import lcam.redditorganized.models.User;

public class MainNetworkClient {

    private final MainApi mainApi;

    @Inject
    public MainNetworkClient(MainApi mainApi) {
        this.mainApi = mainApi;
    }

    Flowable<User> requestName(){
        return mainApi.getUserName();
    }

    Flowable<SavedList> requestSavedList(String username){
        return mainApi.getSavedListFromUser(username);
    }
}
