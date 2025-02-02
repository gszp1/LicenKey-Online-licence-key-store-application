import styles from "@/components/AccountPage/Sections/Orders/OrdersSection.module.css"
import axios from "axios";
import PropTypes from "prop-types";
import { useEffect, useState } from "react";

const OrdersSection = ({setError, setSection}) => {
    const [orders, setOrders] = useState([]);

    useEffect(() => {
        const fetchOrders = async () => {
            let url = `${window._env_.BACKEND_API_URL}${'/api/orders'}`;
            try {
                let response = await axios.get(
                    url,
                    {headers: {
                        "Authorization": `Bearer ${localStorage.getItem("AuthToken")}`
                    }}
                );
                setOrders(response.data.orders);
            } catch (error) {
                setError(error);
                setSection('error');
            }
        }
        fetchOrders();
    }, [setError, setSection]);

    return (
        <div className={styles.section}>
            <h1 className={styles.section_header}> Your Orders </h1>
            <div className={styles.section_content}>
                <p className={styles.orders_header}> Orders </p>
                <div className={styles.orders_field}>

                </div>
            </div>
        </div>
    );
}

OrdersSection.propTypes = {
    setError: PropTypes.func.isRequired,
    setSection: PropTypes.func.isRequired
}

export default OrdersSection;