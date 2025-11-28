package com.codeit.async.entiry;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "shipping_history")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ShippingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "timestamp with time zone", updatable = false, nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "order_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_shipping_order")
    )
    private Order order;

    @Column(length = 20, nullable = false)
    private String status; // REQUESTED, SHIPPED 등

    @Column(length = 500)
    private String message;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }
}
