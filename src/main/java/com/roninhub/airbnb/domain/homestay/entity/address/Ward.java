package com.roninhub.airbnb.domain.homestay.entity.address;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ward")
@RequiredArgsConstructor
@Getter
@Setter
public class Ward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ward_name")
    private String wardName;
}
