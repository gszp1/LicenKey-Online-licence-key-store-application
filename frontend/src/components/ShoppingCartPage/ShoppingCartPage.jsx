import styles from '@/components/ShoppingCartPage/ShoppingCartPage.module.css'
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import HighlightOffIcon from '@mui/icons-material/HighlightOff';
import CheckCircleOutlineOutlinedIcon from '@mui/icons-material/CheckCircleOutlineOutlined';
import { useState } from 'react';

const ShoppingCartPage = () => {

    const [cartItems, setCartItems] = useState(null);
    const [totalPrice, setTotalPrice] = useState(0)

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
                <p className={styles.total_price}>{`${totalPrice}$`}</p>
                <div className={styles.order_button_box}>
                    <button className={styles.order_button}>
                        <CheckCircleOutlineOutlinedIcon sx={{fontSize: '1.5rem'}}/>
                        {'\u00A0Place Order'}
                    </button>
                </div>
            </div>
        );
    }

    return (
        <div className={styles.page}>
            <div className={styles.header_box}>
                <ShoppingCartIcon sx={{fontSize: '3rem'}}/>
                {'\u00A0Shopping Cart'}
            </div>
            <div className={styles.main_box}>
                {cartMenu()}
                {orderMenu()}
            </div>
        </div>
    );
}

export default ShoppingCartPage;