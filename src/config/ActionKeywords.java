package config;


import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import static executionEngine.DriverScript.OR;
import config.Constants;

import org.apache.commons.io.FileUtils;
import org.fluttercode.datafactory.impl.DataFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import executionEngine.DriverScript;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import utility.Log;
import utility.ExcelUtils;

public class ActionKeywords {
		public static String sTestCaseID;
		public static WebDriver driver;
		public static DataFactory df = new DataFactory();
			
	public static void openBrowser(String object,String data){		
		Log.info("Opening Browser");
		try{				
			if(data.equals("Mozilla")){
				System.setProperty("webdriver.firefox.marionette","C:\\Hybrid Keyword Driven\\geckodriver.exe");
				driver=new FirefoxDriver();
				Log.info("Mozilla browser started");				
				}
			else if(data.equals("IE")){
				System.setProperty("webdriver.ie.driver","C:\\Hybrid Keyword Driven\\IEDriverServer.exe");
				driver=new InternetExplorerDriver();
				Log.info("IE browser started");
				}
			else if(data.equals("Chrome")){
				System.setProperty("webdriver.chrome.driver","C:\\Hybrid Keyword Driven\\chromedriver.exe");
				  ChromeOptions ops = new ChromeOptions();
				  ops.addArguments("--disable-notifications");
				 driver = new ChromeDriver(ops);
				Log.info("Chrome browser started");
				}
			
			else if(data.equals("Ghost")){
				System.setProperty("phantomjs.binary.path","C:\\Hybrid Keyword Driven\\phantomjs.exe");
				driver=new PhantomJSDriver();
				Log.info("Ghost browser started");
				}
			
			int implicitWaitTime=(10);
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		}catch (Exception e){
			Log.info("Not able to open the Browser --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void navigate(String object, String data) throws Exception{
		try{
			
			Log.info("Navigating to URL");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			//driver.get(Constants.URL);	
			driver.get(data);	
			Thread.sleep(2000);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			//ActionKeywords.clickOkAlert("", "");
		}catch(NoAlertPresentException e){
			Log.info("Not able to navigate --- " + e.getMessage());
			DriverScript.bResult = false;
			
			}		
		}
	
	public static void click(String object, String data) throws Exception{
		
		Actions builder=new Actions(driver);
			try{
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		
			Log.info("Clicking on Webelement "+ object);
			WebElement ele=driver.findElement(By.cssSelector(OR.getProperty(object)));
    		builder.moveToElement(ele).build().perform();
      		builder.click(ele).build().perform();
      				
			}catch(Exception e){
			try{
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		
				WebElement ele=driver.findElement(By.xpath(OR.getProperty(object)));
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	      		builder.moveToElement(ele).build().perform();
	      		builder.click(ele).build().perform();
				}catch(Exception e2){
			 	Log.error("Not able to click --- " + e2.getMessage());
				DriverScript.bResult = false;
         	}
		
		}
				finally {
				

				}
				}
	public static void input(String object, String data){
		
		try{
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Log.info("Entering the text in " + object);
			
			WebElement ele=driver.findElement(By.cssSelector(OR.getProperty(object)));
		
			ele.sendKeys(data);
			 
		 }catch(Exception e){
			 try{
				 driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);		
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);	 
			 }catch(Exception e1){
			 Log.error("Not able to enter text --- " + e.getMessage());
			DriverScript.bResult = false;
		 	}
		 }
		}
	

	public static void waitFor(String object, String data) throws Exception{
		
		try{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Log.info("Wait for 5 seconds");
			Thread.sleep(3000);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
		 }catch(Exception e){
			 Log.error("Not able to Wait --- " + e.getMessage());
			 DriverScript.bResult = false;
         	}
		}

	public static void closeBrowser(String object, String data){
		try{
			Log.info("Closing the browser");
			driver.quit();
		 }catch(Exception e){
			 Log.error("Not able to Close the Browser --- " + e.getMessage());
			 DriverScript.bResult = false;
         	}
	}
	
	public static void clearField(String object, String data){
			try{
				Log.info("Clear the field");
				driver.findElement(By.cssSelector(OR.getProperty(object))).clear();
			 }catch(Exception e){
				 Log.error("Not able to Clear the Field --- " + e.getMessage());
				 DriverScript.bResult = false;
	         	}
	}
			
