import styles from "@/components/LicencePage/LicencePage.module.css"
import axios from "axios";
import { useEffect, useState } from "react";
import { useLocation } from "react-router";

const LicencePage = () => {
    const location = useLocation();
    const {licence} = location.state;
    const [description, setDescription] = useState(null);

    useEffect(() => {
        const fetchDescription = async () => {
            let apiPath = `/api/licences/${licence['licenceId']}/description`;
            let url = `${window._env_.BACKEND_API_URL}${apiPath}`;
            try {
                let response = await axios.get(url);
                setDescription(response.data.description)
            } catch (error) {
                setDescription('TODO' + error);            
            }
        }
     
        fetchDescription();
    }, [licence])

    return (
        <div className={styles.page}>
            <div className={styles.data_box}>
            </div>
            <div className={styles.description}>
                {description || 'Description for this licence was not provided.'}
            </div>
        </div>
    )
}

export default LicencePage;