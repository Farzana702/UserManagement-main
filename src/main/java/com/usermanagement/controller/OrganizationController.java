package com.usermanagement.controller;
import com.usermanagement.entity.Organization;
import com.usermanagement.entity.UserOrganization;
import com.usermanagement.response.DefaultResponse;
import com.usermanagement.service.OrganizationService;
import com.usermanagement.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;
@RequestMapping("/")
@RestController
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;


    //Task 1 AddOrganization

    @PostMapping("addOrganization")
    public ResponseEntity addOrganization(@RequestBody Organization org,
                                          @RequestParam int orgAdmin,
                                          @RequestParam int pOrg,
                                          @RequestParam int createdBy,
                                          @RequestParam int roleId) {

        LOGGER.info("Received create organization request");
        if (Util.validateOrganizationRequest(org)) {
            return organizationService.saveOrganization(org, orgAdmin, pOrg, createdBy, roleId);
        } else {
            return new ResponseEntity<>(new DefaultResponse("Your request data isn't correct", "F02", org), HttpStatus.NOT_ACCEPTABLE);
        }

    }


    //Task 3 FetchAll Against Parent Organization


    @GetMapping("fetchAll")
    public ResponseEntity fetchAllByParentId(@RequestParam int id) {
        LOGGER.info("Received fetch all organization request");
        ResponseEntity<DefaultResponse> response;

        List<Organization> list = organizationService.fetchAllList(id);
        if (list.isEmpty()) {
            return response = new ResponseEntity<>(new DefaultResponse("Your request data isn't correct", "F02", null), HttpStatus.NOT_ACCEPTABLE);

        }
        return new ResponseEntity<List>(list, HttpStatus.OK);
    }
    //Task 4 Un Approved Organization List


    @GetMapping("unApprovedOrganizationList/{status}")
    public ResponseEntity<?> fetchAllUnapprovedUserOrganization(@PathVariable int status) {

        List<Organization> org = organizationService.fetchAllUnApprovedOrganization(status);
        return new ResponseEntity<>(org, HttpStatus.OK);
    }

    //Task 5 Fetch MultiAdminOrganization


    @GetMapping("fetchAllMultiAdminOrganizations")
    public ResponseEntity fetchAllMultiAdminOrganizations() {
        LOGGER.info("Received fetch all organization request");
        ResponseEntity<DefaultResponse> response;
        List<Organization> list = organizationService.fetchAllMultiAdminOrganizations();
        if (list.isEmpty()) {
            return response = new ResponseEntity<>(new DefaultResponse("No Multi_user available", "F01", null), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<List>(list, HttpStatus.OK);
    }


    //Status Active


    @PostMapping("statusActive/{orgId}")
    public ResponseEntity askAdminApprovelStatus(@PathVariable int orgId) throws Exception {
        LOGGER.info("Received fetch all organization request");
        try {
            return organizationService.statusActiveRequest(orgId);
        } catch (Exception e) {
            return new ResponseEntity<>(new DefaultResponse("Unable To Process Request!", "F01", null), HttpStatus.NOT_ACCEPTABLE);
        }


    }

    // Task  6 Organization Role Status Change Request

    @GetMapping("approvalRequest/orgId/{orgId}/userId/{userId}/newRole/{newRole}")
    public ResponseEntity<DefaultResponse> askAdminApprovalStatus(
            @PathVariable int orgId,
            @PathVariable int userId,
            @PathVariable int newRole) {

        LOGGER.info("Received fetch all organization request");
        try {
            return organizationService.approvalRequest(orgId, userId, newRole);
        } catch (Exception e) {

            return new ResponseEntity<>(new DefaultResponse("Unable To Process Request!", "F01", null), HttpStatus.NOT_ACCEPTABLE);
        }


    }




}











































