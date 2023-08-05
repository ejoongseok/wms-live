package com.ejoongseok.wmslive.inbound.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * 본 프로젝트는 입고(Inbound) 상품(InboundItem) 중 유통기한이 같은 상품들의 논리적인 집합을 LPN이라고 정의한다.
 * 단, 같은 상품의 같은 유통기한을 가진 상품이 여러번 들어오더라도 입고(InboundId)및 입고상품(InboundItemId)이 다르면 LPN이 다르다.
 * Inbound(1) -> InboundItem(n) -> LPN(n)
 * A입고의 입고상품에 유통기한만 다른 상품이 2개 들어온 경우
 * 입고상품에 대한 LPN은 2개가 된다.
 * 전산상 입고를 등록할때는 입고상품의 유통기한을 입력하지 않는다.(발주를 넣은 입고상품에 유통기한이 같은지 다른지 알 수 없기 때문이다.)
 * 유통기한을 확인하는건 실물 상품을 입고하고 분류할 때이다.
 */
@Entity
@Table(name = "lpn")
@Comment("LPN")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "lpnBarcode", callSuper = false)
public class LPN {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("LPN 번호")
    private Long lpnNo;
    @Column(name = "lpn_barcode", nullable = false, unique = true)
    @Comment("LPN 바코드 (중복 불가)")
    private String lpnBarcode;
    @Column(name = "expiration_at", nullable = false)
    @Comment("유통기한")
    private LocalDateTime expirationAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inbound_item_id", nullable = false)
    @Comment("입고 아이템 ID")
    private InboundItem inboundItem;

    public LPN(
            final String lpnBarcode,
            final LocalDateTime expirationAt,
            final InboundItem inboundItem) {
        validateConstructor(lpnBarcode, expirationAt, inboundItem);
        this.lpnBarcode = lpnBarcode;
        this.expirationAt = expirationAt;
        this.inboundItem = inboundItem;
    }

    private void validateConstructor(
            final String lpnBarcode,
            final LocalDateTime expirationAt,
            final InboundItem inboundItem) {
        Assert.hasText(lpnBarcode, "LPN 바코드는 필수입니다.");
        Assert.notNull(expirationAt, "유통기한은 필수입니다.");
        Assert.notNull(inboundItem, "입고 상품은 필수입니다.");
    }
}
