import styles from "@/components/LoginPage/LoginPage.module.css"
import { useState } from 'react';
import { Link } from "react-router";
import BenefitsList from "@/components/BenefitsList/BenefitsList.jsx";

const LoginPage = () => {
    const [errorPrompt, setErrorPrompt] = useState("");

    const [credentials, setCredentials] = useState({
        email: "",
        password: ""
    });

    const updateCredential = (e, credentialName) => {
        let newCredentials = {...credentials,
            [credentialName]: e.target.value
        }
        setCredentials(newCredentials);
    }

    const validateCredentials = () => {
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).+$/;
        if (emailRegex.test(credentials["email"]) == false) {
            setErrorPrompt("Provided credentials are invalid.");
            return false;
        } else if (passwordRegex.test(credentials["password"]) == false || credentials["password"].length < 8) {
            setErrorPrompt("Provided credentials are invalid.");
            return false;
        } else {
            setErrorPrompt("");
        }
        return true;
    }

    const login = () => {
        validateCredentials();
        var url = `${window._env_.BACKEND_API_URL}${"/api/auth/login"}`;
    }

    return (
        <div className={styles.page}>
            <div className={styles.login_box}>
                <h1 className={styles.login_h1}>
                    Login
                </h1>
                <label className={styles.login_label}>
                    Email
                </label>
                <input
                    className={styles.login_input}
                    autoComplete="true"
                    value={credentials["email"]}
                    onChange={(e) => updateCredential(e, "email")}
                    placeholder="example@mail.com"
                />
                <label className={styles.login_label}>
                    Password
                </label>
                <input
                    className={styles.login_input}
                    type="password"
                    value={credentials["password"]}
                    onChange={(e) => updateCredential(e, "password")}
                    placeholder="your password"
                />
                <button
                    className={styles.login_button}
                    onClick={login}
                >
                    Login
                </button>
                <p className={styles.error_label}>
                    {errorPrompt}
                </p>
            </div>
            <div className={styles.register_box}>
                <p className={styles.login_p}>
                    Don&apos;t Have an Account yet?
                </p>
                <Link to="/register">
                    <button className={styles.register_button}>
                        Sign Up
                    </button>
                </Link>
                <BenefitsList/>
            </div>
        </div>
    );
}

export default LoginPage;