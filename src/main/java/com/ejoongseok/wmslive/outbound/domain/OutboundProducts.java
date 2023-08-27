package com.ejoongseok.wmslive.outbound.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class OutboundProducts {
    @OneToMany(mappedBy = "outbound", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OutboundProduct> outboundProducts;

    public OutboundProducts(final List<OutboundProduct> outboundProducts) {
        this.outboundProducts = outboundProducts;
    }

    long splitTotalQuantity() {
        return toList().stream()
                .mapToLong(OutboundProduct::getOrderQuantity)
                .sum();
    }

    public List<OutboundProduct> toList() {
        return outboundProducts;
    }

    OutboundProduct getOutboundProductBy(final Long productNo) {
        return outboundProducts.stream()
                .filter(o -> o.isSameProductNo(productNo))
                .findFirst()
                .orElseThrow();
    }

    long calculateTotalOrderQuantity() {
        return outboundProducts.stream()
                .mapToLong(OutboundProduct::getOrderQuantity)
                .sum();
    }

    Long totalWeight() {
        return outboundProducts.stream()
                .mapToLong(OutboundProduct::calculateOutboundProductWeight)
                .sum();
    }

    Long totalVolume() {
        return outboundProducts.stream()
                .mapToLong(OutboundProduct::calculateOutboundProductVolume)
                .sum();
    }

    void removeIfZeroQuantity() {
        outboundProducts.removeIf(OutboundProduct::isZeroQuantity);
    }

    OutboundProduct createOutboundProductToBeSplit(final Long productNo, final Long quantity) {
        final OutboundProduct outboundProduct = getOutboundProductBy(productNo);
        return outboundProduct.split(quantity);
    }

    public List<Picking> getPickings() {
        return outboundProducts.stream()
                .flatMap(o -> o.getPickings().stream())
                .toList();
    }

    public Set<Long> getProductNos() {
        return outboundProducts.stream()
                .map(OutboundProduct::getProductNo)
                .collect(Collectors.toUnmodifiableSet());
    }
}