package com.usermanagement.service;
import com.usermanagement.entity.Organization;
import com.usermanagement.entity.OrganizationRole;
import com.usermanagement.entity.User;
import com.usermanagement.entity.UserOrganization;
import com.usermanagement.repository.OrganizationRepository;
import com.usermanagement.repository.OrganizationRoleRepository;
import com.usermanagement.repository.UserOrganizationRepository;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.response.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Objects;
@Service
public class UserOrganizationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private UserOrganizationRepository userOrganizationRepository;

    @Autowired
    private OrganizationRoleRepository organizationRoleRepository;

    public ResponseEntity<DefaultResponse> saveUserOrganization(int userId, int organizationId,
                                                                int organizationRoleId) {

        User userAdmin = userRepository.findById(userId);
        if (Objects.isNull(userAdmin)) {
            return new ResponseEntity<>(new DefaultResponse("Organization Admin Id not found!", "F01", null), HttpStatus.NOT_ACCEPTABLE);

        }

        Organization orgId = organizationRepository.findById(organizationId);
        if (Objects.isNull(orgId)) {

            return new ResponseEntity<>(new DefaultResponse("Organization Organization Id is not found!", "F01", null), HttpStatus.NOT_ACCEPTABLE);
        }
        OrganizationRole orgRole = organizationRoleRepository.findById(organizationRoleId);

        if (Objects.isNull(orgRole)) {

            return new ResponseEntity<>(new DefaultResponse("Organization  Role Id id not found!", "F01", null), HttpStatus.NOT_ACCEPTABLE);
        }
        try {

            UserOrganization userOrganization = new UserOrganization();
            userOrganization.setUserId(userAdmin);
            userOrganization.setOrganizationId(orgId);
            userOrganization.setOrganizationRoleId(orgRole);

            userOrganizationRepository.save(userOrganization);

        } catch (Exception e) {

            System.out.println("Error while saving User Organization. " + e);
        }
        return new ResponseEntity<>(new DefaultResponse(" User Organization  created successfully", "S01", null), HttpStatus.OK);

    }



}
