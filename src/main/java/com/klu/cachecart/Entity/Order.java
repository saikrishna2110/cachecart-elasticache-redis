package com.klu.cachecart.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String id;

    private String userId;

    private double subtotal;
    private double discount;
    private double delivery;
    private double total;

    private String status;
    private String date;

    private String shippingName;
    private String shippingEmail;
    private String shippingPhone;
    private String shippingAddress;

    private String paymentMethod;

    @Transient
    private List<OrderItem> items;

    public Order() {
    }

    public Order(
            String id,
            String userId,
            double subtotal,
            double discount,
            double delivery,
            double total,
            String status,
            String date,
            String shippingName,
            String shippingEmail,
            String shippingPhone,
            String shippingAddress,
            String paymentMethod) {

        this.id = id;
        this.userId = userId;
        this.subtotal = subtotal;
        this.discount = discount;
        this.delivery = delivery;
        this.total = total;
        this.status = status;
        this.date = date;
        this.shippingName = shippingName;
        this.shippingEmail = shippingEmail;
        this.shippingPhone = shippingPhone;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDelivery() {
        return delivery;
    }

    public void setDelivery(double delivery) {
        this.delivery = delivery;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingEmail() {
        return shippingEmail;
    }

    public void setShippingEmail(String shippingEmail) {
        this.shippingEmail = shippingEmail;
    }

    public String getShippingPhone() {
        return shippingPhone;
    }

    public void setShippingPhone(String shippingPhone) {
        this.shippingPhone = shippingPhone;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}