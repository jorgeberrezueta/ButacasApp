import { Component } from "react";
import { Field } from "../util/entities";


type TableProps<T> = {
    fields: Field<T>[];
    children?: React.ReactNode;
}

export default class Table<T> extends Component<TableProps<T>> {
    render() {
        return (
            <table className="border-collapse table-auto w-full text-sm">
                <thead>
                    <tr>
                        {this.props.fields.map((field, index) => (
                            <th key={`header-${index}`} className="border-b border-slate-600 font-medium p-4 text-slate-200 text-center">{field.display}</th>
                        ))}
                        <th className="border-b border-slate-600 font-medium p-4 text-slate-200 text-center">Acciones</th>
                    </tr>
                </thead>
                <tbody className="bg-slate-800">
                    {this.props.children}
                </tbody>
            </table>
        );
    }

}