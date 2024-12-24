import {Employee} from "../../models/Employee.ts";

interface DeleteModalProps {
    employee: Employee,
    isOpen: boolean;
    onClose: () => void;
    onConfirm: () => void;
}

const EmployeeDeleteModal: React.FC<DeleteModalProps> = ({ employee, isOpen, onClose, onConfirm }) => {
    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
            <div className="bg-white rounded-lg shadow-lg p-6 w-80">
                <h2 className="text-xl font-semibold text-gray-800 mb-4">Confirm deletion</h2>
                <p className="text-gray-600 mb-6">Are you sure you want to delete {employee.fullName}? This action is irreversible.</p>
                <div className="flex justify-end space-x-3">
                    <button
                        onClick={onClose}
                        className="px-4 py-2 bg-gray-200 text-gray-700 rounded hover:bg-gray-300"
                    >
                        Cancel
                    </button>
                    <button
                        onClick={onConfirm}
                        className="px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700"
                    >
                        Delete
                    </button>
                </div>
            </div>
        </div>
    );
};

export default EmployeeDeleteModal;
