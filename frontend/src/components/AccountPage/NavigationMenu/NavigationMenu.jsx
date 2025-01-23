import styles from "@/components/AccountPage/NavigationMenu/NavigationMenu.module.css"
import PropTypes from "prop-types";
import NavigationMenuEntry from "@/components/AccountPage/NavigationMenu/NavigationMenuEntry.jsx";

const NavigationMenu = ({clickAction, logoutAction}) => {
    return (
        <>
            <div className={styles.navigation_menu}>
                <NavigationMenuEntry
                    entryId={"information"}
                    clickAction={clickAction}
                    text={"Information"}
                />
                <NavigationMenuEntry
                    entryId={"modifyInformation"}
                    clickAction={clickAction}
                    text={"Modify Information"}
                />
                <NavigationMenuEntry
                    entryId={"changePassword"}
                    clickAction={clickAction}
                    text={"Change Password"}
                />
                <NavigationMenuEntry
                    entryId={"orders"}
                    clickAction={clickAction}
                    text={"Orders"}
                />
                <NavigationMenuEntry
                    entryId={"keys"}
                    clickAction={clickAction}
                    text={"Keys"}
                />
                <NavigationMenuEntry
                    entryId={"orders"}
                    clickAction={clickAction}
                    text={"Orders"}
                />
                <NavigationMenuEntry
                    entryId={"logout"}
                    clickAction={logoutAction}
                    text={"Logout"}
                />
            </div>
        </>
    );
}

NavigationMenu.propTypes = {
    clickAction: PropTypes.func.isRequired,
    logoutAction: PropTypes.func.isRequired
}

export default NavigationMenu;