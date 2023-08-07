package com.ejoongseok.wmslive.outbound.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class OrderCustomer {
    @Column(name = "order_customer_name", nullable = false)
    @Comment("주문고객 이름")
    private String name;
    @Column(name = "order_customer_email", nullable = false)
    @Comment("주문고객 이메일")
    private String email;
    @Column(name = "order_customer_phone", nullable = false)
    @Comment("주문고객 전화번호")
    private String phone;
    @Column(name = "order_customer_zip_no", nullable = false)
    @Comment("주문고객 우편번호")
    private String zipNo;
    @Column(name = "order_customer_address", nullable = false)
    @Comment("주문고객 주소")
    private String address;

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
