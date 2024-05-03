package me.jorgeb.storage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

import static me.jorgeb.storage.Statements.ENTITY_BOOKING;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = ENTITY_BOOKING)
public class BookingEntity extends BaseEntity {

    @Column(name = "date", nullable = false)
    @NotNull
    private Date date;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Transient
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @Column(name = "seat_id", nullable = false)
    private Long seatId;

    @Transient
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "seat_id", referencedColumnName = "id")
    private SeatEntity seat;

    @Column(name = "billboard_id", nullable = false)
    private Long billboardId;

    @Transient
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "billboard_id", referencedColumnName = "id")
    private BillboardEntity billboard;

}