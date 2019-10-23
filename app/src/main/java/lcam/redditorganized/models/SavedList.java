package lcam.redditorganized.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavedList {

    //SavedList -> ListData -> List<SavedPost> -> SavedPostAttributes

    @SerializedName("data")
    @Expose
    private ListData listData;

    public SavedList(ListData listData) {
        this.listData = listData;
    }

    public ListData getListData() {
        return listData;
    }

    public void setListData(ListData listData) {
        this.listData = listData;
    }
}
