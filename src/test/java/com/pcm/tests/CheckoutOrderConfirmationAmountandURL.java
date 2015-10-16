package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Cart;
import com.pcm.includes.Checkout;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;
import com.pcm.includes.SignIn;
import com.grund.utility.StatusLog;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;

public class CheckoutOrderConfirmationAmountandURL {

	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String edpno;
	public String cartOrderTotal;
	public String verifyOrderSubtTotal;
	public String sku;
	public String qty;
	public String zipCode;
	public String itemid;
	public String cardNo;
	public String exMonth;
	public String exYear;
	public String securityCode;
	
	
	
	
	Properties sys = System.getProperties(); //Get property values in the build.xml junit tagged.
	
	
	@Test
	public void Checkout_Order_Confirmation_Amount_and_URL() throws Exception{
		
		try{
			
			//setup driver,properties and includes
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost"); //prod or stage
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			
			email = pr.getProperty("CHECKOUT_USER_TAX_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
			sku = pr.getProperty("SEARCH_SKU_INSTANTREBATES");
			qty = "1";
			cardNo = pr.getProperty("CHECKOUT_CCNO_VISA");
			exMonth = pr.getProperty("CHECKOUT_EXPMONTH");
			exYear = pr.getProperty("CHECKOUT_EXPYEAR");
			securityCode = pr.getProperty("CHECKOUT_SCODE_VISA");
			
			//Login user via header
			SignIn.login(Config.driver,email,password);
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("HEADER_LINK_SIGNOUT_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"User is logged in (Sign out link is display)",testStatus);
			
			//Search sku and add to cart
			//Cart.clearcart(Config.driver);
			Search.keyword(Config.driver, sku);
			Search.addtocart(Config.driver, sku, qty);
			
			
			//Go to cart and Proceed to checkout.
			Cart.navigate(Config.driver);
			//Get the Cart Order Total
			cartOrderTotal = verifyXPath.getText(Config.driver,pr.getProperty("CART_LABEL_ORDERTOTAL_XPATH"));
			cartOrderTotal = cartOrderTotal.replaceAll("[$, ]", "");
			
			Cart.proceedtocheckout(Config.driver);
			
			verifyOrderSubtTotal = verifyXPath.getText(Config.driver, pr.getProperty("CHECKOUT_LABEL_ORSUBTOTAL_XPATH")).replaceAll("[$, ]", "");
			Assert.assertEquals(verifyOrderSubtTotal,cartOrderTotal);
			
			Checkout.enterCreditCardExisting(Config.driver,cardNo,exMonth,exYear,securityCode);
			Checkout.placeOrderAccept(Config.driver);
			
			//Check the Subtotal in Order Confirmation
			String orderConfirmationSubTotal = verifyXPath.getText(Config.driver, pr.getProperty("CHECKOUT_LABEL_ORSUBTOTAL_XPATH")).replaceAll("[$, ]", "");
			Assert.assertEquals(orderConfirmationSubTotal,verifyOrderSubtTotal);
			
			String OrderNumber = verifyXPath.getText(Config.driver, pr.getProperty("CHECKOUT_ORDERCONFIRMATION_ORNO_XPATH"));
			StatusLog.printlnPassedResult(Config.driver,"[PCM] Verify Order Checkout CreditCard with Order Number: " + OrderNumber);
			
			//Overall Test Result
			Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
			
			
		} catch (Exception e){
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
