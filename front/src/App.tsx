import './App.css'

import React from 'react';
import {BrowserRouter as Router, Routes, Route, Navigate} from 'react-router-dom';
import SignInPage from './pages/SignInPage.tsx';
import SignUpPage from './pages/SignUpPage.tsx';
import Layout from './components/Layout';
import EmployeePage from './pages/EmployeePage';
import EmployeeListPage from './pages/EmployeeListPage';
import {getToken} from "./services/token.ts";
import PrivateRoute from "./services/PrivateRoute.tsx";

const App: React.FC = () => {
    const isAuthenticated = !!getToken();
    return (
        <Router>
            <Routes>
                <Route path="/" element={<SignInPage />} />
                <Route path="/signin" element={<SignInPage />} />
                <Route path="/signup" element={<SignUpPage />} />
                 <Route element={<PrivateRoute isAuthenticated={isAuthenticated} />}>
                    <Route path="/employee" element={<Layout />} >
                        <Route index element={<EmployeeListPage />} />
                        <Route path="create" element={<EmployeePage />} />
                        <Route path="update/:id" element={<EmployeePage />} />
                    </Route>
                 </Route>
                <Route path="*" element={<Navigate to={isAuthenticated ? "/employee" : "/"} />} />
            </Routes>
        </Router>
    );
};

export default App;

