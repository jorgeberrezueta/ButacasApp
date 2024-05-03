package me.jorgeb.controller;

import me.jorgeb.exception.EntityNotFoundException;
import me.jorgeb.storage.StorageHandler;
import me.jorgeb.storage.model.BookingEntity;
import me.jorgeb.storage.model.SeatEntity;
import me.jorgeb.storage.repository.SeatRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/seat")
public class SeatController extends BaseController<SeatRepository, SeatEntity> {

    private StorageHandler storageHandler;

    public SeatController(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
    }

    @Override
    public SeatRepository getRepository() {
        return this.storageHandler.getSeatRepository();
    }

    // 3.A. Método con transaccionalidad para inhabilitar la butaca y cancelar la reserva.
    @PostMapping("/disable/{id}")
    @Transactional(rollbackFor = Exception.class)
    public SeatEntity disableSeat(@PathVariable Long id) throws EntityNotFoundException {
        return storageHandler.disableSeat(id);
    }


}
