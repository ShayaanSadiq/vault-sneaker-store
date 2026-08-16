package com.vault.store.service;

import com.vault.store.domain.CustomerOrder;
import com.vault.store.domain.Product;
import com.vault.store.dto.CreateOrderRequest;
import com.vault.store.dto.OrderItemRequest;
import com.vault.store.dto.OrderResponse;
import com.vault.store.dto.PaymentRequest;
import com.vault.store.repository.OrderRepository;
import com.vault.store.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderRepository, productRepository);
    }

    @Test
    void createOrderSavesAPaidOrderWithServerTotals() {
        when(productRepository.findById("apex-infrared")).thenReturn(Optional.of(sampleProduct()));
        when(orderRepository.save(any(CustomerOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponse response = orderService.createOrder(validRequest());

        ArgumentCaptor<CustomerOrder> captor = ArgumentCaptor.forClass(CustomerOrder.class);
        verify(orderRepository).save(captor.capture());
        CustomerOrder saved = captor.getValue();

        assertThat(saved.getStatus()).isEqualTo("PAID");
        assertThat(saved.getSubtotal()).isEqualTo(189.0);
        assertThat(saved.getShipping()).isEqualTo(12.0);
        assertThat(saved.getTax()).isEqualTo(15.12);
        assertThat(saved.getTotal()).isEqualTo(216.12);
        assertThat(response.getId()).startsWith("VLT-");
        assertThat(response.getItems()).hasSize(1);
    }

    @Test
    void createOrderRejectsUnknownProducts() {
        when(productRepository.findById("missing")).thenReturn(Optional.empty());
        CreateOrderRequest request = validRequest();
        request.getItems().getFirst().setProductId("missing");

        assertThatThrownBy(() -> orderService.createOrder(request))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Unknown product");
    }

    @Test
    void createOrderRejectsInvalidCards() {
        CreateOrderRequest request = validRequest();
        request.getPayment().setCardNumber("1234 1234 1234 1234");

        assertThatThrownBy(() -> orderService.createOrder(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("card number");
    }

    private CreateOrderRequest validRequest() {
        OrderItemRequest item = new OrderItemRequest();
        item.setProductId("apex-infrared");
        item.setSize(10.0);
        item.setQuantity(1);

        PaymentRequest payment = new PaymentRequest();
        payment.setCardName("Ada Lovelace");
        payment.setCardNumber("4242 4242 4242 4242");
        payment.setExpiry(YearMonth.now().plusYears(1).format(DateTimeFormatter.ofPattern("MM/yy")));
        payment.setCvv("123");

        CreateOrderRequest request = new CreateOrderRequest();
        request.setFullName("Ada Lovelace");
        request.setEmail("ada@example.com");
        request.setPhone("5551234567");
        request.setAddress("12 Analytical Engine Rd");
        request.setCity("London");
        request.setZip("SW1A 1AA");
        request.setItems(List.of(item));
        request.setPayment(payment);
        return request;
    }

    private Product sampleProduct() {
        return Product.builder()
                .id("apex-infrared")
                .name("Apex Infrared")
                .price(189)
                .category("Running")
                .build();
    }
}
