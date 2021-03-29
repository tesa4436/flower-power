package com.flowerpower.data.repository;

import com.flowerpower.data.model.ItemPhoto;
import org.springframework.data.repository.CrudRepository;

public interface PhotoRepository extends CrudRepository<ItemPhoto, Long> {
}
