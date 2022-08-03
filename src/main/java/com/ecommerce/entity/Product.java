package com.ecommerce.entity;

import com.ecommerce.entity.base.BaseEntity;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE products SET status_record = 'INACTIVE' WHERE id = ?")
@Where(clause = "status_record = 'ACTIVE'")
public class Product extends BaseEntity {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Min(1)
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Min(0)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Coupon discount;
//    @Lob
//    @Column(columnDefinition = "MEDIUMBLOB")
//    private String image;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @FieldNameConstants.Exclude
    @OneToOne
    @JoinColumn(name = "product_detail_id", foreignKey = @ForeignKey(name = "fk_product_product_detail_id"), referencedColumnName = "id")
    private ProductDetail productDetail;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @FieldNameConstants.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_product_category_id"), referencedColumnName = "id")
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "products_suppliers",
            joinColumns = @JoinColumn(name = "product_id"),
            foreignKey = @ForeignKey(name = "fk_products_suppliers_product_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id"),
            inverseForeignKey = @ForeignKey(name = "fk_products_suppliers_supplier_id")
    )
    private Set<Supplier> suppliers = new HashSet<>();

    public void addSupplier(Supplier supplier) {
        this.suppliers.add(supplier);
        supplier.getProducts().add(this);
    }

    public void removeSupplier(Supplier supplier) {
        this.getSuppliers().remove(supplier);
        supplier.getProducts().remove(this);
    }

    public void removeSuppliers() {
        for (Supplier supplier : new HashSet<>(suppliers)) {
            removeSupplier(supplier);
        }
    }
}
