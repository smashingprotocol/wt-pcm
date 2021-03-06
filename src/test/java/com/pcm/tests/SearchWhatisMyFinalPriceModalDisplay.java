package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Cart;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;
import com.grund.request.ClickElement;
import com.grund.utility.StatusLog;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;



public class SearchWhatisMyFinalPriceModalDisplay {

	public String skuWithSlashed;
	public String withSlashed;
	public String withMIR;
	public String unitPrice;
	public String finalPrice;
	public String qty;
	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String edpno;
	public String cartOrderTotal;
	public String title;
	
	
	Properties sys = System.getProperties();
	
	
	@Test
	public void Search_What_is_My_Final_Price_Modal_Display() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			
			//Define properties
			skuWithSlashed = pr.getProperty("SEARCH_SKU_WHATFINALPRICE_WITHSLASH");
			qty = "1";
			email = pr.getProperty("SEARCH_EMAIL_WHATFINALPRICE");
			
			Search.uppSubmitEmailFinalPrice(Config.driver,skuWithSlashed,email);
			testStatus = verifyXPath.isfound(Config.driver, Search.UPP_UPPSUCCESSMSG_XPATH);
			StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Able to submit a UPP Final Price to an email.", testStatus);
			
			ClickElement.byXPath(Config.driver, Search.BTN_MODAL_CONTINUESHOPPING_XPATH, "SEARCH: Click Continue Shopping button.");
			
			
			Search.uppAddtoCart(Config.driver,skuWithSlashed);

			//testStatus = verifyXPath.isfound(Config.driver, "//div[@class='cartItemsCon']//td[@class='cartinfotd']//a[contains(@href,'" + skuWithSlashed + "')]");
			testStatus = Cart.verifySkuinCart(Config.driver, skuWithSlashed);
			StatusLog.printlnPassedResultTrue(Config.driver,"[CART] Item is added in cart via Search UPP Modal.", testStatus);
			
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
