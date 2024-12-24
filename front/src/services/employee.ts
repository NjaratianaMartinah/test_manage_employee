import {Employee} from "../models/Employee.ts";
import api from "./interceptor.ts";

const createEmployee = async (employee: Employee): Promise<Employee> => {
    try {
        const response = await api.post<Employee>(`/employees`, employee);
        if (response.status !== 201) {
            throw new Error('Failed to create employee');
        }
        return response.data;
    } catch (error: any) {
        console.error("Failed to create employee:", error.message);
        throw new Error(error.response?.data?.message || "Error creating employee");
    }
};

const getEmployees = async (): Promise<Employee[]> => {
    try {
        const response = await api.get<Employee[]>(`/employees`);
        if (response.status !== 200) {
            throw new Error('Failed to get employee');
        }
        return response.data.data.content;
    } catch (error: any) {
        console.error("Failed to fetch employees:", error.message);
        throw new Error(error.response?.data?.message || "Error fetching employees");
    }
};

const getEmployeeById = async (id: number): Promise<Employee> => {
    try {
        const response = await api.get<Employee>(`/employees/${id}`);
        if (response.status !== 200) {
            throw new Error('Failed to get employee');
        }
        return response.data.data;
    } catch (error: any) {
        console.error("Failed to fetch employee:", error.message);
        throw new Error(error.response?.data?.message || "Error fetching employee");
    }
};

const updateEmployee = async (id: number, employee: Partial<Employee>): Promise<Employee> => {
    try {
        const response = await api.put<Employee>(`/employees/${id}`, employee);
        if (response.status !== 200) {
            throw new Error('Failed to create employee');
        }
        return response.data;
    } catch (error: any) {
        console.error("Failed to update employee:", error.message);
        throw new Error(error.response?.data?.message || "Error updating employee");
    }
};


const deleteEmployeeById = async (id: number): Promise<void> => {
    try {
        const response = await api.delete(`/employees/${id}`);
        if (response.status === 200 || response.status === 204) {
            console.log(`Employee with ID ${id} deleted successfully.`);
        } else {
            throw new Error('Failed to delete employee');
        }
    } catch (error: any) {
        console.error(`Failed to delete employee with ID ${id}:`, error.message);
        throw new Error(error.response?.data?.message || "Error deleting employee");
    }
};

export { getEmployees , getEmployeeById, createEmployee, updateEmployee, deleteEmployeeById}
