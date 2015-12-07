package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Cart;
import com.pcm.includes.Header;
import com.pcm.includes.Homepage;
import com.pcm.includes.PDP;
import com.pcm.includes.Search;
import com.pcm.includes.SignIn;
import com.grund.request.ClickElement;
import com.grund.utility.StatusLog;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;



public class VerifyOrderMIRPriceVerificationSaveforLaterinCart {

	public String sku;
	public String qty;
	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String edpno;
	public String cartOrderTotal;
	public String verifyOrderSubtTotal;
	
	Properties sys = System.getProperties(); //Get property values in the build.xml junit tagged.
	
	
	@Test
	public void Verify_Order_MIRPrice_Verification_SaveforLater_in_Cart() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			
			//set test data
			sku = pr.getProperty("SEARCH_SKU_MAILINREBATES");
			qty = "1";
			email = pr.getProperty("CHECKOUT_USER_TAX_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
			
			//Login user via header
			SignIn.login(Config.driver,email,password);
			testStatus = verifyXPath.isfoundwithWait(Config.driver, Header.LINK_SIGNOUT_XPATH,"2");
			Assert.assertTrue("User is logged in (Sign out link is display)",testStatus);
			
			//Clear the cart first.
			Cart.clearcart(Config.driver);
			
			//Search for the Sku to checkout and add to cart
			Search.keyword(Config.driver,sku);
			Search.addtocart(Config.driver,sku,qty);
			
			//Click Item Description to go to PDP and get edpno
			ClickElement.byXPath(Config.driver, "(" + pr.getProperty("SEARCH_LINK_PDP_XPATH") + ") [position()=1]");
			//edpno = verifyXPath.getAttributeValue(Config.driver, pr.getProperty("PDP_INPUT_EDPNO_XPATH"), "value");
			edpno = PDP.getEDPNo(Config.driver, sku);
			
			//Navigate shopping cart
			Cart.navigate(Config.driver); 
			String itemid = Cart.getItemID(Config.driver, edpno); // get the item id of the item
			
			//Get the Cart Order Total
			cartOrderTotal = verifyXPath.getText(Config.driver,pr.getProperty("CART_LABEL_ORDERTOTAL_XPATH"));
			//cartOrderTotal = cartOrderTotal.replace("$","");
			
			//Click Save for later in cart
			Cart.saveforlater(Config.driver,sku,itemid);
			
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("CART_INPUT_QTY_XPATH"));  //Verify the qty is displayed on cart, it means cart is not empty.
			StatusLog.printlnPassedResultFalse(Config.driver,"[Cart] Shopping Cart is Empty after saving for later the item",testStatus);
			
			//Move all items from Saved items
			Cart.saveitemsmoveall(Config.driver);
			
			//Verify the Sub total is the same after moving all items to cart.
			Assert.assertEquals(verifyXPath.getText(Config.driver,pr.getProperty("CART_LABEL_ORDERTOTAL_XPATH")),cartOrderTotal);
			
			//Proceed to checkout
			Cart.proceedtocheckout(Config.driver); 
			
			//Verify the Cart Total Order is the Sub Total in Checkout's Order Review
			//testStatus = verifyXPath.isfound(Config.driver,"//p[@id='orderReviewSubTotal' and contains(text(),'" + cartOrderTotal + "')]");  //Verify the Order review sub total is equivalent to the cart.
			//Assert.assertTrue("[CHECKOUT] Order Review Sub Total is equals to the cart Sub Total.",testStatus);
			cartOrderTotal = cartOrderTotal.replaceAll("[$, ]", "");
			verifyOrderSubtTotal = verifyXPath.getText(Config.driver, pr.getProperty("CHECKOUT_LABEL_ORSUBTOTAL_XPATH")).replaceAll("[$, ]", "");
			
			Assert.assertEquals(verifyOrderSubtTotal,cartOrderTotal);
			
			System.out.println("[VERIFY ] Verify Order Sub Total of  " + verifyOrderSubtTotal + " matches cart Sub Total of " + cartOrderTotal);
			
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
