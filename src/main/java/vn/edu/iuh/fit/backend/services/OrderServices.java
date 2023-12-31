package vn.edu.iuh.fit.backend.services;

import jakarta.inject.Inject;
import vn.edu.iuh.fit.backend.models.Order;
import vn.edu.iuh.fit.backend.repositories.OrderRepository;

import java.util.List;
import java.util.Optional;

public class OrderServices {
    private final OrderRepository orderRepository;

    public OrderServices() {
        this.orderRepository = new OrderRepository();
    }

    public List<Order> getAll(int page) {
        return orderRepository.getAll(Math.max(page, 1));
    }

    public Optional<Order> findById(long id) {
        return orderRepository.findById(id);
    }

    public boolean add(Order order) {
        return orderRepository.add(order);
    }

    public Optional<Boolean> update(Order order) {
        Optional<Order> orderFind = findById(order.getOrder_id());

        if (orderFind.isEmpty())
            return Optional.empty();

        return Optional.of(orderRepository.update(order));
    }
}
