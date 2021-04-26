package com.flowerpower.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "basket")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Basket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long basketId;

    @ManyToMany(targetEntity = Item.class)
    List<Item> items;
}
