Control Bus Sample
==================

This example demonstrates the functionality of the Control Bus component available with Spring Integration. The Control Bus uses SpEL to send a Control Message to start/stop an inbound adapter. To run the Control Bus sample simply execute **ControlBusDemoTest** in the **org.springframework.integration.samples.controlbus** package :
   mvn -Dtest=org.springframework.integration.samples.controlbus.ControlBusDemoTest#current  test
   mvn -Dtest=org.springframework.integration.samples.controlbus.ControlBusDemoTest#expected  test
