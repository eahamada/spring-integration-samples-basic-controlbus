
package org.springframework.integration.samples.controlbus;

import org.springframework.integration.aop.AbstractMessageSourceAdvice;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;

public class MyAdvice extends AbstractMessageSourceAdvice {

  @Override
  public boolean beforeReceive(MessageSource<?> source) {
    return true;
  }


  @Override
  public Message<?> afterReceive(Message<?> message, MessageSource<?> source) {
    System.out.println("hi");
    return message;
  }
}
