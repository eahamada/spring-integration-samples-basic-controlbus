
package org.springframework.integration.samples.controlbus;

import java.util.Collections;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;

/**
 * @author Oleg Zhurakousky
 *
 */
public class ControlBusDemoTest {

  private static Logger logger = Logger.getLogger(ControlBusDemoTest.class);

  @Test
  public void current() {
    ConfigurableApplicationContext ac =
        new ClassPathXmlApplicationContext(
            "/META-INF/spring/integration/ControlBusDemo-context.xml");
    MessageChannel controlChannel = ac.getBean("controlChannel", MessageChannel.class);
    PollableChannel adapterOutputChanel = ac.getBean("adapterOutputChanel", PollableChannel.class);
    logger.info("Received before adapter started: " + adapterOutputChanel.receive(1000));

    for (int i = 1; i < 5; i++) {
      start(controlChannel, adapterOutputChanel);
      stop(controlChannel, adapterOutputChanel);
    }

    ac.close();
  }
  
  @Test
  public void expected() {
    ConfigurableApplicationContext ac =
        new ClassPathXmlApplicationContext(
            "/META-INF/spring/integration/ControlBusDemo-context.xml");
    MessageChannel controlChannel = ac.getBean("controlChannel", MessageChannel.class);
    PollableChannel adapterOutputChanel = ac.getBean("adapterOutputChanel", PollableChannel.class);
    SourcePollingChannelAdapter spca =
        ac.getBean("inboundAdapter", SourcePollingChannelAdapter.class);
    logger.info("Received before adapter started: " + adapterOutputChanel.receive(1000));

    for (int i = 1; i < 5; i++) {
      start(controlChannel, adapterOutputChanel);
      stopAndClearAdviceChain(controlChannel, adapterOutputChanel, spca);
    }

    ac.close();
  }


  private void stopAndClearAdviceChain(MessageChannel controlChannel, PollableChannel adapterOutputChanel,
      SourcePollingChannelAdapter spca) {
    controlChannel.send(new GenericMessage<String>("@inboundAdapter.stop()"));
    spca.setAdviceChain(Collections.EMPTY_LIST);
    logger.info("Received after adapter stopped: " + adapterOutputChanel.receive(1000));
  }
  private void stop(MessageChannel controlChannel, PollableChannel adapterOutputChanel) {
    controlChannel.send(new GenericMessage<String>("@inboundAdapter.stop()"));
    logger.info("Received after adapter stopped: " + adapterOutputChanel.receive(1000));
  }

  private void start(MessageChannel controlChannel, PollableChannel adapterOutputChanel) {
    controlChannel.send(new GenericMessage<String>("@inboundAdapter.start()"));
    logger.info("Received before adapter started: " + adapterOutputChanel.receive(1000));
  }
}
