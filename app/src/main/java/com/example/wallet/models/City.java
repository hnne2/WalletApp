package com.example.wallet.models;

public class City {
    public int cityId;
    public int countryId;
    public int regionId;
    public String name;

    public City(int cityId, int countryId, int regionId, String name) {
        this.cityId = cityId;
        this.countryId = countryId;
        this.regionId = regionId;
        this.name = name;
    }

    public int getCityId() {
        return cityId;
    }

    public int getCountryId() {
        return countryId;
    }

    public int getRegionId() {
        return regionId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}