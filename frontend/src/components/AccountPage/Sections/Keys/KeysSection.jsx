import styles from "@/components/AccountPage/Sections/Keys/KeysSection.module.css"
import axios from "axios";
import PropTypes from "prop-types";
import { useEffect, useState } from "react";

const KeysSection = ({setError, setSection}) => {
    const [keys, setKeys] = useState([]);

    useEffect(() => {
        const fetchKeys = async () => {
            let url = `${window._env_.BACKEND_API_URL}${'/api/keys'}`;
            try {
                let response = await axios.get(
                    url,
                    {headers: {
                        "Authorization": `Bearer ${localStorage.getItem("AuthToken")}`
                    }}
                );
                setKeys(response.data.keys);
            } catch (error) {
                setError(error);
                setSection('error');
            }
        }
        fetchKeys();
    }, [setError, setSection]);

    const displayKeys = () => {
        return (
            <>
                {keys.map((key, index) => { return displaySingleKey(key, index)})}
            </>
        )
    }

    const displaySingleKey = (key, index) => {
        return (
            <div
                key={index}
            >
                hello world
            </div>
        )
    }

    return (
        <div className={styles.section}>
            <h1 className={styles.section_header}> Your Licence Keys </h1>
            <div className={styles.section_content}>
                <p className={styles.keys_header}> Keys </p>
                <div className={styles.keys_field}>
                    {displayKeys()}
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