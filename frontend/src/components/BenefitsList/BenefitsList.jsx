import styles from "@/components/BenefitsList/BenefitsList.module.css"
import PropTypes from "prop-types";
import ListAltIcon from '@mui/icons-material/ListAlt';
import KeyIcon from '@mui/icons-material/Key';
import { Stack } from "@mui/material";
import AccountCircleIcon from '@mui/icons-material/AccountCircle';

const BenefitsList = () => {
    return (
            <div>
                <p className={styles.benefits_prompt}>
                    What are the benefits of having an account?
                </p>
                <ul className={styles.benefits_list}>
                    <BenefitsListEntry
                        icon={<ListAltIcon sx={{fontSize: '3rem', color: 'orange'}}/>}
                        text="Check previous orders"
                    />
                    <BenefitsListEntry
                        icon={<KeyIcon sx={{fontSize: '3rem', color: 'DeepSkyBlue'}}/>}
                        text="See keys you have bought"
                    />
                    <BenefitsListEntry
                        icon={<AccountCircleIcon sx={{fontSize: '3rem', color: 'orange'}}/>}
                        text="Easily validate your credentials"
                    />
                </ul>
            </div>
    );
}

const BenefitsListEntry = ({icon, text}) => {
    return (
        <li> 
        <Stack direction="row" alignItems="center" gap={1}>
            {icon}
            <p className={styles.li_text}>
                {text}    
            </p>
        </Stack>
    </li>
    );
}

BenefitsListEntry.propTypes = {
    icon: PropTypes.element,
    text: PropTypes.string
}

export default BenefitsList;