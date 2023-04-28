package com.sms.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Roles extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column( name = "role_id")
    private int roleId;

    @Column( name = "role_name")
    private String roleName;

}