			public static void switchToIFrame(String object, String data){
				try{
					//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					Log.info("Switch IFrame");
					driver.switchTo().frame(driver.findElement(By.cssSelector(OR.getProperty(object))));
									
				}catch(Exception e){
					 try{
						// driver.switchTo().frame(driver.findElement(By.id(OR.getProperty(object))));  
					 }catch(Exception e1){
					 Log.error("Not able switch IFrame --- " + e.getMessage());
					DriverScript.bResult = false;
				 	}
		         	}
				}
	

			
			public static void switchToDefaultIframe(String object, String data){				
				try{
				//	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					Log.info("Switch to Default Iframe");
					
					if(data.equals("IE"))
					{
					ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
					driver.switchTo().window(tabs2.get(0));	
				}else{
					driver.switchTo().defaultContent();
				}
				 }catch(Exception e){
					 Log.error("Not able to switch to default frame --- " + e.getMessage());
					 DriverScript.bResult = false;
		         	}
		}
			
			public static void takeScreenShot(String object, String data){				
				try{
					int iTestcase=1;
					sTestCaseID=ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases); 
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					Log.info("Taking Screenshot");				
					Robot robot = new Robot();
						robot.keyPress(KeyEvent.VK_CONTROL);			
						robot.keyPress(KeyEvent.VK_MINUS);	
						robot.keyPress(KeyEvent.VK_MINUS);
				
					String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
					Screenshot screenshot =new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);
					ImageIO.write(screenshot.getImage(), "PNG", new File("C:\\Hybrid Keyword Driven\\src\\Screenshot\\"+data+"_"+timeStamp+".png"));
						
