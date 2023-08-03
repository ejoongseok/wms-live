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
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Entity
@Table(name = "lpn")
@Comment("LPN")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
