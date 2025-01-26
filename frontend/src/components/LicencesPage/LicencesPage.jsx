import styles from "@/components/LicencesPage/LicencesPage.module.css"
import axios from "axios";
import PropTypes from "prop-types";
import { useEffect, useState } from "react";
import { getReasonPhrase } from 'http-status-codes';

const LicencesPage = ({searchKeyword}) => {
    const [licences, setLicences] = useState(null);
    const [executionError, setExecutionError] = useState(null);

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
                setLicences(response.data.licences);
            } catch(error) {
                setExecutionError(error);
            }
        }
        fetchLicences()
    }, [searchKeyword]);

    const displayErrorMessage = () => {
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
            <>
                <p className={styles.error_header}>{errorHeader}</p>
                <p className={styles.error_status}>{`Status: ${errorStatus}`}</p>
                <p className={styles.error_message}>{errorMessage}</p>
            </>
        );
    }

    const displayNoLicences = () => {
        return (
            <>
                <p className={styles.no_licences_header}>
                    No Licences Found
                </p>
                <p className={styles.no_licences_message}>
                    {searchKeyword ?
                        'No licences meet search criteria' :
                        "Looks like we don't have any licences in stock. Try again later."}
                </p>
            </>
        )
    }

    const displayLicences = () => {
        return (
            <>
                <div className={styles.licences_list_header}>
                    {`${searchKeyword ? 'Criteria Matching Licences' : 'Available Licences'}  (${licences.length})`}
                </div>
                {licences.map((licence, index) => (
                    createLicenceCard(licence, index)
                ))}
            </>
        )
    }

    const createLicenceCard = (licence, index) => {
        return (
            <div
                className={styles.licence_list_entry}
                key={index}
            >   
                <p className={styles.licence_list_entry_index}>{index + 1}</p>
                <div className={styles.image_box}>

                </div>
                <div className={styles.licence_data_box}>

                </div>
                <div className={styles.buttons_box}>
                    <button className={styles.page_button}>
                        Search Licence Page
                    </button>
                    <button className={styles.cart_button}>
                        Add To Cart
                    </button>
                </div>
            </div>
        );
    }

    return (
        <div className={styles.page}>
            {executionError ? displayErrorMessage() : (
                (licences && licences.length === 0) ? displayNoLicences() :
                (licences && displayLicences()) 
            )}
        </div>
    );
}

LicencesPage.propTypes = {
    searchKeyword: PropTypes.string.isRequired
}

export default LicencesPage;