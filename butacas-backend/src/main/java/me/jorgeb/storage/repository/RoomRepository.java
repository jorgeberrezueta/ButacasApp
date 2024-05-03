package me.jorgeb.storage.repository;

import me.jorgeb.storage.Statements;
import me.jorgeb.storage.model.RoomEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface RoomRepository extends BaseRepository<RoomEntity, Long> {
    @Query(value = Statements.QUERY_2B, nativeQuery = true)
    List<Map<String, Object>> findRoomSeatInfo();
}
