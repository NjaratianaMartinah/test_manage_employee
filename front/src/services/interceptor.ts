import axios from "axios";
import {getToken} from "./token.ts";

const excludedUrls = ["/signin", "/signup"];

const api = axios.create({
    baseURL: "http://localhost:8080/api",
    headers: {
        "Content-Type": "application/json",
    },
});

api.interceptors.request.use(
    (config) => {
        if (excludedUrls.some((url) => config.url === url)) {
            return config;
        }

        const token = getToken();

        if (token) {
            console.log(token);
            config.headers["Authorization"] = `Bearer ${token}`;
        }

        console.log(config);
        return config;
    },

    (error) => {
        return Promise.reject(error);
    }
);

api.interceptors.response.use(
    (response) => response,
    (error) => {
        // Handle unauthorized errors globally
        if (error.response?.status === 401) {
            console.error("Unauthorized: Redirecting to login...");
            window.location.href = "/";
        }

        return Promise.reject(error);
    }
);

export default api;


