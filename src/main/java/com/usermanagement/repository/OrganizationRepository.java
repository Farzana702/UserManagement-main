package com.usermanagement.repository;
import com.usermanagement.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

    //Find By Name
    Organization findByName(String name);

    //Find By OrganizationId
    Organization findById(int id);

    //Find All Organization List
    List<Organization> findAllByParentOrganizationId(int id);

    //Find UnApproved Organization List
    @Query("SELECT o FROM Organization o WHERE o.status=?1")
    List<Organization> findAllUnApproved(int status);


    //Active Organization Status
    @Modifying
    @Transactional
    @Query("update  Organization u set u.status =1 where u.id=:orgId")
     void updateOrganizationStatusFindById(int orgId);




}
