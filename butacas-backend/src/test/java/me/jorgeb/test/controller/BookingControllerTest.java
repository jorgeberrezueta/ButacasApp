package me.jorgeb.test.controller;

import com.google.gson.Gson;
import me.jorgeb.storage.StorageHandler;
import me.jorgeb.storage.model.BillboardEntity;
import me.jorgeb.storage.model.BookingEntity;
import me.jorgeb.storage.model.MovieEntity;
import me.jorgeb.storage.model.MovieGenreEnum;
import me.jorgeb.storage.repository.BookingRepository;
import me.jorgeb.test.util.Dates;
import me.jorgeb.util.DateParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Time;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StorageHandler storageHandler;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private Gson gson;

    private BookingEntity insertTestData() {
        MovieEntity movie1 = storageHandler.getMovieRepository().save(new MovieEntity("Duna", MovieGenreEnum.SCIENCE_FICTION, (short) 13, (short) 120));
        MovieEntity movie2 = storageHandler.getMovieRepository().save(new MovieEntity("El Conjuro", MovieGenreEnum.HORROR, (short) 16, (short) 110));

        BillboardEntity billboard1 = new BillboardEntity();
        billboard1.setMovieId(movie1.getId()); // Ciencia ficción
        billboard1.setRoomId(1L);
        billboard1.setDate(Dates.today());
        billboard1.setStartTime(new Time(20, 0, 0));
        billboard1.setEndTime(new Time(22, 0, 0));
        billboard1 = storageHandler.getBillboardRepository().save(billboard1);

        BillboardEntity billboard2 = new BillboardEntity();
        billboard2.setMovieId(movie2.getId()); // Terror
        billboard2.setRoomId(2L);
        billboard2.setDate(Dates.threeDaysAgo());
        billboard2.setStartTime(new Time(20, 0, 0));
        billboard2.setEndTime(new Time(22, 0, 0));
        billboard2 = storageHandler.getBillboardRepository().save(billboard2);

        BillboardEntity billboard3 = new BillboardEntity(); // Valido para las condiciones de la consulta
        billboard3.setMovieId(movie2.getId()); // Terror
        billboard3.setRoomId(3L);
        billboard3.setDate(Dates.today());
        billboard3.setStartTime(new Time(20, 0, 0));
        billboard3.setEndTime(new Time(22, 0, 0));
        billboard3 = storageHandler.getBillboardRepository().save(billboard3);

        BookingEntity booking1 = new BookingEntity();
        booking1.setBillboardId(billboard1.getId());
        booking1.setDate(Dates.today());
        booking1.setSeatId(billboard1.getId());
        booking1.setCustomerId(billboard1.getId());
        booking1 = storageHandler.getBookingRepository().save(booking1);

        BookingEntity booking2 = new BookingEntity();
        booking2.setBillboardId(billboard2.getId());
        booking2.setDate(Dates.threeDaysAgo());
        booking2.setSeatId(billboard2.getId());
        booking2.setCustomerId(billboard2.getId());
        booking2 = storageHandler.getBookingRepository().save(booking2);

        BookingEntity booking3 = new BookingEntity(); // Valido para las condiciones de la consulta
        booking3.setBillboardId(billboard3.getId());
        booking3.setDate(Dates.today());
        booking3.setSeatId(billboard3.getId());
        booking3.setCustomerId(billboard3.getId());
        booking3 = storageHandler.getBookingRepository().save(booking3);

        return booking3;
    }

    @Test
    public void query2aTest() throws Exception {
        BookingEntity validEntity = insertTestData();

        String from = DateParser.formatDate(Dates.yesterday());
        String to = DateParser.formatDate(Dates.tomorrow());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/booking/horror/{from}/{to}", from, to))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    String expected = gson.toJson(List.of(validEntity));
                    assert content.equals(expected);
                });
    }
}