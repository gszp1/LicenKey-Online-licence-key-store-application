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
                className={styles.key_entry}
                key={index}
            >
                <p className={styles.key_index}>{ index + 1 }</p>
                <img
                    className={styles.key_image}
                    src={key.imageUrl || '/src/assets/images/placeholder_img.png'}    
                />
                <div className={styles.key_data}>
                    <div className={styles.key_info_box}>
                        <div className={styles.key_identifier}>
                            <p className={styles.name}> {key.name}</p>
                            <p className={styles.id}> {key.licenceId}</p>
                        </div>
                        <div className={styles.key_info}>
                            <div className={styles.info_field}>
                                <p className={styles.text_header}> {"Platform:\u00A0"}</p>
                                <p className={styles.info_text}> {key.platform}</p>
                            </div>
                            <div className={styles.info_field}>
                                <p className={styles.text_header}> {"Publisher:\u00A0"}</p>
                                <p className={styles.info_text}> {key.publisher} </p>
                            </div>
                        </div>
                    </div>
                    <div className={styles.key_code}>
                        {key.keyCode || "Key code not yet obtained from publisher"}
                    </div>
                </div>
            </div>
        )
    }

    return (
        <div className={styles.section}>
            <h1 className={styles.section_header}> Your Licence Keys </h1>
            <div className={styles.section_content}>
                <p className={styles.keys_header}> {"Keys\u00A0\u00A0" + `(${keys ? keys.length : 0})`}</p>
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