package com.example.kitty.myapplication.YelpModels;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kitty on 8/15/16.
 */
public class SearchResponse {

    @SerializedName("businesses")
    @Expose
    private List<Business> businesses = new ArrayList<Business>();
    @SerializedName("total")
    @Expose
    private Integer total;

    /**
     *
     * @return
     * The businesses
     */
    public List<Business> getBusinesses() {
        return businesses;
    }

    /**
     *
     * @param businesses
     * The businesses
     */
    public void setBusinesses(List<Business> businesses) {
        this.businesses = businesses;
    }

    /**
     *
     * @return
     * The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     *
     * @param total
     * The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

}
