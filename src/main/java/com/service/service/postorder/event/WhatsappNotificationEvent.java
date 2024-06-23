package com.service.service.postorder.event;

import com.service.model.entity.Order;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WhatsappNotificationEvent implements PostOrderEvent {

    // Replace these placeholders with your actual Account SID and Auth Token
    public static final String ACCOUNT_SID = "your_account_sid";
    public static final String AUTH_TOKEN = "your_auth_token";

    public WhatsappNotificationEvent() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @Override
    public void process(Order order) {
        log.info("processing whatsapp notification event");

        //todo: change these mobile numbers
        String from = "whatsapp:+14155238886"; // Twilio sandbox number
        String to = "whatsapp:+1234567890"; // Recipient's number

        //todo: change the message content dynamically
        Message message = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(from),
                "Hello from Java! This is a test message from WhatsApp."
        ).create();

        // Print the message SID to confirm the message was sent
        log.info("Message sent with SID: " + message.getSid());
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
