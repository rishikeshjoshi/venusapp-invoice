package com.venus.repository;

import com.venus.model.Party;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hrishikeshjoshi on 6/23/16.
 */
@Repository
public interface PartyRepository extends CrudRepository<Party,Long> {

}
