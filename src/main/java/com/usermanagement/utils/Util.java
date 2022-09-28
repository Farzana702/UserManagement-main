package com.usermanagement.utils;

import com.usermanagement.controller.UserController;
import com.usermanagement.entity.Organization;
import com.usermanagement.entity.User;
import com.usermanagement.entity.UserOrganization;
import com.usermanagement.response.DefaultResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Base64;

public class Util {

    private static final Logger LOGGER = LogManager.getLogger(Util.class);

    public static String encryptStringToBase64(String text) throws Exception {
        String base64String = "";
        try {
            base64String = new String(Base64.getEncoder().encode(text.getBytes()));
        } catch (Exception e) {
            throw new Exception("Unable to encrypt password");
        }

        return base64String;
    }


    public static String decryptStringToBase64(String text) throws Exception {
        String base64String = "";
        try {
            base64String = new String(Base64.getDecoder().decode(text.getBytes()));
        } catch (Exception e) {
            throw new Exception("Unable to decrypt password");
        }

        return base64String;
    }

    //validateSignupRequest
    public static boolean ValidateSigUpRequest(User request, Integer roleId, Integer createdById) {

        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            LOGGER.info("Email is required");
            return false;
        } else if (request.getPassword() == null || request.getPassword().isEmpty()) {
            LOGGER.info("Password is required");
            return false;
        } else if (roleId == null || roleId <= 0) {
            LOGGER.info("Role Id must be greater than zero");
            return false;
        } else if (createdById == null || createdById <= 0) {
            LOGGER.info("Created by Id must be greater than zero");
            return false;
        } else
            return true;

    }

    //validateOrganizationRequest

    public static boolean validateOrganizationRequest(Organization request) {

        if (Strings.isBlank(request.getName())) {
            LOGGER.info("Name is required");
            return false;
        }
        if (request.getAddress() == null || request.getAddress().isEmpty()) {
            LOGGER.info("Address is required");
            return false;
        }
        return true;
    }

    //validateUserOrganizationRequest
    public static boolean validateUserOrganizationRequest(Integer userId, Integer organizationId) {

        if (userId == null) {
            LOGGER.info("userId is required");
            return false;
        }
        if (organizationId == null) {
            LOGGER.info("OrganizationId is required");
            return false;
        }


        return true;


    }
}