package me.jorgeb.controller;

import me.jorgeb.storage.StorageHandler;
import me.jorgeb.storage.model.RoomEntity;
import me.jorgeb.storage.repository.RoomRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/room")
public class RoomController extends BaseController<RoomRepository, RoomEntity> {

    private StorageHandler storageHandler;

    public RoomController(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
    }

    @Override
    public RoomRepository getRepository() {
        return this.storageHandler.getRoomRepository();
    }

    /**
     * Obtiene el numero de butacas disponibles y ocupadas por sala en la cartelera del día actual.
     *
     * @return Lista de información de butacas por sala.
     */
    @GetMapping("/seat_info")
    public List<Map<String, Object>> getRoomSeatInfo() {
        return this.getRepository().findRoomSeatInfo();
    }
}
