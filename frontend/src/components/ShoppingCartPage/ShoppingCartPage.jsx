import styles from '@/components/ShoppingCartPage/ShoppingCartPage.module.css'
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import HighlightOffIcon from '@mui/icons-material/HighlightOff';

const ShoppingCartPage = () => {

    const cartMenu = () => {
        return (
            <div className={styles.cart_menu}>
                <div className={styles.cart_menu_header}>
                    <p className={styles.cart_menu_header_text}>Licences in Cart</p>
                    <div className={styles.clear_cart_button_box}>
                        <button className={styles.clear_cart_button}>
                            <HighlightOffIcon sx={{fontSize: '1.5rem'}}/>
                            {'\u00A0Clear Cart'}
                        </button>

                    </div>
                </div>
                <div className={styles.cart_content}>

                </div>
            </div>
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