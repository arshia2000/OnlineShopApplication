package com.example.digicala.webserviceapi;

import com.example.digicala.entity.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DigiCalaApi {


    String base_url = "http://www.mocky.io/";


    @GET("v2/5ec248432f0000a87dc350f7")
    Call<ArrayList<Product>> getallproduct();


}
