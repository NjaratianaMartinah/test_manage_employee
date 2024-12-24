import React, { useEffect, useState } from "react";
import { Employee } from "../../models/Employee";
interface EmployeeFormProps {
  onSubmit: (formData: Employee) => void;
  initialData?: Employee;
  isUpdate: boolean;
  errorMessage: string | null;
}

const EmployeeForm = ({ onSubmit, initialData = { id: undefined, fullName: "", dateOfBirth: null }, isUpdate, errorMessage }: EmployeeFormProps) => {
  const [formData, setFormData] = useState<Employee>({
    id: initialData.id,
    fullName: initialData.fullName || "",
    dateOfBirth: isUpdate ? initialData.dateOfBirth : null,
  });

  useEffect(() => {
    setFormData({
      id: initialData.id,
      fullName: initialData.fullName || "",
      dateOfBirth: isUpdate ? initialData.dateOfBirth : null,
    });
  }, [initialData, isUpdate, errorMessage]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const {name, value} = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === "dateOfBirth" ? (value ? new Date(value) : null) : value,
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(formData);
  };
  return (
      <div className="flex justify-center items-center min-h-screen">
        <div className="w-full max-w-lg bg-white shadow-lg rounded-lg p-8">
          <h2 className="text-4xl font-bold text-yellow-600 mb-6 text-center">
            {isUpdate ? "Update Employee" : "Create New Employee"}
          </h2>
          {errorMessage && (
              <div className="mb-4 text-center text-red-600 text-sm font-medium">
                {errorMessage}
              </div>
          )}
          <form className="space-y-6" onSubmit={handleSubmit}>
            <div>
              <label className="block text-sm font-medium text-gray-800 mb-1">
                Username
              </label>
              <input
                  type="text"
                  name="fullName"
                  value={formData.fullName}
                  onChange={handleChange}
                  className="w-full px-5 py-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-yellow-500 focus:border-yellow-500 transition-shadow"
                  placeholder="Enter username"
                  required
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-800 mb-1">
                Date of Birth
              </label>
              <input
                  type="date"
                  name="dateOfBirth"
                  value={
                    formData.dateOfBirth
                        ? new Date(formData.dateOfBirth).toISOString().split("T")[0]
                        : ""
                  }
                  onChange={handleChange}
                  className="w-full px-5 py-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-yellow-500 focus:border-yellow-500 transition-shadow"
                  required={!isUpdate}
              />
            </div>
            <button
                type="submit"
                className="w-full py-3 bg-yellow-300 hover:bg-yellow-600 text-white font-semibold rounded-md shadow-md transition-transform transform hover:scale-105"
            >
              {isUpdate ? "Update Employee" : "Create Employee"}
            </button>
          </form>
        </div>
      </div>
  );
}
export default EmployeeForm;
