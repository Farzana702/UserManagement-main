package com.usermanagement.controller;

import com.usermanagement.response.DefaultResponse;
import com.usermanagement.service.UserOrganizationService;
import com.usermanagement.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserOrganizationController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    @Autowired
    UserOrganizationService userOrganizationService;

    //Task 2 addUserOrganization

    @PostMapping("addUserOrganization")
    public ResponseEntity saveUserOrganization(
            @RequestParam int userId,
            @RequestParam int organizationId,
            @RequestParam int organizationRoleId) {

        LOGGER.info("Received crate user request");

        ResponseEntity<DefaultResponse> response;

        if (Util.validateUserOrganizationRequest(userId, organizationId)) {

            response = userOrganizationService.saveUserOrganization(userId, organizationId, organizationRoleId);

        } else {
            response = new ResponseEntity<>(new DefaultResponse("Your request data isn't correct", "F02", null), HttpStatus.NOT_ACCEPTABLE);
        }

        return response;

    }
}