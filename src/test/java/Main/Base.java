package Main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Base {

    public WebElement scrollToElement(WebDriver driver, String xpath){
        int time = (int)System.currentTimeMillis()/1000 + 5;

        while (time > (int)System.currentTimeMillis()/1000){
            try {
                WebElement element = driver.findElement(By.xpath(xpath));
                Actions action = new Actions(driver);
                action.moveToElement(element).perform();
                return element;
            }
            catch (Exception ignored){}
        }
        return null;
    }
    public WebElement clickToElement(WebDriver driver, String xpath){
        int time = (int)System.currentTimeMillis()/1000 + 5;

        while (time > (int)System.currentTimeMillis()/1000){
            try {
                WebElement element = driver.findElement(By.xpath(xpath));
                element.click();
                return element;
            }
            catch (Exception ignored){}
        }
        return null;
    }
    public void sendKeysToElement(WebDriver driver, String xpath, String value){
        int time = (int)System.currentTimeMillis()/1000 + 5;

        while (time > (int)System.currentTimeMillis()/1000) {
            try {
                WebElement element = driver.findElement(By.xpath(xpath));
                element.clear();
                element.sendKeys(value);
                return;
            } catch (Exception ignored) {}
        }
    }
    public String getTextToElement(WebDriver driver, String xpath){
        int time = (int)System.currentTimeMillis()/1000 + 5;

        while (time > (int)System.currentTimeMillis()/1000){
            try {
                WebElement element = driver.findElement(By.xpath(xpath));
                return element.getText();
            }
            catch (Exception ignored){}
        }
        return null;
    }
    public void switchWindow(WebDriver driver, int i) {
        new WebDriverWait(driver, 20).until((ExpectedCondition<WebDriver>) driver1 ->
            {
                String window = ((ArrayList<String>) driver.getWindowHandles()).get(i);
                return driver.switchTo().window(window);
            }
        );
    }

    public void writeXML(String result) throws TransformerException, IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element root = doc.createElement("test");

        Element element1 = doc.createElement("name");
        element1.setTextContent("yandex");

        Element element2 = doc.createElement("date");
        Date date = new Date();
        element2.setTextContent(date.toString());

        Element element3 = doc.createElement("result");
        element3.setTextContent(result);

        doc.appendChild(root);
        root.appendChild(element1);
        root.appendChild(element2);
        root.appendChild(element3);

        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream("src/test/resources/results/result.xml")));
    }
}