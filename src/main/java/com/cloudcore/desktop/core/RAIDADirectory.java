package com.cloudcore.desktop.core;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RAIDADirectory {


    @Expose
    @SerializedName("networks")
    public Network[] networks;
}
