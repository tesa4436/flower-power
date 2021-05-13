package com.flowerpower.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "flower_order")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @OneToOne(targetEntity = Basket.class, cascade = CascadeType.ALL)
    private Basket basket;

    private OrderStatus status;
    private String paymentMethod;
    private Date creationDate;
    private String shippingAddress;
    private String greetingMessage;
    private String name;
    private String phone;
    private String surname;

    @PrePersist
    void setCreationDate() {
        this.creationDate = new Date();
    }
}
