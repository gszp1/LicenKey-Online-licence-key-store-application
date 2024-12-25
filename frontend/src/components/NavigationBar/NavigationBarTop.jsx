import styles from "@/components/NavigationBar/NavigationBarTop.module.css"

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
                    <button></button>
                </div>
            </div>
            <div className={styles.bottom_section}></div>
        </div>
    )
}

export default NavigationBarTop;