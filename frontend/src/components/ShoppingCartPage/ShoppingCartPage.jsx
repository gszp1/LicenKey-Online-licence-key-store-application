import styles from '@/components/ShoppingCartPage/ShoppingCartPage.module.css'
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
const ShoppingCartPage = () => {

    const cartMenu = () => {
        return (
            <div></div>
        );
    }

    const orderMenu = () => {
        return (
            <div className={styles.order_menu}>
                <p className={styles.total_price_header}>Total Price:</p>
                <p className={styles.total_price}>2137</p>
                <div className={styles.order_button_box}>
                    <button className={styles.order_button}>
                        Place Order
                    </button>
                </div>
            </div>
        );
    }

    return (
        <div className={styles.page}>
            <div className={styles.header_box}>
                <ShoppingCartIcon sx={{fontSize: '3rem'}}/>
                Shopping Cart
            </div>
            <div className={styles.main_box}>
                {cartMenu()}
                {orderMenu()}
            </div>
        </div>
    );
}

export default ShoppingCartPage;