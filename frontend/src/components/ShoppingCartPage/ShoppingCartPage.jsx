import styles from '@/components/ShoppingCartPage/ShoppingCartPage.module.css'
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import HighlightOffIcon from '@mui/icons-material/HighlightOff';
import CheckCircleOutlineOutlinedIcon from '@mui/icons-material/CheckCircleOutlineOutlined';
import { useEffect, useState } from 'react';
import axios from 'axios';

const ShoppingCartPage = () => {

    const [cartItems, setCartItems] = useState(null);
    const [totalPrice, setTotalPrice] = useState(0)

    useEffect(() => {
        const fetchCartItems = async () => {
            const url = `${window._env_.BACKEND_API_URL}/api/shopping-carts`;
            try {
                let response = await axios.get(
                    url,
                    {headers: {
                        'Authorization': `Bearer ${localStorage.getItem('AuthToken')}`
                    }}
                );
                console.log(response);
                setCartItems(response.data.shoppingCartEntries);
            } catch (error) {
                console.log(error);
            }
        }
        fetchCartItems();
    }, [])

    const sendClearCartRequest = async () => {
        const url = `${window._env_.BACKEND_API_URL}${'/api/shopping-carts/all'}`;
        try {
            let response = await axios.delete(
                url,
                {headers: {
                    'Authorization': `Bearer ${localStorage.getItem('AuthToken')}`
                }}
            )
            console.log(response);
        } catch (error) {
            console.log(error);
        }
    }

    const cartMenu = () => {
        return (
            <div className={styles.cart_menu}>
                <div className={styles.cart_menu_header}>
                    <p className={styles.cart_menu_header_text}>Licences in Cart</p>
                    <div className={styles.clear_cart_button_box}>
                        <button 
                            className={styles.clear_cart_button}
                            onClick={(e) => {
                                e.preventDefault();
                                e.stopPropagation();
                                sendClearCartRequest();
                            }}    
                        >
                            <HighlightOffIcon sx={{fontSize: '1.5rem'}}/>
                            {'\u00A0Clear Cart'}
                        </button>
                    </div>
                </div>
                <div className={styles.cart_content}>
                    {displayCartContents()}
                </div>
            </div>
        );
    }

    const displayCartContents = () => {
        if (!cartItems || cartItems.length == 0) {
            return <p className={styles.cart_empty_message}>
                Your Cart is Empty.
            </p>
        }
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