package com.ejoongseok.wmslive.outbound.domain;

public class OrderCustomerFixture {
    private String name = "name";
    private String email = "email";
    private String phone = "phone";
    private String zipNo = "zipNo";
    private String address = "address";

    public static OrderCustomerFixture anOrderCustomer() {
        return new OrderCustomerFixture();
    }

    public OrderCustomerFixture name(final String name) {
        this.name = name;
        return this;
    }

    public OrderCustomerFixture email(final String email) {
        this.email = email;
        return this;
    }

    public OrderCustomerFixture phone(final String phone) {
        this.phone = phone;
        return this;
    }

    public OrderCustomerFixture zipNo(final String zipNo) {
        this.zipNo = zipNo;
        return this;
    }

    public OrderCustomerFixture address(final String address) {
        this.address = address;
        return this;
    }

    public OrderCustomer build() {

        return new OrderCustomer(
                name,
                email,
                phone,
                zipNo,
                address
        );
    }
}