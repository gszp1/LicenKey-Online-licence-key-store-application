import styles from "@/components/AccountPage/Sections/Orders/OrdersSection.module.css"

const OrdersSection = () => {
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

export default OrdersSection;