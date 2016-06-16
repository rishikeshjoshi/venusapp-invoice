package com.venus.repository;

import com.venus.model.InvoiceStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hrishikeshjoshi on 6/15/16.
 */
@Repository
public interface InvoiceStatusRepository extends CrudRepository<InvoiceStatus,Long> {



}
