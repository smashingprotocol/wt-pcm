package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.pcm.engine.Config;
import com.pcm.includes.Cart;
import com.pcm.includes.Homepage;
import com.pcm.utility.StatusLog;
import com.pcm.utility.TableContainer;
import com.pcm.utility.TakeScreenShot;

public class ShoppingCartAvailableShippingMethod {

	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String edpno;
	public String cartOrderTotal;
	public String verifyOrderSubtTotal;
	public static boolean isfreeshipping;
	public String worksheet;
	public String sku;
	public String qty;
	public String zipCode;
	public String title;
	public String shipMtdValue;
	public String shipMtd;
	public Boolean shipMtdAvailable;
	public boolean teststatus;
	public boolean tcStatus = true;
	
	Properties sys = System.getProperties(); //Get property values in the build.xml junit tagged.
	
	
	@Test
	public void Shopping_Cart_Available_Shipping_Method() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties(); //create a method for the pcm.properies
			email = pr.getProperty("CHECKOUT_USER_TAX_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
			
			//Get table container value and declared as property values
			//get the number of test cases in the excel
			int rowCount = TableContainer.getRowCount();
	
			for(int i = 1; i <= rowCount; i++){
				
				
					sku = TableContainer.getCellValue(i,"sku");
					qty = TableContainer.getCellValue(i,"qty");
					zipCode = TableContainer.getCellValue(i,"zipCode");
					title = TableContainer.getCellValue(i,"title");
					teststatus = true;
					
					//Search SKU and Add to Cart.
					Cart.clearcart(Config.driver);
					Cart.quickaddsku(Config.driver, sku);
					
					if(!qty.isEmpty()){
						Cart.updateItemQty(Config.driver,qty);
					}
					
					Cart.estimateShippingSubmitZip(Config.driver, zipCode);
					
					//Total number of shipping methods
					int shipMtdCtr = Integer.parseInt(pr.getProperty("SHIP_MTD_COUNT"));
					System.out.println(shipMtdCtr);
					boolean ShippingMethodFound = true;
					
					
					//Verify the Shipping Method
					for(int s = 1; s <= shipMtdCtr; s++){
						
						//ship method property name
						shipMtd = "shipmethod." + String.valueOf(s);
						
						//Value of the ship method property (true,false)
						shipMtdValue = TableContainer.getCellValue(i,"isshipmethod." + String.valueOf(s));
						
						if (!shipMtdValue.isEmpty()){
							shipMtdAvailable = Boolean.valueOf(shipMtdValue);
							System.out.println("shipMtdAvailable: " + shipMtdAvailable);
							ShippingMethodFound = Cart.verifyShipMethodAvailability(Config.driver,shipMtd);
							
							//if the shipping method found did not match the excel condition, entire test case is failed
							if(!shipMtdAvailable.equals(ShippingMethodFound)){
								System.out.println("[CART] Shipping method found is " + shipMtdAvailable + " but result is " + ShippingMethodFound);
								teststatus = false;	
							}	
							
						} //end if
		
					} //end for s
					
					StatusLog.printlnPassedResultTrue(Config.driver,"[CART] " + title,teststatus);
				
			} //end for
			
			//Overall Test Result
			Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
			
		} catch (Exception e){
			
			Assert.fail(e.getMessage());
		}
	}
	

	@AfterClass
	public static void quit() throws IOException{
		TakeScreenShot.CurrentPage(Config.driver, "Last Page Test Result");
		Config.driver.close();
		Config.driver.quit();
		
	}
	
	
}
