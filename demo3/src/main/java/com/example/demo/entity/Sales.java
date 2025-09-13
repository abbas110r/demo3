package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Sales {
    @Id
    private String saleID;
    private String date;
    private String customerID;

    private String customerName;

    private String customerContact;

    private String productID;

    private String productName;

    private Integer quantity;

    private Double unitPrice;

    private Double totalPrice;

    private Double subTotal;

    private Double tax;

    private Double totalAmount;

    private String paymentMethod;

    private String salesRep;

    private String deptID;

    private String deptName;

    private String storeNumber;


}
