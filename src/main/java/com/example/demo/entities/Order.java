package com.example.demo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int distance;
    private String status;
    private Double start_latitude;
    private Double start_longitude;

    private Double end_latitude;
    private Double end_longitude;

    public Order() {
    }



    public Double getStart_latitude() {
        return start_latitude;
    }

    public void setStart_latitude(Double start_latitude) {
        this.start_latitude = start_latitude;
    }

    public Double getStart_longitude() {
        return start_longitude;
    }

    public void setStart_longitude(Double start_longitude) {
        this.start_longitude = start_longitude;
    }

    public Double getEnd_latitude() {
        return end_latitude;
    }

    public void setEnd_latitude(Double end_latitude) {
        this.end_latitude = end_latitude;
    }

    public Double getEnd_longitude() {
        return end_longitude;
    }

    public void setEnd_longitude(Double end_longitude) {
        this.end_longitude = end_longitude;
    }

    public Long getOrderId() {
        return id;
    }

    public void setOrderId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
