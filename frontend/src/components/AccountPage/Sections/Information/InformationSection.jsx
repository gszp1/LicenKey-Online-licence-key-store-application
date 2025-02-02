import styles from "@/components/AccountPage/Sections/Information/InformationSection.module.css"
import PropTypes from "prop-types";
import {format} from 'date-fns'

const DataEntry = ({header, content}) => {
    return (
        <>
            <h2 className={styles.data_header}>
                {header}
            </h2>
            <p className={styles.data_field}>
                {content== null ? "Not-Specified": content}
            </p>
        </>
    )
}

const InformationSection = ({userData}) => {
    return (
        <div className={styles.section}>
            <h1 className={styles.section_header}> 
                Account Information
            </h1>
            <div className={styles.section_content}>
                <div className={styles.data_box}>
                    <DataEntry
                        header={"Email"}
                        content={userData.email}
                    />
                    <DataEntry
                        header={"Username"}
                        content={userData.username}
                    />
                </div>
                <div className={styles.data_box}>
                    <DataEntry
                        header={"First Name"}
                        content={userData.firstName}
                    />
                    <DataEntry
                        header={"Last Name"}
                        content={userData.lastName}
                    />
                    <DataEntry
                        header={"Join Date"}
                        content={userData.creationDate ? format(new Date(userData.creationDate), 'dd-MM-yyyy') : null}
                    />
                </div>
            </div>
        </div>
    )
}

InformationSection.propTypes = {
    userData: PropTypes.object.isRequired
}

DataEntry.propTypes = {
    header: PropTypes.string.isRequired,
    content: PropTypes.string 
}

export default InformationSection;