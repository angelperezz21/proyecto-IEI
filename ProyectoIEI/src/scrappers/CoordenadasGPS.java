package scrappers;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CoordenadasGPS {
    public static String direccionDeCoordenadas(double latitud, double longitud){
        //Creamos una instancia de Firefox
        WebDriver driver = new FirefoxDriver();

        //Vamos a la pagina
        driver.get("https://www.coordenadas-gps.com/");

        //Localizamos los textfield de longitud y latitud
        WebElement latitude_txtf = driver.findElement(By.id("latitude"));
        WebElement longitude_txtf = driver.findElement(By.id("longitude"));

        //Escribimos la longitud y latitud en los textfield
        latitude_txtf.sendKeys(String.valueOf(latitud));
        longitude_txtf.sendKeys(String.valueOf(longitud));

        //Localizamos el boton de obtener direccion
        WebElement getaddress_btn = driver.findElement(By.xpath(".//button[contains(@onclick, 'codeLatLng(1)')]"));

        //Hacemos click en el boton
        getaddress_btn.click();

        //Esperamos al resultado
        WebDriverWait waiting = new WebDriverWait(driver, Duration.ofSeconds(10));
        waiting.until(ExpectedConditions.presenceOfElementLocated(By.id("geocodedAddress")));

        //Recuperamos el span con la info
        WebElement geocodedAddressSpan = driver.findElement(By.id("geocodedAddress"));

        //Recuperamos su valor
        String res = geocodedAddressSpan.getAttribute("innerHTML");

        System.out.println(res);

        return null;
    }
}