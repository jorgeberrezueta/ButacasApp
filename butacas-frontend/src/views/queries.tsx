import { ReactNode, useState } from "react";
import { ExecuteIcon, Spinner } from "../components/icons";
import { Booking } from "../util/entities";
import * as httpService from "../util/httpService";

function QueryItem(props: { title: string, children: ReactNode, execute?: () => void }) {
    return (
        <div className="flex flex-col space-y-5 py-10">
            <div className="flex flex-row space-x-5">
                <button className="hover:text-green-400 transition-colors px-3 py-1 bg-blue-500 rounded" onClick={props.execute}>
                    <ExecuteIcon />
                </button>
                <h2 className="font-bold">{props.title}</h2>
            </div>
            <div>
                {props.children}
            </div>
        </div>
    )
}

function Query2A() {
    const [data, setData] = useState<Booking[] | undefined>(undefined);
    const [fromDate, setFromDate] = useState<string>("");
    const [toDate, setToDate] = useState<string>("");
    const [loading, setLoading] = useState<boolean>(false);

    const onExecute = async () => {
        if (fromDate === "") return alert("Debe ingresar una fecha de inicio.");
        if (toDate === "") return alert("Debe ingresar una fecha de fin.");
        if (fromDate > toDate) return alert("La fecha de inicio no puede ser mayor a la fecha de fin.");
        setLoading(true);
        let res = await httpService.get(`booking/horror/${fromDate}/${toDate}`).catch((e) => {
            console.error("Error al tratar de obtener la información:", e);
        });
        setData(res as Booking[]);
        setLoading(false);
    }

    console.log(data);

    if (!data) {
        return (
            <QueryItem
                title="Reservas de películas de terror por rango de fechas"
                execute={onExecute}
            >
                <div className="flex flex-row space-x-5 text-black pb-5">
                    <input
                        type="date"
                        value={fromDate}
                        onChange={(e) => setFromDate(e.target.value)}
                        className="border border-slate-600 p-2 rounded"
                    />
                    <input
                        type="date"
                        value={toDate}
                        onChange={(e) => setToDate(e.target.value)}
                        className="border border-slate-600 p-2 rounded"
                    />
                </div>
                {
                    loading ?
                        <Spinner /> :
                        <h1>Presione el botón para cargar la información.</h1>
                }
            </QueryItem>
        )
    }

    return (
        <QueryItem title="Reservas de películas de terror por rango de fechas" execute={onExecute}>
            <div className="flex flex-row space-x-5 text-black">
                <input
                    type="date"
                    value={fromDate}
                    onChange={(e) => setFromDate(e.target.value)}
                    className="border border-slate-600 p-2 rounded"
                />
                <input
                    type="date"
                    value={toDate}
                    onChange={(e) => setToDate(e.target.value)}
                    className="border border-slate-600 p-2 rounded"
                />
            </div>
            <table className="border-collapse table-auto w-full text-sm">
                <thead>
                    <tr>
                        <th className="border-b border-slate-600 font-medium p-4 text-slate-200 text-center">ID de Reserva</th>
                        <th className="border-b border-slate-600 font-medium p-4 text-slate-200 text-center">Fecha de Reserva</th>
                        <th className="border-b border-slate-600 font-medium p-4 text-slate-200 text-center">ID de Cliente</th>
                        <th className="border-b border-slate-600 font-medium p-4 text-slate-200 text-center">ID de Butaca</th>
                        <th className="border-b border-slate-600 font-medium p-4 text-slate-200 text-center">ID de Cartelera</th>
                    </tr>
                </thead>
                <tbody className="bg-slate-800">
                    {data.length === 0 ? (
                        <tr>
                            <td className="border-b border-slate-600 p-4 text-slate-200 text-center" colSpan={5}>No hay información disponible</td>
                        </tr>
                    ) : (
                        data.map((booking, index) => (
                            <tr key={`booking-${index}`} className="even:bg-[#232f42]">
                                <td className="border-b border-slate-600 p-4 text-slate-200 text-center">{booking.id}</td>
                                <td className="border-b border-slate-600 p-4 text-slate-200 text-center">{booking.date}</td>
                                <td className="border-b border-slate-600 p-4 text-slate-200 text-center">{booking.customer_id}</td>
                                <td className="border-b border-slate-600 p-4 text-slate-200 text-center">{booking.seat_id}</td>
                                <td className="border-b border-slate-600 p-4 text-slate-200 text-center">{booking.billboard_id}</td>
                            </tr>
                        ))
                    )}
                </tbody>
            </table>
        </QueryItem>
    )
}

type RoomSeatInfo = {
    room_id: number;
    name: string;
    total_seats: number;
    occupied_seats: number;

}

function Query2B() {
    const [data, setData] = useState<RoomSeatInfo[] | undefined>(undefined);

    const onExecute = async () => {
        let res = await httpService.get("room/seat_info").catch((e) => {
            console.error("Error al tratar de obtener la información:", e);
        });
        setData(res as RoomSeatInfo[]);
    }

    if (!data) {
        return (
            <QueryItem
                title="Butacas disponibles y ocupadas por sala de la fecha actual"
                execute={onExecute}
            >
                <h1>Presione el botón para cargar la información.</h1>
            </QueryItem>
        )
    }
    return (
        <QueryItem title="Butacas disponibles y ocupadas por sala de la fecha actual" execute={onExecute}>
            <table className="border-collapse table-auto w-full text-sm">
                <thead>
                    <tr>
                        <th className="border-b border-slate-600 font-medium p-4 text-slate-200 text-center">ID de Sala</th>
                        <th className="border-b border-slate-600 font-medium p-4 text-slate-200 text-center">Nombre de Sala</th>
                        <th className="border-b border-slate-600 font-medium p-4 text-slate-200 text-center">Asientos totales</th>
                        <th className="border-b border-slate-600 font-medium p-4 text-slate-200 text-center">Asientos ocupados</th>
                    </tr>
                </thead>
                <tbody className="bg-slate-800">
                    {data.length === 0 ? (
                        <tr>
                            <td className="border-b border-slate-600 p-4 text-slate-200 text-center" colSpan={4}>No hay información disponible</td>
                        </tr>
                    ) : (
                        data.map((room, index) => (
                            <tr key={`room-${index}`} className="even:bg-[#232f42]">
                                <td className="border-b border-slate-600 p-4 text-slate-200 text-center">{room.room_id}</td>
                                <td className="border-b border-slate-600 p-4 text-slate-200 text-center">{room.name}</td>
                                <td className="border-b border-slate-600 p-4 text-slate-200 text-center">{room.total_seats}</td>
                                <td className="border-b border-slate-600 p-4 text-slate-200 text-center">{room.occupied_seats}</td>
                            </tr>
                        ))
                    )}
                </tbody>
            </table>
        </QueryItem>
    )
}

export function QueriesView() {
    return (
        <div>
            <h1 className="text-xl font-bold pb-10">Queries</h1>
            <div className="flex flex-col divide-y">
                <Query2A />
                <Query2B />
            </div>
        </div>
    )
}