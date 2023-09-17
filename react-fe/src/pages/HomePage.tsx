import { useEffect, useState } from "react";
import RegisterForm from "../components/auth/RegisterForm";
import LoginForm from "../components/auth/LoginForm";
import { useNavigate } from "react-router-dom";

const HomePage = () => {
    const [showRegister, setShowRegister] = useState(false);
    const nav = useNavigate();

    useEffect(() => {
        const localAuth = localStorage.getItem("token") || undefined;
        if (localAuth) {
            nav("/dashboard");
        }
    }, []);

    return (
        <div className="grid grid-cols-2 h-[100vh] bg-primary overflow-hidden">
            <div>
                <img src="https://cdn.pixabay.com/photo/2016/11/14/03/05/surgery-1822458_1280.jpg" className="w-full h-full object-cover opacity-50" />
            </div>
            <div className="bg-white h-full overflow-y-auto p-4">
                {showRegister ?
                    <RegisterForm onSwitch={() => setShowRegister(false)} /> :
                    <LoginForm onSwitch={() => setShowRegister(true)} />
                }
            </div>
        </div>
    )
}

export default HomePage;