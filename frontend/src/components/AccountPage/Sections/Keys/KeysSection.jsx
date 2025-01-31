import styles from "@/components/AccountPage/Sections/Keys/KeysSection.module.css"
import { useState } from "react";

const KeysSection = () => {
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

export default KeysSection;