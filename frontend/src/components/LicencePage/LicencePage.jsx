import styles from "@/components/LicencePage/LicencePage.module.css"
import axios from "axios";
import { useEffect, useState } from "react";
import { useLocation } from "react-router";
import { getReasonPhrase } from "http-status-codes";

const LicencePage = () => {
    const location = useLocation();
    const {licence} = location.state;
    const [description, setDescription] = useState(null);
    const [executionError, setExecutionError] = useState(null);

    useEffect(() => {
        const fetchDescription = async () => {
            let apiPath = `/api/licences/${licence['licenceId']}/description`;
            let url = `${window._env_.BACKEND_API_URL}${apiPath}`;
            try {
                let response = await axios.get(url);
                setDescription(response.data.description)
            } catch (error) {
                setExecutionError(error);
            }
        }
     
        fetchDescription();
    }, [licence])

    const displayError = () => {
        let errorMessage = "Server does not respond - try again later.";
        let errorHeader = "Server error";
        let errorStatus = "Unknown";
        if (executionError.response) {
            if (executionError.response.status) {
                errorHeader = executionError.response.status;
                errorStatus = getReasonPhrase(executionError.response.status);
            }
            if (executionError.response.data) {
                errorMessage = executionError.response.data;
            }
            setExecutionError(null);
        }
        return (
            <div className={styles.error_box}>
                <p className={styles.error_header}>{errorHeader}</p>
                <p className={styles.error_status}>{`Status: ${errorStatus}`}</p>
                <p className={styles.error_message}>{errorMessage}</p>
            </div>
        );
    }

    return (
        <div className={styles.page}>
            <div className={styles.data_box}>
            </div>
            {executionError ? displayError() : (
                <div className={styles.description}>
                    {description || 'Description for this licence was not provided.'}
                </div>
            )}
        </div>
    )
}

export default LicencePage;