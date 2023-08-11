package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.outbound.domain.Outbound;
import com.ejoongseok.wmslive.outbound.domain.OutboundProduct;
import com.ejoongseok.wmslive.outbound.domain.OutboundProducts;
import com.ejoongseok.wmslive.outbound.domain.OutboundRepository;
import com.ejoongseok.wmslive.outbound.domain.OutboundSplitter;
import com.ejoongseok.wmslive.outbound.domain.PackagingMaterialRepository;
import com.ejoongseok.wmslive.outbound.domain.PackagingMaterials;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SplitOutbound {
    private final OutboundSplitter outboundSplitter = new OutboundSplitter();
    private final OutboundRepository outboundRepository;
    private final PackagingMaterialRepository packagingMaterialRepository;

    @PostMapping("/outbounds/split")
    @Transactional
    public void request(@RequestBody final Request request) {
        final Outbound outbound = outboundRepository.getBy(request.outboundNo);
        final OutboundProducts splitOutboundProducts = splitOutboundProducts(outbound, request.products);
        final PackagingMaterials packagingMaterials = new PackagingMaterials(packagingMaterialRepository.findAll());

        final Outbound splitted = outboundSplitter.splitOutbound(outbound, splitOutboundProducts, packagingMaterials);

        // 분할된 출고를 저장.
        outboundRepository.save(splitted);
    }

    private OutboundProducts splitOutboundProducts(
            final Outbound outbound,
            final List<Request.Product> products) {
        return new OutboundProducts(
                products.stream()
                        .map(product -> createOutboundProductToBeSplit(outbound, product))
                        .collect(Collectors.toList()));
    }

    private OutboundProduct createOutboundProductToBeSplit(final Outbound outbound, final Request.Product product) {
        return outbound.outboundProducts().createOutboundProductToBeSplit(product.productNo, product.quantity);
    }

    public record Request(Long outboundNo, List<Request.Product> products) {
        public Request {
            Assert.notNull(outboundNo, "출고번호가 없습니다.");
            Assert.notEmpty(products, "상품이 없습니다.");
        }

        public record Product(Long productNo, Long quantity) {
            public Product {
                Assert.notNull(productNo, "상품번호가 없습니다.");
                Assert.notNull(quantity, "수량이 없습니다.");
                if (1 > quantity) throw new IllegalArgumentException("수량이 1보다 작습니다.");
            }
        }
    }
}
