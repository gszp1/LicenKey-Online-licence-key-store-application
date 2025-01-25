import styles from "@/components/AccountPage/Sections/ModifyInformation/ModifyInformationSection.module.css"
import axios from "axios";
import PropTypes from "prop-types";
import { useState } from "react";

const ModifyInformationSection = ({userData, setUserData, setError, setSection}) => {
    const [localUserData, setLocalUserData] = useState({...userData});
    const [prompt, setPrompt] = useState({'message': '', 'color': 'red'});

    const updateCredenials = (e, propertyName) => {
        setLocalUserData({...localUserData, [propertyName]: e.target.value});
    }

    const sendData = async () => {
        const sendData = {...localUserData};
        if (!sendData['username']) {
            setPrompt({'message': 'Username must be provided.', 'color': 'red'});
            return;
        }
        let url = `${window._env_.BACKEND_API_URL}${'/api/users'}`;
        try {
            let response = await axios.patch(
                url,
                sendData,
                {headers: {
                    'Authorization': `Bearer ${localStorage.getItem("AuthToken")}`,
                    'Content-Type': 'application/json'
                }}
            )
            setPrompt({'message': response.data.contents, 'color': 'green'});
            setUserData(sendData);
        } catch (error) {
            if (error.response) {
                if (error.response.status == 400 || error.response.status == 409) {
                    setPrompt({'message': error.response.data, 'color': 'red'});
                } else {
                    setError(error);
                    setSection('error');
                }
            } else {
                setError(error);
                setSection('error');
            }
        }
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
                    onChange={(e) => updateCredenials(e, 'firstName')}
                />
                <label className={styles.input_label}> Last Name </label>
                <input
                    className={styles.input_field}
                    value={localUserData['lastName']}
                    onChange={(e) => updateCredenials(e, 'lastName')}
                />
                <button
                    className={styles.modify_info_button}
                    onClick={sendData}
                >
                    Modify Information
                </button>
                <p 
                    className={styles.result_prompt}
                    style={{'color': prompt.color}}    
                > 
                    {prompt.message}
                </p>
            </div>
        </div>
    );
}

ModifyInformationSection.propTypes = {
    userData: PropTypes.object.isRequired,
    setError: PropTypes.func.isRequired,
    setSection: PropTypes.func.isRequired,
    setUserData: PropTypes.func.isRequired
}


export default ModifyInformationSection;