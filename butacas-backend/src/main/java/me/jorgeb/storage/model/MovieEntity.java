package me.jorgeb.storage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static me.jorgeb.storage.Statements.ENTITY_MOVIE;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = ENTITY_MOVIE)
public class MovieEntity extends BaseEntity {
    @Column(name = "name", nullable = false)
    @NotNull
    @Size(max = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    @NotNull
    private MovieGenreEnum genre;

    @Column(name = "allowed_age", nullable = false)
    @NotNull
    private short allowedAge;

    @Column(name = "length_minutes", nullable = false)
    @NotNull
    private short lengthMinutes;

}
