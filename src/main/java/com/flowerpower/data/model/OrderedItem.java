package com.flowerpower.data.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "ordered_item")
public class OrderedItem implements Serializable {
    @Id
    private Long id;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @ManyToOne(targetEntity = Item.class)
    private Item item;

    @ManyToOne(targetEntity = Basket.class)
    private Basket basket;

    private String shippingAddress;
    private String greetingMessage;
}
