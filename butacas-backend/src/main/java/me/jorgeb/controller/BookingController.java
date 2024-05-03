package me.jorgeb.controller;

import me.jorgeb.exception.InvalidInputDateException;
import me.jorgeb.storage.StorageHandler;
import me.jorgeb.storage.model.BookingEntity;
import me.jorgeb.storage.repository.BookingRepository;
import me.jorgeb.util.DateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/booking")
public class BookingController extends BaseController<BookingRepository, BookingEntity> {

    private StorageHandler storageHandler;

    @Autowired
    public BookingController(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
    }

    @Override
    public BookingRepository getRepository() {
        return this.storageHandler.getBookingRepository();
    }

    // 2.A. Obtener todas las reservas de películas de terror que se encuentren dentro de un rango de fechas.
    @GetMapping("/horror/{from}/{to}")
    public List<BookingEntity> query2a(@PathVariable String from, @PathVariable String to) throws InvalidInputDateException {
        List<BookingEntity> entities = new ArrayList<>();
        try {
            Date fromDate = DateParser.parseDate(from);
            Date toDate = DateParser.parseDate(to);
            storageHandler.getBookingRepository().findHorrorBookingsWithinDate(
                    fromDate,
                    toDate
            ).iterator().forEachRemaining((e) -> {
                System.out.println(e);
                entities.add(e);
            });
            return entities;
        } catch (DateTimeParseException e) {
            throw new InvalidInputDateException(e.getParsedString());
        }
    }
}
