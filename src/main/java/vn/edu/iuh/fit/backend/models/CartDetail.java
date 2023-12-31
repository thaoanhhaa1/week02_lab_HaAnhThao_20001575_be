package vn.edu.iuh.fit.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "cart_detail")
@NamedQueries({
        @NamedQuery(name = "CartDetail.countByCustomer", query = "SELECT count(*) FROM CartDetail where cart.customer.id = :customerId"),
        @NamedQuery(name = "CartDetail.getCartDetailsByCustomerId", query = "From CartDetail where cart.customer.id = :customerId order by product.id"),
        @NamedQuery(name = "CartDetail.findById", query = "FROM CartDetail WHERE product.id = :productId AND cart.customer.id = :cartId"),
        @NamedQuery(name = "CartDetail.delete", query = "DELETE CartDetail WHERE product.id = :productId AND cart.customer.id = :cartId"),
        @NamedQuery(name = "CartDetail.addQty", query = "UPDATE CartDetail SET quantity = quantity + :qty WHERE product.id = :productId AND cart.customer.id = :cartId"),
        @NamedQuery(name = "CartDetail.getByProductIds", query = "FROM CartDetail where product.id in :productIds AND cart.customer.id = :cartId"),
        @NamedQuery(name = "CartDetail.deleteByProductIds", query = "DELETE CartDetail WHERE product.id in :productIds AND cart.customer.id = :cartId")
})
public class CartDetail {
    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", referencedColumnName = "cust_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cart cart;
    @Column(name = "qty")
    private int quantity;

    public CartDetail() {
    }

    public CartDetail(Product product, Cart cart) {
        this.product = product;
        this.cart = cart;
    }

    public CartDetail(Product product, Cart cart, int quantity) {
        this.product = product;
        this.cart = cart;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartDetail{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
