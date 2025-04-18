import styles from "@/components/LicencesPage/LicencesPage.module.css"
import axios from "axios";
import PropTypes from "prop-types";
import { useEffect, useState } from "react";
import { getReasonPhrase } from 'http-status-codes';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import { Link } from "react-router";

const LicencesPage = ({searchKeyword}) => {
    const [licences, setLicences] = useState(null);
    const [executionError, setExecutionError] = useState(null);
    const [image, setImage] = useState(null)

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

    const sendAddToCartRequest = async (licenceId) => {
        const url = `${window._env_.BACKEND_API_URL}${'/api/shopping-carts'}`;
        try {
            let response = await axios.post(
                url,
                { 'licenceId': licenceId },
                {headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('AuthToken')}`
                }}
            );
            console.log(response);
        } catch (error) {
            console.log(error);
        }
    }

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
                    {`${searchKeyword ? 'Licences Matching Criterias' : 'Available Licences'}  (${licences.length})`}
                </div>
                {licences.map((licence, index) => (
                    createLicenceCard(licence, licence['licenceId'], index)
                ))}
            </>
        )
    }

    const createLicenceCard = (licence, index, listIndex) => {
        return (
            <Link
                to={`/licences/${licence['licenceId']}`}
                state={{licence}}
                className={styles.licence_list_entry}
                key={index}
            >   
                <p className={styles.licence_list_entry_index}>{listIndex + 1}</p>
                <img 
                    className={styles.image_box}
                    src={image || '/src/assets/images/placeholder_img.png'}
                />
                <div className={styles.licence_data_box}>
                    <div className={styles.list_entry_left_section}>
                        <h2 className={styles.name}>{licence['name']}</h2>
                        {createDataEntries(licence)}
                    </div>
                    <div className={styles.list_entry_right_section}>
                        <h2 className={styles.price}>{licence['price']+'$'}</h2>
                        <p className={styles.available}>{licence['availableForSale'] ? 'Available' : 'Not Available'}</p>
                        <div className={styles.button_box}>
                            {licence['availableForSale'] ? 
                                (   
                                    <button className={styles.cart_button}
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            e.preventDefault();
                                            sendAddToCartRequest(licence['licenceId']);
                                        }}
                                    >
                                        <ShoppingCartIcon sx={{fontSize: '2rem'}}/>
                                        Add To Cart
                                    </button>
                                ) : (
                                    <button className={styles.cart_button_disabled}
                                    >
                                        <ShoppingCartIcon sx={{fontSize: '2rem'}}/>
                                        Add To Cart
                                    </button>
                                )
                            }
                        </div>
                    </div>
                </div>
            </Link>
        );
    }

    const createDataEntries = (licence) => {
        let wantedProperties = {
            'Category': licence['category'],
            'Platform': licence['platform'],
            'Publisher': licence['publisher'],
            'Type': licence['type']
        }
        let properties = Object.keys(wantedProperties);
        return (
            <>
                <div className={styles.entry_data_column}>
                    {properties.map((property, index) => {
                        return createDataEntry(property, wantedProperties[property], index);
                    })}
                </div>
            </>
          );
    }

    const createDataEntry = (header, value, index) => {
        return (
            <div 
                className={styles.data_entry}
                key={index}
            >
                <p className={styles.data_entry_header}>{header+':\u00A0\u00A0'}</p>
                <p className={styles.data_entry_value}>{value}</p>
            </div>
        )
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