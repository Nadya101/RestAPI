package reqres_objects;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;

@Data
public class UserList {

    private Support support;
    public Integer page;
    @SerializedName("per_page")
    public Integer perPage;
    public Integer total;
    @SerializedName("total_pages")
    public Integer totalPages;
    public ArrayList<UserClass> data;

}