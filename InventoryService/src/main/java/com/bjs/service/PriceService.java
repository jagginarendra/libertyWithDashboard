package com.bjs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjs.model.Price;
import com.bjs.repository.PriceRepository;

@Service
public class PriceService {

	@Autowired
	private PriceRepository priceRepository;
	
	public Price getPriceDetails(String sku){
		
		System.out.println("InventoryMS  PriceService getPriceDetails  skuId=" + sku);
		
		Price price = priceRepository.findBySku(sku);
		
		if(price == null){
			System.out.println("price is null not found in DB");
			price = new Price();
			price.setSku(sku);
			price.setPrice(0);
			
		}
		else
			System.out.println(price.toString());
		
		return price;
	}
}
