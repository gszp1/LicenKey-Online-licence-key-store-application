import styles from "@/components/AccountPage/AccountPage.module.css"
import InformationSection from "@/components/AccountPage/Sections/Information/InformationSection.jsx"
import ModifyInformationSection from "@/components/AccountPage/Sections/ModifyInformation/ModifyInformationSection.jsx"
import OrdersSection from "@/components/AccountPage/Sections/Orders/OrdersSection.jsx"
import KeysSection from "@/components/AccountPage/Sections/Keys/KeysSection.jsx"
import ChangePasswordSection from "@/components/AccountPage/Sections/ChangePassword/ChangePasswordSection.jsx"

import { useState } from "react";
import NavigationMenu from "./NavigationMenu/NavigationMenu"
import axios from "axios"

const AccountPage = () => {
    const [section, setSection] = useState("information")
    const [userData, setUserData] = useState({
        'email': '',
        'username': '',
        'firstName': '',
        'lastName': '',
        'creationDate': ''
    });
    
    const decodeToken = () => {
        let decodedToken = jwt
    }

    const renderSection = () => {
        switch (section) {
            case 'information':
                return <InformationSection/>
            case 'modifyInformation':
                return <ModifyInformationSection/>
            case 'orders':
                return <OrdersSection/>
            case 'keys':
                return <KeysSection/>
            case 'changePassword':
                return <ChangePasswordSection/>
            default:
                return <InformationSection/>
        }
    }

    const changeSection = (e) => {
        setSection(e.currentTarget.id);
    }

    const logout = (e) => {
        console.log("TODO" + e);
    }

    return (
        <div className={styles.page}>
            <NavigationMenu
                clickAction={changeSection}
                logoutAction={logout}
            />
            {renderSection()}
        </div>
    );
}

export default AccountPage;
