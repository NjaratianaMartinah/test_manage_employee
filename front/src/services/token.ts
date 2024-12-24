

const setToken = (token: string) => {
    localStorage.setItem("token", token);
};

const getToken = (): string | null => {
    return localStorage.getItem("token");
};

const removeToken = () => {
    localStorage.removeItem("token");
};

export {getToken, setToken, removeToken };
