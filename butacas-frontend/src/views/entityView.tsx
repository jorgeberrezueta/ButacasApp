import { Component, ReactNode } from "react";
import Table from "../components/table";

import { BaseEntity, EntityDefinition } from "../util/entities";
import { DataModal } from "../components/dataModal";
import { DeleteIcon, EditIcon, LockIcon, Spinner } from "../components/icons";
import { Field } from "../util/entities";
import * as httpService from "../util/httpService";
import { ConfirmationModal } from "../components/confirmationModal";
import { CreationModal } from "../components/creationModal";
import { EditionModal } from "../components/editionModal";

type EntityViewProps<T> = {
    entityDefinition: EntityDefinition<T>;
}

type EntityViewState<T> = {
    data: T[];
    modal: ReactNode;
    loading: boolean;
}

export default class EntityView<T extends BaseEntity> extends Component<EntityViewProps<T>, EntityViewState<T>> {

    constructor(props: EntityViewProps<T>) {
        super(props);
        this.state = {
            data: [],
            modal: null,
            loading: true
        }
        this.onReferenceClick = this.onReferenceClick.bind(this);
        this.onModalDismiss = this.onModalDismiss.bind(this);
        this.setModal = this.setModal.bind(this);
        this.onDeleteClick = this.onDeleteClick.bind(this);
        this.onEditClick = this.onEditClick.bind(this);
        this.retrieveData = this.retrieveData.bind(this);
        this.onCreateClick = this.onCreateClick.bind(this);
    }

    componentDidMount() {
        this.retrieveData();
    }

    retrieveData() {
        httpService.get(this.props.entityDefinition.endpoint).then((json) => {
            this.setState({
                loading: false,
                data: json as T[]
            })
        }).catch((e) => {
            console.error("Error al tratar de obtener la información:", e);
        });

    }

    setModal = (modal: ReactNode) => {
        this.setState({
            modal: modal
        })
    }

    onReferenceClick = (entity: T, field: Field<T>) => {
        if (!field.reference) return;
        const refDefinition = field.reference();
        const endpoint = refDefinition.endpoint;
        const id = entity[field.name];
        httpService.get(`${endpoint}/${id}`).then((json) => {
            this.setState({
                modal: <DataModal definition={refDefinition} data={json} dismiss={this.onModalDismiss} key={""} setModal={this.setModal} />
            })
        }).catch((e) => {
            console.error("Error al tratar de obtener la información:", e);
        });
    }

    onModalDismiss = () => {
        this.setState({
            modal: null
        })
    }

    onDeleteClick = (entity: T) => {
        this.setModal(
            <ConfirmationModal
                message="¿Está seguro que desea eliminar este elemento?"
                onConfirm={() => {
                    this.onModalDismiss();
                    httpService.del<T>(`${this.props.entityDefinition.endpoint}/${entity.id}`).then(() => {
                        this.retrieveData();
                    });
                }}
                onCancel={() => {
                    this.onModalDismiss();
                }}
            />
        )
    }

    onEditClick = (entity: T) => {
        this.setModal(<EditionModal<T> definition={this.props.entityDefinition} dismiss={this.onModalDismiss} update={this.retrieveData} initialData={entity} />)
    }

    onCreateClick = () => {
        this.setModal(<CreationModal<T> definition={this.props.entityDefinition} dismiss={this.onModalDismiss} create={this.retrieveData} />);
    }

    render() {
        const data = this.state.data;
        const f = this.props.entityDefinition.fields;
        const header = (
            <div className="flex">
                <h1 className="text-xl font-bold pb-10 grow">{this.props.entityDefinition.name}</h1>
                <div>
                    <button
                        className="bg-blue-600 px-4 py-2 rounded font-bold"
                        onClick={this.onCreateClick}
                    >
                        Nuevo
                    </button>
                </div>
            </div>
        )
        if (this.state.loading) return (
            <div className="w-full h-full flex flex-col">
                {header}
                <div className="w-full h-full flex items-center justify-center">
                    <Spinner />
                </div>
            </div>
        );

        if (data.length === 0) return (
            <div className="w-full h-full flex flex-col">
                {header}
                <div className="w-full h-full flex items-center justify-center">
                    <h1 className="text-xl text-slate-200">No hay elementos</h1>
                </div>
                {this.state.modal}
            </div>
        );
        return (
            <div>
                {header}

                <Table<T>
                    fields={f}
                >
                    {
                        data.map((entity: T, i1: number) => (
                            <tr key={`entity-${i1}`} className="even:bg-[#232f42]">
                                {
                                    f.map((field: Field<T>, i2: number) => {
                                        const value = (entity[field.name] as string)?.toString();
                                        if (field.foreign && value) {
                                            return (
                                                <td key={`field-${i2}`} className="border-b border-slate-600 p-4 text-slate-200 text-center">
                                                    <button className="bg-blue-700 px-2 py-1 mono rounded underline" onClick={() => this.onReferenceClick(entity, field)}>ID {value}</button>
                                                </td>
                                            )
                                        } else {
                                            return (
                                                <td key={`field-${i2}`} className="border-b border-slate-600 p-4 text-slate-200 text-center">
                                                    {
                                                        field.type === "boolean" ?
                                                            (entity[field.name] as boolean) ? <span className="bg-green-600 px-3 rounded">Sí</span> : <span className="bg-red-600 px-3 rounded">No</span>
                                                            : value ? <span>{value}</span> : "N/A"
                                                    }
                                                </td>
                                            )
                                        }
                                    })
                                }
                                <td className="border-b dark:border-slate-600 p-4 text-slate-200 text-center">
                                    <div className="flex flex-row items-center justify-center space-x-3">
                                        <button onClick={() => this.onEditClick(entity)}>
                                            <EditIcon />
                                        </button>

                                        {
                                            this.props.entityDefinition.endpoint === "seat" ?
                                                <button onClick={() => {
                                                    this.setModal(
                                                        <ConfirmationModal
                                                            message="¿Está seguro que desea inhabilitar este asiento y cancelar las reservas asociadas?"
                                                            onConfirm={async () => {
                                                                await httpService.post(`seat/disable/${entity.id}`, {}).catch((e) => {
                                                                    console.error("Error al tratar de inhabilitar el asiento:", e)
                                                                });
                                                                this.retrieveData();
                                                                this.onModalDismiss();
                                                            }}
                                                            onCancel={this.onModalDismiss}
                                                        />
                                                    )
                                                }}>
                                                    <LockIcon />
                                                </button>
                                                : null
                                        }

                                        {
                                            this.props.entityDefinition.endpoint === "billboard" ?
                                                <button onClick={() => {
                                                    this.setModal(
                                                        <ConfirmationModal
                                                            message="¿Está seguro que desea cancelar esta cartelera? Se cancelarán todas las reservas asociadas."
                                                            onConfirm={async () => {
                                                                await httpService.post(`billboard/cancel/${entity.id}`, {}).catch((e) => {
                                                                    console.error("Error al tratar de cancelar la cartelera:", e)
                                                                });
                                                                this.retrieveData();
                                                                this.onModalDismiss();
                                                            }}
                                                            onCancel={this.onModalDismiss}
                                                        />
                                                    )
                                                }}>
                                                    <LockIcon />
                                                </button>
                                                : null
                                        }

                                        <button onClick={() => this.onDeleteClick(entity)}>
                                            <DeleteIcon />
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        ))
                    }
                </Table>
                {this.state.modal}
            </div>
        );
    }

}
