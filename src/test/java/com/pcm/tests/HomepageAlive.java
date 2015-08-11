package com.pcm.tests;


import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.pcm.engine.Config;
import com.pcm.includes.Homepage;
import com.pcm.utility.StatusLog;
import com.pcm.utility.TakeScreenShot;
import com.pcm.verify.verifyXPath;

public class HomepageAlive {

	public String env;
	public boolean testStatus;
	static Properties sys = System.getProperties();
	
	
	@Test
	public void openHomepage() throws Exception{

		try 
		{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties(); //create a method for the pcm.properies
			
			//Verify the xpath of the Search box is found.
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("SEARCH_INPUT_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[HOMEPAGE] Verify Search box is display in homepage.",testStatus);
			
			Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
			
			
		}catch (Exception e){	
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
