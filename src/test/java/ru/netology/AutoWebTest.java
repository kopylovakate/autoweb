package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutoWebTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }


    @Test
    void shouldBeFailedEmptyNameInput() {
        WebElement phoneNumberField = driver.findElement(By.xpath("//*[@data-test-id='phone']//input"));

        phoneNumberField.sendKeys("+79286352417");
        driver.findElement(By.xpath("//*[@data-test-id='agreement']")).click();
        driver.findElement(By.xpath("//button[@role='button']")).click();

        WebElement result = driver.findElement(By.xpath("//*[contains(@class, 'input_invalid')][@data-test-id='name']//*[contains(@class, 'input__sub')]"));

        assertEquals("Поле обязательно для заполнения", result.getText().trim());
    }

    @Test
    void shouldBeFailedIncorrectNameInput() {
        WebElement firstLastNameField = driver.findElement(By.xpath("//*[@data-test-id='name']//input"));
        WebElement phoneNumberField = driver.findElement(By.xpath("//*[@data-test-id='phone']//input"));

        firstLastNameField.sendKeys("Александр2 Иванов");
        phoneNumberField.sendKeys("+792863524172");
        driver.findElement(By.xpath("//*[@data-test-id='agreement']")).click();
        driver.findElement(By.xpath("//button[@role='button']")).click();

        WebElement result = driver.findElement(By.xpath("//*[contains(@class, 'input_invalid')][@data-test-id='name']//*[contains(@class, 'input__sub')]"));

        assertEquals("Имя и Фамилия указанны неверно. Допустимы только русские буквы, пробелы и дефисы.", result.getText().trim());
    }

    @Test
    void validTest() {
        WebElement firstLastNameField = driver.findElement(By.xpath("//*[@data-test-id='name']//input"));
        WebElement phoneNumberField = driver.findElement(By.xpath("//*[@data-test-id='phone']//input"));

        firstLastNameField.sendKeys("Александр Иванов");
        phoneNumberField.sendKeys("+79286352417");
        driver.findElement(By.xpath("//*[@data-test-id='agreement']")).click();
        driver.findElement(By.xpath("//button[@role='button']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-test-id='order-success']")));

        WebElement result = driver.findElement(By.xpath("//*[@data-test-id='order-success']"));

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", result.getText().trim());
    }


    @Test
    void shouldBeFailedLongNumberInput() {
        WebElement firstLastNameField = driver.findElement(By.xpath("//*[@data-test-id='name']//input"));
        WebElement phoneNumberField = driver.findElement(By.xpath("//*[@data-test-id='phone']//input"));

        firstLastNameField.sendKeys("Александр Иванов");
        phoneNumberField.sendKeys("+7928635241");
        driver.findElement(By.xpath("//*[@data-test-id='agreement']")).click();
        driver.findElement(By.xpath("//button[@role='button']")).click();

        WebElement result = driver.findElement(By.xpath("//*[contains(@class, 'input_invalid')][@data-test-id='phone']//*[contains(@class, 'input__sub')]"));

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", result.getText().trim());
    }

    @Test
    void shouldBeFailedEmptyNumberInput() {
        WebElement firstLastNameField = driver.findElement(By.xpath("//*[@data-test-id='name']//input"));

        firstLastNameField.sendKeys("Александр Иванов");
        driver.findElement(By.xpath("//*[@data-test-id='agreement']")).click();
        driver.findElement(By.xpath("//button[@role='button']")).click();

        WebElement result = driver.findElement(By.xpath("//*[contains(@class, 'input_invalid')][@data-test-id='phone']//*[contains(@class, 'input__sub')]"));

        assertEquals("Поле обязательно для заполнения", result.getText().trim());
    }

}
