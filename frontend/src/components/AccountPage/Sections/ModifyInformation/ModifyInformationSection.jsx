import styles from "@/components/AccountPage/Sections/ModifyInformation/ModifyInformationSection.module.css"
import PropTypes from "prop-types";
import { useState } from "react";

const ModifyInformationSection = ({userData, setError, setSection}) => {
    const [localUserData, setLocalUserData] = useState({...userData});
    
    const updateCredenials = (e, propertyName) => {
        let newLocalUserData = {...localUserData, [propertyName]: e.target.value};
        setLocalUserData(newLocalUserData);
    }

    return (
        <div className={styles.section}>
            <h1 className={styles.section_header}> Modify Your Account Information </h1>
            <div className={styles.section_content}>
                <label> Email </label>
                <input
                    readOnly
                    value={localUserData['email']}
                />
                <label> Username </label>
                <input
                    value={localUserData['username']}
                    onChange={(e) => updateCredenials(e, 'username')}
                />
                <label> First Name </label>
                <input
                    value={localUserData['firstName']}
                />
                <label> Last Name </label>
                <input
                    value={localUserData['lastName']}
                />
                <button>
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