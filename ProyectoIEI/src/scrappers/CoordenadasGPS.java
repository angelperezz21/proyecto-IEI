package scrappers;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CoordenadasGPS {
    /**
     * La wea esta abre el chrome y te devuelve el string con el CP y esas cosas
     * @param latitud
     * @param longitud
     * @return
     */
    public static String direccionDeCoordenadas(double latitud, double longitud){
        //Creamos una instancia de Chrome
        WebDriver driver = new ChromeDriver();

        //Creamos actions para movernos por la pagina
        Actions actions = new Actions(driver);

        //Creamos un waiting para esperar que ocurran eventos
        WebDriverWait waiting = new WebDriverWait(driver, Duration.ofSeconds(30));

        //Vamos a la pagina
        driver.get("https://www.coordenadas-gps.com/");

        //Hacemos scroll al mapa
        WebElement element = driver.findElement(By.id("latitude"));
        actions.moveToElement(element);
        actions.perform();

        //Esperamos a que cargue geocodedAddress
        waiting.until(ExpectedConditions.presenceOfElementLocated(By.id("geocodedAddress")));
        WebElement geocodedAddressSpan = driver.findElement(By.id("geocodedAddress"));

        //Localizamos los textfield de longitud y latitud
        WebElement latitude_txtf = driver.findElement(By.id("latitude"));
        WebElement longitude_txtf = driver.findElement(By.id("longitude"));

        //Escribimos la longitud y latitud en los textfield
        latitude_txtf.clear();
        latitude_txtf.sendKeys(String.valueOf(latitud));

        longitude_txtf.clear();
        longitude_txtf.sendKeys(String.valueOf(longitud));

        //Localizamos el boton de obtener direccion
        WebElement getaddress_btn = driver.findElement(By.xpath(".//button[contains(@onclick, 'codeLatLng(1)')]"));

        //Hacemos click en el boton
        getaddress_btn.click();

        //Esperamos a que cargue el resultado
        geocodedAddressSpan = driver.findElement(By.id("geocodedAddress"));
        String initialAddress = geocodedAddressSpan.getAttribute("innerHTML");

        waiting.until(ExpectedConditions.invisibilityOfElementWithText(By.id("geocodedAddress"), initialAddress));

        //Recuperamos el span con la info
        geocodedAddressSpan = driver.findElement(By.id("geocodedAddress"));

        //Recuperamos su valor
        String addressText = geocodedAddressSpan.getAttribute("innerHTML");

        return addressText;
    }
}