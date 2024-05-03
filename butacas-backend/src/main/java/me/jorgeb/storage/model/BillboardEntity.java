package me.jorgeb.storage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.util.Date;

import static me.jorgeb.storage.Statements.ENTITY_BILLBOARD;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = ENTITY_BILLBOARD)
public class BillboardEntity extends BaseEntity {
    @Column(name = "date", nullable = false)
    @DateTimeFormat
    @NotNull
    private Date date;

    @Column(name = "start_time", nullable = false)
    @NotNull
    private Time startTime;

    @Column(name = "end_time", nullable = false)
    @NotNull
    private Time endTime;

    @Column(name = "movie_id", nullable = false)
    private Long movieId;

    @Transient
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private MovieEntity movie;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Transient
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private RoomEntity room;

}