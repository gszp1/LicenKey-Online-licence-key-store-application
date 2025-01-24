import styles from "@/components/AccountPage/Sections/Error/ErrorSection.module.css"
import PropTypes from "prop-types";
import { useEffect } from "react";
import { useNavigate } from "react-router";

const ErrorSection = ({error}) => {
    const navigate = useNavigate();

    let errorCode = 'Unknown';
    let errorMessage = 'Unexpected error occurred.';
    if (error.response) {
        errorCode = error.response.status || errorCode;
        errorMessage = error.response.data || errorMessage;
    } else if (error.request) {
        errorMessage = "Server does not respond."
    }

    useEffect(() => {
        localStorage.removeItem("AuthToken");
        setTimeout(() => {
            navigate("/");
        }, 2000);
    }, [navigate]);
    
    return (
        <div className={styles.section}>
            <h1 className={styles.section_header}> Error </h1>
            <div className={styles.section_content}>
                <h2>Error Code</h2>
                <p>{errorCode}</p>
                <h2>Message</h2>
                <p>{errorMessage}</p>
                <h2>You will be now logged out.</h2>
            </div>
        </div>
    )
}

ErrorSection.propTypes = {
    logoutFunction: PropTypes.func.isRequired,
    error: PropTypes.object
}

export default ErrorSection;