import styles from "@/components/AccountPage/Sections/ChangePassword/ChangePasswordSection.module.css"
import axios from "axios";
import PropTypes from "prop-types";
import { useState } from "react";

const ChangePasswordSection = ({email, setSection, setError}) => {
    const [credentials, setCredentials] = useState({
        'email': email,
        'currentPassword': '',
        'newPassword': '',
        'passwordConfirmation': ''
    });

    const [errorPrompts, setErrorPrompts] = useState({
        'currentPassword': '',
        'newPassword': '',
        'passwordConfirmation': '',
        'passwordChanged': ''
    });

    const [changePrompt, setChangePrompt] = useState({
        'changePrompt': '',
        'color': ''
    });
    
    const valueChangeAction = (e, header) => {
        setCredentials({...credentials, [header]: e.target.value})
    }

    const validateCredentials = () => {
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).+$/;
        const prompts = {
            'currentPassword': '',
            'newPassword': '',
            'passwordConfirmation': '',
            'passwordChanged': ''
        };

        // Validate old password
        const oldPassword = credentials["currentPassword"];
        if (!oldPassword) {
            prompts["currentPassword"] = "Current password is required.";
        } else if (passwordRegex.test(oldPassword) == false || oldPassword.length < 8) {
            prompts["currentPassword"] = "Password is invalid.";
        }

        // Validate password
        const password = credentials["newPassword"];
        if (!password) {
            prompts["newPassword"] = "Password is required.";
        } else if (passwordRegex.test(password) == false || password.length < 8) {
            prompts["newPassword"] = "Password is invalid.";
        }

        // Validate passwordConfirmation
        const passwordConfirmation = credentials["passwordConfirmation"];
        if (!passwordConfirmation) {
            prompts["passwordConfirmation"] = "Password confirmation is required.";
        } else if (!(passwordConfirmation === password)) {
            prompts["passwordConfirmation"] = "Password confirmation is not equal to password.";
        }
        setErrorPrompts(prompts);
        return !(prompts["currentPassword"] || prompts["newPassword"] || prompts["passwordConfirmation"]);
    }

    const sendRequest = async () => {
        console.log("Here")
        if (!validateCredentials()) {
            return;
        }
        console.log("here too")
        const url = `${window._env_.BACKEND_API_URL}${'/api/users/password'}`;
        console.log(credentials)
        try {
            let response = await axios.patch(
                url,
                credentials,
                {headers: {
                    'Authorization': `Bearer ${localStorage.getItem("AuthToken")}`,
                    'Content-Type': 'application/json'
                }}
            );
            console.log(response)
            setChangePrompt({'changePrompt': response.data.contents, 'color': 'green'});
        } catch (error) {
            if (error.response) {
                if (error.response.status == 400 || error.response.status == 409) {
                    setChangePrompt({'changePrompt': error.response.data, 'color': 'red'})
                } else {
                    setError(error);
                    setSection('error');
                }
            } else {
                setError(error);
                setSection('error');
            }
        }
    }

    return (
        <div className={styles.section}>
            <h1 className={styles.section_header}> Change Password </h1>
            <div className={styles.section_content}>
                <div className={styles.password_box}>
                    <label className={styles.input_label}> Current Password </label>
                    <input
                        className={styles.input_field}
                        value={credentials['currentPassword']}
                        onChange={(e) => valueChangeAction(e, 'currentPassword')}
                        type='password'
                    />
                    <p className={styles.input_prompt}> {errorPrompts['currentPassword']} </p>
                    <label className={styles.input_label}> New Password </label>
                    <input
                        className={styles.input_field}
                        value={credentials['newPassword']}
                        onChange={(e) => valueChangeAction(e, 'newPassword')}
                        type='password'
                    />
                    <p className={styles.input_prompt}> {errorPrompts['newPassword']} </p>
                    <label className={styles.input_label}> Confirm New Password </label>
                    <input
                        className={styles.input_field}
                        value={credentials['passwordConfirmation']}
                        onChange={(e) => valueChangeAction(e, 'passwordConfirmation')}
                        type='password'
                    />
                    <p className={styles.input_prompt}> {errorPrompts['passwordConfirmation']} </p>
                    <button 
                        className={styles.change_password_button}
                        onClick={sendRequest}
                     >
                        Change Password
                    </button>
                    <p 
                        className={styles.input_prompt}
                        style={{'color': changePrompt['color']}}
                    >
                        {changePrompt['changePrompt']}
                     </p>
                </div>
                <div className={styles.information_box}>
                    <ol>
                        <li>
                            Values in New Password field and Confirm New Password field must be equal.
                        </li>
                        <li>Password must include:
                            <ul>
                                <li>1 Capital letter</li>
                                <li>1 Lowercase letter</li>
                                <li>1 Special character</li>
                                <li>1 Numeric character</li>
                            </ul>
                        </li>
                    </ol>
                </div>
            </div>
        </div>
    )
}

ChangePasswordSection.propTypes = {
    email: PropTypes.string.isRequired,
    setSection: PropTypes.func.isRequired,
    setError: PropTypes.func.isRequired
}

export default ChangePasswordSection;