						robot.keyPress(KeyEvent.VK_0);
						robot.keyRelease(KeyEvent.VK_CONTROL);
						 
				}
				catch(Exception e){
					try{
					String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
						TakesScreenshot ts=(TakesScreenshot)driver;
						File source= ts.getScreenshotAs(OutputType.FILE);						
						FileUtils.copyFile(source, new File("C:\\Hybrid Keyword Driven\\src\\Screenshot\\"+data+"_"+timeStamp+".png"));
						}catch(Exception e1){
							Log.error("Not able to take Screenshot --- " + e.getMessage());
				 			
							DriverScript.bResult = false;
						}
					finally{}
			}
		
			}
			@SuppressWarnings("unused")
			public static void switchToChildWindow(String object, String data){				
				try{				
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					Log.info("Switch to child Window");
					String parentWindowHandler = driver.getWindowHandle(); // Store your parent window
					 String subWindowHandler = null;

					 Set<String> handles = driver.getWindowHandles(); // get all window handles
					 Iterator<String> iterator = handles.iterator();
					 while (iterator.hasNext()){
					     subWindowHandler = iterator.next();
					 }
					 driver.switchTo().window(subWindowHandler); // switch to popup window
					driver.manage().window().maximize();
					
				}catch(Exception e){
					 Log.error("Not able to switch window --- " + e.getMessage());
					 DriverScript.bResult = false;
		         	}
		}
			
			public static void closeWindow(String object, String data){
				try{
					Log.info("Closing the Window");
					driver.close();
				 }catch(Exception e){
					 Log.error("Not able to Close the window --- " + e.getMessage());
					 DriverScript.bResult = false;
		         	}
			}
			
			public static void switchToParentWindow(String object, String data){				
				try{
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					Log.info("Switch to child Window");
					Set <String> set1=driver.getWindowHandles();
					Iterator <String> win1=set1.iterator();
					String parent=win1.next();
					driver.switchTo().window(parent);
				}catch(Exception e){
					 Log.error("Not able to switch Parent Window --- " + e.getMessage());
					 DriverScript.bResult = false;
		         	}
		}
			
			public static void doubleClick(String object, String data) throws Exception{
				
				Actions builder=new Actions(driver);
				WebElement ele = null;
					try{
						int time=5;

					Log.info("Double clicking on Webelement "+ object);
					for(int i=0;i<time;i++)
					{
					try{
						
						driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					ele=driver.findElement(By.cssSelector(OR.getProperty(object)));					
						
					builder.moveToElement(ele,137,0).click().build().perform();
					new Actions(driver).moveByOffset(110,0).click().build().perform(); 
			
					break;
					}
					catch(Exception e){
						try{
							Log.info("AutoIT code 2"+ object);
						 	break;
							} 
					catch(Exception e1){
				
					 	Log.error("Not able to double click --- " + e1.getMessage());
					 	DriverScript.bResult = false;
		         	}
					}
					}
					}
					finally{}
					}
					
		
			public static void clickCancleAlert(String object, String data){
				try{
					Log.info("Click Cancle on Alert");
					Alert alert=driver.switchTo().alert();
					alert.dismiss();
					 
				}catch(Exception e){
					 Log.error("Not able to switch to Alert --- " + e.getMessage());
					 DriverScript.bResult = false;
		         	}
		}
			
			public static void clickOkAlert(String object, String data){
				try{
					Log.info("Click Ok on Alert");
					
					Alert alert=driver.switchTo().alert();
					alert.accept();
 
					
				}catch(Exception e){
					 Log.error("Not able to lick Ok on Alert --- " + e.getMessage());
					 DriverScript.bResult = false;
		         	}
		}

			public static void handleProductDetails(String object, String data) {
				try{
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				 Actions actions = new Actions(driver);		
				 DataFactory df = new DataFactory();				
				
				WebElement table = driver.findElement(By.id("sgs-mainTable")); 

				// Now get all the TR elements from the table 
				List<WebElement> allRows = table.findElements(By.tagName("tr")); 

				// And iterate over 
				for (WebElement row : allRows) { 
				    List<WebElement> cells = row.findElements(By.cssSelector("td:nth-child(4)")); 		    
				    // Keyin the contents of quantity cell
				    for (WebElement cell : cells) { 
				    	String comments=df.getRandomWord();
				    	int quantity1= df.getNumberBetween(1,999);
				    	String quantity=Integer.toString(quantity1);
						actions.click(cell);
						actions.sendKeys(quantity);
						actions.sendKeys(Keys.TAB);					
						actions.sendKeys(comments);
						actions.build().perform();	
						
				    }
				    actions.sendKeys(Keys.TAB);
				}
				}catch(Exception e){
						 Log.error("Not able to Handle Product Details table --- " + e.getMessage());
						 DriverScript.bResult = false;
			         	}

					    }
			public static void dropDown(String object, String data)
			{
				
				try{
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					Log.info("Dropdown selected");
				DataFactory df = new DataFactory();
				int end=Integer.parseInt(data);
				int index=df.getNumberBetween(1,end);
				//WebElement mySelectElement=driver.findElement(By.xpath(OR.getProperty(object)));
				Select select = new Select(driver.findElement(By.xpath(OR.getProperty(object))));	
				select.selectByIndex(index);
									
				}catch(Exception e){
					 Log.error("Not able to select DropDown --- " + e.getMessage());
					 DriverScript.bResult = false;
		         	}	
				
			}	
			
			public static void refreshData(String object, String data) throws Exception{
	                try{
	                	
	                	Log.info("Refreshing Data");
	                /*	XSSFWorkbook ExcelWBook;
	                	String SheetName=Constants.Sheet_TestSteps;
	                	FileInputStream ExcelFile = new FileInputStream(Constants.Path_OR);
	                    ExcelWBook = new XSSFWorkbook(ExcelFile);
	                	ExcelWBook.getSheet(SheetName);
	                	XSSFFormulaEvaluator.evaluateAllFormulaCells(ExcelWBook);
	                	FormulaEvaluator evaluator = ExcelWBook.getCreationHelper().createFormulaEvaluator();	 */               	
	                	//evaluator.evaluateAll();
	                	ExcelUtils.refreshData();
	                 	                    
	                 }catch (Exception e){
	                     Log.error("Class Utils | Method getCellData | Exception desc : "+e.getMessage());
	                     DriverScript.bResult = false;
	                     
	                     }
	                 }
						
			 public static void lookUp(String object, String data) throws Exception{
				 	//Actions builder=new Actions(driver);
	                try{	               
	                	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		
	        			Log.info("Selecting LookUp Data "+ object);
	        			//WebElement ele=
	        		
	        			driver.findElement(By.xpath(OR.getProperty(object))).click();	        	
	        		/*	builder.moveToElement(ele).build().perform();
	        			builder.click(ele).build().perform();*/
	        			Thread.sleep(1000);
	        			driver.findElement(By.xpath("//li[@id='item0']/a[2]")).click();
	        			                    
	                 }catch (Exception e){
	                	 try{
	                		 
	 	        			WebElement ele=driver.findElement(By.xpath("//li[@id='item0']/a[2]"));
	 	        			JavascriptExecutor js = (JavascriptExecutor)driver;
	 	        			js.executeScript("arguments[0].click();", ele);	 
	                	 }
	                	 
	                	 catch(Exception e2){
	                     Log.error("Not able to select LookUp data "+e.getMessage());
	                     e2.printStackTrace();
	                     DriverScript.bResult = false;
	                     
	                     }
	                 }
			 } 

			 public static void scrollDiv(String object, String data) throws Exception{
				 	//Actions builder=new Actions(driver);
	                try{	               
	                	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		
	        			Log.info("Scroll div");
	        			JavascriptExecutor js;
	        			js = (JavascriptExecutor) driver;
	        			js.executeScript("$(\".ext-strict\").animate({ scrollTop: \"500px\" })");
	        			//WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
	        			//js.executeScript("arguments[0].scrollIntoView(true);",element);
	                 }catch (Exception e){
	                	 Log.error("Not able to scroll "+e.getMessage());
	                     e.printStackTrace();
	                 }}
			 
			 public static void refresh(String object, String data) throws Exception{
					
					try{
						driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						Log.info("Refresh");
						driver.navigate().refresh();
						WebDriverWait wait = new WebDriverWait(driver, 2);
						try{
						    wait.until(ExpectedConditions.alertIsPresent());
						    Alert alert = driver.switchTo().alert();
						    alert.accept();
						}
						catch (Exception e){
						    System.out.println("No alert");
						}
						driver.switchTo().defaultContent();
						//Actions actions = new Actions(driver);
						//actions.keyDown(Keys.CONTROL).sendKeys(Keys.F5).perform();				
						driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						
					 }catch(Exception e){
						 Log.error("Not able to Wait --- " + e.getMessage());
						 DriverScript.bResult = false;
			         	}
					}
			 
			 public static void FrameList(String object, String data) throws Exception{
					
					try{
					//	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						Log.info("FrameList");
						List<WebElement> frameList = driver.findElements(By.cssSelector("div#navigatortab"));
						int numElements = frameList.size();
						System.out.println("Frame name= "+frameList+" "+numElements) ;
						
						driver.switchTo().frame(numElements);
						driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						
					 }catch(Exception e){
						 Log.error("Not able to select Frame --- " + e.getMessage());
						 DriverScript.bResult = false;
			         	}
					}
			 
			 public static void elementList(String object, String data) throws Exception{
					
				 try{
							driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							Log.info("List of elements");						
							DataFactory df = new DataFactory();
							int end=Integer.parseInt(data);
							System.out.println(end);
							int index=df.getNumberBetween(0,end);
							WebElement ele = driver.findElement(By.xpath(OR.getProperty(object)));
							List<WebElement> list = ele.findElements(By.xpath(OR.getProperty(data))); 
							Select select = new Select((WebElement) list);	
							select.selectByIndex(index);
							driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							
						 }catch(Exception e){
							 Log.error("Not able to select element --- " + e.getMessage());
							 DriverScript.bResult = false;
				         	}
						}
			 
			 public static void radio(String object, String data) throws Exception{
					
				 try{
							driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							Log.info("Clicking on Radio Button");						
							List<WebElement>radioButton = driver.findElements(By.cssSelector((OR.getProperty(object))));
						 
						    Random random = new Random();
						    int index = random.nextInt(radioButton.size());
						    radioButton.get(index).click();
						/*    int end=radioButton.size();
						    int index=df.getNumberBetween(0,end);
						    Select select = new Select((WebElement) radioButton);	
							select.selectByIndex(2);		*/		
							driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							
						 }catch(Exception e){
							 Log.error("Not able to Click on Radio Button --- " + e.getMessage());
							 DriverScript.bResult = false;
				         	}
						}
			 
					}

			 
			   
				    					