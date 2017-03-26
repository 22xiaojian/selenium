package lib2.selenium;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collection;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

@RunWith(Parameterized.class)
public class Selenium {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private String id, pwd, github;

	public Selenium(String id, String github) {
		this.id = id;
		this.pwd = id.substring(4);
		this.github = github;
	}
  @Before
  public void setUp() throws Exception {
	System.setProperty("webdriver.firefox.bin", "D:/App/Mozilla Firefox/firefox.exe");  //指定firefox浏览器的路径
    
	driver = new FirefoxDriver();
    baseUrl = "http://121.193.130.195:8080";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }
  
  //获取csv文件中的数据
  @Parameters
  public static Collection<Object[]> exportCsv() throws IOException {
	  String srcCSV = "D:/Learn/软件项目测试/Lab2/inputgit.csv";;
	  CsvReader reader = new CsvReader(srcCSV, ',', Charset.forName("GBK"));
	  Object[][] obj = new Object[117][];
	  int count = 0;
	  while (reader.readRecord()) {
	  //把头保存起来
		  obj[count] = new Object[] { reader.get("学号"), reader.get("github地址") };
			count++;
		}
		return Arrays.asList(obj);
  }

  @Test
  public void testSelenium() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys(this.id);
    driver.findElement(By.id("pwd")).clear();
    driver.findElement(By.id("pwd")).sendKeys(this.pwd);
    driver.findElement(By.id("submit")).click();
    assertEquals(this.github, driver.findElement(By.xpath("//tbody[@id='table-main']/tr[3]/td[2]")).getText());
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

}
