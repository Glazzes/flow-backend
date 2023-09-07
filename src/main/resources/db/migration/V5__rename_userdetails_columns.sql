alter table user_details rename column is_active to is_enabled;
alter table user_details rename column is_non_locked to is_account_non_locked;
alter table user_details rename column has_not_expired to is_account_non_expired;
alter table user_details rename column are_credentials_not_expired to is_credentials_not_expired;
