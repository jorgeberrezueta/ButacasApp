package me.jorgeb.controller;

import jakarta.validation.Valid;
import me.jorgeb.exception.EntityNotFoundException;
import me.jorgeb.exception.InvalidInputException;
import me.jorgeb.storage.model.BaseEntity;
import me.jorgeb.storage.repository.BaseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseController<R extends BaseRepository<T, Long>, T extends BaseEntity>  {

    public BaseController() {

    }

    public abstract R getRepository();

    @GetMapping
    public List<T> get() {
        return getRepository().findAll();
    }

    @GetMapping("/{id}")
    public T get(@PathVariable Long id) throws InvalidInputException, EntityNotFoundException {
        T entity = this.getRepository().findById(id).orElse(null);
        if (entity == null) {
            throw new EntityNotFoundException("La entidad solictada no existe.");
        }
        return entity;
    }

    protected void verifyClashingEntities(T entity) throws InvalidInputException {}

    @PostMapping
    public T post(@RequestBody @Valid T entity) throws InvalidInputException {
        if (entity.getId() != null) {
            throw new InvalidInputException("El ID debe no ser proporcionado.");
        }
        verifyClashingEntities(entity);
        return getRepository().save(entity);
    }

    /**
     * Realiza la validación del request body de una solicitud. Devuelve la entidad en caso de existir.
     *
     * @param entity Obtejo de la entidad a validar (input del usuario).
     * @return Entidad existente en la base de datos.
     * @throws InvalidInputException Si la entidad no existe.
     */
    protected T validateAndFindFromRequest(T entity) throws InvalidInputException {
        if (entity.getId() == null) {
            throw new IllegalArgumentException("El ID debe ser proporcionado.");
        }
        return getRepository().findById(entity.getId()).orElseThrow(
                () -> new InvalidInputException("La entidad no existe.")
        );
    }

    @PutMapping
    public T put(@RequestBody @Valid T requestEntity) throws InvalidInputException {
        T existing = validateAndFindFromRequest(requestEntity);
        requestEntity.setId(existing.getId());
        return getRepository().save(requestEntity);
    }

    @DeleteMapping("/{id}")
    public T delete(@PathVariable Long id) throws InvalidInputException, EntityNotFoundException {
        T entity = this.getRepository().findById(id).orElse(null);
        if (entity == null) {
            throw new EntityNotFoundException("La entidad solictada no existe.");
        }
        getRepository().delete(entity);
        return entity;
    }

}
