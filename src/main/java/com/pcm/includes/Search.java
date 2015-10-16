package com.pcm.includes;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Properties;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.grund.engine.Config;
import com.grund.form.SetInputField;
import com.grund.form.SetSelectField;
import com.grund.request.ClickElement;
import com.grund.utility.StatusLog;
import com.grund.verify.verifyXPath;


public class Search {

	public static Properties properties;
	
	public static void keyword(WebDriver driver,String keyword) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		try {
			
			SetInputField.byXPath(driver,properties.getProperty("SEARCH_INPUT_XPATH"),keyword);
			ClickElement.byXPath(driver,properties.getProperty("SEARCH_LINK_XPATH"));
			
		} catch (Exception e){
			//PDP.searchKeyword(driver, keyword);
		}
		
		
	}

	public static void addtocart(WebDriver driver, String sku, String qty) throws Exception{
		
		try{
		
		Boolean testStatus = verifyXPath.isfound(Config.driver,"//a[@id='addCartButtonFor" + sku + "']");
		StatusLog.printlnPassedResultTrue(driver,"[SEARCH] Verify Add to cart button of " + sku + " is found.",testStatus);
		//Failed test case if add to cart is not found.
		Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
		
		// Enter Quantity, Click Add to cart and Proceed to cart button on the modal	
		SetInputField.byXPath(driver,"//input[@id='addCartQty" + sku + "']",qty);
		ClickElement.byXPath(driver,"//a[@id='addCartButtonFor" + sku + "']"); // Click the Add to cart button in the search.
		
		//if(verifyXPath.isfound(driver, properties.getProperty("SEARCH_BTN_MODALCONTINUESHOPPING_XPATH"))){
			//ClickElement.byXPath(driver, properties.getProperty("SEARCH_BTN_MODALCONTINUESHOPPING_XPATH"));
		//}
		
		
		} catch (Exception e){
			Assert.fail("[SEARCH] ADD ITEM TO CART...FAILED");
		}
	}

	public static void uppSubmitEmailFinalPrice(WebDriver driver,
			String sku, String email) throws Exception {
		

		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);

		//Search keyword and click Click Here
		Search.keyword(driver, sku);
		ClickElement.byXPath(driver, properties.getProperty("SEARCH_BTN_CLICKHERE_XPATH"));
		SetInputField.byXPath(driver, properties.getProperty("SEARCH_INPUT_UPPMODALEMAIL_XPATH"), email);
		ClickElement.byXPath(driver, properties.getProperty("SEARCH_BTN_UPPMODALSUBMIT_XPATH"));
		
	}

	public static void uppAddtoCart(WebDriver driver, String sku) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		
		//Search keyword and click Click Here
		Search.keyword(driver, sku);
		ClickElement.byXPath(driver, properties.getProperty("SEARCH_BTN_CLICKHERE_XPATH"));
		ClickElement.byXPath(driver, properties.getProperty("SEARCH_BTN_UPPMODALADDTOCART_XPATH"));
		
	}

	public static void setFacetPriceRange(WebDriver driver, String minPrice,
			String maxPrice) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		if(!minPrice.isEmpty()){
			SetInputField.byXPath(driver, properties.getProperty("SEARCH_INPUT_MINPRICE_XPATH"), "");
			SetInputField.byXPath(driver, properties.getProperty("SEARCH_INPUT_MINPRICE_XPATH"), minPrice);
		}
		
		if(!maxPrice.isEmpty()){
			SetInputField.byXPath(driver, properties.getProperty("SEARCH_INPUT_MAXPRICE_XPATH"), "");
			SetInputField.byXPath(driver, properties.getProperty("SEARCH_INPUT_MAXPRICE_XPATH"), maxPrice);
		}
		

		
		
		
	}

	public static void clickFilterResultsBtn(WebDriver driver) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		ClickElement.byXPath(driver, properties.getProperty("SEARCH_BTN_FILTERRESULTS_XPATH"));
		
	}

	public static void selectComparebySku(WebDriver driver, String sku) throws Exception {
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		ClickElement.byXPath(driver, "//input[@id='chkCompare" + sku + "']");
		
	}

	public static void compareSkus(WebDriver driver,
			ArrayList<String> skuList) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		System.out.println(skuList.size());
		
		for(int i = 0; i < skuList.size(); i++){
			Search.keyword(driver,skuList.get(i));
			Search.selectComparebySku(driver,skuList.get(i));
		}
		
		ClickElement.byXPath(driver, properties.getProperty("SEARCH_BTN_COMPARE_XPATH"));
		
		
	}

	public static void sortByLowestPriceFirst(WebDriver driver) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		WebElement sortBySelect = driver.findElement(By.xpath(properties.getProperty("SEARCH_SELECT_SORTBY_XPATH")));
		JavascriptExecutor jse=(JavascriptExecutor) driver;
		jse.executeScript("arguments[0].setAttribute('style', 'display: block;')",sortBySelect);
		SetSelectField.byXPath(driver, properties.getProperty("SEARCH_SELECT_SORTBY_XPATH"), "PcMall_CartPrice:::asc");
		
		
	}

	public static void sortByHighestPriceFirst(WebDriver driver) throws Exception {
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		WebElement sortBySelect = driver.findElement(By.xpath(properties.getProperty("SEARCH_SELECT_SORTBY_XPATH")));
		JavascriptExecutor jse=(JavascriptExecutor) driver;
		jse.executeScript("arguments[0].setAttribute('style', 'display: block;')",sortBySelect);
		SetSelectField.byXPath(driver, properties.getProperty("SEARCH_SELECT_SORTBY_XPATH"), "PcMall_CartPrice:::desc");
		
		
	}

	public static void selectViewBy(WebDriver driver, String view) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		WebElement viewBySelect = driver.findElement(By.xpath(properties.getProperty("SEARCH_SELECT_VIEWBY_XPATH")));
		JavascriptExecutor jse=(JavascriptExecutor) driver;
		jse.executeScript("arguments[0].setAttribute('style', 'display: block;')",viewBySelect);
		SetSelectField.byXPath(driver, properties.getProperty("SEARCH_SELECT_VIEWBY_XPATH"), view);
		
		
	}

	public static void sortByBestMatch(WebDriver driver) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		WebElement sortBySelect = driver.findElement(By.xpath(properties.getProperty("SEARCH_SELECT_SORTBY_XPATH")));
		JavascriptExecutor jse=(JavascriptExecutor) driver;
		jse.executeScript("arguments[0].setAttribute('style', 'display: block;')",sortBySelect);
		SetSelectField.byXPath(driver, properties.getProperty("SEARCH_SELECT_SORTBY_XPATH"), "");
		
	}

	public static void ctoModalClickConfigureItems(WebDriver driver, String sku, String qty) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		//Search keyword and click Click Here
		Search.keyword(Config.driver, sku);
		Search.addtocart(Config.driver, sku, qty);
		
		ClickElement.byXPath(driver, properties.getProperty("SEARCH_BTN_MODAL_CONFIGURE_XPATH"));
		
	}

	public static void ctoModalClickProceedtoCart(WebDriver driver,
			String sku, String qty) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		//Search keyword and click Click Here
		Search.keyword(Config.driver, sku);
		Search.addtocart(Config.driver, sku, qty);
		
		ClickElement.byXPath(driver, properties.getProperty("SEARCH_BTN_MODALCTO_PROCEEDCART_XPATH"));
	}

	public static void filterByBrand(WebDriver driver, String brand) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		ClickElement.byXPath(driver, "//input[@id='" + brand + "']");
		Search.clickFilterResultsBtn(driver);
		
	}

	public static String getBrandFacetCount(WebDriver driver, String brand) throws Exception {
		
		String brandCount;
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		brandCount = verifyXPath.getText(driver, "//label[@for='" + brand + "']");
		brandCount = brandCount.replaceAll("[^\\d.]", "");
		brandCount = brandCount.replaceAll("[.]", "");
		
		return brandCount;
		
	}

	public static void openPDPbySearchResult(WebDriver driver, String keyword) throws Exception {
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		ClickElement.byXPath(Config.driver, "(" + properties.getProperty("SEARCH_LINK_PDP_XPATH") + ") [position()=1]");
		
	}

	public static void setGridView(WebDriver driver) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		ClickElement.byXPath(Config.driver, properties.getProperty("SEARCH_BTN_GRIDVIEW_XPATH"));
	}

	public static void warrantyModalAddtoCart(WebDriver driver,
			String warrantyqty) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		Boolean testStatus = verifyXPath.isfound(Config.driver,properties.getProperty("SEARCH_BTN_WARRANTYMODAL_ADDITEMCART_XPATH"));
		StatusLog.printlnPassedResultTrue(driver,"[VERIFY] Add item to cart button of Warranty Modal is display",testStatus);
		//Failed test case if add to cart is not found.
		Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
		
		// Enter Quantity, Click Add to cart and Proceed to cart button on the modal	
		SetInputField.byXPath(driver,properties.getProperty("SEARCH_INPUT_WARRANTYMODAL_QTY_XPATH"),warrantyqty);
		
		ClickElement.byXPath(driver,properties.getProperty("SEARCH_BTN_WARRANTYMODAL_ADDITEMCART_XPATH")); // Click the Add to cart button in the modal.
		
		
	}

	public static void openPDPbySearchSku(WebDriver driver, String sku) throws Exception {
		
		Search.keyword(Config.driver, sku);
		ClickElement.byXPath(Config.driver, "//div[@class='rcprodinfo']//a[@data-action='Link:Product Detail Page' and contains(@href,'" + sku + "')]");

		
	}

}
