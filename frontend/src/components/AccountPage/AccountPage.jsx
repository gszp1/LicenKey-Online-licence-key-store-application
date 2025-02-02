import styles from "@/components/AccountPage/AccountPage.module.css"
import InformationSection from "@/components/AccountPage/Sections/Information/InformationSection.jsx"
import ModifyInformationSection from "@/components/AccountPage/Sections/ModifyInformation/ModifyInformationSection.jsx"
import OrdersSection from "@/components/AccountPage/Sections/Orders/OrdersSection.jsx"
import KeysSection from "@/components/AccountPage/Sections/Keys/KeysSection.jsx"
import ChangePasswordSection from "@/components/AccountPage/Sections/ChangePassword/ChangePasswordSection.jsx"
import { useEffect, useState } from "react";
import NavigationMenu from "./NavigationMenu/NavigationMenu"
import axios from "axios"
import { jwtDecode } from "jwt-decode"
import { useNavigate } from "react-router"
import ErrorSection from "@/components/AccountPage/Sections/Error/ErrorSection.jsx"

const AccountPage = () => {
    const [section, setSection] = useState("information")
    const [executionError, setExecutionError] = useState(null);
    const [userData, setUserData] = useState({
        'email': '',
        'username': '',
        'firstName': '',
        'lastName': '',
        'creationDate': ''
    });
    const navigate = useNavigate();
    
    useEffect(() => {
        const getUserData = async () => {
            let decodedToken = '';
            let email = '';
            try {
                decodedToken = decodeToken();
                email = decodedToken.sub;
            } catch (error) {
                setExecutionError(error);
                setSection("error");
            }
            let url = `${window._env_.BACKEND_API_URL}${'/api/users'}`;
            try {
                let response = await axios.post(
                    url,
                    {'email': email},
                    {headers: {
                        'Authorization': `Bearer ${localStorage.getItem("AuthToken")}`,
                        "Content-Type": 'application/json'
                    }}
                );
                let data = {
                    'email': response.data.email,
                    'username': response.data.username,
                    'firstName': response.data.firstName,
                    'lastName': response.data.lastName,
                    'creationDate': response.data.creationDate
                };
                setUserData(data);
            } catch (error) {
                setExecutionError(error);
                setSection("error");
            }
        }
        getUserData();
    }, []);

    const decodeToken = () => {
        return jwtDecode(localStorage.getItem("AuthToken"));
    }

    const renderSection = () => {
        switch (section) {
            case 'information':
                return (
                    <InformationSection
                        userData={userData}    
                    />
                )
            case 'modifyInformation':
                return (
                    <ModifyInformationSection
                        userData={userData}
                        setUserData={setUserData}
                        setError={setExecutionError}
                        setSection={setSection}
                    />
                )
            case 'changePassword':
                return (
                    <ChangePasswordSection
                        email={userData['email']}
                        setSection={setSection}
                        setError={setExecutionError}
                    />
                )
            case 'orders':
                return (
                    <OrdersSection/>
                )
            case 'keys':
                return (
                    <KeysSection
                        setError={setExecutionError}
                        setSection={setSection}
                    />
                )
            case 'error':
                return (
                    <ErrorSection
                        error={executionError}
                    />
                )
            default:
                <InformationSection
                    userData={userData}    
                />
        }
    }

    const changeSection = (e) => {
        setSection(e.currentTarget.id);
    }

    const logout = () => {
        localStorage.removeItem("AuthToken");
        setTimeout(() => {
            navigate("/");
        }, 400);
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
