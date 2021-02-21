package com.example.priceengine.model.exchange;

public class ProductExchange {
    private Long id;
    private String name;
    private Integer cartonSize;
    private Double cartonPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCartonSize() {
        return cartonSize;
    }

    public void setCartonSize(Integer cartonSize) {
        this.cartonSize = cartonSize;
    }

    public Double getCartonPrice() {
        return cartonPrice;
    }

    public void setCartonPrice(Double unitPrice) {
        this.cartonPrice = unitPrice;
    }
}
