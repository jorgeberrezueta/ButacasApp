package me.jorgeb.test.storage;

import me.jorgeb.exception.EntityNotFoundException;
import me.jorgeb.storage.StorageHandler;
import me.jorgeb.storage.model.BookingEntity;
import me.jorgeb.storage.model.SeatEntity;
import me.jorgeb.storage.repository.BookingRepository;
import me.jorgeb.storage.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StorageHandlerTest {

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private BookingRepository bookingRepository;

    private StorageHandler storageHandler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        storageHandler = new StorageHandler(null, bookingRepository, null, null, null, seatRepository);
    }

    @Test
    @DisplayName("La butaca se inhabilita correctamente y se cancelan las reservas asociadas")
    public void seatIsSuccessfullyDisabledAndBookingsCancelled() throws EntityNotFoundException {
        SeatEntity seat = new SeatEntity();
        seat.setId(1L);
        seat.setStatus(true);

        BookingEntity booking1 = new BookingEntity();
        booking1.setSeatId(1L);
        booking1.setId(1L);
        booking1.setStatus(true);

        BookingEntity booking2 = new BookingEntity();
        booking2.setSeatId(1L);
        booking2.setId(2L);
        booking2.setStatus(true);

        when(seatRepository.findById(anyLong())).thenReturn(Optional.of(seat));
        when(bookingRepository.findBySeatId(anyLong())).thenReturn(List.of(booking1, booking2));

        storageHandler.disableSeat(1L);

        verify(seatRepository, times(1)).save(seat);
        verify(bookingRepository, times(2)).save(booking1);
        verify(bookingRepository, times(2)).save(booking2);

        assertFalse(seat.getStatus());
        assertFalse(booking1.getStatus());
        assertFalse(booking2.getStatus());
    }

    @Test
    @DisplayName("La butaca no existe")
    public void seatDoesNotExist() {
        when(seatRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> storageHandler.disableSeat(1L));

        verify(seatRepository, times(1)).findById(anyLong());
        verify(bookingRepository, times(0)).findBySeatId(anyLong());
    }
}