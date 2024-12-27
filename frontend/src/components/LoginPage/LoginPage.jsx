import styles from "@/components/LoginPage/LoginPage.module.css"
import ListAltIcon from '@mui/icons-material/ListAlt';
import KeyIcon from '@mui/icons-material/Key';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import { Stack, Typography } from "@mui/material";
import PropTypes from "prop-types";
import { useState } from 'react';

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
        } else if (passwordRegex.test(credentials["password"]) == false) {
            setErrorPrompt("Provided credentials are invalid.");
            return false;
        } else {
            setErrorPrompt("");
        }
        return true;
    }

    const login = () => {
        validateCredentials();
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
                <button className={styles.login_button}>
                    Sign Up
                </button>
                <BenefitsList/>
            </div>
        </div>
    );
}


const BenefitsList = () => {
    return (
        <>
            <p className={styles.benefits_prompt}>
                What are the benefits of having an account?
            </p>
            <ul className={styles.benefits_list}>
                <BenefitsListEntry
                    icon={<ListAltIcon sx={{fontSize: '3rem', color: 'orange'}}/>}
                    text="Check previous orders"
                />
                <BenefitsListEntry
                    icon={<KeyIcon sx={{fontSize: '3rem', color: 'DeepSkyBlue'}}/>}
                    text="See keys you have bought"
                />
                <BenefitsListEntry
                    icon={<AccountCircleIcon sx={{fontSize: '3rem', color: 'orange'}}/>}
                    text="Easily validate your credentials"
                />
            </ul>
        </>
    );
}

const BenefitsListEntry = ({icon, text}) => {
    return (
        <li> 
        <Stack direction="row" alignItems="center" gap={1}>
            {icon}
            <Typography>
                <p className={styles.li_text}>
                    {text}    
                </p>
            </Typography>
        </Stack>
    </li>
    );
}

BenefitsListEntry.propTypes = {
    icon: PropTypes.any,
    text: PropTypes.text
}

export default LoginPage;