import { EntityDefinition, Field } from "../util/entities";
import { ReactNode } from 'react';
import * as httpService from "../util/httpService";

type DataModalProps = {
    definition: EntityDefinition<any>;
    data: any;
    dismiss: () => void;
    setModal: (node: ReactNode) => void;
}

export function DataModal(props: DataModalProps) {

    const onReferenceClick = (field: Field<any>) => {
        if (!field.reference) return;
        const refDefinition = field.reference();
        const endpoint = refDefinition.endpoint;
        const id = props.data[field.name];
        httpService.get(`${endpoint}/${id}`).then((json) => {
            props.setModal(<DataModal definition={refDefinition} data={json} dismiss={props.dismiss} key={""} setModal={props.setModal} />)
        }).catch((e) => {
            console.error("Error al tratar de obtener la información:", e);
        });
    }

    return (
        <div className="fixed h-[100vh] w-[100vw] backdrop-blur bg-black/60 z-30 top-0 left-0 flex justify-center items-center transition-all">
            <div className="bg-slate-800 rounded-3xl p-5 text-center w-[450px] flex flex-col gap-10">
                <div className='text-2xl font-bold'>
                    <h1>{props.definition.name}</h1>
                </div>
                <div className="text-lg">
                    <table className="border-collapse table-auto w-full text-sm">
                        <tbody className="bg-slate-800">
                            {props.definition.fields.map((field, index) => {
                                if (field.foreign) {
                                    return (
                                        <tr key={`field-${index}`}>
                                            <td className="border-b border-slate-600 px-5 py-4 text-slate-200 text-left font-bold">{field.display}</td>
                                            <td className="border-b border-slate-600 px-5 py-4 text-slate-200 text-right">
                                                <button onClick={() => onReferenceClick(field)} className="bg-blue-700 px-2 py-1 mono rounded">
                                                    ID {props.data[field.name]?.toString()}
                                                </button>
                                            </td>
                                        </tr>
                                    );
                                } else {
                                    return (
                                        <tr key={`field-${index}`}>
                                            <td className="border-b border-slate-600 p-5 text-slate-200 text-left font-bold">{field.display}</td>
                                            <td className="border-b border-slate-600 p-5 text-slate-200 text-right">{props.data[field.name]?.toString()}</td>
                                        </tr>
                                    );
                                }

                            })}
                        </tbody>
                    </table>
                </div>
                <div>
                    <button
                        className="bg-slate-700 w-full py-1 rounded-full hover:bg-slate-600 transition-color max-w-96"
                        onClick={props.dismiss}
                    >
                        Cerrar
                    </button>
                </div>
            </div>
        </div>
    )
}