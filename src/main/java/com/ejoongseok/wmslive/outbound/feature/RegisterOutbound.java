package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.outbound.domain.Order;
import com.ejoongseok.wmslive.outbound.domain.OrderRepository;
import com.ejoongseok.wmslive.outbound.domain.Outbound;
import com.ejoongseok.wmslive.outbound.domain.OutboundProduct;
import com.ejoongseok.wmslive.outbound.domain.OutboundRepository;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

class RegisterOutbound {
    private final OrderRepository orderRepository;
    private final OutboundRepository outboundRepository;

    RegisterOutbound(final OrderRepository orderRepository, final OutboundRepository outboundRepository) {
        this.orderRepository = orderRepository;
        this.outboundRepository = outboundRepository;
    }

    public void request(final Request request) {
        //주문 정보를 가져오고.
        final Order order = orderRepository.getBy(request.orderNo);

        // 주문정보에 맞는 상품의 재고가 충분한지 확인하고 충분하지 않으면 예외를 던진다.

        // 출고에 사용할 포장재를 선택해준다.

        // 출고를 생성하고.
        final List<OutboundProduct> outboundProducts = order.orderProducts().stream()
                .map(orderProduct -> new OutboundProduct(
                        orderProduct.product(),
                        orderProduct.orderQuantity(),
                        orderProduct.unitPrice()))
                .toList();

        final Outbound outbound = new Outbound(
                order.orderNo(),
                order.orderCustomer(),
                order.deliveryRequirements(),
                outboundProducts,
                request.isPriorityDelivery,
                request.desiredDeliveryAt
        );
        //출고를 등록한다.
        outboundRepository.save(outbound);
    }

    public record Request(
            Long orderNo,
            Boolean isPriorityDelivery,
            LocalDate desiredDeliveryAt) {

        public Request {
            Assert.notNull(orderNo, "주문번호는 필수입니다.");
            Assert.notNull(isPriorityDelivery, "우선출고여부는 필수입니다.");
            Assert.notNull(desiredDeliveryAt, "희망출고일은 필수입니다.");
        }
    }
}
