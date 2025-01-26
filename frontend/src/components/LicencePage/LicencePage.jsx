import styles from "@/components/LicencePage/LicencePage.module.css"
import axios from "axios";
import { useEffect, useState } from "react";
import { useLocation } from "react-router";
import { getReasonPhrase } from "http-status-codes";
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';

const LicencePage = () => {
    const location = useLocation();
    const {licence} = location.state;
    const [description, setDescription] = useState(null);
    const [image, setImage] = useState(null);
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
                <div className={styles.name_and_identifier}>
                    <p className={styles.name}>{licence['name']}</p>
                    <p className={styles.identifier}>{`Identifier:\u00A0\u00A0${licence['licenceId']}`}</p>
                </div>
                <div className={styles.image_data_order}>
                    <img
                        className={styles.image}
                        src={image || '/src/assets/images/placeholder_img.png'}
                    />
                    <div className={styles.data}>
                    </div>
                    <div className={styles.order}>
                        <p className={styles.price}> {licence['price']+'$'} </p>
                        <p className={styles.available}>{licence['availableForSale'] ? 'Available' : 'Not Available'}</p>
                        <div className={styles.button_box}>
                        {licence['availableForSale'] ? 
                            (   
                                <button className={styles.cart_button}>
                                    <ShoppingCartIcon sx={{fontSize: '2rem'}}/>
                                    Add To Cart
                                </button>
                            ) : (
                                <button className={styles.cart_button_disabled}>
                                    <ShoppingCartIcon sx={{fontSize: '2rem'}}/>
                                    Add To Cart
                                </button>
                            )
                        }
                        </div>
                    </div>
                </div>
            </div>
            {/* {executionError ? displayError() : ( */}
                {/* <div className={styles.description}> */}
                    {/* {description || 'Description for this licence was not provided.'} */}
                {/* </div> */}
            {/* )} */}
        </div>
    )
}

export default LicencePage;