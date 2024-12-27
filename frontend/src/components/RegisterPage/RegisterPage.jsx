import styles from "@/components/RegisterPage/RegisterPage.module.css"
import { Link } from "react-router";
import BenefitsList from "@/components/BenefitsList/BenefitsList.jsx";
import { useState } from "react";
import PropTypes from "prop-types";

const RegisterPage = () => {
    const [credentials, setCredentials] = useState({
        email: "",
        emailConfirmation: "",
        password: "",
        passwordConfirmation: "",
        username: "",
        firstName: "",
        lastName: ""
    });

    const [errorPrompts, setErrorPrompts] = useState({
        email: "",
        emailConfirmation: "",
        password: "",
        passwordConfirmation: "",
        username: "",
        firstName: "",
        lastName: ""
    });

    const updateValue = (e, credentialName) => {
        let newCredentials = {...credentials,
            [credentialName]: e.target.value
        };
        setCredentials(newCredentials);
    };

    return (
        <div className={styles.page}>
            <Credentials
                credentials={credentials}
                errorPrompts={errorPrompts}
                updateValue={updateValue}
            />
            <AdditionalInformation/>
        </div>
    );
}

const Credentials = ({credentials, errorPrompts, updateValue}) => {
    return (
        <div className={styles.credentials}>
            <LeftSection
                credentials={credentials}
                errorPrompts={errorPrompts}
                updateValue={updateValue}
            />
            <RightSection
                credentials={credentials}
                errorPrompts={errorPrompts}
                updateValue={updateValue}
            />
        </div>
    );
}

const LeftSection = ({credentials, errorPrompts, updateValue}) => {
    return (
        <div className={styles.left_section}>
            <label className={styles.input_label}> Email </label>
            <input
                className={styles.input_field}
                placeholder="example@mail.com"   
                value={credentials["email"]} 
                onChange={(e) => updateValue(e, "email")}
            />
            <p className={styles.error_prompt}>
                {errorPrompts["email"]}
            </p>
            <label className={styles.input_label}> Confirm Email </label>
            <input
                className={styles.input_field}
                placeholder="example@mail.com"
                value={credentials["emailConfirmation"]} 
                onChange={(e) => updateValue(e, "emailConfirmation")}
            />
            <p className={styles.error_prompt}>
                {errorPrompts["emailConfirmation"]}
            </p>
            <label className={styles.input_label}> Username </label>
            <input 
                className={styles.input_field}
                placeholder="your username"
                value={credentials["username"]} 
                onChange={(e) => updateValue(e, "username")}
            />
            <p className={styles.error_prompt}>
                {errorPrompts["username"]}
            </p>
            <label className={styles.input_label}> Password </label>
            <input
                className={styles.input_field}
                placeholder="your password"
                value={credentials["password"]} 
                type="password"
                onChange={(e) => updateValue(e, "password")}
            />
            <p className={styles.error_prompt}>
                {errorPrompts["password"]}
            </p>
            <label className={styles.input_label}> Confirm Password</label>
            <input 
                className={styles.input_field}
                placeholder="your password confirmation"
                value={credentials["passwordConfirmation"]} 
                type="password"
                onChange={(e) => updateValue(e, "passwordConfirmation")}
            />
            <p className={styles.error_prompt}>
                {errorPrompts["passwordConfirmation"]}
            </p>
        </div>
    );
}

const RightSection = ({credentials, errorPrompts, updateValue}) => {
    return (
        <div className={styles.right_section}>
            <div className={styles.secondary_box}>
                <label className={styles.input_label}> Name* </label>
                <input
                    className={styles.input_field}
                    placeholder="name (optional)"
                    value={credentials["firstName"]} 
                    onChange={(e) => updateValue(e, "firstName")}
                />
                <p className={styles.error_prompt}>
                    {errorPrompts["firstName"]}
                </p>
                <label className={styles.input_label}> Second Name* </label>
                <input
                    className={styles.input_field}
                    placeholder="second name (optional)"
                    value={credentials["lastName"]} 
                    onChange={(e) => updateValue(e, "lastName")}
                />
                <p className={styles.error_prompt}>
                    {errorPrompts["lastName"]}
                </p>
            </div>
            <button className={styles.register_button}>
                Sign Up
            </button>
            <Link to="/login" style={{marginTop: '5px'}}>
                <p>Already have an account?</p>
            </Link>
        </div>
    );
}

const AdditionalInformation = () => {
    return (
        <div className={styles.additional_information}>
            <Requirements/>
            <div className={styles.benefits}>
                <BenefitsList/>
            </div>
        </div>
    );
}

const Requirements = () => {
    return (
        <div className={styles.requirements}>
            <ol>
                <li>Password must include:
                    <ul>
                        <li>1 Capital character</li>
                        <li>1 Small character</li>
                        <li>1 Special character</li>
                        <li>1 Numeric character</li>
                    </ul>
                </li>
                <li>
                    Fields marked with * are optional - you can fill them later in account page.
                </li>
                <li>
                    You can create only one account per email.
                </li>
                <li>
                    Your username must be unique.
                </li>
                <li>
                    Values in given field and confirm field must be equal.
                </li>
            </ol>
        </div>
    );
}


Credentials.propTypes = {
    credentials: PropTypes.object,
    errorPrompts: PropTypes.object,
    updateValue: PropTypes.func
}

LeftSection.propTypes = {
    credentials: PropTypes.object,
    errorPrompts: PropTypes.object,
    updateValue: PropTypes.func
}

RightSection.propTypes = {
    credentials: PropTypes.object,
    errorPrompts: PropTypes.object,
    updateValue: PropTypes.func
}

export default RegisterPage;