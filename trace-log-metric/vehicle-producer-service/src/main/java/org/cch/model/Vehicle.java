package org.cch.model;

import java.util.Date;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Vehicle {
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

    
    public Vehicle(String vehicleId, float speed, double direction, double tachometer, float dynamical,
            Date timestamp) {
        this.vehicleId = vehicleId;
        this.speed = speed;
        this.direction = direction;
        this.tachometer = tachometer;
        this.dynamical = dynamical;
        this.timestamp = timestamp;
    }

}
