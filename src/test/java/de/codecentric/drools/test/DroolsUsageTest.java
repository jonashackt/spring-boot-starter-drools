package de.codecentric.drools.test;

import de.codecentric.drools.TestApplication;
import de.codecentric.drools.test.model.Address;
import de.codecentric.drools.test.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
public class DroolsUsageTest {

    private static final Logger LOG = LoggerFactory.getLogger(DroolsUsageTest.class);
    
    @Autowired
    private KieSession kieSession;
    
    @Test
    public void adressRulePostcode() {
        // Given
        Address address = new Address();
        address.setPostcode("99425");
        
        // When
        // Let´s give the Drools Knowledge-Base an Object, we can then apply rules on
        FactHandle factHandle = kieSession.insert(address);
        int ruleFiredCount = kieSession.fireAllRules();
        kieSession.delete(factHandle);
                
        // Then     
        assertEquals("there´s 1 rule, thats meets the condition, so there should be 1 applied", 1, ruleFiredCount);
        LOG.debug("Rules checked: {}" + ruleFiredCount);
    }
    
    @Test
    public void adressRulePostcodeNotMet() {
        // Given
        Address address = new Address();
        address.setPostcode("994259");
        
        // When
        // Let´s give the Drools Knowledge-Base an Object, we can then apply rules on
        FactHandle factHandle = kieSession.insert(address);
        int ruleFiredCount = kieSession.fireAllRules();
        kieSession.delete(factHandle);
                
        // Then     
        assertEquals("there´s 0 rule, thats meets the condition, so there should be 0 applied", 0, ruleFiredCount);
        LOG.debug("Rules checked: {}", ruleFiredCount);
    }
    
    
    @Test
    public void adressRuleOrder() {
        // Given
        Address address = new Address();
        address.setPostcode("99425");
        address.setState("GERMANY");
        
        Order order = new Order();
        order.setAmount(2);
        order.setState2ship2("GERMANY");
        
        // When
        // Let´s give the Drools Knowledge-Base an Object, we can then apply rules on
        FactHandle addressFactHandle = kieSession.insert(address);
        FactHandle orderFactHandle = kieSession.insert(order);
        int ruleFiredCount = kieSession.fireAllRules();
        kieSession.delete(addressFactHandle);
        kieSession.delete(orderFactHandle);
                
        // Then     
        assertEquals("there are 3 rules, which meet the condition, so there should be 3 applied", 3, ruleFiredCount);
        LOG.debug("Rules checked: {}", ruleFiredCount);
    }

    @Test
    public void orderWithAmountLess100() {
        Order order = new Order();
        order.setAmount(99);

        FactHandle factHandle = kieSession.insert(order);
        int ruleFiredCount = kieSession.fireAllRules();
        kieSession.delete(factHandle);

        assertEquals("there is 1 rule, which met the condition, so there should be 1 applied", 1, ruleFiredCount);
        LOG.debug("Rules checked: {}", ruleFiredCount);
    }
    
    

}
