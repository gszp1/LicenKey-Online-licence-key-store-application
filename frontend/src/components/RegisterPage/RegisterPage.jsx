import styles from "@/components/RegisterPage/RegisterPage.module.css"

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
                <label className={styles.input_label}> Name </label>
                <input className={styles.input_field}></input>
                <p className={styles.error_prompt}>error placeholder</p>
                <label className={styles.input_label}> Second Name </label>
                <input className={styles.input_field}></input>
                <p className={styles.error_prompt}>error placeholder</p>
            </div>
            <button className={styles.register_button}>
                Sign Up
            </button>
        </div>
    );
}

const Requirements = () => {
    return (
        <div className={styles.requirements}>
            requirements
        </div>
    );
}

export default RegisterPage;