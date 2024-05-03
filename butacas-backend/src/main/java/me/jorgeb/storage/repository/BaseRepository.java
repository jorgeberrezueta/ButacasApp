package me.jorgeb.storage.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Interfaz que define los métodos básicos de un repositorio.
 * La clase CrudRepository de Spring Data JPA proporciona métodos para realizar operaciones CRUD en una entidad.
 * Por defecto la clase CrudReposity hereda configuraciones de transaccionalidad y manejo de excepciones.
 *
 * @param <T> Tipo de la entidad.
 * @param <V> Tipo de la llave primaria de la entidad.
 */
@NoRepositoryBean
public interface BaseRepository<T, V> extends CrudRepository<T, V> {

    /**
     * Busca todas las entidades que tengan el campo status en true.
     * El campo status marca una entidad como activa o inactiva/inhabilitada.
     * El cuerpo del método es proporcionado por Spring Data JPA.
     *
     * @return Lista de entidades encontradas.
     */
    List<T> findAllByStatusIsTrue();

    @NotNull
    List<T> findAll();

}
