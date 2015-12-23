package com.example.spinno.taxiappdriver.settergetter;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saifi45 on 10/23/2015.
 */
public class ridedestsettergetter {
    @SerializedName("status")
    public String status;

    @SerializedName("result")

    public Innerdestination innerdestination = new Innerdestination();
}
