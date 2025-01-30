package com.jdshah.notification.service;

import com.jdshah.order.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final JavaMailSender javaMailSender;

    @JmsListener(destination = "order-notification", containerFactory = "jmsListenerContainerFactory")
    public void listen(OrderPlacedEvent orderPlacedEvent) {
        log.info("Got message from order-placed topic {}", orderPlacedEvent);

        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("springshop@email.com");
            mimeMessageHelper.setTo(orderPlacedEvent.getEmail());
            mimeMessageHelper.setSubject(String.format("Your order with orderNumber %s is placed successfully", orderPlacedEvent.getOrderNumber()));
            mimeMessageHelper.setText(String.format("""
                        Hi
                    
                        Your order with order number %s is now placed successfully
                    
                        Best Regards,
                        Spring Shop
                    """, orderPlacedEvent.getOrderNumber()));
        };
        try {
            javaMailSender.send(mimeMessagePreparator);
            log.info("Order Notification Email Sent");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail");
            throw new RuntimeException("Exception occurred when sending mail to " + orderPlacedEvent.getEmail(), e);
        }
    }
}
