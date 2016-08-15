package com.example.kitty.myapplication.YelpModels;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kitty on 8/15/16.
 */
public class Location {

    @SerializedName("address")
    @Expose
    private List<String> address = new ArrayList<String>();
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("coordinate")
    @Expose
    private Coordinate coordinate;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("cross_streets")
    @Expose
    private String crossStreets;
    @SerializedName("display_address")
    @Expose
    private List<String> displayAddress = new ArrayList<String>();
    @SerializedName("geo_accuracy")
    @Expose
    private Double geoAccuracy;
    @SerializedName("neighborhoods")
    @Expose
    private List<String> neighborhoods = new ArrayList<String>();
    @SerializedName("postal_code")
    @Expose
    private String postalCode;
    @SerializedName("state_code")
    @Expose
    private String stateCode;

    /**
     *
     * @return
     * The address
     */
    public List<String> getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(List<String> address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The coordinate
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     *
     * @param coordinate
     * The coordinate
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     *
     * @return
     * The countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     *
     * @param countryCode
     * The country_code
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     *
     * @return
     * The crossStreets
     */
    public String getCrossStreets() {
        return crossStreets;
    }

    /**
     *
     * @param crossStreets
     * The cross_streets
     */
    public void setCrossStreets(String crossStreets) {
        this.crossStreets = crossStreets;
    }

    /**
     *
     * @return
     * The displayAddress
     */
    public List<String> getDisplayAddress() {
        return displayAddress;
    }

    /**
     *
     * @param displayAddress
     * The display_address
     */
    public void setDisplayAddress(List<String> displayAddress) {
        this.displayAddress = displayAddress;
    }

    /**
     *
     * @return
     * The geoAccuracy
     */
    public Double getGeoAccuracy() {
        return geoAccuracy;
    }

    /**
     *
     * @param geoAccuracy
     * The geo_accuracy
     */
    public void setGeoAccuracy(Double geoAccuracy) {
        this.geoAccuracy = geoAccuracy;
    }

    /**
     *
     * @return
     * The neighborhoods
     */
    public List<String> getNeighborhoods() {
        return neighborhoods;
    }

    /**
     *
     * @param neighborhoods
     * The neighborhoods
     */
    public void setNeighborhoods(List<String> neighborhoods) {
        this.neighborhoods = neighborhoods;
    }

    /**
     *
     * @return
     * The postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     *
     * @param postalCode
     * The postal_code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     *
     * @return
     * The stateCode
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     *
     * @param stateCode
     * The state_code
     */
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

}
