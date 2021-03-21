package com.flowerpower.data.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
public class Basket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long basketId;

    @OneToMany(targetEntity = OrderedItem.class)
    List<OrderedItem> items;
}
