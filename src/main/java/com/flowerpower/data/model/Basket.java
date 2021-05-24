package com.flowerpower.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @Version
    private long version;
}
