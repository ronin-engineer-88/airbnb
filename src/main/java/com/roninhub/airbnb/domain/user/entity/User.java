package com.roninhub.airbnb.domain.user.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "user")
@RequiredArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "type")
    private Integer type;

    @Column(name = "status")
    private Integer status;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, optional = false)
    private Profile profile;

    @Column(name = "extra_data")
    private String extraData;

    @Column(name = "version")
    private Long version;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;
}
