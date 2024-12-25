import styles from "@/components/NavigationBar/NavigationBarTop.module.css"
import SearchIcon from '@mui/icons-material/Search';
import PersonIcon from '@mui/icons-material/Person';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';

const NavigationBarTop = () => {
    return (
        <div className={styles.navigation_bar_top}>
            <div className={styles.top_section}>
                <img
                    className={styles.website_logo}
                    src="/src/assets/images/website-logo.png"
                />
                <div className={styles.search_bar}>
                    <input/>
                    <button> <SearchIcon/> </button>
                </div>
                <div className={styles.account_button}>
                    <PersonIcon sx={{fontSize: '3rem'}}/>
                    <p>Login / Register</p>
                </div>
                <div className={styles.shopping_cart_button}>
                    <ShoppingCartIcon sx={{fontSize: '3rem'}}/>
                    <p>Shopping Cart</p>
                </div>
            </div>
            <div className={styles.bottom_section}></div>
        </div>
    )
}

export default NavigationBarTop;