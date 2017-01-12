package com.shareads.user.myapplication;

import android.support.annotation.NonNull;
import android.util.Log;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jay Rambhia on 5/26/16.
 */
public class ValidationUtils {

    private static String MOBILE_NUMBER_REGEX = "[0-9][0-9]{9}$";
    private static String TEXT_WITH_MOBILE_NUMBER_REGEX = ".*[7-9][0-9]{9}.*";
    private static String TEXT_WITH_EMAIL_ADDRESS_REGEX = ".*[a-zA-Z0-9\\+\\" +".\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9\\+\\\" +\".\\_\\%\\-\\+]{1,64}\\.[a-zA-Z0-9]{1,25}.*";

    private static String USERNAME_REGEX = "^[a-zA-Z][a-zA-Z._0-9]{2,19}$";
    private static String TEXT_WITH_FOUR_CONSECUTIVE_NUMBERS_REGEX = ".*[0-9]{5,}.*";
    private static String WEBSITE_REGEX = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public static boolean isValidMobileNumber(String phNumber) {
        Pattern mPattern = Pattern.compile(MOBILE_NUMBER_REGEX);
        Matcher matcher = mPattern.matcher(phNumber);
        return matcher.find();
    }

    public static ValidationResult<String> isValidUsername(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure("name can't be blank", username);
        }

        if (username.length() < 3) {
            return ValidationResult.failure("name should have 3 or more characters", username);
        }

//        Pattern mPattern = Pattern.compile(USERNAME_REGEX);
//        Matcher matcher = mPattern.matcher(username);
        boolean isValid = username.length() >= 3;

        if (isValid) {
            return ValidationResult.success(username);
        }

        return ValidationResult.failure("username should contain only alphanumeric characters", username);
    }

    public static ValidationResult<String> isValidPicode(String picode) {
        if (picode.isEmpty()) {
            return ValidationResult.failure("pincode can't be blank", picode);
        }


        boolean isValid = picode.length() == 6;

        if (isValid) {
            return ValidationResult.success(picode);
        }

        return ValidationResult.failure("pincode must be only 6 dingit", picode);
    }

    public static ValidationResult<String> isValidWebsite(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure(null, username);
        }

        if (username.length() < 3) {
            return ValidationResult.failure("website should have 6 or more characters", username);
        }

//        Pattern mPattern = Pattern.compile(WEBSITE_REGEX);
//        Matcher matcher = mPattern.matcher(username);
//        boolean isValid = matcher.find();

//        if (android.util.Patterns.WEB_URL.matcher(username).matches()) {
//            return ValidationResult.success(username);
//        }
        if (username.contains("http://")) {
            return ValidationResult.success(username);
        }

        return ValidationResult.failure("website should be in form of wwww.google.com", username);
    }

    public static ValidationResult<String> isValidAddress(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure("address can't be null", username);
        }

        if (username.length() < 3) {
            return ValidationResult.failure("Address should have 3 or more characters", username);
        }else{
            return ValidationResult.success(username);
        }
    }

    public static ValidationResult<String> isValidState(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure(null, username);
        }

        if (username.length() < 2) {
            return ValidationResult.failure("State should have 2 or more characters", username);
        }else{
            return ValidationResult.success(username);
        }
    }

    public static ValidationResult<String> isValidCity(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure("city can't be blank", username);
        }

        if (username.length() < 2) {
            return ValidationResult.failure("City should have 2 or more characters", username);
        }else{
            return ValidationResult.success(username);
        }
    }

    public static ValidationResult<String> isValidPincode(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure("Pincode can't be blank", username);
        }

        if (username.length() != 6) {
            return ValidationResult.failure("Pincode  must have six digit only", username);
        }else{
            return ValidationResult.success(username);
        }
    }

    public static ValidationResult<String> isValidPassword(String password) {
        if (password.isEmpty()) {
            return ValidationResult.failure("Password can't be blank", password);
        }

        if (password.length() < 3) {
            return ValidationResult.failure("Password should have 3 or more characters", password);
        }else{
            return ValidationResult.success(password);
        }
    }

    public static ValidationResult<String> isValidCondirmPassword(String old,String newPassword) {
        if (newPassword.isEmpty()) {
            return ValidationResult.failure("Password can't be blank", newPassword);
        }

        if (!newPassword.toString().equals(old.trim()) ) {
            Log.e("OLD",""+ old);
            Log.e("NEW","" + newPassword);
            return ValidationResult.failure("Password doesn't match", newPassword);
        }else{
            return ValidationResult.success(newPassword);
        }
    }

    public static ValidationResult<String> isValidTime(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure(null, "\"Time cannot be blank");
        }

        return ValidationResult.failure("Time cannot be blank", username);
    }

    public static boolean containsFourConsecutiveNumbers(String text) {
        Pattern mPattern = Pattern.compile(TEXT_WITH_FOUR_CONSECUTIVE_NUMBERS_REGEX);
        Matcher matcher = mPattern.matcher(text);
        return matcher.find();
    }

    public static boolean containsMobileNumber(String text) {
        Pattern mPattern = Pattern.compile(TEXT_WITH_MOBILE_NUMBER_REGEX);
        Matcher matcher = mPattern.matcher(text);
        return matcher.find();
    }

    public static ValidationResult<String> isValidEmailAddress(@NonNull String text) {
        if (text.isEmpty()) {
            return ValidationResult.failure("email can't be blank", text);
        }
        Pattern mPattern = Pattern.compile(TEXT_WITH_EMAIL_ADDRESS_REGEX);
        Matcher matcher = mPattern.matcher(text);
        boolean isValid = matcher.find();

        if (isValid) {
            return ValidationResult.success(text);
        }

//        if (android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
//            return ValidationResult.success(text);
//        }

        return ValidationResult.failure("Please enter correct email address", text);
    }

    public static ValidationResult<String> isValidDescription(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure("Description can't be blank", username);
        }

        if (username.length() < 2) {
            return ValidationResult.failure("Description should have 10 or more characters", username);
        }else{
            return ValidationResult.success(username);
        }
    }

    public static ValidationResult<String> isValidPublicationName(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure("Publication can't be blank", username);
        }

        if (username.length() < 2) {
            return ValidationResult.failure("Publication should have 2 or more characters", username);
        }else{
            return ValidationResult.success(username);
        }
    }

    public static ValidationResult<String> isValidISBNNumber(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure("ISBN number can't be blank", username);
        }

        if (username.contains("-")) {
            return ValidationResult.failure("Please remove - from ISBN number", username);
        }
        if (username.length() < 10) {
            return ValidationResult.failure("ISBN number must be greater than 10 or more characters", username);
        }else{
            return ValidationResult.success(username);
        }
    }

    public static ValidationResult<String> isValidEdition(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure("Edition can't be blank", username);
        }

        if (username.length() < 3) {
            return ValidationResult.failure("Edition must be greater than 3 or more characters", username);
        }else{
            return ValidationResult.success(username);
        }
    }
    public static ValidationResult<String> isValidPageNumber(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure("Pages number  can't be blank", username);
        }

        if (username.equals("0")) {
            return ValidationResult.failure("Page number can't be zero", username);
        }else{
            return ValidationResult.success(username);
        }
    }

    public static ValidationResult<String> isValidPublishingYear(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure("Publishing Year can't be blank", username);
        }
        else{
            return ValidationResult.success(username);
        }
    }

    public static ValidationResult<String> isValidBookPrice(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure("Price can't be blank", username);
        }
        if (username.equals("0")) {
            return ValidationResult.failure("Book Price can't be Zero", username);
        }
        else{
            return ValidationResult.success(username);
        }
    }


}
