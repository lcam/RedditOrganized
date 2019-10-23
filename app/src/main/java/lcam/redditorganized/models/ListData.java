package lcam.redditorganized.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListData {

    @SerializedName("dist")
    @Expose
    private Integer dist;

    @SerializedName("children")
    @Expose
    private List<SavedPost> savedPostList = null;

    public ListData(Integer dist, List<SavedPost> savedPostList) {
        this.dist = dist;
        this.savedPostList = savedPostList;
    }

    public Integer getDist() {
        return dist;
    }

    public void setDist(Integer dist) {
        this.dist = dist;
    }

    public List<SavedPost> getSavedPostList() {
        return savedPostList;
    }

    public void setSavedPostList(List<SavedPost> savedPostList) {
        this.savedPostList = savedPostList;
    }
}
