package com.roninhub.airbnb.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "profile")
@RequiredArgsConstructor
@Getter
@Setter
public class Profile {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "work")
    private String work;

    @Column(name = "about")
    private String about;

    @Column(name = "interests", columnDefinition = "text[]")
    @Type(ListArrayType.class)
    private List<String> interests;

    @Column(name = "status")
    private Integer status;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

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
