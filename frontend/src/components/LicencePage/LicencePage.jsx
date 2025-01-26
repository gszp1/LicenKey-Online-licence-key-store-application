import styles from "@/components/LicencePage/LicencePage.module.css"
import { useLocation } from "react-router";

const LicencePage = () => {
    const location = useLocation();
    const {licence} = location.state;

    console.log(licence);
    return (
        <div className={styles.page}>
        </div>
    )
}

export default LicencePage;