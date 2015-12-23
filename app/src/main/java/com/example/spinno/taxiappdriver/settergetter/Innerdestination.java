package com.example.spinno.taxiappdriver.settergetter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by saifi45 on 10/23/2015.
 */
public class Innerdestination {
    @SerializedName("geometry")
    public Geometry geometry = new Geometry();


    public class Geometry {
        @SerializedName("location")
        public Location11 location11 = new Location11();


        public class Location11 {
            @SerializedName("lat")
            public String lat;

            @SerializedName("lng")
            public String lng;
        }
    }
}
