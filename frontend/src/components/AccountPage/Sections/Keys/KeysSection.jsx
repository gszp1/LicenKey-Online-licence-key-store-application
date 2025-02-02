import styles from "@/components/AccountPage/Sections/Keys/KeysSection.module.css"
import PropTypes from "prop-types";
import { useState } from "react";

const KeysSection = ({setError, setSection}) => {
    const [keys, setKeys] = useState([]);

    return (
        <div className={styles.section}>
            <h1 className={styles.section_header}> Your Licence Keys </h1>
            <div className={styles.section_content}>
                <p className={styles.keys_header}> Keys </p>
                <div className={styles.keys_field}>
                    
                </div>
            </div>
        </div>
    )
}

KeysSection.propTypes = {
    setError: PropTypes.func.isRequired,
    setSection: PropTypes.func.isRequired
}

export default KeysSection;