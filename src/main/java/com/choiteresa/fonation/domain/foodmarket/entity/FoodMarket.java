package com.choiteresa.fonation.domain.foodmarket.entity;


import jakarta.persistence.*;

@Entity
@Table
public class FoodMarket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer id;
    @Column
    public String area;
    @Column
    public String unitySigngu;
    @Column
    public String address;
    @Column
    public String detailAddress;
    @Column
    public String phoneNumber;
    @Column
    public String significant;
    @Column
    public Double longitude;
    @Column
    public Double latitude;

}
