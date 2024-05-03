package me.jorgeb.storage.repository;

import me.jorgeb.storage.Statements;
import me.jorgeb.storage.model.BookingEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookingRepository extends BaseRepository<BookingEntity, Long> {

    @Query(value = Statements.QUERY_2A, nativeQuery = true)
    List<BookingEntity> findHorrorBookingsWithinDate(Date from, Date to);

    List<BookingEntity> findBySeatId(Long seatId);

    List<BookingEntity> findByBillboardId(Long id);
}
