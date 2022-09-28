package com.usermanagement.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    private Integer status;
    @CreationTimestamp
    private java.sql.Timestamp createdAt;
    @UpdateTimestamp
    private java.sql.Timestamp updatedAt;
    private Timestamp lastLogin;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)//self relation with user
    @JoinColumn
    private User createdBy;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    @JsonIgnore
    @JoinColumn
    private Role role;

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "createdBy")
    private Set<Organization> organization;


}
