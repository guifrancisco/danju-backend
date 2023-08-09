package org.github.guifrancisco.danju.domain.dto;

import org.github.guifrancisco.danju.domain.entity.Order;
import org.github.guifrancisco.danju.domain.entity.OrderLine;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record DataOrder(
        String id,
        DataCustomer customer,
        List<DataOrderLine> orderLines,
        LocalDate deliveryDate,
        String paymentType,
        double totalValue,
        String status,
        String createdBy
) {
    public DataOrder(Order order) {
        this(
                order.getId(),
                new DataCustomer(order.getCustomer()),
                order.getOrderLines().stream()
                        .map(orderLine -> new DataOrderLine(orderLine.getId(), orderLine.getProduct().getId(), orderLine.getQuantity()))
                        .collect(Collectors.toList()),
                order.getDeliveryDate(),
                order.getPaymentType(),
                order.getTotalValue(),
                order.getStatus().getStatus(),
                order.getCreatedBy()
        );
    }
}