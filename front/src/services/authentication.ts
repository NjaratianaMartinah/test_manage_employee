import {User} from "../models/User.ts";
import {Token} from "../models/Token.ts";
import api from "./interceptor.ts";
import {setToken} from "./token.ts";


const signIn = async (data: User): Promise<Token> => {
    try {
        const response = await api.post<any>(`/users/login`, data, {});

        if (response.status === 200 && response.data.data.token) {
            setToken(response.data.data.token);
            return response.data.data;
        }

        throw new Error('SignIn failed. Please try again');
    } catch (error : any) {
        console.log(error.message);
        throw new Error(error.response?.data?.message || "SignIn failed. Please try again");
    }
};

const signUp = async (data: User) => {
    try {
        const response = await api.post(`users/register`, data);

        if (response.status !== 201) {
            throw new Error('Registration failed');
        }
        return response.data;
    } catch (error: any) {
        throw new Error(error.response?.data?.message || error.message);
    }
};
export { signUp, signIn };
