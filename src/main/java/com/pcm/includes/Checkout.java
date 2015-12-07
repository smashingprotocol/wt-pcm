package com.pcm.includes;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.grund.form.SetInputField;
import com.grund.form.SetSelectField;
import com.grund.request.ClickElement;
import com.grund.utility.Wait;
import com.grund.verify.verifyXPath;


public class Checkout {

	public static Properties properties;
	public static String shipMtdPriceTxt;
	public static String shipMtdName;
	
	public static String PAYPAL_CREDIT_XPATH="//div[@id='paymentCardTypeBillLater']";
	public static String PREFERRED_ACCOUNT_XPATH="//div[@id='paymentCardTypeMacmall']";
	public static String SELECT_PAYPALMONTH_XPATH="//select[@id='date_of_birth_month']";
	public static String SELECT_PAYPALDAY_XPATH="//select[@id='date_of_birth_day']";
	public static String SELECT_PAYPALYEAR_XPATH="//select[@id='date_of_birth_year']";
	public static String INPUT_PAYPALSSN_XPATH="//input[@id='ssn']";
	public static String CHECK_PAYPALESIGN_XPATH="//input[@id='esign_consent']";
	public static String BTN_PAYPALAGREE_XPATH="//a[@id='new_customer_agree_and_continue']";
	public static String BTN_PAYPALCONFIRMAGREE_XPATH="//a[@id='submit_button']";
	public static String CHECKOUT_INPUT_BILLEMAIL_XPATH="//input[@id='billingEmail']";
	public static String CHECKOUT_INPUT_BILLCONFIRMEMAIL_XPATH="//input[@id='billingConfirmEmail']";
	public static String CHECKOUT_INPUT_BILLPSWD_XPATH="//input[@id='billingPassword']";
	public static String CHECKOUT_INPUT_BILLCONFIRMPSWD_XPATH="//input[@id='billingConfirmPassword']";
	public static String CHECKOUT_INPUT_BILLFIRSTNAME_XPATH="//input[@id='billingFname']";
	public static String CHECKOUT_INPUT_BILLLASTNAME_XPATH="//input[@id='billingLname']";
	public static String CHECKOUT_INPUT_BILLCO_XPATH="//input[@id='billingCompany']";
	public static String CHECKOUT_INPUT_BILLADD1_XPATH="//input[@id='billingAddress']";
	public static String CHECKOUT_INPUT_BILLADD2_XPATH="//input[@id='billingAddress_2']";
	public static String CHECKOUT_INPUT_BILLZIP_XPATH="//input[@id='billingZip']";
	public static String CHECKOUT_INPUT_BILLPHONE_XPATH="//input[@id='billingTelephone']";
	public static String CHECKOUT_INPUT_BILLMOBILE_XPATH="//input[@id='billingMobile']";
	public static String CHECKOUT_LINK_BACKTOCART_XPATH="//a[@href='/n/Shopping-Cart/msc-cart?op=mall.web.zones.shoppingCartMain.processCart']";
	public static String LINK_ORDERPAGE_HOME_XPATH="//a[@href='/home']";	
	public static String IMG_SHOPPERAPPROVEDCLOSE_XPATH="//img[@id='sa_close']";
	public static String INPUT_PREFERREDACT_SSN1_XPATH="//input[@id='ssn_1_input']";
	public static String INPUT_PREFERREDACT_SSN2_XPATH="//input[@id='ssn_2_input']";
	public static String INPUT_PREFERREDACT_SSN3_XPATH="//input[@id='ssn_3_input']";
	public static String SELECT_PREFERREDACT_MM_XPATH="//select[@id='date_of_birth_month_input']";
	public static String SELECT_PREFERREDACT_DD_XPATH="//select[@id='date_of_birth_day_input']";
	public static String SELECT_PREFERREDACT_YY_XPATH="//select[@id='date_of_birth_year_input']";
	public static String RADIO_PREFERREDACT__OWN_XPATH="//input[@id='residence_status_own']";
	public static String CHECK_PREFERREDACT_ESIGN_XPATH="//input[@id='esign_consent_input']";
	public static String BTN_PREFERREDACT_AGREE_XPATH="//input[@id='validate_order_button']";
	public static String BTN_PREFERREDACT_CONFIRMAGREE_XPATH="//a[@id='prompt_submit_button']";
	public static String INPUT_PREFERREDACT_INCOME_XPATH="//input[@id='annual_income_input']";
	
