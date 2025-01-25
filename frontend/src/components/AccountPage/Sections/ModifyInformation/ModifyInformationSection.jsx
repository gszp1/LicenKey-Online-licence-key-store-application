import styles from "@/components/AccountPage/Sections/ModifyInformation/ModifyInformationSection.module.css"
import PropTypes from "prop-types";
import { useState } from "react";

const ModifyInformationSection = ({userData, setError, setSection}) => {
    const [localUserData, setLocalUserData] = useState({...userData});
    
    const updateCredenials = (e, propertyName) => {
        setLocalUserData({...localUserData, [propertyName]: e.target.value});
    }

    return (
        <div className={styles.section}>
            <h1 className={styles.section_header}> Modify Your Account Information </h1>
            <div className={styles.section_content}>
                <label className={styles.input_label}> Email </label>
                <input
                    className={styles.input_field}
                    readOnly
                    value={localUserData['email']}
                />
                <label className={styles.input_label}> Username </label>
                <input
                   className={styles.input_field}
                    value={localUserData['username']}
                    onChange={(e) => updateCredenials(e, 'username')}
                />
                <label className={styles.input_label}> First Name </label>
                <input
                  className={styles.input_field}
                    value={localUserData['firstName']}
                />
                <label className={styles.input_label}> Last Name </label>
                <input
                    className={styles.input_field}
                    value={localUserData['lastName']}
                />
                <button
                    className={styles.modify_info_button}
                >
                    Modify Information
                </button>
            </div>
        </div>
    );
}

ModifyInformationSection.propTypes = {
    userData: PropTypes.object.isRequired,
    setError: PropTypes.func.isRequired,
    setSection: PropTypes.func.isRequired
}


export default ModifyInformationSection;