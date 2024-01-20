package israel.project.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.CharJdbcType;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JdbcType(CharJdbcType.class)
    @Column(unique = true, updatable = false)
    private UUID uuid;
    private int category;
    @Column(nullable = false)
    private java.util.Date startDate;
    @Column(nullable = false)
    private Date endDate;
    @Column(nullable = false)
    private int amount;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    private String image;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id")
    private Company company;
    @ManyToMany
    @JoinTable(name = "customer_coupon"
            , joinColumns = @JoinColumn(name = "coupon_id")
            , inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private Set<Customer> customers;
    @Version
    private Long version;

    @PrePersist
    public void randomUuid() {
        uuid = UUID.randomUUID();
    }
}