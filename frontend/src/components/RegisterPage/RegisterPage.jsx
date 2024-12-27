import styles from "@/components/RegisterPage/RegisterPage.module.css"
import { Link } from "react-router";
import BenefitsList from "@/components/BenefitsList/BenefitsList.jsx";
import { useState } from "react";
import PropTypes, { func, object } from "prop-types";

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

    return (
        <div className={styles.page}>
            <Credentials
                credentials={credentials}
                setCredentials={setCredentials}
            />
            <AdditionalInformation/>
        </div>
    );
}

const Credentials = ({credentials, setCredentials}) => {
    return (
        <div className={styles.credentials}>
            <LeftSection
                credentials={credentials}
                setCredentials={setCredentials}
            />
            <RightSection
                credentials={credentials}
                setCredentials={setCredentials}
            />
        </div>
    );
}

const LeftSection = ({credentials, setCredentials}) => {
    return (
        <div className={styles.left_section}>
            <label className={styles.input_label}> Email </label>
            <input
                className={styles.input_field}
                placeholder="example@mail.com"    
            />
            <p className={styles.error_prompt}></p>
            <label className={styles.input_label}> Confirm Email </label>
            <input
                className={styles.input_field}
                placeholder="example@mail.com"
            />
            <p className={styles.error_prompt}></p>
            <label className={styles.input_label}> Username </label>
            <input 
                className={styles.input_field}
                placeholder="your username"
            />
            <p className={styles.error_prompt}></p>
            <label className={styles.input_label}> Password </label>
            <input
                className={styles.input_field}
                placeholder="your password"
            />
            <p className={styles.error_prompt}></p>
            <label className={styles.input_label}> Confirm Password</label>
            <input 
                className={styles.input_field}
                placeholder="your password confirmation"
            />
            <p className={styles.error_prompt}></p>
        </div>
    );
}

const RightSection = ({credentials, setCredentials}) => {
    return (
        <div className={styles.right_section}>
            <div className={styles.secondary_box}>
                <label className={styles.input_label}> Name* </label>
                <input
                    className={styles.input_field}
                    placeholder="name (optional)"
                />
                <p className={styles.error_prompt}></p>
                <label className={styles.input_label}> Second Name* </label>
                <input
                    className={styles.input_field}
                    placeholder="second name (optional)"
                />
                <p className={styles.error_prompt}></p>
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
    setCredentials: func
}

LeftSection.propTypes = {
    credentials: PropTypes.object,
    setCredentials: func
}

RightSection.propTypes = {
    credentials: PropTypes.object,
    setCredentials: func
}

export default RegisterPage;