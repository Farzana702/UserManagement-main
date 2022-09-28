package com.usermanagement.repository;

import com.usermanagement.entity.OrganizationRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRoleRepository extends JpaRepository<OrganizationRole,Integer> {

 OrganizationRole findById(int id);
}
