import { useNavigate } from "react-router-dom";
import EmployeeList from "../components/employee/EmployeeList";

const EmployeeListPage: React.FC = () => {
    const navigate = useNavigate();


    const handleCreateEmployee = () => {
      navigate('create');
    };


    
    return (
      <>
          <div className="flex justify-evenly items-center mb-6">
              <button
                  onClick={handleCreateEmployee}
                  className="bg-blue-500 text-white py-2 px-4 rounded-lg hover:bg-blue-600"
              >
                  + Create New Employee
              </button>
              <h1 className="text-2xl text-center font-bold text-gray-900">List of Employee</h1>
          </div>

          <EmployeeList/>
      </>
    );
};

export default EmployeeListPage;
