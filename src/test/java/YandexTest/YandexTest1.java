package YandexTest;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;


public class YandexTest1 {
    /**
     * Техническое задание
     * С помощью Java+Maven+JUnit5:
     *
     * -Перейти в браузере на yandex.ru
     * -Вбить в поиске Яндекс почта
     * -Нажать кнопку найти
     * -В результатах поиска выбрать и перейти на страницу Яндекс Почты
     * -Проскроллить страницу вниз и нажать Войти
     * -В поле Логин ввести случайное значение (Каждый прогон теста уникальное)
     * -Нажать кнопку Войти
     * -Проверить наличие ошибки: Такой логин не подойдет
     * -Создать папку results, куда сгенерировать xml файл result.xml формата:
     * <test>
     *    <name>yandex</yandex>
     *    <date>текущая дата и время</date>
     *    <result>failed либо passed</result>
     * </test>
     * -Код проекта выложить в гит
     * -Сообщить о готовности
     */

    @Test
    public void test() throws ParserConfigurationException, TransformerException, IOException {

        //определение пути до драйвера и его настройка
        System.setProperty("webdriver.chrome.driver", "webdrivers/chromedriver");
        //создание экземпляра драйвера
        WebDriver driver = new ChromeDriver();
        //окно разворачивается на полный экран
        driver.manage().window().maximize();
        //задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //получение ссылки на страницу входа из файла настроек
        driver.get("https://yandex.ru/");
        //Вбиваем в поиске Яндекс почта
        driver.findElement(By.xpath("//input[@name=\"text\"]")).sendKeys("Яндекс почта");
        //Нажимаем на кнопку найти
        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();

        // получаем набор текущих открытых окон
        Set<String> oldWindowsSet = driver.getWindowHandles();

        //В результатах поиска выбираем и переходим на страницу Яндекс Почты
        // нажимаем на ссылку, которая открывает документ в новом окне
        driver.findElement(By.xpath("//b[text()=\"mail.yandex.ru\"]")).click();

        // ожидаем открытия и получаем дескриптор нового окна
        String newWindowHandle = (new WebDriverWait(driver, 10))
                .until(new ExpectedCondition<String>() {
                           public String apply(WebDriver driver) {
                               Set<String> newWindowsSet = driver.getWindowHandles();
                               newWindowsSet.removeAll(oldWindowsSet);
                               return newWindowsSet.size() > 0 ? newWindowsSet.iterator().next() : null;
                           }
                       }
                );
        driver.switchTo().window(newWindowHandle);

        WebElement element = scrollToElement(driver, "(//a/span[text()=\"Войти\"])[2]/..");
        element.click();

        int a = (int) (1000 + Math.random() * 2000);
        driver.findElement(By.xpath("//label[text()=\"Введите логин, почту или телефон\"]/../input")).sendKeys(String.valueOf(a));
        driver.findElement(By.xpath("//span[text()=\"Войти\"]/..")).click();
        WebElement actualElement = driver.findElement(By.xpath("//div[@class=\"passp-form-field__error\"]"));
        String actual = actualElement.getText();
        String expected = "Такой логин не подойдет";
        boolean bool = true;

        try {
            assertEquals(expected, actual);
            WriteParamXML("passed");
            bool = false;
        }
        finally {
            if (bool)
               WriteParamXML("failed");
        }
        driver.quit();
    }

    public void WriteParamXML(String result) throws TransformerException, IOException, ParserConfigurationException {
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
}