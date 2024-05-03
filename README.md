# Reserva de butacas de cine
Prueba técnica de desarrollo por Jorge Berrezueta.

## Requisitos
- Java 17
- Gradle 7.2
- NodeJS 14

_________________

## Ejecución del entorno

El entorno de la plataforma está compuesto por un servicio de backend en Java y un la interfaz de usuario (front-end) en React.

El repositorio incluye versiones pre-compiladas de ambos módulos. Para ejecutar todo el entorno (front y back) se debe ejecutar el siguiente comando (Windows):

```batch
start_env.bat
```

### Frontend

Para ingresar a la interfaz de usuario se debe acceder a la siguiente URL:

[http://localhost:3000](http://localhost:3000)

En esta interfaz se muestran los distintos controladores de las entidades y queries adicionales.


### Backend

El backend se ejecuta en el puerto 8080 y se puede acceder con la siguiente URL:

[http://localhost:8080/](http://localhost:8080/)

Esta API REST expone los siguientes endpoints comunes para todas las enitdades:
```
Entidades: billboard, booking, customer, movie, room, seat
```

| Endpoint                    | Descripción           |
|-----------------------------|-----------------------|
| `GET /api/<entidad>`        | Lista de entidades    |
| `GET /api/<entidad/{id}`    | Entidad por ID        |
| `POST /api/<entidad>`       | Crear entidad         |
| `PUT /api/<entidad/{id}`    | Actualizar entidad    |
| `DELETE /api/<entidad/{id}` | Eliminar entidad      |

Además, se exponen los siguientes endpoints adicionales:

| Endpoint                              | Descripción                                           |
|---------------------------------------|-------------------------------------------------------|
| `POST /api/billboard/cancel/{id}`     | Butacas disponibles                                   |
| `GET /api/booking/horror/{from}/{to}` | Reservas de películas de terror en un rango de fechas |
| `GET /api/room/seat_info`             | Información de butacas por sala                       |
| `POST /api/seat/disable/{id}`         | Deshabilitar butaca                                   |

**NOTA:** Las notaciones `{id}`, `{from}` y `{to}` hacen referencia a variables del URL que deben ser reemplazadas por valores reales.
_________________

## Entorno de desarrollo

### Backend
    
Para ejecutar el backend en el entorno de desarrollo se debe ejecutar el siguiente comando:

```batch
cd butacas-backend
gradlew bootRun
```

### Frontend

Para ejecutar el frontend en modo desarrollo se debe ejecutar el siguiente comando:

```batch
cd butacas-frontend
npm install
npm run start
```