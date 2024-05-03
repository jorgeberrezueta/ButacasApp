package me.jorgeb.test.controller;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.jorgeb.storage.StorageHandler;
import me.jorgeb.storage.model.*;
import me.jorgeb.storage.repository.RoomRepository;
import me.jorgeb.test.util.Dates;
import me.jorgeb.util.DateParser;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Time;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StorageHandler storageHandler;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private Gson gson;

    private void insertTestData() {
//        storageHandler.getRoomRepository().deleteAll();
//        storageHandler.getSeatRepository().deleteAll();

        RoomEntity room1 = storageHandler.getRoomRepository().save(new RoomEntity("Sala 1", (short) 5));
        RoomEntity room2 = storageHandler.getRoomRepository().save(new RoomEntity("Sala 2", (short) 3));

        SeatEntity seat1 = storageHandler.getSeatRepository().save(new SeatEntity((short) 1, (short) 2, room1.getId(), room1));
        SeatEntity seat2 = storageHandler.getSeatRepository().save(new SeatEntity((short) 1, (short) 2, room1.getId(), room1));
        SeatEntity seat3 = storageHandler.getSeatRepository().save(new SeatEntity((short) 1, (short) 3, room1.getId(), room1));
        storageHandler.getSeatRepository().save(new SeatEntity((short) 1, (short) 4, room1.getId(), room1));
        storageHandler.getSeatRepository().save(new SeatEntity((short) 1, (short) 5, room1.getId(), room1));

        SeatEntity seat4 = storageHandler.getSeatRepository().save(new SeatEntity((short) 1, (short) 1, room2.getId(), room2));
        storageHandler.getSeatRepository().save(new SeatEntity((short) 1, (short) 2, room2.getId(), room2));
        storageHandler.getSeatRepository().save(new SeatEntity((short) 1, (short) 3, room2.getId(), room2));

        MovieEntity movie1 = storageHandler.getMovieRepository().save(new MovieEntity("Duna", MovieGenreEnum.SCIENCE_FICTION, (short) 13, (short) 120));
        MovieEntity movie2 = storageHandler.getMovieRepository().save(new MovieEntity("El Conjuro", MovieGenreEnum.HORROR, (short) 16, (short) 110));

        BillboardEntity billboard1 = new BillboardEntity();
        billboard1.setMovieId(movie1.getId());
        billboard1.setRoomId(room1.getId());
        billboard1.setDate(Dates.today());
        billboard1.setStartTime(new Time(20, 0, 0));
        billboard1.setEndTime(new Time(22, 0, 0));
        billboard1 = storageHandler.getBillboardRepository().save(billboard1);

        BillboardEntity billboard2 = new BillboardEntity();
        billboard2.setMovieId(movie2.getId());
        billboard2.setRoomId(room2.getId());
        billboard2.setDate(Dates.today());
        billboard2.setStartTime(new Time(20, 0, 0));
        billboard2.setEndTime(new Time(22, 0, 0));
        billboard2 = storageHandler.getBillboardRepository().save(billboard2);

        BillboardEntity billboard3 = new BillboardEntity();
        billboard3.setMovieId(movie2.getId());
        billboard3.setRoomId(room1.getId());
        billboard3.setDate(Dates.today());
        billboard3.setStartTime(new Time(20, 0, 0));
        billboard3.setEndTime(new Time(22, 0, 0));
        billboard3 = storageHandler.getBillboardRepository().save(billboard3);

        /**
         * Reservas
         */

        BookingEntity booking1 = new BookingEntity();
        booking1.setBillboardId(billboard1.getId());
        booking1.setDate(Dates.today());
        booking1.setSeatId(seat1.getId());
        booking1.setCustomerId(billboard1.getId());
        storageHandler.getBookingRepository().save(booking1);

        BookingEntity booking2 = new BookingEntity();
        booking2.setBillboardId(billboard2.getId());
        booking2.setDate(Dates.today());
        booking2.setSeatId(seat2.getId());
        booking2.setCustomerId(billboard2.getId());
        storageHandler.getBookingRepository().save(booking2);

        BookingEntity booking3 = new BookingEntity();
        booking3.setBillboardId(billboard3.getId());
        booking3.setDate(Dates.today());
        booking3.setSeatId(seat3.getId());
        booking3.setCustomerId(billboard3.getId());
        storageHandler.getBookingRepository().save(booking3);

        BookingEntity booking4 = new BookingEntity();
        booking4.setBillboardId(billboard3.getId());
        booking4.setDate(Dates.today());
        booking4.setSeatId(seat4.getId());
        booking4.setCustomerId(billboard3.getId());
        storageHandler.getBookingRepository().save(booking4);
    }

    @Test
    public void query2bTest() throws Exception {
        insertTestData();

        System.out.println(storageHandler.getMovieRepository().findAll());
        System.out.println(storageHandler.getBookingRepository().findAll());
        System.out.println(storageHandler.getSeatRepository().findAll());
        System.out.println(storageHandler.getRoomRepository().findAll());
        System.out.println(storageHandler.getBillboardRepository().findAll());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/room/seat_info"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    Set<RoomSeatInfo> data = Sets.set(gson.fromJson(content, RoomSeatInfo[].class));
                    Set<RoomSeatInfo> expected = Sets.set(
                            new RoomSeatInfo(1L, "Sala 1", 3, 5),
                            new RoomSeatInfo(2L, "Sala 2", 1, 3)
                    );
                    System.out.println(data);
                    System.out.println(expected);
                    assert data.equals(expected);
                });
    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    private static class RoomSeatInfo {
        private Long roomId;
        private String name;
        private int occupiedSeats;
        private int totalSeats;
    }
}