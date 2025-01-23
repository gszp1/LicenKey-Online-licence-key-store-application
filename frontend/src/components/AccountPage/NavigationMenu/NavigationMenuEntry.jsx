import styles from "@/components/AccountPage/NavigationMenu/NavigationMenu.module.css"
import PropTypes from "prop-types";

const navigationMenuEntry = ({entryId, clickAction, text}) => {
    return (
        <div
            id={entryId}
            className={styles.navigation_menu_entry}
            onClick={clickAction}
        >
            {text}
        </div>
    );
}

navigationMenuEntry.propTypes = {
    entryId: PropTypes.string.isRequired,
    clickAction: PropTypes.func.isRequired,
    text: PropTypes.string.isRequired
}

export default navigationMenuEntry;