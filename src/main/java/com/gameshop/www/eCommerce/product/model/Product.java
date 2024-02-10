package com.gameshop.www.eCommerce.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.search.annotations.*;
import org.hibernate.type.SqlTypes;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Indexed
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Field(analyze = Analyze.NO)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "short_description")
    private String shortDescription;

    @Field(analyze = Analyze.NO)
    @Facet
    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "image_url")
    private String imageUrl;

    @Field
    @DateBridge(resolution = Resolution.HOUR)
    @SortableField
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Field
    @ElementCollection
    @IndexedEmbedded(includePaths = {"characteristics.*"})
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", name = "characteristics")
    private Map<String, Object> characteristics;

    @Field(analyze = Analyze.NO)
    @Facet
    @Column(name = "price_with_sale")
    private Integer priceWithSale;

    @OneToOne(mappedBy = "product", cascade = CascadeType.REMOVE, optional = false, orphanRemoval = true)
    private Inventory inventory;


    @ManyToOne(optional = false)
    @IndexedEmbedded(includePaths = {"name"})
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @IndexedEmbedded
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @IndexedEmbedded(includePaths = {"name"})
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}