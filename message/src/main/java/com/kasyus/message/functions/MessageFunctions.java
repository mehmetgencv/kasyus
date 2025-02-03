package com.kasyus.message.functions;

import com.kasyus.message.dto.OrderMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    private static final Logger log = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<OrderMsgDto, OrderMsgDto> email(){
        return orderMsgDto -> {
            log.info("Sending email with the details: " + orderMsgDto.toString());
            return orderMsgDto;
        };
    }

    @Bean
    public Function<OrderMsgDto, Long> sms(){
        return orderMsgDto -> {
            log.info("Sending sms with the details: " + orderMsgDto.toString());
            return orderMsgDto.userId();
        };
    }
}
