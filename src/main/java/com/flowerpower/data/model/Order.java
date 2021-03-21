package com.flowerpower.data.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "flower_order")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @OneToOne(targetEntity = Basket.class)
    private Basket basket;

    private OrderStatus status;
    private String paymentMethod;
    private Date creationDate;

    @PrePersist
    void setCreationDate() {
        this.creationDate = new Date();
    }
}
