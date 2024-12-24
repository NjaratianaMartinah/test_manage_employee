import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import EmployeeForm from "../components/employee/EmployeeForm.tsx";
import {Employee} from "../models/Employee";
import {createEmployee, getEmployeeById, updateEmployee} from "../services/employee.ts";
import {toast, Toaster} from "sonner";

const EmployeePage = () => {
    const navigate = useNavigate();
    const {id} = useParams();
    const isUpdate = !!id;
    const idParam = Number(id);
    const [employee, setEmployee] = useState<Employee | null>(null);
    const [error, setError] = useState<string | null>(null);


    useEffect(() => {
        const fetchEmployee = async () => {
            if (isUpdate) {
                try {
                    const employee = await getEmployeeById(idParam);
                    setEmployee(employee);
                } catch (error) {
                    setError("Error fetching employee.");
                }
            }
        };

        fetchEmployee();
    }, [isUpdate, id]);

    const handleFormSubmit = async (formData: Employee) => {
        try {
            if (isUpdate && id) {
                // Update existing employee
                const updatedEmployee = await updateEmployee(idParam, formData);
                toast.success("employee created");
            } else {
                // Create new employee
                const newEmployee = await createEmployee(formData);
                toast.success("employee updated");
            }
            navigate('/employee');
        } catch (error: any) {
            console.error('Error saving employee:', error.message);
            setError(error.data?.message || error.message);
        }
    };

    return (
        <div className="flex justify-center">
            <div className="flex justify-center w-full">
                {isUpdate && !employee ? (
                    <p>Loading...</p>
                ) : (
                    <>
                        <Toaster position="bottom-right"/>
                        <EmployeeForm
                            onSubmit={handleFormSubmit}
                            initialData={employee || {id: undefined, fullName: "", dateOfBirth: null}}
                            isUpdate={isUpdate}
                            errorMessage={error}
                        />
                    </>
                )}
            </div>
        </div>
    );
};

export default EmployeePage;
