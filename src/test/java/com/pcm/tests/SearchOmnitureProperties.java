package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;
import com.grund.utility.StatusLog;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;



public class SearchOmnitureProperties {

	public String keyword;
	public String isContent;
	public String maxPrice;
	public String filterlabel;
	public String priceStrike;
	public String unitPrice;
	public String FinalPrice;
	public String qty;
	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String edpno;
	public String cartOrderTotal;
	public String title;
	
	public static final String SEARCH_SCRIPTS_OMNIEVENTS_XPATH="//script[contains(text(),'s.events = \"event1\"')]";
	public static final String SEARCH_SCRIPTS_OMNIEVAR3_XPATH="//script[contains(text(),'s.eVar3 = \"search\"')]";
	
	
	Properties sys = System.getProperties();
	
	
	@Test
	public void Search_Omniture_Properties() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			
			//Define properties
			keyword = pr.getProperty("SEARCH_KEYWORD_DFLT");
			Search.keyword(Config.driver, keyword);
	
			//Verify the Omniture script s.events has a value
			testStatus = verifyXPath.isfound(Config.driver, SEARCH_SCRIPTS_OMNIEVENTS_XPATH);
			StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Omniture s.events has a value.",testStatus);
		
			//Verify the Omniture script s.eVar3 has a value
			testStatus = verifyXPath.isfound(Config.driver, SEARCH_SCRIPTS_OMNIEVAR3_XPATH);
			StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Omniture s.eVar3 has a value.",testStatus);
		
			//Verify the Omniture script s.eVar1 has a value
			testStatus = verifyXPath.isfound(Config.driver, "//script[contains(text(),'s.eVar1 = \"" + keyword + "\"')]");
			StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Omniture s.eVar1 has a value.",testStatus);
		
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
