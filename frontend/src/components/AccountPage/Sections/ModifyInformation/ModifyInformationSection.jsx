import styles from "@/components/AccountPage/Sections/ModifyInformation/ModifyInformationSection.module.css"
import PropTypes from "prop-types";
import { useState } from "react";

const ModifyInformationSection = ({userData}) => {
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
            </div>
        </div>
    );
}

ModifyInformationSection.propTypes = {
    userData: PropTypes.object.isRequired,
    errorHandler: PropTypes.object.isRequired
}


export default ModifyInformationSection;