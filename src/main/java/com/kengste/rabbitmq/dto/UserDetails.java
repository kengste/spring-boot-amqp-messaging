package com.kengste.rabbitmq.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

// Data Transfer Object
public final class UserDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String supplierId;
    private final String supplierName;
    private final String supplierUrl;

    public UserDetails(@JsonProperty("supplierId") String supplierId,
                       @JsonProperty("supplierName") String supplierName,
                       @JsonProperty("supplierUrl") String supplierUrl) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierUrl = supplierUrl;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupplierUrl() {
        return supplierUrl;
    }

    @Override
    public String toString() {
        return "CrawlSupplierData [supplierId=" +
                supplierId + ", supplierName=" +
                supplierName + ", supplierUrl=" + supplierUrl + "]";
    }

}