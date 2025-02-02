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

    const displayOrders = () => {
        return (
            <>
                {orders.map((order, index) => {return displaySingleOrder(order, index)})}
            </>
        )
    }

    const displaySingleOrder = (order, index) => {
        return (
            <div
                key={index}
            >
                <div>
                    hello
                    {displayOrderedItems(order)}
                </div>
            </div>
        )
    }

    const displayOrderedItems = (order) => {
        return (
            <>
                {order.orderEntries.map((item, index) => {return displaySingleOrderItem(item, index)})}
            </>
        )
    }

    const displaySingleOrderItem = (orderItem, index) => {
        return (
            <div
                key={index}
            >
                yes
            </div>
        )
    }

    const displayNoKeysMessage = () => {
        return (
            <p className={styles.no_orders_message}>
                {"You haven't placed any orders"}
            </p>
        )
    }

    return (
        <div className={styles.section}>
            <h1 className={styles.section_header}> Your Orders </h1>
            <div className={styles.section_content}>
                <p className={styles.orders_header}> {"Orders\u00A0\u00A0" + `(${orders ? orders.length : 0})`}</p>
                <div className={styles.orders_field}>
                    {(orders && orders.length != 0) ? displayOrders() : displayNoKeysMessage()}
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