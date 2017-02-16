package com.bjs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjs.model.Inventory;
import com.bjs.model.Price;
import com.bjs.repository.InventoryRepository;

@Service
public class InventoryService {

	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	public Inventory getInventoryDetails(String sku){
		
		System.out.println("InventoryMS  InventoryService getInventoryDetails  skuId=" + sku);
		
		Inventory inventory ;
		
		inventory = inventoryRepository.findBySku(sku);
		
		if(inventory == null){
			System.out.println("price is null not found in DB");
			inventory = new Inventory();
			inventory.setSku(sku);
			inventory.setInventory(0);
			
		}
		else
			System.out.println(inventory.toString());
		
		return inventory;
	}
}
