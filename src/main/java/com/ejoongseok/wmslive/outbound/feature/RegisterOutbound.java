package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.location.domain.InventoryRepository;
import com.ejoongseok.wmslive.outbound.domain.Order;
import com.ejoongseok.wmslive.outbound.domain.OrderProduct;
import com.ejoongseok.wmslive.outbound.domain.OrderRepository;
import com.ejoongseok.wmslive.outbound.domain.Outbound;
import com.ejoongseok.wmslive.outbound.domain.OutboundProduct;
import com.ejoongseok.wmslive.outbound.domain.OutboundRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RegisterOutbound {
    private final OrderRepository orderRepository;
    private final OutboundRepository outboundRepository;
    private final InventoryRepository inventoryRepository;


    @PostMapping("/outbounds")
    @ResponseStatus(HttpStatus.CREATED)
    public void request(@RequestBody @Valid final Request request) {
        //주문 정보를 가져오고.
        final Order order = orderRepository.getBy(request.orderNo);

        // 주문정보에 맞는 상품의 재고가 충분한지 확인하고 충분하지 않으면 예외를 던진다.
        for (final OrderProduct orderProduct : order.orderProducts()) {
            // 해당 상품의 재고를 전부 가져온다.
            final Inventories inventories = new Inventories(
                    inventoryRepository.findByProductNo(orderProduct.getProductNo()),
                    orderProduct.orderQuantity());
            inventories.validateInventory();
        }
        // 출고에 사용할 포장재를 선택해준다.

        // 출고를 생성하고.

        final Outbound outbound = createOutbound(request, order);
        //출고를 등록한다.
        outboundRepository.save(outbound);
    }

    private Outbound createOutbound(final Request request, final Order order) {
        return new Outbound(
                order.orderNo(),
                order.orderCustomer(),
                order.deliveryRequirements(),
                mapToOutboundProducts(order.orderProducts()),
                request.isPriorityDelivery,
                request.desiredDeliveryAt
        );
    }

    private List<OutboundProduct> mapToOutboundProducts(
            final List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(this::newOutboundProduct)
                .toList();
    }

    private OutboundProduct newOutboundProduct(final OrderProduct orderProduct) {
        return new OutboundProduct(
                orderProduct.product(),
                orderProduct.orderQuantity(),
                orderProduct.unitPrice());
    }

    public record Request(
            @NotNull(message = "주문번호는 필수입니다.")
            Long orderNo,
            @NotNull(message = "우선출고여부는 필수입니다.")
            Boolean isPriorityDelivery,
            @NotNull(message = "희망출고일은 필수입니다.")
            LocalDate desiredDeliveryAt) {

    }
}
