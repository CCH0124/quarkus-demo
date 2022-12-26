package org.cch.entity;

import java.util.Date;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

public class Vehicle extends PanacheMongoEntity {
    // 識別碼
    public String vehicleId;
    // 車輛速度
    public float speed;
    // 行駛方向
    public double direction;
    // 發動機轉速
    public double tachometer;
    // 順時油耗
    public float dynamical;
    // 上傳時間
    public Date timestamp;
}
