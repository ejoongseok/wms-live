package com.ejoongseok.wmslive.outbound.domain;

class OrderCustomer {
    private final String name;
    private final String email;
    private final String phone;
    private final String zipNo;
    private final String address;

    public OrderCustomer(
            final String name,
            final String email,
            final String phone,
            final String zipNo,
            final String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.zipNo = zipNo;
        this.address = address;
    }
}
