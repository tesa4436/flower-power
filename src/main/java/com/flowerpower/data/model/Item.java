package com.flowerpower.data.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.util.Date;

@Data
@RequiredArgsConstructor
@Entity
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private ItemType itemType;
    private Long amount;
    private Long photoId;
    private Date creationDate;
    private String description;

    @PrePersist
    void setCreationDate() {
        this.creationDate = new Date();
    }
}
