package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Cart;
import com.pcm.includes.Checkout;
import com.pcm.includes.Header;
import com.pcm.includes.Homepage;
import com.pcm.includes.PDP;
import com.pcm.includes.SignIn;
import com.grund.utility.StatusLog;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;

public class VerifyOrderReviewQtyWeightLimitValidation {

	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String edpno;
	public String cartOrderTotal;
	public String verifyOrderSubtTotal;
	public String sku;
	public String qtyorderlimit;
	public String qtyapolimit;
	public String zipCode;
	public String itemid;
	
	
	Properties sys = System.getProperties(); //Get property values in the build.xml junit tagged.
	
	
	@Test
	public void Verify_Order_Review_Qty_Weight_Limit_Validation() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies

			email = pr.getProperty("CHECKOUT_USER_TAX_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
			sku = pr.getProperty("SEARCH_SKU_OVERWEIGHT");
			qtyorderlimit = pr.getProperty("CHECKOUT_QTY_ORDERWEIGHTLIMIT");
			qtyapolimit = pr.getProperty("CHECKOUT_QTY_APOWEIGHTLIMIT");
			
			//Login user via header
			SignIn.login(Config.driver,email,password);
			testStatus = verifyXPath.isfoundwithWait(Config.driver, Header.LINK_SIGNOUT_XPATH,"2");
			Assert.assertTrue("User is logged in (Sign out link is display)",testStatus);
			
			//Search sku and add to cart
			Cart.clearcart(Config.driver);
			
			//Search sku, open PDP and get edpno
			edpno = PDP.getEDPNo(Config.driver,sku);
			PDP.addtoCart(Config.driver);
			
			//Go to cart and Proceed to Checkout.
		
			Cart.proceedtocheckout(Config.driver);
			
			//Enter the Order Weight Limit qty
			Checkout.updateOrderItemQTY(Config.driver, edpno, qtyorderlimit);
			testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("CHECKOUT_INVALID_ERRMSG_ORQTY_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[CHECKOUT] Validate Order Weight Limit NON APO.", testStatus);
			
			//Change Address to APO to validate the weight limit order.
			Checkout.changeShipAddbyZipcode(Config.driver, pr.getProperty("CHECKOUT_ZIP_APO"));
			
			Checkout.updateOrderItemQTY(Config.driver, edpno, qtyapolimit);
			testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("CHECKOUT_INVALID_ERRMSG_ORQTY_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[CHECKOUT] Validate Order Weight Limit APO.", testStatus);
			
			//Overall Test Result
			Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
			
		} catch (Exception e){
			StatusLog.printlnFailedResult(Config.driver,"[PCM] Verify_Order_Review_Qty_Weight_Limit_Validation");
			System.out.println(e);
		}
	}
	

	@AfterClass
	public static void quit() throws IOException{
		TakeScreenShot.CurrentPage(Config.driver, "Last Page Test Result");
		Config.driver.close();
		Config.driver.quit();
		
	}
	
	
}
