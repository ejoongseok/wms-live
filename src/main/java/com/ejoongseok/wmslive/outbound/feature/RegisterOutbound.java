package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.location.domain.InventoryRepository;
import com.ejoongseok.wmslive.outbound.domain.Inventories;
import com.ejoongseok.wmslive.outbound.domain.Order;
import com.ejoongseok.wmslive.outbound.domain.OrderProduct;
import com.ejoongseok.wmslive.outbound.domain.OrderRepository;
import com.ejoongseok.wmslive.outbound.domain.Outbound;
import com.ejoongseok.wmslive.outbound.domain.OutboundProduct;
import com.ejoongseok.wmslive.outbound.domain.OutboundRepository;
import com.ejoongseok.wmslive.outbound.domain.PackagingMaterial;
import com.ejoongseok.wmslive.outbound.domain.PackagingMaterialRepository;
import com.ejoongseok.wmslive.outbound.domain.PackagingMaterials;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
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
    private final PackagingMaterialRepository packagingMaterialRepository;
    private final ConsturctOutbound consturctOutbound = new ConsturctOutbound();


    @PostMapping("/outbounds")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void request(@RequestBody @Valid final Request request) {
        //주문 정보를 가져오고.
        final Order order = orderRepository.getBy(request.orderNo);
        final List<Inventories> inventoriesList = inventoriesList(order.orderProducts());
        final PackagingMaterials packagingMaterials = new PackagingMaterials(packagingMaterialRepository.findAll());

        final Outbound outbound = consturctOutbound.create(
                inventoriesList,
                packagingMaterials,
                order,
                request.isPriorityDelivery,
                request.desiredDeliveryAt);

        //출고를 등록한다.
        outboundRepository.save(outbound);
    }

    private List<Inventories> inventoriesList(final List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(orderProduct -> new Inventories(
                        inventoryRepository.findByProductNo(orderProduct.getProductNo())
                )).
                toList();
    }

    Outbound createOutbound(
            final List<Inventories> inventoriesList,
            final PackagingMaterials packagingMaterials,
            final Order order,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt) {
        return consturctOutbound.create(inventoriesList, packagingMaterials, order, isPriorityDelivery, desiredDeliveryAt);
    }

    private void validateInventory(final List<Inventories> inventoriesList, final List<OrderProduct> orderProducts) {
        consturctOutbound.validateInventory(inventoriesList, orderProducts);
    }

    private Inventories getInventories(final List<Inventories> inventoriesList, final OrderProduct orderProduct) {
        return consturctOutbound.getInventories(inventoriesList, orderProduct);
    }

    private Outbound newOutbound(
            final Order order,
            final PackagingMaterial packagingMaterial,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt) {
        return consturctOutbound.newOutbound(order, packagingMaterial, isPriorityDelivery, desiredDeliveryAt);
    }

    private List<OutboundProduct> mapToOutboundProducts(
            final List<OrderProduct> orderProducts) {
        return consturctOutbound.mapToOutboundProducts(orderProducts);
    }

    private OutboundProduct newOutboundProduct(final OrderProduct orderProduct) {
        return consturctOutbound.newOutboundProduct(orderProduct);
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
