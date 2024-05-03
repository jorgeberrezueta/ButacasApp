package me.jorgeb.test.controller;

import me.jorgeb.controller.BillboardController;
import me.jorgeb.storage.StorageHandler;
import me.jorgeb.storage.model.*;
import me.jorgeb.storage.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BillboardController.class)
public class BillboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StorageHandler storageHandler;

    @MockBean
    private BillboardRepository billboardRepository;
    @MockBean
    private BookingRepository bookingRepository;
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private MovieRepository movieRepository;
    @MockBean
    private RoomRepository roomRepository;

    private CustomerEntity generateCustomer() {
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setStatus(true);
        customer.setName("Cliente 1");
        customer.setLastname("Apellido");
        customer.setDocumentNumber("0912345678");
        customer.setAge((short) 18);
        customer.setPhoneNumber("0987654321");
        customer.setEmail("test@test.com");
        return customer;
    }

    private MovieEntity generateMovie() {
        MovieEntity movie = new MovieEntity();
        movie.setId(1L);
        movie.setStatus(true);
        movie.setName("Película 1");
        movie.setGenre(MovieGenreEnum.ACTION);
        movie.setAllowedAge((short) 18);
        movie.setLengthMinutes((short) 120);
        return movie;
    }

    private RoomEntity generateRoom() {
        RoomEntity room = new RoomEntity();
        room.setId(1L);
        room.setStatus(true);
        room.setName("Room 1");
        room.setNumber((short) 10);
        return room;
    }

    private BillboardEntity generateBillboard(boolean status, Date date) {
        BillboardEntity billboard = new BillboardEntity();
        billboard.setId(1L);
        billboard.setStatus(status);
        billboard.setDate(date);
        billboard.setStartTime(new Time(System.currentTimeMillis()));
        billboard.setEndTime(new Time(System.currentTimeMillis() + 3600000));
        billboard.setMovieId(1L);
        billboard.setRoomId(1L);
        return billboard;
    }

    private BookingEntity generateBooking() {
        BookingEntity booking = new BookingEntity();
        booking.setId(1L);
        booking.setStatus(true);
        booking.setDate(new Date(System.currentTimeMillis() + 86400000));
        booking.setCustomerId(1L);
        booking.setSeatId(1L);
        booking.setBillboardId(1L);
        return booking;
    }

    public void setUpTest(Date billboardDate, boolean billboardStatus, ResultMatcher matcher) throws Exception {
        CustomerEntity customer = generateCustomer();
        BillboardEntity billboard = generateBillboard(billboardStatus, billboardDate);
        BookingEntity booking = generateBooking();

        when(storageHandler.getBillboardRepository()).thenReturn(billboardRepository);
        when(storageHandler.getBookingRepository()).thenReturn(bookingRepository);
        when(storageHandler.getCustomerRepository()).thenReturn(customerRepository);

        when(billboardRepository.findById(anyLong())).thenReturn(Optional.of(billboard));
        when(bookingRepository.findByBillboardId(anyLong())).thenReturn(List.of(booking));
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/billboard/cancel/{id}", 1L))
                .andExpect(matcher);
    }

    @Test
    @DisplayName("La cartelera se cancela correctamente")
    public void billboardShouldBeCancelled() throws Exception {
        setUpTest(
                new Date(System.currentTimeMillis() + 86400000),
                true,
                status().isOk()
        );
    }

    @Test
    @DisplayName("La cartelera no se puede cancelar por su fecha")
    public void billboardShouldNotBeCancelledByDate() throws Exception {
        setUpTest(
                new Date(System.currentTimeMillis() - 86400000),
                true,
                status().isBadRequest()
        );
    }

    @Test
    @DisplayName("La cartelera ya se encuentra cancelada")
    public void billboardAlreadyCancelled() throws Exception {
        setUpTest(
                new Date(System.currentTimeMillis() + 86400000),
                false,
                status().isBadRequest()
        );
    }

}