package lab2test;

import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class lab2 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private String id;
  private String password;
  private String expected;
  private String actualpath;


  public static List<String> importCsv(File file){

	List<String> dataList=new ArrayList<String>();

	BufferedReader br=null;
	try {
             br = new BufferedReader(new FileReader(file));
             String line = "";
             while ((line = br.readLine()) != null) {
                 dataList.add(line);
             }
         }catch (Exception e) {
         }finally{
             if(br!=null){
                 try {
                     br.close();
                     br=null;
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         }
 
         return dataList;
     }
  @Before
  public void setUp() throws Exception {
	  String driverPath = "D:/workspace/lab1/src/lab2/geckodriver.exe";
	  System.setProperty("webdriver.gecko.driver", driverPath);
	  System.setProperty("webdriver.firefox.bin","D:/迅雷下载/firefox.exe"); 
	  driver = new FirefoxDriver();
	  baseUrl = "http://121.193.130.195:8800";
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testBaidu() throws Exception {
      List<String> dataList=importCsv(new File("D:/学习/大三下/软件测试技术/实验/实验2/软件测试名单.csv"));
         for(int i = 1 ; i < dataList.size(); i++) {
        	 System.out.println(dataList.get(i));
              String[] split = dataList.get(i).split(",");
              this.id=split[1];
              this.password=split[1].substring(4,10);
              this.expected="";
              if (split.length==4)
            	  this.expected=split[3];
              driver.get(baseUrl + "/");
		   	  driver.findElement(By.name("id")).clear();
		   	  System.out.println(this.id+"  "+this.password+"  "+this.expected);
		   	  driver.findElement(By.name("id")).sendKeys(this.id);
		   	  driver.findElement(By.name("password")).clear();
		   	  driver.findElement(By.name("password")).sendKeys(this.password);
		   	  //new Select(driver.findElement(By.id("gender"))).selectByVisibleText("男");
		   	  driver.findElement(By.id("btn_login")).click();
		   	  this.actualpath = driver.findElement(By.id("student-git")).getText();
		   	  driver.findElement(By.linkText("LOG OUT")).click();
		   	  System.out.println(this.actualpath);

		   	  //assertEquals(this.expected, git1);
//		   	  System.out.println(git1.toString());
		   	  assertEquals(this.expected, this.actualpath);
         }
	  
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}

