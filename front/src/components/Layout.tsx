import React from 'react';
import { Outlet, useNavigate } from 'react-router-dom';
import {removeToken} from "../services/token.ts";

const Layout: React.FC = () => {

    const navigate = useNavigate();
    const handleLogout = () => {
        removeToken();
        console.log("User logged out.");
        navigate("/");
    };

    return (
        <div className="flex flex-col min-h-screen">
            <nav className="bg-yellow-400 text-white px-4 py-3">
                        <div className="container mx-auto flex justify-between items-center">
                            <h1 className="text-lg font-bold">MyApp</h1>
                            <ul className="flex space-x-4">
                                <li>
                                    <button
                                        onClick={handleLogout}
                                        className="bg-red-400 text-white py-2 px-4 rounded-lg"
                                    >
                                        Logout
                                    </button>
                                </li>
                            </ul>
                        </div>
                    </nav>
            <main className="container mx-auto px-6 py-12">
              <Outlet/>
            </main>
        </div>
    );
};

export default Layout;
