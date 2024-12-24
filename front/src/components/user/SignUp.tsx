import React, {useState} from 'react';
import {SubmitHandler, useForm} from "react-hook-form";
import { User } from '../../models/User';
import { signUp } from '../../services/authentication';

const SignUp: React.FC = () => {
  const {
    register: formRegister,
    handleSubmit,
    formState: { errors },
  } = useForm<User>();

  const [error, setError] = useState<string | null>(null);

  const onSubmit: SubmitHandler<User> = async (data: User) => {
    console.log("Form submitted:", data);

    try {
      const result = await signUp(data);
      console.log('Registration successful:', result);

      window.location.href = "/";
    } catch (error: any) {
      console.error("Registration failed:", error.message);
      setError(error.message);
    }
  };

  return (
      <div className="min-h-screen flex items-center justify-center p-4">
        <div className="max-w-md w-full bg-white rounded-xl shadow-lg p-8">
          <h2 className="text-2xl font-bold text-gray-900 mb-6 text-center">
            Sign Up
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
                  {...formRegister("email", {
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
                  <p className="text-red-500 text-sm mt-1">{errors.email.message}</p>
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
                  {...formRegister("password", {
                    required: "Password is required",
                    minLength: {
                      value: 8,
                      message: "Password must be at least 8 characters long",
                    },
                    pattern: {
                      value:
                          /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/,
                      message:
                          "The password must include at least one upper-case letter, one lower-case letter, one number and one special character.",
                    },
                  })}
                  className={`w-full px-4 py-2 border rounded-lg outline-none transition-all ${
                      errors.password
                          ? "border-red-500 focus:ring-red-500 focus:border-red-500"
                          : "border-gray-300 focus:ring-yellow-500 focus:border-yellow-500"
                  }`}
                  placeholder="••••••••"
              />
              {errors.password && (
                  <p className="text-red-500 text-sm mt-1">{errors.password.message}</p>
              )}
            </div>

            <button
                type="submit"
                className="w-full bg-yellow-300 hover:bg-yellow-700 text-white font-medium py-2.5 rounded-lg transition-colors"
            >
              Sign Up
            </button>
          </form>
          <div className="mt-6 text-center text-sm text-gray-600">
            Already have an account?{" "}
            <a
                href="/signin"
                className="text-yellow-600 hover:text-yellow-500 font-medium"
            >
              Log in
            </a>
          </div>
        </div>
      </div>
  );
};

export default SignUp;
