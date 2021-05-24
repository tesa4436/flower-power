package com.flowerpower.data.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "item")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private ItemType itemType;
    private Long amount;
    private Long photoId;
    private Date creationDate;
    private String description;
    private BigDecimal price;

    @Version
    private long version;

    @PrePersist
    void setCreationDate() {
        this.creationDate = new Date();
    }
}
