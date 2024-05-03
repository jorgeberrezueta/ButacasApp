package me.jorgeb.storage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static me.jorgeb.storage.Statements.ENTITY_ROOM;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = ENTITY_ROOM)
public class RoomEntity extends BaseEntity {
    @Column(name = "name", nullable = false)
    @NotNull
    @Size(max = 50)
    private String name;

    @Column(name = "number", nullable = false)
    @NotNull
    private Short number;

}
