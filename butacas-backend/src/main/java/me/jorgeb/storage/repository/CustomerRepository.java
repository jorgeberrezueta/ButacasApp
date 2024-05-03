package me.jorgeb.storage.repository;

import me.jorgeb.storage.model.CustomerEntity;

public interface CustomerRepository extends BaseRepository<CustomerEntity, Long> {

    CustomerEntity findByDocumentNumber(String documentNumber);

}
