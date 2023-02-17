package com.cch.model;

import java.util.Date;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@MongoEntity(collection="vehicles")
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
    @BsonIgnore
    public Date  timestamp;

    
    public Vehicle(String vehicleId, float speed, double direction, double tachometer, float dynamical, Date timestamp) {
        this.vehicleId = vehicleId;
        this.speed = speed;
        this.direction = direction;
        this.tachometer = tachometer;
        this.dynamical = dynamical;
        this.timestamp = timestamp;
    }

    public Vehicle() {
        
    }


    @Override
    public String toString() {
        return "Vehicle [vehicleId=" + vehicleId + ", speed=" + speed + ", direction=" + direction + ", tachometer="
                + tachometer + ", dynamical=" + dynamical + ", timestamp=" + timestamp + "]";
    }

    public static void deleteFromVehicleId(String vehicleId) {
        delete("vehicleId", vehicleId);
    }

    public static List<Vehicle> findByVehicleId(String vehicleId, int page, int size, Direction direction) {
        PanacheQuery<Vehicle> vehicles = Vehicle.find("vehicleId", Sort.by("timestamp").direction(direction), vehicleId);
        List<Vehicle> list = vehicles.page(page, size).list();;
        return list;
    }
}
