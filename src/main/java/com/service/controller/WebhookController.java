package com.service.controller;

import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.service.config.RazorpayConfig;
import com.service.service.order.WebhookService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
@CrossOrigin
public class WebhookController {

    private final RazorpayConfig razorpayConfig;
    private final WebhookService webhookService;

    @PostMapping("/payment-event")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload,
                                                @RequestHeader("X-Razorpay-Signature") String signature) {
        try {
            if (razorpayConfig.isValidate_webhook() && verifySignature(payload, signature, razorpayConfig.getRzp_webhook_secret())) {
                webhookService.handlePaymentCallback(new JSONObject(payload));
                return ResponseEntity.ok("Webhook processed successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
            }
        } catch (RazorpayException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Webhook processing failed");
        }
    }

    private boolean verifySignature(String payload, String signature, String secret) throws RazorpayException {
        return Utils.verifyWebhookSignature(payload, signature, secret);
    }
}
