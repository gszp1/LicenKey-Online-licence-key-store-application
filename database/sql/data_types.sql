CREATE TYPE user_status AS ENUM ('active', 'deactivated', 'banned');
CREATE TYPE user_role AS ENUM ('user', 'admin');
CREATE TYPE licence_type AS ENUM ('monthly', 'trial', 'yearly', 'lifetime', 'weekly', 'daily')