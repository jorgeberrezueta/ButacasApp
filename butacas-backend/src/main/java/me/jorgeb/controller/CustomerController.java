package me.jorgeb.controller;

import me.jorgeb.exception.InvalidInputException;
import me.jorgeb.storage.StorageHandler;
import me.jorgeb.storage.model.CustomerEntity;
import me.jorgeb.storage.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/customer")
public class CustomerController extends BaseController<CustomerRepository, CustomerEntity> {

    private final StorageHandler storageHandler;

    @Autowired
    public CustomerController(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
    }

    @Override
    public CustomerRepository getRepository() {
        return this.storageHandler.getCustomerRepository();
    }

    @Override
    protected void verifyClashingEntities(CustomerEntity entity) throws InvalidInputException {
        // Verificar si el cliente con esa cedula ya existe.
        if (entity.getDocumentNumber() != null) {
            CustomerEntity existingCustomer = getRepository().findByDocumentNumber(entity.getDocumentNumber());
            if (existingCustomer != null && !existingCustomer.getId().equals(entity.getId())) {
                throw new InvalidInputException("El cliente con la cédula " + entity.getDocumentNumber() + " ya existe.");
            }
        }
    }

}
