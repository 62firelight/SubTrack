/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import domain.Customer;
import domain.Subscription;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tanlu824
 */
public class CustomerCollectionsDAO {
    
	private static final Map<Integer, Customer> items = new HashMap<>();
	private static final Multimap<Integer, Subscription> subscriptions = HashMultimap.create();
	
	static {
		if(items.isEmpty()) {
//			items.put("ACC123", new Customer());
		}
	}

	public List<Customer> getList() {
		return new ArrayList<>(items.values());
	}

	public void addItem(Customer cust) {
		items.put(cust.getCustomerId(), cust);
	}

	public Customer getById(String itemName) {
		return items.get(itemName);
	}

	public void delete(String name) {
		items.remove(name);
	}

	public void updateItem(Integer id, Customer updatedItem) {
		items.put(id, updatedItem);
	}

	public boolean exists(String itemName) {
		return items.containsKey(itemName);
	}
	
	public void addSubscription(Customer cust, Subscription sub) {
		subscriptions.put(cust.getCustomerId(), sub);
	}
}
