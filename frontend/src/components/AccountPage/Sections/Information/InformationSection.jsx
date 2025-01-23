import styles from "@/components/AccountPage/Sections/Information/InformationSection.module.css"
import PropTypes from "prop-types";

const InformationSection = ({userData}) => {
    return (
        <div className={styles.section}>
            <h1 className={styles.section_header}> 
                Account Information
            </h1>
            <div className={styles.section_content}>
                <h2>Email</h2>
                <p>{userData.email}</p>
                <h2>Username</h2>
                <p>{userData.username}</p>
                <h2>First Name</h2>
                <p>{userData.firstName== null ? "Not-Specified": userData.firstName}</p>
                <h2>Last Name</h2>
                <p>{userData.lastName == null ? "Not-Specified": userData.lastName}</p>
                <h2>Join Date</h2>
                <p>{Date(userData.creationDate)}</p>
            </div>
        </div>
    )
}

InformationSection.propTypes = {
    userData: PropTypes.object.isRequired
}

export default InformationSection;