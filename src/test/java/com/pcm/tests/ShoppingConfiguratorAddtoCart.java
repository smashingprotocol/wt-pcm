package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Configurator;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;
import com.grund.utility.StatusLog;
import com.grund.utility.TableContainer;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.VerifyDocumentURL;
import com.grund.verify.verifyXPath;

public class ShoppingConfiguratorAddtoCart {

	public String email;
	public String password;
	public String env;
	public String sku;
	public String qty;
	public String navlinks;
	public String title;
	public String edpno;
	public String cartOrderTotal;
	public String verifyOrderSubtTotal;
	public boolean testStatus;
	public boolean tcStatus = true;
	
	Properties sys = System.getProperties(); //Get property values in the build.xml junit tagged.
	
	@Test
	public void Shopping_Configurator_AddtoCart() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			email = pr.getProperty("CHECKOUT_USER_TAX_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
			
			//Get table container value and declared as property values
			//get the number of test cases in the excel
			int rowCount = TableContainer.getRowCount();
	
			for(int i = 1; i <= rowCount; i++){
				
					sku = TableContainer.getCellValue(i, "sku");
					qty = TableContainer.getCellValue(i, "qty");
					navlinks = TableContainer.getCellValue(i, "navlinks");
					title = TableContainer.getCellValue(i, "title"); 
					
					Search.ctoModalClickConfigureItems(Config.driver,sku,qty);
					testStatus = VerifyDocumentURL.containsText(Config.driver,navlinks);
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] CTO Modal redirect to Configurator Page.", testStatus);
					
					String finalPrice = Configurator.getFinalPrice(Config.driver);
					Configurator.addToCart(Config.driver);
					
					testStatus = verifyXPath.isfound(Config.driver, "//div[@class='prodInfo']//span[contains(text(),'" + sku + "')]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[CART] Item from Configurator is added to cart.", testStatus);
					
					testStatus = verifyXPath.isfound(Config.driver, "//div[@class='ordersValue total' and contains(text(),'" + finalPrice + "')]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[CART] Price from Configurator is equal to cart.", testStatus);

			} //end for
			
			//Overall Test Result
			Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
		
		} catch (Exception e){
			Assert.fail(e.getMessage());
		} //end try catch
	} //end void
	

	@AfterClass
	public static void quit() throws IOException{
		TakeScreenShot.CurrentPage(Config.driver, "Last Page Test Result");
		Config.driver.close();
		Config.driver.quit();
		
	}
	
	
}
