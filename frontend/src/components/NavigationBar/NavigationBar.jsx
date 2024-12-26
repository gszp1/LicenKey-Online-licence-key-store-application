import styles from "@/components/NavigationBar/NavigationBar.module.css"
import SearchIcon from '@mui/icons-material/Search';
import PersonIcon from '@mui/icons-material/Person';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import { useState, useEffect } from "react";

const NavigationBar = () => {
    const [scrollY, setScrollY] = useState(0)
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
                const barWidth = (screenBottomY / rootHeigth) * 100
                bar.style.width = `${barWidth}%`
            }
        }
        
        const onScroll = () => {
            const y = window.scrollY;
            setScrollY(y);
            setShowBottomSection(y < 74);
            fillBar();
        }

        fillBar();
        window.addEventListener('scroll', onScroll)
        return () => window.removeEventListener('scroll', onScroll);
    }, []);

    return (
        <div className={styles.navigation_bar}>
            <div className={styles.top_section}>
                <img
                    className={styles.website_logo}
                    src="/src/assets/images/website-logo.png"
                />
                <div className={styles.search_bar}>
                    <input/>
                    <button>
                        <SearchIcon fontSize="large"/>
                    </button>
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
            <div className={styles.progression_bar_border}/>
            <div className={styles.progression_bar}>
                <div id="progressBar" className={styles.progression_bar_fill}></div>
            </div>
            <div className={styles.progression_bar_border}/>
            { showBottomSection && (
                <div className={styles.bottom_section}></div>
            )}
        </div>
    )
}

export default NavigationBar;