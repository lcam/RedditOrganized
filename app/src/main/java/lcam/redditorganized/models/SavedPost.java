package lcam.redditorganized.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavedPost {

    @SerializedName("data")
    @Expose
    private SavedPostAttributes savedPostAttributes;

    public SavedPost(SavedPostAttributes savedPostAttributes) {
        this.savedPostAttributes = savedPostAttributes;
    }

    public SavedPostAttributes getSavedPostAttributes() {
        return savedPostAttributes;
    }

    public void setSavedPostAttributes(SavedPostAttributes savedPostAttributes) {
        this.savedPostAttributes = savedPostAttributes;
    }
}
