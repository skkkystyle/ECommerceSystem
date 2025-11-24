package com.payments.adapter;

import com.payments.application.service.PaymentService;
import com.payments.domain.Account;
import com.payments.dto.TopUpAccountRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payments API", description = "API управления счетами")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/account")
    @Operation(summary = "Создать счёт для пользователя")
    public UUID createAccount(@RequestParam UUID userId) {
        return paymentService.createAccount(userId);
    }

    @PostMapping("/topup")
    @Operation(summary = "Пополнить счёт")
    public void topUpAccount(@RequestParam UUID accountId, @RequestBody TopUpAccountRequest request) {
        paymentService.topUpAccount(accountId, request.getAmount());
    }

    @GetMapping("/account/{accountId}")
    @Operation(summary = "Получить информацию о счёте")
    public Account getAccount(@PathVariable UUID accountId) {
        return paymentService.getAccountById(accountId);
    }
}