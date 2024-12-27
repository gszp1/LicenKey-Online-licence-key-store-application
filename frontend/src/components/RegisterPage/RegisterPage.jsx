import styles from "@/components/RegisterPage/RegisterPage.module.css"
import { Link } from "react-router";

const RegisterPage = () => {
    return (
        <div className={styles.page}>
            <Credentials/>
            <Requirements/>
        </div>
    );
}

const Credentials = () => {
    return (
        <div className={styles.credentials}>
            <LeftSection/>
            <RightSection/>
        </div>
    );
}

const LeftSection = () => {
    return (
        <div className={styles.left_section}>
            <label className={styles.input_label}> Email </label>
            <input className={styles.input_field}></input>
            <p className={styles.error_prompt}>error placeholder</p>
            <label className={styles.input_label}> Confirm Email </label>
            <input className={styles.input_field}></input>
            <p className={styles.error_prompt}>error placeholder</p>
            <label className={styles.input_label}> Username </label>
            <input className={styles.input_field}></input>
            <p className={styles.error_prompt}>error placeholder</p>
            <label className={styles.input_label}> Password </label>
            <input className={styles.input_field}></input>
            <p className={styles.error_prompt}>error placeholder</p>
            <label className={styles.input_label}> Confirm Password</label>
            <input className={styles.input_field}></input>
            <p className={styles.error_prompt}>error placeholder</p>
        </div>
    );
}

const RightSection = () => {
    return (
        <div className={styles.right_section}>
            <div className={styles.secondary_box}>
                <label className={styles.input_label}> Name* </label>
                <input className={styles.input_field}></input>
                <p className={styles.error_prompt}>error placeholder</p>
                <label className={styles.input_label}> Second Name* </label>
                <input className={styles.input_field}></input>
                <p className={styles.error_prompt}>error placeholder</p>
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

export default RegisterPage;