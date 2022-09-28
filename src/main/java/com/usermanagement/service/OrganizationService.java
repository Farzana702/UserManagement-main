package com.usermanagement.service;

import com.usermanagement.entity.*;
import com.usermanagement.repository.*;
import com.usermanagement.response.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserOrganizationRepository userOrganizationRepository;

    @Autowired
    private OrganizationRoleRepository organizationRoleRepository;

    //Add Organization

    public ResponseEntity<DefaultResponse> saveOrganization(
            Organization org, int orgAdmin, int pOrg, int createBy, int role) {


        if (organizationRepository.findByName(org.getName()) != null) {
            return new ResponseEntity<>(new DefaultResponse("Organization Name already exist!", "F01", null), HttpStatus.NOT_ACCEPTABLE);
        }
        User orgAdminId = userRepository.findById(orgAdmin);

        if (orgAdminId == null) {
            return new ResponseEntity<>(new DefaultResponse("Organization Admin  not found", "F01", null), HttpStatus.NOT_ACCEPTABLE);

        }
        Organization parentOrg = organizationRepository.findById(pOrg);

        if (parentOrg == null) {
            return new ResponseEntity<>(new DefaultResponse("Parent Organization Admin  not found", "F01", null), HttpStatus.NOT_ACCEPTABLE);

        }

        User createdBy = userRepository.findById(createBy);

        if (createdBy == null) {
            return new ResponseEntity<>(new DefaultResponse("Organization Created By  not found", "F01", null), HttpStatus.NOT_ACCEPTABLE);
        }
        OrganizationRole roleId = organizationRoleRepository.findById(role);
        if (roleId == null) {
            return new ResponseEntity<>(new DefaultResponse("Role Id for organization not found", "F01", null), HttpStatus.NOT_ACCEPTABLE);

        }

        try {
            org.setOrganizationAdmin(orgAdminId);
            org.setParentOrganization(parentOrg);
            org.setCreatedBy(createdBy);
            org.setUpdatedBy(null);
            org.setStatus(0);

            organizationRepository.save(org);
        } catch (Exception e) {
            return new ResponseEntity<>(new DefaultResponse("Error while saving Organization!", "S02", null), HttpStatus.OK);
        }

        UserOrganization userOrganization = new UserOrganization();

        userOrganization.setUserId(orgAdminId);
        userOrganization.setOrganizationRoleId(roleId);
        userOrganization.setOrganizationId(org);
        userOrganizationRepository.save(userOrganization);
        return new ResponseEntity<>(new DefaultResponse("Organization And User Organization  Saved Successfully!", "S02", null), HttpStatus.OK);

    }

    //UnApproved list

    public List<Organization> fetchAllUnApprovedOrganization(int status) {

        List<Organization> organizationList;
        organizationList = organizationRepository.findAllUnApproved(status);

        return organizationList;
    }

    public List<Organization> fetchAllList(Integer parentOrganizationId) {

        //List<Organization> list = organizationRepository.findAllByParentOrganizationId(parentOrganizationId);

        return organizationRepository.findAllByParentOrganizationId(parentOrganizationId);
    }


    public ResponseEntity<DefaultResponse> approvalRequest(
            int orgId, int userId, int newRole) {
        User uId = userRepository.findById(userId);
        if (uId == null) {
            return new ResponseEntity<>(new DefaultResponse("Organization Admin Not Found!", "F01", null), HttpStatus.NOT_FOUND);
        }
        try {
            if (uId.getRole().getId() == 1) {
                userOrganizationRepository.updateRole(orgId, userId, newRole);


            } else {

                return new ResponseEntity<>(new DefaultResponse("Something Wrong !Unable To Process Your Request !", "F01", null), HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new DefaultResponse("Unable To Processed Successfully!", "F01", null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new DefaultResponse("Organization Role Updated  Successfully!", "S01", null), HttpStatus.OK);
    }


    //Approval Request


    public ResponseEntity<DefaultResponse> statusActiveRequest(int orgId) {
        Organization org = organizationRepository.findById(orgId);
        try {
            if (org != null) {
                if (org.getOrganizationAdmin().getRole().getId() == 1) {
                    organizationRepository.updateOrganizationStatusFindById(orgId);
                    userOrganizationRepository.updateOrganizationStatusFindById(orgId);

                }
            } else {
                return new ResponseEntity<>(new DefaultResponse("Something Wrong !Unable To Process Your Request !", "F01", null), HttpStatus.NOT_ACCEPTABLE);
            }

        } catch (Exception e) {

            return new ResponseEntity<>(new DefaultResponse("Organization Status Cannot Be Updated  successfully!", "F01", null), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(new DefaultResponse("Organization Status  Updated  Successfully!", "S01", null), HttpStatus.ACCEPTED);
    }


    //Fetch MultiAdminOrganization


    public List<Organization> fetchAllMultiAdminOrganizations() {
        Map<Integer, Integer> orgIdCount = new HashMap<>();
        List<Organization> multiAdminOrganizationsList = new ArrayList<>();
        try {
            List<UserOrganization> allUserOrganizations = userOrganizationRepository.findAll();
            if (Objects.nonNull(allUserOrganizations) && !allUserOrganizations.isEmpty())
                for (UserOrganization org : allUserOrganizations) {
                    if (org.getOrganizationRoleId().getId() == 1) {
                        int orgId = org.getOrganizationId().getId();
                        if (orgIdCount.containsKey(orgId)) {
                            multiAdminOrganizationsList.add(organizationRepository.findById(orgId));
                        } else {
                            orgIdCount.put(orgId, 1);
                        }
                    }
                }
        } catch (Exception e) {
            System.out.println("Exception while fetching organization" + e);
        }
        return multiAdminOrganizationsList;

    }


}

















/*
    public List<UserOrganization> fetchAllMultiAdminOrganizations() {


        List<UserOrganization> list = userOrganizationRepository.findAllMultiAdminOrganizationList();
        return list;
    }


*/


