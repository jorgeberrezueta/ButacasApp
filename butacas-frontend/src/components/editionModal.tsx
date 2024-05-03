import { Component } from "react";
import { BaseEntity, EntityDefinition, Field } from "../util/entities";
import * as httpService from "../util/httpService";

type EditionModalProps<T> = {
    initialData: T;
    definition: EntityDefinition<T>;
    dismiss: () => void;
    update: (data: T) => void;
}

type EditionModalState<T> = {
    data: T;
    references: { [x in keyof T]?: BaseEntity[] };
}

const capitalize = (s: string) => {
    return s.charAt(0).toUpperCase() + s.slice(1).toLowerCase().replace(/_/g, " ");
}

export class EditionModal<T extends BaseEntity> extends Component<EditionModalProps<T>, EditionModalState<T>> {

    constructor(props: EditionModalProps<T>) {
        super(props);
        this.state = {
            data: props.initialData,
            references: {}
        }
        this.onFieldChange = this.onFieldChange.bind(this);
    }

    async componentDidMount() {
        const references: any = {};
        for (const field of this.props.definition.fields) {
            if (field.foreign) {
                if (field.reference === undefined) {
                    continue;
                }
                const reference = field.reference();
                const endpoint = reference.endpoint;
                const options = await httpService.get(endpoint).catch((e) => {
                    console.error("Error al tratar de obtener la información:", e);
                });
                references[field.name] = options;
            }
        }
        this.setState({
            references: references
        });
    }

    onFieldChange = (field: Field<T>, value: string | boolean | number) => {
        this.setState({
            data: {
                ...this.state.data,
                [field.name]: value
            }
        });
    }

    update = async () => {
        const res = await httpService.put<T>(this.props.definition.endpoint, this.state.data);
        if (res.exception) {
            alert(res.messages?.join("\n"));
            return;
        }
        this.props.update(res as T);
        this.props.dismiss();
    }

    getFieldValue = (field: Field<T>): string => {
        if (this.state.data[field.name] === undefined) {
            return "";
        } else if (field.type === "boolean") {
            return this.state.data[field.name] ? "true" : "false";
        }
        return this.state.data[field.name] as string;
    }

    renderInput = (field: Field<T>) => {
        if (field.foreign) {

            if (field.reference === undefined) {
                return (
                    <input type="number" className="bg-slate-700 text-slate-200 p-2 rounded" value={this.getFieldValue(field)} placeholder={"0"} onChange={(e) => this.onFieldChange(field, e.target.value)} />
                );
            }
            const reference = field.reference();
            const options = this.state.references[field.name];
            if (options) {
                let columns = reference.fields.filter((e, i) => i < 4).map((field) => field.display).join(" - ");
                return (
                    <select className="bg-slate-700 text-slate-200 p-2 rounded" value={this.getFieldValue(field)} required onChange={(e) => this.onFieldChange(field, e.target.value)}>
                        <option disabled selected>{columns}</option>
                        {options.map((option: any, index: number) => {
                            let display = reference.fields.filter((e, i) => i < 4).map((field) => option[field.name]).join(" - ");
                            return <option key={`option-${index}`} value={option.id}>{display}</option>
                        })}
                    </select>
                );
            }
            return (
                <input type="number" className="bg-slate-700 text-slate-200 p-2 rounded" value={this.getFieldValue(field)} placeholder={"0"} onChange={(e) => this.onFieldChange(field, e.target.value)} />
            );
        } else {
            let input = <input type="text" className="bg-slate-700 text-slate-200 p-2 rounded" value={this.getFieldValue(field)} required onChange={(e) => this.onFieldChange(field, e.target.value)} />;
            if (field.type === "number") {
                input = <input type="number" className="bg-slate-700 text-slate-200 p-2 rounded" value={this.getFieldValue(field)} placeholder={"0"} required onChange={(e) => this.onFieldChange(field, e.target.value)} />
            } else if (field.type === "boolean") {
                input = <input type="checkbox" className="bg-slate-700 text-slate-200 p-2 rounded" checked={this.getFieldValue(field) === "true"} required onChange={(e) => this.onFieldChange(field, e.target.checked)} />
            } else if (field.type === "date") {
                input = <input type="date" className="bg-slate-700 text-slate-200 p-2 rounded" value={this.getFieldValue(field)} required onChange={(e) => this.onFieldChange(field, e.target.value)} />
            } else if (field.type === "time") {
                input = <input type="time" className="bg-slate-700 text-slate-200 p-2 rounded" step="2" value={this.getFieldValue(field)} required onChange={(e) => this.onFieldChange(field, e.target.value)} />
            } else if (field.values) {
                input = (
                    <select className="bg-slate-700 text-slate-200 p-2 rounded" value={this.getFieldValue(field)} required onChange={(e) => this.onFieldChange(field, e.target.value)}>
                        <option disabled selected>Seleccionar...</option>
                        {field.values.map((value, index) => (
                            <option key={`option-${index}`} value={value}>{capitalize(value)}</option>
                        ))}
                    </select>
                );
            }
            return input;
        }
    }

    render() {
        const props = this.props;
        return (
            <div className="fixed h-[100vh] w-[100vw] backdrop-blur bg-black/60 z-30 top-0 left-0 flex justify-center items-center transition-all">
                <div className="bg-slate-800 rounded-3xl p-5 text-center w-[750px] flex flex-col gap-10">
                    <div className='text-2xl font-bold'>
                        <h1>Editar {props.definition.name}</h1>
                    </div>
                    <div className="text-lg">
                        <table className="border-collapse table-auto w-full text-sm">
                            <tbody className="bg-slate-800">
                                {props.definition.fields
                                    .filter((field) => !["id", "status"].includes(field.name?.toString()))
                                    .map((field, index) => {
                                        if (field.foreign) {
                                            return (
                                                <tr key={`field-${index}`}>
                                                    <td className="border-b border-slate-600 px-5 py-4 text-slate-200 text-left font-bold">
                                                        <div className="flex flex-row items-center">
                                                            <span className="grow">{field.display}</span>
                                                            <div className="text-xs">REF</div>
                                                        </div>
                                                    </td>
                                                    <td className="border-b border-slate-600 px-5 py-4 text-slate-200 text-right">
                                                        {this.renderInput(field)}
                                                    </td>
                                                </tr>
                                            );
                                        } else {
                                            let input = this.renderInput(field);
                                            return (
                                                <tr key={`field-${index}`}>
                                                    <td className="border-b border-slate-600 p-5 text-slate-200 text-left font-bold">{field.display}</td>
                                                    <td className="border-b border-slate-600 p-5 text-slate-200 text-right">
                                                        <div className="h-full flex justify-end">
                                                            {input}
                                                        </div>
                                                    </td>
                                                </tr>
                                            );
                                        }

                                    })}
                            </tbody>
                        </table>
                    </div>
                    <div className="space-x-5">
                        <button
                            onClick={props.dismiss}
                            className="bg-red-500 px-2 py-1 mono rounded w-24"
                        >
                            Cancelar
                        </button>
                        <button
                            onClick={this.update}
                            className="bg-blue-500 px-2 py-1 mono rounded w-24"
                        >
                            Actualizar
                        </button>
                    </div>
                </div>
            </div>
        );
    }

}
