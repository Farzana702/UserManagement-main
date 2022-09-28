package com.usermanagement.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Getter
@Setter
public class UserOrganization implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User userId ;



    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Organization organizationId;
    private int status;


    @ManyToOne
    @JoinColumn
    private OrganizationRole organizationRoleId;


    }

