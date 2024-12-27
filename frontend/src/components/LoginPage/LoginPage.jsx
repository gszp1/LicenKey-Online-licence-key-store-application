import styles from "@/components/LoginPage/LoginPage.module.css"

const LoginPage = () => {
    return (
        <div className={styles.page}>
            <div className={styles.login_box}>
                Login
                <label>email</label>
                <input/>
                <p>error placeholder</p>
                <label>password</label>
                <input/>
                <p>error placeholder</p>
                <button>Login</button>
            </div>
            <div className={styles.register_box}>
                Register
                <p>Don't Have an Account yet?</p>
                <button>Sign Up</button>
            </div>
        </div>
    );
}

export default LoginPage;
