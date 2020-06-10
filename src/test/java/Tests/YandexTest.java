package Tests;

import Main.Base;
import com.google.common.base.Strings;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;


public class YandexTest extends Base{
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
        String os = System.getProperty("os.name").toLowerCase();
        //условие
        if (os.contains("mac")){
            System.setProperty("webdriver.chrome.driver", "webdrivers/chromedriver");
        }
        else if (os.contains("win")){
            System.setProperty("webdriver.chrome.driver", "webdrivers/chromedriver.exe");
        }
        else {
            System.out.println("Для данной системы нет браузера");
        }

        //создание экземпляра драйвера
        WebDriver driver = new ChromeDriver();
        //окно разворачивается на полный экран
        driver.manage().window().maximize();
        //задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //получение ссылки на страницу входа из файла настроек
        driver.get("https://yandex.ru/");
        //Вбиваем в поиске Яндекс почта
        sendKeysToElement(driver, "//input[@name=\"text\"]","Яндекс почта" );
        //Нажимаем на кнопку найти
        clickToElement(driver, "//button[@type=\"submit\"]");

        //В результатах поиска выбираем и переходим на страницу Яндекс Почты
        // нажимаем на ссылку, которая открывает документ в новом окне
        clickToElement(driver, "//b[text()=\"mail.yandex.ru\"]");

        // ожидаем открытия и получаем дескриптор нового окна
        switchWindow(driver, 1);
        WebElement element = scrollToElement(driver, "(//a/span[text()=\"Войти\"])[2]/..");
        element.click();

        int a = (int) (1000 + Math.random() * 2000);
        sendKeysToElement(driver, "//label[text()=\"Введите логин, почту или телефон\"]/../input", Integer.toString(a) );
        clickToElement(driver, "//span[text()=\"Войти\"]/..");
        WebElement actualElement = driver.findElement(By.xpath("//div[@class=\"passp-form-field__error\"]"));
        String actual = actualElement.getText();
        String expected = "Такой логин не подойдет";
        boolean bool = true;
        try {
            assertEquals(expected, actual);
            writeXML("passed");
            bool = false;
        }
        finally {
            if (bool)
               writeXML("failed");
        }

        driver.quit();
    }
}