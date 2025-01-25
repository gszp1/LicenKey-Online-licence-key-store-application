import styles from "@/components/NavigationBar/NavigationBar.module.css"
import SearchIcon from '@mui/icons-material/Search';
import PersonIcon from '@mui/icons-material/Person';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import { Link } from "react-router";
import { useState, useEffect } from "react";
import PropTypes from "prop-types";

const NavigationBar = ({keyword, setSearchKeyword}) => {
    const [showBottomSection, setShowBottomSection] = useState(true);
    
    useEffect(() => {
        const fillBar = () => {
            const y = window.scrollY;
            const bar = document.getElementById('progressBar');
            const root = document.getElementById("root");

            if (bar && root) {
                const viewportHeight = window.innerHeight;
                const screenBottomY = y + viewportHeight;
                const rootHeigth = root.getBoundingClientRect().height;
                let barWidth = (screenBottomY / rootHeigth) * 100
                barWidth = barWidth > 100 ? 100 : barWidth;
                bar.style.width = `${barWidth}%`
            }
        }
        
        const onScroll = () => {
            const y = window.scrollY;
            setShowBottomSection(y < 74);
            fillBar();
        }

        fillBar();
        window.addEventListener('scroll', onScroll)
        return () => window.removeEventListener('scroll', onScroll);
    }, []);

    const updateKeyword = (e) => {
        setSearchKeyword(e.target.value);
    }

    return (
        <div className={styles.navigation_bar}>
            <div className={styles.top_section}>
                <Link to="/">
                    <img
                        className={styles.website_logo}
                        src="/src/assets/images/website-logo.png"
                    />
                </Link>
                <div className={styles.search_bar}>
                    <input
                        placeholder="Search in store"
                        value={keyword}
                        onChange={updateKeyword}
                    />
                    <button>
                        <SearchIcon fontSize="large"/>
                    </button>
                </div>
                {localStorage.getItem("AuthToken") == null ? (
                    <Link 
                        style={{color: 'inherit', textDecoration: 'inherit' }}
                        to="/login"
                    >
                        <div className={styles.account_button}>
                            <PersonIcon sx={{fontSize: '3rem'}}/>
                            <p>Login / Register</p>
                        </div>
                    </Link>
                ) : (
                    <Link 
                    style={{color: 'inherit', textDecoration: 'inherit' }}
                    to="/account"
                >
                    <div className={styles.account_button}>
                        <PersonIcon sx={{fontSize: '3rem'}}/>
                        <p>Account</p>
                    </div>
                </Link>
                )} 
                <Link 
                    style={{color: 'inherit', textDecoration: 'inherit' }}
                    to="/store/cart"    
                >
                    <div className={styles.shopping_cart_button}>
                        <ShoppingCartIcon sx={{fontSize: '3rem'}}/>
                        <p>Shopping Cart</p>
                    </div>
                </Link>
            </div>
            <div className={styles.progression_bar_border}/>
            <div className={styles.progression_bar}>
                <div id="progressBar" className={styles.progression_bar_fill}></div>
            </div>
            <div className={styles.progression_bar_border}/>
            { showBottomSection && (
                <div className={styles.bottom_section}></div>
            )}
        </div>
    );
}

NavigationBar.propTypes = {
    keyword: PropTypes.string.isRequired,
    setSearchKeyword: PropTypes.func.isRequired
}

export default NavigationBar;