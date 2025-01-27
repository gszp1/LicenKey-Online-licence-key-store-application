import styles from '@/components/ShoppingCartPage/ShoppingCartPage.module.css'
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import HighlightOffIcon from '@mui/icons-material/HighlightOff';
import RemoveIcon from '@mui/icons-material/Remove';
import AddIcon from '@mui/icons-material/Add';
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
                let price = 0;
                response.data.shoppingCartEntries.forEach((item) => {
                    price += (item['price'] * item['quantity']);
                })
                setTotalPrice(price);
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
            setCartItems(null);
            setTotalPrice(0);
            console.log(response);
        } catch (error) {
            console.log(error);
        }
    }

    const decreaseQuantity = async(item, index) => {
        const url = `${window._env_.BACKEND_API_URL}${'/api/shopping-carts/quantity/decrease'}`;
        try {
            let response = await axios.patch(
                url,
                { 'licenceId': item['licenceId'] },
                {
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem('AuthToken')}`,
                        'Content-Type': 'application/json'
                    }
                }
            )
            let price = item['price'];
            if (item['quantity'] == 1) {
                setCartItems(cartItems.filter((_, id) => id !== index));
            } else {
                const updatedCartItems = cartItems.map((cartItem, idx) => {
                    if (idx === index) {
                        return { ...cartItem, quantity: cartItem.quantity - 1 };
                    }
                    return cartItem;
                });
                setCartItems(updatedCartItems);   
            }
            setTotalPrice(totalPrice - price);
            console.log(response);
        } catch (error) {
            console.log(error);
        }
    }

    const increaseQuantity = async(item, index) => {
        const url = `${window._env_.BACKEND_API_URL}${'/api/shopping-carts/quantity/increase'}`;
        try {
            let response = await axios.patch(
                url,
                { 'licenceId': item['licenceId'] },
                {
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem('AuthToken')}`,
                        'Content-Type': 'application/json'
                    }
                }
            )
            let price = item['price'];
            setTotalPrice(totalPrice + price);
            const updatedCartItems = cartItems.map((cartItem, idx) => {
                if (idx === index) {
                    return { ...cartItem, quantity: cartItem.quantity + 1 };
                }
                return cartItem;
            });
            setCartItems(updatedCartItems);   
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
        return (
            (!cartItems || cartItems.length == 0) ? (
                <p className={styles.cart_empty_message}>
                    Your Cart is Empty.
                </p>
            ) : (
                cartItems.map((item, index) => {
                    return (
                        <div 
                            className={styles.cart_item}
                            key={index}
                        >
                            <div className={styles.item_index}>
                                {index + 1}
                            </div>
                            <img className={styles.item_image}
                            />
                            <div className={styles.item_data}>
                                <p className={styles.item_name}>{item['name']}</p>
                                <p className={styles.item_price}>{item['price'] +'$'}</p>
                            </div>
                            <div className={styles.item_quantity_box}>
                                <button
                                    className={styles.increase}
                                    onClick={(e) => {
                                        e.preventDefault();
                                        e.stopPropagation();
                                        increaseQuantity(item, index);
                                    }} 
                                >
                                    <AddIcon sx={{fontSize: '2rem'}}/>
                                </button>
                                <div className={styles.item_quantity}>{item['quantity']}</div>
                                <button
                                    className={styles.decrease}
                                    onClick={(e) => {
                                        e.preventDefault();
                                        e.stopPropagation();
                                        decreaseQuantity(item, index);
                                    }}        
                                >
                                    <RemoveIcon sx={{fontSize: '2rem'}}/>
                                </button>
                            </div>
                        </div>
                    )
                })
            )
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