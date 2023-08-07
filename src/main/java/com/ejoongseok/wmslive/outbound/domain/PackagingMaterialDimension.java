package com.ejoongseok.wmslive.outbound.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PackagingMaterialDimension {
    @Column(name = "inner_width", nullable = false)
    @Comment("내부 폭 (mm)")
    private Long innerWidthInMillimeters;
    @Column(name = "inner_height", nullable = false)
    @Comment("내부 높이 (mm)")
    private Long innerHeightInMillimeters;
    @Column(name = "inner_length", nullable = false)
    @Comment("내부 길이 (mm)")
    private Long innerLengthInMillimeters;
    @Column(name = "outer_width", nullable = false)
    @Comment("외부 폭 (mm)")
    private Long outerWidthInMillimeters;
    @Column(name = "outer_height", nullable = false)
    @Comment("외부 높이 (mm)")
    private Long outerHeightInMillimeters;
    @Column(name = "outer_length", nullable = false)
    @Comment("외부 길이 (mm)")
    private Long outerLengthInMillimeters;

    public PackagingMaterialDimension(
            final Long innerWidthInMillimeters,
            final Long innerHeightInMillimeters,
            final Long innerLengthInMillimeters,
            final Long outerWidthInMillimeters,
            final Long outerHeightInMillimeters,
            final Long outerLengthInMillimeters) {
        validateConstructor(
                innerWidthInMillimeters,
                innerHeightInMillimeters,
                innerLengthInMillimeters,
                outerWidthInMillimeters,
                outerHeightInMillimeters,
                outerLengthInMillimeters);
        this.innerWidthInMillimeters = innerWidthInMillimeters;
        this.innerHeightInMillimeters = innerHeightInMillimeters;
        this.innerLengthInMillimeters = innerLengthInMillimeters;
        this.outerWidthInMillimeters = outerWidthInMillimeters;
        this.outerHeightInMillimeters = outerHeightInMillimeters;
        this.outerLengthInMillimeters = outerLengthInMillimeters;
    }

    private void validateConstructor(
            final Long innerWidthInMillimeters,
            final Long innerHeightInMillimeters,
            final Long innerLengthInMillimeters,
            final Long outerWidthInMillimeters,
            final Long outerHeightInMillimeters,
            final Long outerLengthInMillimeters) {
        Assert.notNull(innerWidthInMillimeters, "내부 폭은 필수입니다.");
        if (1 > innerWidthInMillimeters) {
            throw new IllegalArgumentException("내부 폭은 1mm 이상이어야 합니다.");
        }
        Assert.notNull(innerHeightInMillimeters, "내부 높이는 필수입니다.");
        if (1 > innerHeightInMillimeters) {
            throw new IllegalArgumentException("내부 높이는 1mm 이상이어야 합니다.");
        }
        Assert.notNull(innerLengthInMillimeters, "내부 길이는 필수입니다.");
        if (1 > innerLengthInMillimeters) {
            throw new IllegalArgumentException("내부 길이는 1mm 이상이어야 합니다.");
        }
        Assert.notNull(outerWidthInMillimeters, "외부 폭은 필수입니다.");
        if (1 > outerWidthInMillimeters) {
            throw new IllegalArgumentException("외부 폭은 1mm 이상이어야 합니다.");
        }
        Assert.notNull(outerHeightInMillimeters, "외부 높이는 필수입니다.");
        if (1 > outerHeightInMillimeters) {
            throw new IllegalArgumentException("외부 높이는 1mm 이상이어야 합니다.");
        }
        Assert.notNull(outerLengthInMillimeters, "외부 길이는 필수입니다.");
        if (1 > outerLengthInMillimeters) {
            throw new IllegalArgumentException("외부 길이는 1mm 이상이어야 합니다.");
        }
    }

    public Boolean isAvailable(final Long totalVolume) {
        return totalVolume <= getInnerVolume();
    }

    private Long getInnerVolume() {
        return innerWidthInMillimeters * innerHeightInMillimeters * innerLengthInMillimeters;
    }

    public Long outerVolume() {
        return outerWidthInMillimeters * outerHeightInMillimeters * outerLengthInMillimeters;
    }
}
