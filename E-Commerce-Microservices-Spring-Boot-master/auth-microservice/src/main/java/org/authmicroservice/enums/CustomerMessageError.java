package org.authmicroservice.enums;

public enum CustomerMessageError {
    USER_NOT_FOUND("User not found"),
    USER_NOT_FOUND_WITH_ID_EQUALS("User not found with ID = "),
    EMAIL_ALREADY_EXISTS("Email already exists."),
    ID_IS_INVALID_EQUALS("ID is invalid. ID = "),
    REGISTER_ERROR("An error occurred. Please try again later."),
    INTERNAL_SERVER_ERROR("Server error"),
    USER_NOT_AUTHORIZED("Unauthorized"),
    EMAIL_ALREADY_EXIST("Email address already in use."),
    PASSWORD_TOKEN_EXPIRED("Request expired. Please resend a new password reset request."),
    EMAIL_INVALID("Invalid email address."),
    FIRSTNAME_INVALID("Invalid first name. It should only contain characters."),
    LASTNAME_INVALID("Invalid last name. It should only contain characters."),
    PASSWORD_MATCH_ERROR("The entered password does not match the confirmation password."),
    ACCOUNT_NOT_CONFIRMED("Your account is not confirmed. Please check your email!"),
    PASSWORD_LENGTH_ERROR("The password must contain at least 8 characters. And the password must contain at least one special character, one lowercase letter, one uppercase letter, and one digit."),
    PHONE_INVALID("Invalid phone number."),
    ROLE_ALREADY_EXIST("This role already exists."),
    EMAIL_ACC_NOT_EXIST("This email address is not registered. Please sign up."),
    USER_NOT_FOUND_WITH_EMAIL_EQUALS("User not found with email = "),
    USER_NOT_FOUND_WITH_USERNAME_EQUALS("User not found with username = "),
    USERNAME_IS_REQUIRED("Username is required."),
    FIRSTNAME_IS_REQUIRED("First name is required."),
    LASTNAME_IS_REQUIRED("Last name is required."),
    PASSWORD_IS_REQUIRED("Password is required."),
    EMAIL_IS_REQUIRED("Email is required."),
    EMAIL_IS_INVALID("Email is invalid, please check the format."),
    PHONE_NUMBER_NOT_VALID("Phone number not valid."),
    INVALID_REQUEST("Invalid request."),
    PASSWORDS_DO_NOT_MATCH("Passwords do not match.");

    private final String msg;
    CustomerMessageError(String msg){
        this.msg = msg;
    }
    public String getMessage(){
        return this.msg;
    }
}
