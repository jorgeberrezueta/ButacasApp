package me.jorgeb.storage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static me.jorgeb.storage.Statements.ENTITY_CUSTOMER;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = ENTITY_CUSTOMER)
public class CustomerEntity extends BaseEntity {
    @Column(name = "document_number", unique = true, nullable = false)
    @NotNull
    @Size(min = 10, max = 20, message = "El número de documento debe tener entre 10 y 20 caracteres")
    private String documentNumber;

    @Column(name = "name", nullable = false)
    @NotNull
    @Size(max = 30)
    private String name;

    @Column(name = "lastname", nullable = false)
    @NotNull
    @Size(max = 30)
    private String lastname;

    @Column(name = "age", nullable = false)
    @NotNull
    private Short age;

    @Column(name = "phone_number")
    @Size(max = 20, message = "El número de teléfono no puede tener más de 20 caracteres")
    private String phoneNumber;

    @Column(name = "email")
    @Email(message = "El correo electrónico no es válido")
    @Size(max = 100, message = "El correo electrónico no puede tener más de 100 caracteres")
    private String email;

}
