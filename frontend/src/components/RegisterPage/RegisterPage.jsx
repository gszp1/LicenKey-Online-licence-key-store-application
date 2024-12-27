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
            <MainBox/>
            <SecondaryBox/>
        </div>
    );
}

const MainBox = () => {
    return (
        <div className={styles.main_box}>

        </div>
    );
}

const SecondaryBox = () => {
    return (
        <div className={styles.secondary_box}>

        </div>
    );
}

const Requirements = () => {
    return (
        <div className={styles.requirements}>
            
        </div>
    );
}

export default RegisterPage;