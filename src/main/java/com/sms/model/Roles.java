package com.sms.model;

import lombok.Data;

import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;

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
