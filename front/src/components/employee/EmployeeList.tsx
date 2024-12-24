import { Employee } from "../../models/Employee.ts";
import { createColumnHelper , useReactTable,getCoreRowModel,flexRender,ColumnDef, } from "@tanstack/react-table";
import React, {useEffect, useState} from 'react';
import { useNavigate} from "react-router-dom";
import {deleteEmployeeById, getEmployees} from "../../services/employee.ts";
import {toast, Toaster} from "sonner";
import EmployeeDeleteModal from "./EmployeeDeleteModal.tsx";

const EmployeeList: React.FC = () => {
    const navigate = useNavigate();
    const [employees, setEmployees] =  useState<Employee[]>([]);
    const [employeeToDelete, setEmployeeToDelete] = useState<Employee | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);

    // Define columns
    const createColumn = createColumnHelper<Employee>();


    // Create column helper
    const columns: ColumnDef<Employee,any>[] = [
        createColumn.accessor('fullName', {
            header: 'User Name',
            cell: (info) => info.getValue(),
        }),
        createColumn.accessor('dateOfBirth', {
            header: 'Date of Birth',
            cell: (info) => new Date(info.getValue()).toLocaleDateString(),
        }),
        createColumn.display({
            id: 'actions',
            header: 'Actions',
            cell: ({ row }) => (
                <div className="flex space-x-2">
                    <button
                        className="bg-gray-100 text-blue px-2 py-1 rounded hover:bg-yellow-300 hover:text-white"
                        onClick={() => handleEdit(row.original)}
                    >
                        Edit
                    </button>
                    <button
                        className="bg-gray-100 text-red px-2 py-1 rounded hover:bg-red-600 hover:text-white"
                        onClick={() => handleDelete(row.original)}
                    >
                        Delete
                    </button>
                </div>
            ),
        }),
    ];

    useEffect(() => {
        const fetchEmployees = async () => {
            try {
                const data = await getEmployees();
                setEmployees(data);
            } catch (error: any) {
                    setError(error.message);
            }
        };

        fetchEmployees();
    }, []);

    const handleEdit = (employee: Employee) => {
        navigate(`/employee/update/${employee.id}`);
    };

    const handleDelete = (employee: Employee) => {
        setEmployeeToDelete(employee);
        setIsModalOpen(true);
    };

    const handleConfirmDelete = async () => {
        if (employeeToDelete) {
            try {
                await deleteEmployeeById(employeeToDelete.id!);
                toast.success(`Employee deleted successfully.`);
                setEmployees(employees.filter(e => e.id !== employeeToDelete.id));
            } catch (error: any) {
                console.error(`Error deleting employee: ${error.message}`);
            }
        }
        setIsModalOpen(false);
        setEmployeeToDelete(null);
    };

    const table = useReactTable({
        data : employees,
        columns: columns,
        getCoreRowModel: getCoreRowModel(),
      });


    return (
            <div className="overflow-hidden rounded-lg border border-gray-200 shadow-md m-5">
                <div className="relative overflow-x-auto">
                    {error && (
                        <p className="text-red-500 mb-4"> {error}</p>
                    )}
                    {employees.length === 0 && !error ? (
                        <p>No employees found.</p>
                    ) : (
                        <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                            <thead
                                className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                            {table.getHeaderGroups().map((headerGroup) => (
                                <tr key={headerGroup.id}>
                                    {headerGroup.headers.map((header) => (
                                        <th key={header.id} className="px-6 py-3">
                                            {header.isPlaceholder
                                                ? null
                                                : flexRender(
                                                    header.column.columnDef.header,
                                                    header.getContext()
                                                )}
                                        </th>
                                    ))}
                                </tr>
                            ))}
                            </thead>
                            <tbody>
                            {table.getRowModel().rows.map((row) => (
                                <tr key={row.id} className="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
                                    {row.getVisibleCells().map((cell) => (
                                        <td key={cell.id} className="px-6 py-4">
                                            {flexRender(cell.column.columnDef.cell, cell.getContext())}
                                        </td>
                                    ))}
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    )}
                </div>
                <EmployeeDeleteModal
                    employee={employeeToDelete!}
                    isOpen={isModalOpen}
                    onClose={() => setIsModalOpen(false)}
                    onConfirm={handleConfirmDelete}
                />
                <Toaster position="bottom-right"/>
            </div>
        );
    };

export default EmployeeList;
