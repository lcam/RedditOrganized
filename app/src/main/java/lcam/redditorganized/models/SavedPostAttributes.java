package lcam.redditorganized.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavedPostAttributes {

    @SerializedName("title")
    @Expose
    private String title;

    public SavedPostAttributes(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
