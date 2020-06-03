package YandexTest;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;


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
    public void test() {

        //определение пути до драйвера и его настройка
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        //создание экземпляра драйвера
        WebDriver driver = new ChromeDriver();
        //окно разворачивается на полный экран
        driver.manage().window().maximize();
        //задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //получение ссылки на страницу входа из файла настроек
        driver.get(ConfProperties.getProperty("page"));







    }
}


