package com.venus.repository;

import com.venus.model.ItemType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hrishikeshjoshi on 6/16/16.
 */
@Repository
public interface ItemTypeRepository extends CrudRepository<ItemType,Long> {
}
