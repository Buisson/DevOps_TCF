package integration;


import arquillian.AbstractAchatest;
import fr.unice.polytech.devops.*;
import fr.unice.polytech.devops.entities.*;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class PaymentIntegrationTest extends AbstractAchatest {

	@EJB private Payment cashier;
	@EJB private CustomerFinder finder;
	@EJB private CustomerRegistration registration;

	@EJB private OrderProcessing orderProcess;
	@EJB private Tracker tracker;

	private Set<Item> items;

	@Before
	public void setUpContext() {
		memory.flush();
		items = new HashSet<>();
		items.add(new Item(Cookies.CHOCOLALALA, 3));
		items.add(new Item(Cookies.DARK_TEMPTATION, 2));
	}

	@Test
	public void integrationBetweenCustomersAndOrders() throws Exception {
		registration.register("john", "1234-896983");
		Customer retrieved = finder.findByName("john").get();
		assertTrue(retrieved.getOrders().isEmpty());
		String id = cashier.payOrder(retrieved, items);
		Order order = memory.getOrders().get(id);
		assertTrue(retrieved.getOrders().contains(order));
	}

	@Test
	public void integrationBetweenModules() throws Exception {
		registration.register("richard", "4321-896983");
		Customer retrieved = finder.findByName("richard").get();
		String id = cashier.payOrder(retrieved, items);
		assertTrue(tracker.status(id).equals(OrderStatus.IN_PROGRESS));
	}

}
