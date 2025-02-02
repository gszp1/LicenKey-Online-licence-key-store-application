import styles from "@/components/AccountPage/Sections/Orders/OrdersSection.module.css"
import axios from "axios";
import PropTypes from "prop-types";
import { useEffect, useState } from "react";
import {format} from 'date-fns'

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
            <div className={styles.order_entry}
                key={index}
            >   
                <p className={styles.order_index}>{index + 1}</p>
                <div className={styles.order_info}>
                    <div className={styles.order_info_entry}>
                        <p className={styles.order_info_label}> {"Identifier:\u00A0"}</p>
                        <p className={styles.order_info_value}> {order.orderIdentifier} </p>
                    </div>
                    <div className={styles.order_info_entry}>
                        <p className={styles.order_info_label}> {"Price:\u00A0"}</p>
                        <p className={styles.order_info_value}> {`${order.totalPrice}$`} </p>
                    </div>
                    <div className={styles.order_info_entry}>
                        <p className={styles.order_info_label}> {"Placement date:\u00A0"}</p>
                        <p className={styles.order_info_value}> {`${format(order.placingDate, 'dd-MM-yyyy')}`} </p>
                    </div>
                </div>
                {displayOrderedItems(order)}
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