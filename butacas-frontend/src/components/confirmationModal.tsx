
export function ConfirmationModal(props: { message: string, onConfirm: () => void, onCancel: () => void }) {
    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
            <div className="bg-gray-800 p-5 rounded-lg shadow-lg">
                <p>{props.message}</p>
                <div className="flex flex-row justify-end space-x-3 mt-5">
                    <button className="bg-red-500 text-white px-3 py-1 rounded" onClick={props.onCancel}>Cancelar</button>
                    <button className="bg-green-500 text-white px-3 py-1 rounded" onClick={props.onConfirm}>Confirmar</button>
                </div>
            </div>
        </div>
    );
}