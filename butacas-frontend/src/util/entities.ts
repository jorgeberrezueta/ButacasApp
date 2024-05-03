export type EntityType = 'Billboard' | 'Booking' | 'Customer' | 'Movie' | 'Room' | 'Seat';

export type BaseEntity = { id: number, status: boolean, exception?: string, messages?: string[]};

export type Billboard = BaseEntity & {
    date: string;
    start_time: string;
    end_time: string;
    movie_id: number;
    room_id: number;
}

export type Booking = BaseEntity & {
    date: string;
    customer_id: number;
    seat_id: number;
    billboard_id: number;
}

export type Customer = BaseEntity & {
    document_number: string;
    name: string;
    lastname: string;
    age: number;
    email: string;
    phone_number: string;
}

export type Movie = BaseEntity & {
    name: string;
    length_minutes: number;
    genre: string;
    allowed_age: number;
}

export type Room = BaseEntity & {
    name: string;
    number: number;
}

export type Seat = BaseEntity & {
    row_number: number;
    number: number;
    room_id: number;
}

export type Entity = Billboard | Booking | Customer | Movie | Room | Seat;

export type EntityDefinition<T> = { name: string, endpoint: string, fields: Field<T>[] };
export type Field<T> = { display: string, name: keyof T, type: string, values?: string[], foreign?: boolean, reference?: () => EntityDefinition<any> };

export const billboardDefinition: EntityDefinition<Billboard> = {
    name: "Cartelera",
    endpoint: 'billboard',
    fields: [
        { display: "ID", name: 'id', type: 'number' },
        { display: "Fecha", name: 'date', type: 'date' },
        { display: "Fecha de inicio", name: 'start_time', type: 'time' },
        { display: "Fecha de fin", name: 'end_time', type: 'time' },
        { display: "Película", name: 'movie_id', type: 'number', foreign: true, reference: () => movieDefinition },
        { display: "Sala", name: 'room_id', type: 'number', foreign: true, reference: () => roomDefinition },
        { display: "Activo", name: 'status', type: 'boolean' },
    ]
}

export const bookingDefinition: EntityDefinition<Booking> = {
    name: "Reserva",
    endpoint: 'booking',
    fields: [
        { display: "ID", name: 'id', type: 'number' },
        { display: "Fecha", name: 'date', type: 'date' },
        { display: "Cliente", name: 'customer_id', type: 'number', foreign: true, reference: () => customerDefinition},
        { display: "Asiento", name: 'seat_id', type: 'number', foreign: true, reference: () => seatDefinition},
        { display: "Cartelera", name: 'billboard_id', type: 'number', foreign: true, reference: () => billboardDefinition},
        { display: "Activo", name: 'status', type: 'boolean' },
    ]
}

export const customerDefinition: EntityDefinition<Customer> = {
    name: "Cliente",
    endpoint: 'customer',
    fields: [
        { display: "ID", name: 'id', type: 'number' },
        { display: "Número de documento", name: 'document_number', type: 'string' },
        { display: "Nombre", name: 'name', type: 'string' },
        { display: "Apellido", name: 'lastname', type: 'string' },
        { display: "Edad", name: 'age', type: 'number' },
        { display: "Correo electrónico", name: 'email', type: 'string' },
        { display: "Número de teléfono", name: 'phone_number', type: 'string' },
        { display: "Activo", name: 'status', type: 'boolean' },
    ]
}

export const movieDefinition: EntityDefinition<Movie> = {
    name: "Película",
    endpoint: 'movie',
    fields: [
        { display: "ID", name: 'id', type: 'number' },
        { display: "Nombre", name: 'name', type: 'string' },
        { display: "Duración en minutos", name: 'length_minutes', type: 'number' },
        { display: "Género", name: 'genre', type: 'string', values: [ "ACTION", "ADVENTURE", "COMEDY", "DRAMA", "FANTASY", "HORROR", "MUSICALS", "MYSTERY", "ROMANCE", "SCIENCE_FICTION", "SPORTS", "THRILLER", "WESTERN" ] },
        { display: "Edad permitida", name: 'allowed_age', type: 'number' },
        { display: "Activo", name: 'status', type: 'boolean' },
    ]
}

export const roomDefinition: EntityDefinition<Room> = {
    name: "Sala",
    endpoint: 'room',
    fields: [
        { display: "ID", name: 'id', type: 'number' },
        { display: "Nombre", name: 'name', type: 'string' },
        { display: "Capacidad", name: 'number', type: 'number' },
        { display: "Activo", name: 'status', type: 'boolean' },
    ]
} 

export const seatDefinition: EntityDefinition<Seat> = {
    name: "Asiento",
    endpoint: 'seat',
    fields: [
        { display: "ID", name: 'id', type: 'number' },
        { display: "Fila", name: 'row_number', type: 'number' },
        { display: "Número", name: 'number', type: 'number' },
        { display: "Sala", name: 'room_id', type: 'number', foreign: true, reference: () => roomDefinition},
        { display: "Activo", name: 'status', type: 'boolean' },
    ]
} 