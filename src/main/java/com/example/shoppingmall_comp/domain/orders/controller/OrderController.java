package com.example.shoppingmall_comp.domain.orders.controller;

import com.example.shoppingmall_comp.domain.orders.dto.OrderPageResponse;
import com.example.shoppingmall_comp.domain.orders.dto.OrderRequest;
import com.example.shoppingmall_comp.domain.orders.dto.OrderResponse;
import com.example.shoppingmall_comp.domain.orders.dto.PayCancelRequest;
import com.example.shoppingmall_comp.domain.orders.service.Impl.OrderServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "주문 관련 api", description = "주문 등록, 취소, 조회 api입니다.")
public class OrderController {

    private final OrderServiceImpl orderService;

    //주문 생성
    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "주문 생성 api", description = "주문을 생성하는 api 입니다.")
    public OrderResponse addOrder(@Valid @RequestBody OrderRequest orderRequest,
                                  @AuthenticationPrincipal User user) {
        return orderService.create(orderRequest, user);
    }

    //주문 취소
    @DeleteMapping("/payCancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "결제 취소 api", description = "결제를 취소하는 api 입니다.")
    public void deleteOrder(@RequestBody PayCancelRequest payCancelRequest,
                            @AuthenticationPrincipal User user) {
        orderService.payCancel(payCancelRequest, user);
    }

}
