package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.grund.utility.StatusLog;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;

public class SearchKeyword {
	
	public String keyword;
	public String qty;
	public String env;
	public boolean testStatus;
	Properties sys = System.getProperties();
	
	@Test
	public void Search_Keyword_with_Results_Display() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			
			keyword = pr.getProperty("SEARCH_KEYWORD_DFLT");
			
			Search.keyword(Config.driver,keyword);
			
			testStatus = verifyXPath.isfound(Config.driver,"(" + Search.ITEMCOL_XPATH + ") [position()=1]");  //Verify the Qty field in search results appears
			StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Search for keyword will not display empty item. " + keyword, testStatus);
			
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
