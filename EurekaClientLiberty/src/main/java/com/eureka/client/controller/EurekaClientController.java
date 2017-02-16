package com.eureka.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class EurekaClientController {

	/*
	 * @Autowired private RestTemplate restTemplate;
	 */
	@Autowired
	private LoadBalancerClient loadBalancerClient;

	// @Autowired private DiscoveryClient discoveryClient;

	@Value("${price_service_url}")
	private String priceServiceUrl;

	@Value("${inventory_service_url}")
	private String inventoryServiceUrl;

	@RequestMapping("/inventory/sku/{skuId}")
	@HystrixCommand(fallbackMethod = "inventoryFallback")
	public String getInventoryDetails(@PathVariable String skuId) {

		String inventoryDetails = "";

		/*
		 * inventoryDetails = restTemplate.getForObject(inventoryServiceUrl +
		 * skuId, String.class);
		 * 
		 * System.out.println(inventoryDetails);
		 */

		// List<ServiceInstance> list =
		// discoveryClient.getInstances("InventoryService");
		/*
		 * System.out.println("list" + list);
		 * 
		 * ServiceInstance instance = list.get(0);
		 */

		System.out.println("EurekaClient  EurekaClientController  getInventoryDetails  skuId=" + skuId);

		ServiceInstance instance = loadBalancerClient.choose("InventoryService");

		// System.out.println(instance.getUri()); //
		// http://BLRNKUM9520320.sapient.com:9080

		int port = instance.getPort();

		System.out.println("oldURL=" + inventoryServiceUrl);

		/*
		 * inventoryServiceUrl = inventoryServiceUrl.replace("{0}", port + "");
		 * inventoryServiceUrl = inventoryServiceUrl.replace("{1}", skuId);
		 */
		String url = inventoryServiceUrl + port + "/InventoryService/inventory/sku/" + skuId;

		System.out.println("newURL=" + url);

		inventoryDetails = new RestTemplate().getForObject(url, String.class);

		return inventoryDetails;
	}

	public String inventoryFallback(String skuId) {

		String dummyOutput = "{'sku':" + skuId + "},{'inventory':0}";
		System.out.println("In inventory fallback() sending 0 inventory");
		return dummyOutput;
	}

	@RequestMapping("/price/sku/{skuId}")
    @HystrixCommand(fallbackMethod = "priceFallback")
	public String getPriceDetails(@PathVariable String skuId) {

		String priceDetails = null;

		System.out.println("EurekaClient  EurekaClientController  getPriceDetails  skuId=" + skuId);

		ServiceInstance instance = loadBalancerClient.choose("InventoryService");

		int port = instance.getPort();

		/*
		 * priceServiceUrl = priceServiceUrl.replace("{0}", port + "");
		 * priceServiceUrl = priceServiceUrl.replace("{1}", skuId);
		 */
		String url = priceServiceUrl + port + "/InventoryService/price/sku/" + skuId;

		System.out.println("newURL=" + url);

		priceDetails = new RestTemplate().getForObject(url, String.class);

		return priceDetails;
	}

	public String priceFallback(String skuId) {

		String dummyOutput = "{'sku':" + skuId + "},{'price':0}";
		System.out.println("In priceFallback() , sending 0 price");
		return dummyOutput;
	}

}
