import React, {useState} from 'react';
import { useForm, SubmitHandler } from "react-hook-form";
import {User} from "../../models/User.ts";
import {signIn} from "../../services/authentication.ts";
import {useNavigate} from "react-router-dom";


const SignIn: React.FC = () => {
    const navigate = useNavigate();
    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm<User>();

    const [error, setError] = useState<string | null>(null);

    const onSubmit: SubmitHandler<User> = async (data: User) => {

        try {
            await signIn(data);
            setTimeout(() => {
                navigate('/employee');
            }, 2000);
        } catch (error: any) {
            setError(error.message);
        }
    };


    return (
            <div className="min-h-screen flex items-center justify-center p-4">
                <div className="max-w-md w-full bg-white rounded-xl shadow-lg p-8">
                    <h2 className="text-2xl font-bold text-gray-900 mb-6 text-center">
                        Sign In
                    </h2>
                    {error && (
                        <div className="mb-4 text-center text-red-500 text-sm">
                            {error}
                        </div>
                    )}
                    <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                        <div>
                            <label
                                htmlFor="email"
                                className="block text-sm font-medium text-gray-700 mb-1 text-left"
                            >
                                Email
                            </label>
                            <input
                                id="email"
                                type="email"
                                {...register("email", {
                                    required: "Email is required",
                                    pattern: {
                                        value: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
                                        message: "Invalid email address",
                                    },
                                })}
                                className={`w-full px-4 py-2 border rounded-lg outline-none transition-all ${
                                    errors.email
                                        ? "border-red-500 focus:ring-red-500 focus:border-red-500"
                                        : "border-gray-300 focus:ring-yellow-500 focus:border-yellow-500"
                                }`}
                                placeholder="your@email.com"
                            />
                            {errors.email && (
                                <p className="text-red-500 text-sm mt-1">
                                    {errors.email.message}
                                </p>
                            )}
                        </div>
                        <div>
                            <label
                                htmlFor="password"
                                className="block text-sm font-medium text-gray-700 mb-1 text-left"
                            >
                                Password
                            </label>
                            <input
                                id="password"
                                type="password"
                                {...register("password", {
                                    required: "Password is required",
                                })}
                                className={`w-full px-4 py-2 border rounded-lg outline-none transition-all ${
                                    errors.password
                                        ? "border-red-500 focus:ring-red-500 focus:border-red-500"
                                        : "border-gray-300 focus:ring-yellow-500 focus:border-yellow-500"
                                }`}
                                placeholder="••••••••"
                            />
                            {errors.password && (
                                <p className="text-red-500 text-sm mt-1">
                                    {errors.password.message}
                                </p>
                            )}
                        </div>
                        <button
                            type="submit"
                            className="w-full bg-yellow-300 hover:bg-yellow-700 text-white font-medium py-2.5 rounded-lg transition-colors"
                        >
                            Sign In
                        </button>
                    </form>
                    <div className="mt-6 text-center text-sm text-gray-600">
                        Don't have an account?{" "}
                        <a
                            href="signup"
                            className="text-yellow-600 hover:text-yellow-500 font-medium"
                        >
                            Sign up
                        </a>
                    </div>
                </div>
            </div>
    );
};

export default SignIn;
