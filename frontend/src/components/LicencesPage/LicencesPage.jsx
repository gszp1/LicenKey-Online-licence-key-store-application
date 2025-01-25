import styles from "@/components/LicencesPage/LicencesPage.module.css"
import axios from "axios";
import PropTypes from "prop-types";
import { useEffect, useState } from "react";

const LicencesPage = ({searchKeyword}) => {
    const [licences, setLicences] = useState(null);
    const [executionError, setExecutionError] = useState('');

    useEffect(() => {
        const fetchLicences = async () => {
            let url = `${window._env_.BACKEND_API_URL}${'/api/licences/all'}`;
            try {
                let response = await axios.get(
                    url,
                    {params: {
                      'keyword': searchKeyword  
                    }}
                );
                setLicences(response.data);
            } catch(error) {
                setExecutionError(error);
            }
        }
        fetchLicences()
    }, [searchKeyword]);

    const getErrorMessage = () => {
        let errorMessage = "Server does not respond - try again later.";
        let errorHeader = "Server error";
        if (executionError.response) {
            if (executionError.response.status) {
                errorHeader = executionError.response.status;
            }
            if (executionError.response.data) {
                errorMessage = executionError.response.data;
            }
        }
        return (
            <div></div>
        );
    }

    return (
        <div className={styles.page}>
            {executionError && }
        </div>
    );
}

LicencesPage.propTypes = {
    searchKeyword: PropTypes.string.isRequired
}

export default LicencesPage;