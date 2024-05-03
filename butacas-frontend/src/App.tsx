import React, { ReactElement } from 'react';
import './App.css';
import EntityView from './views/entityView';
import { 
    Billboard, 
    Booking, 
    Customer,
    Movie,
    Room,
    Seat,
    billboardDefinition,
    bookingDefinition, 
    customerDefinition,
    movieDefinition,
    roomDefinition,
    seatDefinition
} from './util/entities';
import { QueriesView } from './views/queries';

type View = {
    name: string;
    component: () => ReactElement;

}

const views: View[] = [
    { name: 'Carteleras', component: () => <EntityView<Billboard> entityDefinition={billboardDefinition} key={"billboard"} /> },
    { name: 'Reservas', component: () => <EntityView<Booking> entityDefinition={bookingDefinition} key={"booking"} /> },
    { name: 'Clientes', component: () => <EntityView<Customer> entityDefinition={customerDefinition} key={"customer"} /> },
    { name: 'Películas', component: () => <EntityView<Movie> entityDefinition={movieDefinition} key={"movie"} /> },
    { name: 'Salas', component: () => <EntityView<Room> entityDefinition={roomDefinition} key={"room"} /> },
    { name: 'Asientos', component: () => <EntityView<Seat> entityDefinition={seatDefinition} key={"seat"} /> },
]

const actions: View[] = [
    { name: 'Queries', component: () => <QueriesView/> },
]

type SidebarProps = {
    onSelect: (view: View) => void;
}

function Sidebar({ onSelect }: SidebarProps) {
    return (
        <div className="h-full bg-gray-900 text-white w-48">
            <h1 className="p-5">Butacas</h1>

            <div className="pt-5">
                <h1 className="uppercase text-gray-400 px-5 py-4">Datos</h1>
                <ul>
                    {views.map((view, index) => (
                        <li key={`view-${index}`} className="w-full bg-gray-800 py-4 px-10 hover:bg-gray-700 cursor-pointer" onClick={() => onSelect(view)}>
                            {view.name}
                        </li>
                    ))}
                </ul>
                <h1 className="uppercase text-gray-400 px-5 py-4">Acciones</h1>
                <ul>
                    {actions.map((view, index) => (
                        <li key={`view-${index}`} className="w-full bg-gray-800 py-4 px-10 hover:bg-gray-700 cursor-pointer" onClick={() => onSelect(view)}>
                            {view.name}
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
}

type MainContentPaneProps = {
    children: ReactElement;
}

function MainContentPane(props: MainContentPaneProps) {
    return (
        <div id="main-content" className="grow flex items-center justify-center">
            <div className="container bg-gradient-to-br from-white/10 to-white/15 p-10 min-h-[50%] max-h-[90%] shadow-xl overflow-x-hidden overflow-scroll">
                { props.children }
            </div>
        </div>
    );
}

function App() {
    const [view, setView] = React.useState(views[0]);
    
    const selected = view.component();

    return (
        <div className="h-full bg-[#101a30] text-white overflow-hidden">
            <div className="h-full flex flex-row">
                <Sidebar onSelect={setView}/>
                <MainContentPane>
                    { selected }
                </MainContentPane>
            </div>
        </div>
    );
}

export default App;
