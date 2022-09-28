package com.usermanagement.repository;

import com.usermanagement.entity.Organization;
import com.usermanagement.entity.UserOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserOrganizationRepository extends JpaRepository<UserOrganization, Integer> {

    //Active Organization Status
    @Modifying
    @Transactional
    @Query(value = "update  user_organization u set u.status =1 where u.organization_id_id=:orgId", nativeQuery = true)
    void updateOrganizationStatusFindById(int orgId);



    //Find All User Organization List
    List<UserOrganization> findAll();


    //Update Organization Role Query
    @Modifying
    @Transactional
    @Query(value = "update user_organization set organization_role_id_id = :newRole " +
            "WHERE organization_id_id = :orgId AND user_id_id = :userId", nativeQuery = true)
    void updateRole(@Param("orgId") Integer orgId, @Param("userId") Integer userId, @Param("newRole") Integer newRole);


/*
   @Query (value="SELECT *FROM user_organization
    WHERE organization_id_id IN (SELECT organization_id_id FROM user_organization GROUP BY organization_id_id
            HAVING COUNT(distinct user_id_id) > 1)",nativeQuery=true)



   @Query(value = "select * from user_organization o where o.organizaton_id_id In (select o.organization_id_id from user_organizaton o Group By o.organization_id_id having count(distinct o.user_id_id>1)", nativeQuery = true)
    List<UserOrganization> findAllMultiAdminOrganizationList();
*/

}


