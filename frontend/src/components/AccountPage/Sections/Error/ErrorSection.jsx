import styles from "@/components/AccountPage/Sections/Error/ErrorSection.module.css"
import PropTypes from "prop-types";
import { useEffect } from "react";
import { useNavigate } from "react-router";

const ErrorSection = ({error}) => {
    const navigate = useNavigate();

    let errorCode = 'Unknown';
    let errorMessage = 'Unexpected error occurred.';
    if (error != null) {
        if (error.response) {
            errorCode = error.response.status || errorCode;
            errorMessage = error.response.data || errorMessage;
        } else if (error.request) {
            errorMessage = "Server does not respond."
        }
    }

    useEffect(() => {
        localStorage.removeItem("AuthToken");
        setTimeout(() => {
            navigate("/");
        }, 5000);
    }, [navigate]);
    
    return (
        <div className={styles.section}>
            <h1 className={styles.section_header}> Error </h1>
            <div className={styles.section_content}>
                <h2 className={styles.text_header_notifier}>Something went wrong!</h2>
                <h2 className={styles.text_header}>Error Code</h2>
                <p className={styles.text}>{errorCode}</p>
                <h2 className={styles.text_header}>Message</h2>
                <p className={styles.text}>{errorMessage}</p>
                <h2 className={styles.text_header}>You will be now logged out and redirected to main page.</h2>
            </div>
        </div>
    )
}

ErrorSection.propTypes = {
    error: PropTypes.object
}

export default ErrorSection;