package com.choiteresa.fonation.domain.foodmarket.model;


import java.math.BigDecimal;
import java.nio.DoubleBuffer;

public class GeoLocationApiResponseDto {
    String latitude;
    String longitude;

    public BigDecimal getLatitude(){
        return new BigDecimal(latitude);
    }

    public BigDecimal getLongitude(){
        return new BigDecimal(longitude);
    }
}
