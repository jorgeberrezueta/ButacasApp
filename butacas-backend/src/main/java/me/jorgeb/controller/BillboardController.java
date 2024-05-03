package me.jorgeb.controller;

import me.jorgeb.exception.InvalidInputException;
import me.jorgeb.storage.StorageHandler;
import me.jorgeb.storage.model.BillboardEntity;
import me.jorgeb.storage.repository.BillboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/billboard")
public class BillboardController extends BaseController<BillboardRepository, BillboardEntity> {

    private StorageHandler storageHandler;

    @Autowired
    public BillboardController(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
    }

    @Override
    public BillboardRepository getRepository() {
        return this.storageHandler.getBillboardRepository();
    }

    // 3.B. Implementar el método con transaccionalidad para cancelar la cartelera y cancelar todas las reservas de la sala, adicional se debe habilitar las butacas e imprimir por consola la lista de clientes que fueron afectados.
    // 3.C. Si se trata de cancelar una cartelera cuando la función sea menor a la fecha actual se debe lanzar una excepción personalizada con el mensaje: 'No se puede cancelar funciones de la cartelera con fecha anterior a la actual'.
    @PostMapping("/cancel/{id}")
    @Transactional(rollbackFor = Exception.class)
    public void cancelBillboard(@PathVariable Long id) throws InvalidInputException {
        BillboardEntity billboard = this.getRepository().findById(id).orElseThrow(
                () -> new InvalidInputException("No se encontró la cartelera con id: " + id)
        );
        if (!billboard.getStatus()) {
            throw new InvalidInputException("La cartelera ya se encuentra cancelada");
        }
        if (billboard.getDate().before(new Date())) {
            throw new InvalidInputException("No se puede cancelar funciones de la cartelera con fecha anterior a la actual");
        }
        billboard.setStatus(false);
        this.getRepository().save(billboard);
        System.out.println("Clientes afectados:");
        this.storageHandler.getBookingRepository().findByBillboardId(id).forEach((b) -> {
            b.setStatus(false);
            this.storageHandler.getBookingRepository().save(b);
            this.storageHandler.getCustomerRepository().findById(b.getCustomerId()).ifPresent(c -> System.out.println(c.getName() + " " + c.getLastname()));
        });
    }

}
