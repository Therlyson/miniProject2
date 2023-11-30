package model;

import model.solicitações.Request;

public class Espaco {
    private String type;
    private Integer capacity;
    private String name;
    private String location;

    public Espaco(String type, Integer capacity, String name, String location) {
        this.type = type;
        this.capacity = capacity;
        this.name = name;
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
