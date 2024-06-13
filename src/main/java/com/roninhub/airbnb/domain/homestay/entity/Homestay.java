package com.roninhub.airbnb.domain.homestay.entity;


import com.roninhub.airbnb.domain.homestay.dto.response.HomestayDetail;
import com.roninhub.airbnb.domain.homestay.entity.address.Ward;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


//@NamedNativeQuery(
//        name = "Homestay.searchHomestay",
//        resultSetMapping = "HomestayDetailMapping",
//        query = """
//            select * from homestay inner join (
//                select h.id, avg(ha.price) as avg_price
//                from homestay h
//                join homestay_availability ha on h.id = ha.homestay_id
//                where ha.status = :status
//                and ha.date between :checkInDate and :checkOutDate
//                group by h.id
//                having count(ha.date) = (date :checkOutDate - date :checkInDate) + 1)
//            as vh using (id)
//            """
//)

@SqlResultSetMapping(
        name = "HomestayDetailMapping",
        classes = @ConstructorResult(
                targetClass = HomestayDetail.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "description", type = String.class),
                        @ColumnResult(name = "type", type = Integer.class),
                        @ColumnResult(name = "status", type = Integer.class),
                        @ColumnResult(name = "phone_number", type = String.class),
                        @ColumnResult(name = "address", type = String.class),
                        @ColumnResult(name = "guests", type = Integer.class),
                        @ColumnResult(name = "bedrooms", type = Integer.class),
                        @ColumnResult(name = "bathrooms", type = Integer.class),
                        @ColumnResult(name = "version", type = Long.class),
                        @ColumnResult(name = "avg_price", type = Double.class)
                }
        )
)


@Entity
@Table(name = "homestay")
@RequiredArgsConstructor
@Getter
@Setter
public class Homestay implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private Integer type;

    @Column(name = "status")
    private Integer status;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ward_id")
    private Ward ward;

    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "city_id")
    private String cityId;

    @Column(name = "province_id")
    private String provinceId;

    @Column(name = "guests")
    private Integer guests;

    @Column(name = "bedrooms")
    private Integer bedrooms;

    @Column(name = "bathrooms")
    private Integer bathrooms;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "homestay_amenity",
            joinColumns = @JoinColumn(name = "homestay_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<Amenity> amenities;

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

    public Homestay(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