	public static void changeShipAddbyZipcode(WebDriver driver, String zipcode) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		
		try{
			properties.load(reader);
			System.out.println("IN VERIFY ORDER, UPDATE THE ADDRESS IN THE SHIPPING ADDRESS BOOK.");
			//If the zipcode is not yet selected as the shipping address, change the address.
			if(!verifyXPath.isfound(driver, "//div[@class='shippingAddCon']//div[contains(text(),'" + zipcode + "')]")){
				
				// TODO Auto-generated method stub
				ClickElement.byXPath(driver,properties.getProperty("CHECKOUT_LINK_CHANGESHIP_XPATH")); //click change to go to Address book
				verifyXPath.isfound(driver, properties.getProperty("CHECKOUT_BTN_ADDSHIPADD_XPATH")); //Verify the Add a Shipping Address button in Address Book
				
				int xpathctr = verifyXPath.count(driver, properties.getProperty("CHECKOUT_LINK_ADDBOOKEDIT_XPATH")) + 1; //Count the number of Edit Link in Address Book to get the no of addresses.
				System.out.println("[CHECKOUT] " + xpathctr + " other Address found in Shipping Address Book");
				
				boolean flag = false;
					System.out.println("[CHECKOUT] Looking for address with zipcode: " + zipcode + "...");
					for(int i = 1; i<xpathctr; i++){	
						flag = verifyXPath.isfound(driver, "(//div[@id='shipInfoCont']//div[@class='selectedAddress' or @class='shippingAddressBlock']) [position()=" + i + "]//p[contains(text(),'" + zipcode + "')]");  //Looking for the Address block that containts the text zipcode
						
						if(flag){
							String shipid = verifyXPath.getAttributeValue(driver,"(//a[@class='fBold btnEditAddress']) [position()=" + i + "]","shipid");  //Get the shipid of the Ship to Address button.
							System.out.println("//a[@class='buttonShipToAddress' and @shipid='" + shipid + "']");
							
							ClickElement.byXPath(driver,"//a[@class='buttonShipToAddress' and @shipid='" + shipid + "']"); //Click the Ship to Address that matches the zipcode
							break;
						} //end if
						
					} // end for
				
			} //end if
			
		}catch (Exception e){
			Assert.fail("[CHECKOUT] Change Shipping Address was not successful.");
			e.printStackTrace();
			
		}
		

	}

	public static void updateOrderItemQTY(WebDriver driver, String itemid, String qty) throws FileNotFoundException {
		System.out.println("[STEP] IN VERIFY ORDER, UPDATE THE ITEM QTY.");
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		
		try{
			properties.load(reader);
			//wait = Long.parseLong(properties.getProperty("WAIT_SEC"));
			//Thread.sleep(wait);
			
			ClickElement.byXPath(driver, properties.getProperty("CHECKOUT_LINK_EDITCART_XPATH"));  //Click the Edit your cart link in Order Review
			SetInputField.byXPath(driver, properties.getProperty("CHECKOUT_INPUT_QTY_XPATH"), qty); //Enter the qty value in the Order Review
			System.out.println("[CHECKOUT] Updating qty for itemid: " + itemid);
			ClickElement.byXPath(driver, properties.getProperty("CHECKOUT_LINK_UPDATEQTY_XPATH"));  //Click the Update link in Order Review
			
			
		} catch(Exception e) {
			Assert.fail("[CHECKOUT] Unable to Edit your Cart in Order Review: " + e.getMessage());
			
		}
	}

	public static void calculateShipMethodAll(WebDriver driver,boolean withFreeShipping) throws FileNotFoundException {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		
		try{
			properties.load(reader);
			Long wait = Long.parseLong(properties.getProperty("WAIT_SEC"));
			
			int shipmtdctr = verifyXPath.count(driver, properties.getProperty("CHECKOUT_RADIO_SHIPMTD_XPATH")) + 1; //Count the number of Shipping Method Radio Buttons.
			System.out.println("[CHECKOUT] " + shipmtdctr + " Shipping method found");

			for(int i = 1; i<shipmtdctr; i++){
				
				//Get the Value attribute of the radio button.
				String optionValue = verifyXPath.getAttributeValue(driver, "("+ properties.getProperty("CHECKOUT_RADIO_SHIPMTD_XPATH") + ") [position()=" + i + "]", "value"); 
				ClickElement.byXPath(driver, "("+ properties.getProperty("CHECKOUT_RADIO_SHIPMTD_XPATH") + ") [position()=" + i + "]"); //Select the radio button.
	
				//Store the Price attribute of the selected shipping mtd checkbox.
				shipMtdPriceTxt = verifyXPath.getAttributeValue(driver, "//label[@id='" + optionValue + "_price']","price"); 
				
				//Get the Shipping Fee in Order Review
				
				Thread.sleep(wait);
				//
				//wait.until(ExpectedConditions.textToBePresentInElementValue(properties.getProperty("CHECKOUT_LABEL_ORSHIPFEE_XPATH"), "$ " + shipMtdPriceTxt));
				String verifyOrderShipFee = verifyXPath.getText(driver, properties.getProperty("CHECKOUT_LABEL_ORSHIPFEE_XPATH")).replaceAll("[$, ]", "");
				
				
				
				
				if(shipMtdPriceTxt.equals("0") && withFreeShipping){
					shipMtdPriceTxt = "0.00";
				}
				
				System.out.println("Selected Shipping method price is: " + shipMtdPriceTxt);
				System.out.println("Shipping Fee in Order Review: " + verifyOrderShipFee);		
				
				Assert.assertEquals(shipMtdPriceTxt,verifyOrderShipFee);
				
			}
			
		}catch (Exception e){
			Assert.fail("[CHECKOUT] Error in Calculating Shipping Method" + e.getMessage());
		}
		
	}

	public static void returntoCart(WebDriver driver) throws Exception {
			System.out.println("[STEP] IN VERIFY ORDER, GO BACK TO CART.");
			ClickElement.byXPath(driver,CHECKOUT_LINK_BACKTOCART_XPATH);
		
	}

	public static boolean verifyShipMethodAvailability(WebDriver driver, String shipMtd) throws FileNotFoundException {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		boolean flag;
		
		try{
			properties.load(reader);
			shipMtdName = properties.getProperty(shipMtd);
			
			flag = verifyXPath.isfound(driver, "//label[contains(@for,'shippingMethod')]/span[contains(text(),'" + shipMtdName + "')]");
			return flag;
			
		} catch (Exception e){
			return false;
		}
		
	
		
	}

	public static void enterCreditCardExisting(WebDriver driver, String cardNo,
			String exMonth, String exYear, String securityCode) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		
		//Wait
		//WebDriverWait wait = new WebDriverWait(driver, 10);
		
		
		try {
			properties.load(reader);
			System.out.println("[STEP] IN VERIFY ORDER, ENTER CREDIT CARD INFORMATION.");
			String cardXPath = properties.getProperty("CHECKOUT_INPUT_CARDNO_XPATH");
			String exMonthXPath = properties.getProperty("CHECKOUT_SELECT_CCMONTHSELECT_XPATH");
			String exYearXPath  = properties.getProperty("CHECKOUT_SELECT_CCYEARSELECT_XPATH");
			String ccCodeXPath = properties.getProperty("CHECKOUT_INPUT_SECCODE_XPATH");
			
			SetInputField.byXPath(driver, cardXPath , cardNo);
			
			//wait
			//WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("id('paymentExpirationMonth_12')")));
			//element.click();
			
			//make the select Month field visible and select a month.
			WebElement monthSelecty = driver.findElement(By.xpath(exMonthXPath));
			JavascriptExecutor jse=(JavascriptExecutor) driver;
			jse.executeScript("arguments[0].setAttribute('style', 'display: block;')",monthSelecty);
			SetSelectField.byXPath(driver, exMonthXPath, exMonth);
			
			//make the select Year field visible and select a year.
			WebElement yearSelecty = driver.findElement(By.xpath(exYearXPath));
			JavascriptExecutor jsy=(JavascriptExecutor) driver;
			jsy.executeScript("arguments[0].setAttribute('style', 'display: block;')",yearSelecty);
			SetSelectField.byXPath(driver, exYearXPath , exYear);
			
			SetInputField.byXPath(driver, ccCodeXPath , securityCode);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Assert.fail("[CHECKOUT] Error in entering a Credit Card Information." + e.getMessage());
		} //end catch
		
	} // end enterCreditCardExisting

	public static void placeOrderAccept(WebDriver driver) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		
		try {
			properties.load(reader);
			System.out.println("[STEP] IN VERIFY ORDER. ACCEPT TERMS AND PLACE ORDER");
			ClickElement.byXPath(driver, properties.getProperty("CHECKOUT_RADIO_PLACEORDERTERMS_XPATH"));
			ClickElement.byXPath(driver, properties.getProperty("CHECKOUT_BTN_PLACEYOURORDER_XPATH"));
			
		} catch (IOException e) {
			Assert.fail("[CHECKOUT] Error in accepting terms and clicking Place Order button." + e.getMessage());
		} //end catch
		
		
	}//end placeOrderAccept

	public static void deleteItem(WebDriver driver, String sku) throws FileNotFoundException {
		// TODO Auto-generated method stub
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		
		try{
			properties.load(reader);
			System.out.println("[STEP] IN VERIFY ORDER, DELETE A ITEM IN ORDER REVIEW.");
			//GET THE NUMBER OF ITEM ROW
			int itemCtr = verifyXPath.count(driver, properties.getProperty("CHECKOUT_ORDER_ITEMROW_XPATH"));
			ClickElement.byXPath(driver, properties.getProperty("CHECKOUT_LINK_EDITCART_XPATH")); 
			
			for(int i = 1; i <= itemCtr; i++){
				Boolean itemRowFound = verifyXPath.isfound(driver, "(" + properties.getProperty("CHECKOUT_ORDER_ITEMROW_XPATH") + ") [position()=" + i + "]//span[contains(text(),'" + sku + "')]");
				if(itemRowFound){
					
					ClickElement.byXPath(driver, "(" + properties.getProperty("CHECKOUT_ORDER_ITEMROW_XPATH") + ") [position()=" + i + "]"+ properties.getProperty("CHECKOUT_LINK_ORDER_DELETE_ITEM_XPATH") +"");
					break;
				} //end if
			} // end for
			
		} catch (Exception e){
			Assert.fail("[CHECKOUT] Error in deleting the item." + e.getMessage());
		} //end catch
		
	} //end deleteItem

	public static void enterNewCustomerBillAdd(WebDriver driver, ArrayList<String> billFields) throws FileNotFoundException {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		
		try{
			properties.load(reader);
			System.out.println("[STEP] IN VERIFY ORDER, FILL UP BILLING INFORMATION.");
			SetInputField.byXPath(driver, properties.getProperty("CHECKOUT_INPUT_BILLEMAIL_XPATH"), billFields.get(0));
			SetInputField.byXPath(driver, properties.getProperty("CHECKOUT_INPUT_BILLCONFIRMEMAIL_XPATH"), billFields.get(1));
			SetInputField.byXPath(driver, properties.getProperty("CHECKOUT_INPUT_BILLPSWD_XPATH"), billFields.get(2));
			SetInputField.byXPath(driver, properties.getProperty("CHECKOUT_INPUT_BILLCONFIRMPSWD_XPATH"), billFields.get(3));
			SetInputField.byXPath(driver, properties.getProperty("CHECKOUT_INPUT_BILLFIRSTNAME_XPATH"), billFields.get(4));
			SetInputField.byXPath(driver, properties.getProperty("CHECKOUT_INPUT_BILLLASTNAME_XPATH"), billFields.get(5));
			SetInputField.byXPath(driver, properties.getProperty("CHECKOUT_INPUT_BILLCO_XPATH"), billFields.get(6));
			SetInputField.byXPath(driver, properties.getProperty("CHECKOUT_INPUT_BILLADD1_XPATH"), billFields.get(7));
			SetInputField.byXPath(driver, properties.getProperty("CHECKOUT_INPUT_BILLADD2_XPATH"), billFields.get(8));
			SetInputField.byXPath(driver, properties.getProperty("CHECKOUT_INPUT_BILLZIP_XPATH"), billFields.get(9));
			Wait.sleep("6");
			SetInputField.byXPath(driver, properties.getProperty("CHECKOUT_INPUT_BILLPHONE_XPATH"), billFields.get(10));
			SetInputField.byXPath(driver, properties.getProperty("CHECKOUT_INPUT_BILLMOBILE_XPATH"), billFields.get(11));
				
		} catch(Exception e){
			
		} //end try
		
	} //end enterNewCustomerFormTableContainer

	public static void enterPayPalCreditNew(WebDriver driver, String sSNumber,
			String dayofBirth, String monthofBirth,
			String yearofBirth) throws Exception {
		System.out.println("[STEP] IN VERIFY ORDER, ENTER PAYPAL CREDIT INFORMATION.");
		Wait.waitforXPath(driver, SELECT_PAYPALMONTH_XPATH, "3");
		
		SetSelectField.byXPath(driver, SELECT_PAYPALMONTH_XPATH, monthofBirth);
		SetSelectField.byXPath(driver, SELECT_PAYPALDAY_XPATH, dayofBirth);
		SetSelectField.byXPath(driver, SELECT_PAYPALYEAR_XPATH, yearofBirth);
		SetInputField.byXPath(driver, INPUT_PAYPALSSN_XPATH, sSNumber);
		
		ClickElement.byXPath(driver, CHECK_PAYPALESIGN_XPATH);
		ClickElement.byXPath(driver, BTN_PAYPALAGREE_XPATH);
		ClickElement.byXPath(driver, BTN_PAYPALCONFIRMAGREE_XPATH);
		
		
	}

	public static void qasModalUseDefaultAddress(WebDriver driver) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		System.out.println("[STEP] IN QAS MODAL, SELECT USE SHIPPING AND BILLING ADDRESS.");
		//Verify the QAS Modal I understand use address anyway link
		ClickElement.byXPath(driver, properties.getProperty("CHECKOUT_LINK_QASMODAL_USEBILLADD_XPATH"));
		ClickElement.byXPath(driver, properties.getProperty("CHECKOUT_LINK_QASMODAL_USESHIPADD_XPATH"));
		
		
	}

	public static void clickPayPalCredit(WebDriver driver) throws Exception {
		System.out.println("[STEP] IN VERIFY ORDER, SELECT PAYPAL CREDIT.");
		ClickElement.byXPath(driver, PAYPAL_CREDIT_XPATH);
		
		
	}

	public static void enterGuestBillAdd(WebDriver driver,
			ArrayList<String> billFields) throws Exception {
		System.out.println("[STEP] IN VERIFY ORDER, ENTER GUEST BILLING INFORMATION.");
		SetInputField.byXPath(driver, CHECKOUT_INPUT_BILLEMAIL_XPATH, billFields.get(0));
		SetInputField.byXPath(driver, CHECKOUT_INPUT_BILLCONFIRMEMAIL_XPATH, billFields.get(1));
		//SetInputField.byXPath(driver, CHECKOUT_INPUT_BILLPSWD_XPATH, billFields.get(2));
		//SetInputField.byXPath(driver, CHECKOUT_INPUT_BILLCONFIRMPSWD_XPATH, billFields.get(3));
		SetInputField.byXPath(driver, CHECKOUT_INPUT_BILLFIRSTNAME_XPATH, billFields.get(4));
		SetInputField.byXPath(driver, CHECKOUT_INPUT_BILLLASTNAME_XPATH, billFields.get(5));
		SetInputField.byXPath(driver, CHECKOUT_INPUT_BILLCO_XPATH, billFields.get(6));
		SetInputField.byXPath(driver, CHECKOUT_INPUT_BILLADD1_XPATH, billFields.get(7));
		SetInputField.byXPath(driver, CHECKOUT_INPUT_BILLADD2_XPATH, billFields.get(8));
		SetInputField.byXPath(driver, CHECKOUT_INPUT_BILLZIP_XPATH, billFields.get(9));
		Wait.sleep("5");
		SetInputField.byXPath(driver, CHECKOUT_INPUT_BILLPHONE_XPATH, billFields.get(10));
		SetInputField.byXPath(driver, CHECKOUT_INPUT_BILLMOBILE_XPATH, billFields.get(11));
		
		
	}

	public static void orderConfirmationBacktoHome(WebDriver driver) throws Exception {
		System.out.println("IN ORDER CONFIRMATION, RETURN TO HOMEPAGE");
		ClickElement.byXPathwithWait(driver,IMG_SHOPPERAPPROVEDCLOSE_XPATH,"3");
		ClickElement.byXPath(driver,LINK_ORDERPAGE_HOME_XPATH);
	}

	public static void clickPreferredAccount(WebDriver driver) throws Exception {
		System.out.println("[STEP] IN VERIFY ORDER, CLICK PREFERRED ACCOUNT.");
		ClickElement.byXPath(driver, PREFERRED_ACCOUNT_XPATH);
		
	}

	public static void enterPreferredAccountNew(WebDriver driver, String sSN1,
			String sSN2, String sSN3, String dayofBirth, String monthofBirth,
			String yearofBirth, String rStatus, String income) throws Exception {
		System.out.println("[STEP] IN VERIFY ORDER, ENTER PREFERRED ACCOUNT.");
		Wait.waitforXPath(driver, INPUT_PREFERREDACT_SSN1_XPATH, "3000");
		
		SetInputField.byXPath(driver, INPUT_PREFERREDACT_SSN1_XPATH, sSN1);
		SetInputField.byXPath(driver, INPUT_PREFERREDACT_SSN2_XPATH, sSN2);
		SetInputField.byXPath(driver, INPUT_PREFERREDACT_SSN3_XPATH, sSN3);
		SetSelectField.byXPath(driver, SELECT_PREFERREDACT_MM_XPATH, monthofBirth);
		SetSelectField.byXPath(driver, SELECT_PREFERREDACT_DD_XPATH, dayofBirth);
		SetSelectField.byXPath(driver, SELECT_PREFERREDACT_YY_XPATH, yearofBirth);
		ClickElement.byXPath(driver, RADIO_PREFERREDACT__OWN_XPATH);
		SetInputField.byXPath(driver, INPUT_PREFERREDACT_INCOME_XPATH, income);
		
		ClickElement.byXPath(driver, CHECK_PREFERREDACT_ESIGN_XPATH);
		ClickElement.byXPath(driver, BTN_PREFERREDACT_AGREE_XPATH);
		ClickElement.byXPath(driver, BTN_PREFERREDACT_CONFIRMAGREE_XPATH);
		
		
		
	}

	
		

}
