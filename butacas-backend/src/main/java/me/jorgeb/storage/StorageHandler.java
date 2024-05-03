package me.jorgeb.storage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.jorgeb.exception.EntityNotFoundException;
import me.jorgeb.storage.model.BookingEntity;
import me.jorgeb.storage.model.SeatEntity;
import me.jorgeb.storage.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor @Getter
public class StorageHandler {

    private final BillboardRepository billboardRepository;
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final SeatRepository seatRepository;

    public SeatEntity disableSeat(Long seatId) throws EntityNotFoundException {
        SeatEntity seat = seatRepository.findById(seatId).orElseThrow(
                () -> new EntityNotFoundException("No se encontró la butaca con id: " + seatId)
        );
        seat.setStatus(false);
        // Buscar todas las reservas asociadas al asiento y cancelarlas.
        List<BookingEntity> bookings = bookingRepository.findBySeatId(seatId);
        bookings.forEach((b) -> {
            b.setStatus(false);
            bookingRepository.save(b);
        });
        return seatRepository.save(seat);
    }

}
