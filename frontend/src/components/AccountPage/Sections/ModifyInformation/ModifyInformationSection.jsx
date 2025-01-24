import styles from "@/components/AccountPage/Sections/ModifyInformation/ModifyInformationSection.module.css"

const InputSection = ({userData, propertyName, header}) => {
    return (
        <>
            <label> {header} </label>
            <input></input>        
        </>
    );
}


const ModifyInformationSection = ({userData, errorHandler}) => {
    return (
        <div className={styles.section}>
            <h1 className={styles.section_header}> Modify Your Account Information </h1>
            <div className={styles.section_content}>
                <label> Email </label>
                <input></input>
                <label> Username </label>
                <input></input>
                <label> First Name </label>
                <input></input>
                <label> Last Name </label>
                <input></input>
            </div>
        </div>
    );
}


export default ModifyInformationSection;