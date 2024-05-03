package me.jorgeb.storage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static me.jorgeb.storage.Statements.ENTITY_SEAT;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = ENTITY_SEAT)
public class SeatEntity extends BaseEntity {
    @Column(name = "number", nullable = false)
    @NotNull
    private Short number;

    @Column(name = "row_number", nullable = false)
    @NotNull
    private Short rowNumber;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Transient
    @ManyToOne(optional = false)
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
    private RoomEntity room = null;

}
