package com.example.firstapirest.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import com.example.firstapirest.models.ResponseDTO;

import io.micrometer.common.util.StringUtils;

@Service // con esta anotacion indicamos que es un servicio el cual podremos inyectar en otras clases
public class ScrapperAlphaServicesImp implements ScrapperAlphaService{ // implementamos la interfaz ScrapperService

    List<String> urls = List.of("https://riyasewana.com/search/", "https://www.alpha.com/alpha", "https://www.alpha.com/alpha/alpha");
    List<String> urls2 = List.of("https://www.cruzverde.cl/search?query=", "https://www.alpha.com/alpha", "https://www.alpha.com/alpha/alpha");

    @Override
    public Set<ResponseDTO> getMedicinesByName(String medicineName) {
        Set<ResponseDTO> responseDTOS = new HashSet<>();
        
        for (String url: urls2) {
            if (url.contains("cruzverde")) {
                //method to extract data from cruzverde.cl
               // extractDataFromCruzverde(responseDTOS, url + medicineName);
                extractDataFromCruzverdeWithSelenium(responseDTOS, url + medicineName);
            } else if (url.contains("alpha")) {
                //method to extract data from alpha.com
                //extractDataFromAlpha(responseDTOS, url + medicineName);
            }
        }

        return responseDTOS;
    }

    private void extractDataFromCruzverde(Set<ResponseDTO> responseDTOS, String url) {
        try{
        Document document = Jsoup.connect(url).get();
        Elements elements = document.select("grid.grid-cols-1.gap-15.lg.grid-cols-3.lg.gap-30.ng-star-inserted");
        System.out.println("elements");
        System.out.println(url);   //version JSoup
      /*    UserAgent userAgent = new UserAgent();
      userAgent.visit(url);
      
      userAgent.getLocalStorage().setItem("location", "value");

      Element element = userAgent.doc.findFirst("<grid.grid-cols-1.gap-15.lg.grid-cols-3.lg.gap-30.ng-star-inserted>");
            */
      //System.out.println(element.outerHTML());
        for (Element element : elements) {
            
            String brand = element.select("div.italic.uppercase.cursor-pointer.text-12.hover\\:text-accent").text();
            String name = element.select("a.font-open.flex.items-center.text-main.text-16.sm\\:text-18.leading-20.font-semibold.ellipsis.hover\\:text-accent").text();
            String price = element.select("span.font-bold.text-prices").text();
            String tag = element.select("span.text-white.bg-prices.rounded-full.ml-4.sm\\:ml-8.text-10.sm\\:text-12.mt-2.font-bold.px-5").text();
            String normalPrice = element.select("div.text-12.sm\\:text-14.line-through").text();
        
            System.out.println("Producto: " + brand);
            System.out.println("Descripción: " + name);
            System.out.println("Oferta: " + price);
            System.out.println("Etiqueta Oferta: " + tag);
            System.out.println("Precio Normal: " + normalPrice);
            System.out.println("---");
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void extractDataFromCruzverdeWithSelenium(Set<ResponseDTO> responseDTOS, String url)
    {
        try {
            System.setProperty("webdriver.chrome.driver", "/Users/abarrap/Documents/ppers/chromedriver_mac64/chromedriver");
            WebDriver driver = new ChromeDriver();
            List<String> data = new ArrayList<>();
            // driver.get(url);
            driver.navigate().to(url);
            driver.manage().window().maximize();
            WebElement button = driver.findElement(By.cssSelector(".btn-secondary"));

            // Hacer clic en el botón
            button.click();
        
            // Buscar todos los elementos que contienen los precios
            
            List<WebElement> productos = driver.findElements(By.cssSelector(".flex.flex-col.justify-end.h-full.text-12.sm\\:text-14 .font-bold.text-prices"));
            for (WebElement producto : productos) {
                WebElement precio = producto.findElement(By.cssSelector(""));
                System.out.println(precio.getText());
                WebElement name = producto.findElement(By.cssSelector(".font-open.flex.items-center.text-main.text-16.sm\\:text-18.leading-20.font-semibold.ellipsis.hover\\:text-accen .ng-star-inserted"));
                System.out.println(name.getText());             
            }

            // Crear un ArrayList para almacenar los textos de los precios
            ArrayList<String> preciosText = new ArrayList<String>();
            ArrayList<String> namesText = new ArrayList<String>();


            // Iterar sobre la lista de precios y agregar cada texto a la lista de preciosText
    /*       for (WebElement precio : precios) {
                preciosText.add(precio.getText());
            }
          for (WebElement name : names) {
                namesText.add(name.getText());
            }
*/
            // Imprimir los textos de los precios en la consola
            for (String precioText : preciosText) {
                System.out.println(precioText);
            }
           for (String nameText : namesText) {
                System.out.println(nameText);
            }
            ResponseDTO responseDTO = new ResponseDTO();

            /*List<WebElement> productos = driver.findElements(By.cssSelector(".flex.flex-col.flex-1.relative"));
            for (WebElement producto : productos) {
                WebElement bayerElement = producto.findElement(By.cssSelector(".italic.uppercase.cursor-pointer.text-12.hover:text-accent"));
                WebElement descripcionElement = producto.findElement(By.cssSelector("a.font-open.flex.items-center.text-main.text-16.sm\\:text-18.leading-20.font-semibold.ellipsis.hover:text-accent"));
                WebElement ofertaElement = producto.findElement(By.cssSelector(".flex.items-center.order-4.ng-star-inserted .font-bold.text-prices"));
                WebElement ofertaTypeElement = producto.findElement(By.cssSelector(".flex.items-center.order-4.ng-star-inserted .text-white.bg-prices.rounded-full.ml-4.sm\\:ml-8.text-10.sm\\:text-12.mt-2.font-bold.px-5"));
                WebElement normalElement = producto.findElement(By.cssSelector(".text-12.sm\\:text-14.line-through.order-3.ng-star-inserted"));
                
                String nombre = bayerElement.getText();
                String descripcion = descripcionElement.getText();
                String oferta = ofertaElement.getText();
                String ofertaType = ofertaTypeElement.getText();
                String normal = normalElement.getText();
                if (!StringUtils.isEmpty(nombre) ) {     
                    responseDTO.setTitle(nombre); // cambiar a tittle
                    responseDTO.setUrl(oferta);
                }

                if (responseDTO.getUrl() != null) responseDTOS.add(responseDTO);
                data.add(nombre + " - " + descripcion + " - " + oferta + " " + ofertaType + " - " + normal);
            } */
            

            /*for (String  precio : preciosText) {
                ResponseDTO responseDTO = new ResponseDTO();

                if (!StringUtils.isEmpty(precio) ) {                    //mapping data to the model class
                    responseDTO.setTitle(precio); // cambiar a tittle
                    responseDTO.setUrl(precio);
                
                }
                if (responseDTO.getUrl() != null) responseDTOS.add(responseDTO);
            } */
            System.out.println(data);
             driver.close();
        } catch (Exception e) { 
            System.out.println("ERROR: "+e);
        }


    }
    
    @Override
    public Set<ResponseDTO> getVehiclesByModel(String vehicleModel) {
        Set<ResponseDTO> responseDTOS = new HashSet<>();
        for (String url: urls) {

            if (url.contains("ikman")) {
                //method to extract data from Ikman.lk
                //extractDataFromIkman(responseDTOS, url + vehicleModel);
            } else if (url.contains("riyasewana")) {               //method to extract Data from riyasewana.com
                extractDataFromRiyasewana(responseDTOS, url + vehicleModel);
            }

        }

        return responseDTOS;
    }

    private void extractDataFromRiyasewana(Set<ResponseDTO> responseDTOS, String url) {

        try {            //loading the HTML to a Document Object
            Document document = Jsoup.connect(url).get();            //Selecting the element which contains the ad list
            Element element = document.getElementById("content");
            //getting all the <a> tag elements inside the content div tag
            Elements elements = element.getElementsByTag("a");
           //traversing through the elements
            for (Element ads: elements) {
                ResponseDTO responseDTO = new ResponseDTO();

                if (!StringUtils.isEmpty(ads.attr("title")) ) {                    //mapping data to the model class
                    responseDTO.setTitle(ads.attr("title"));
                    responseDTO.setUrl(ads.attr("href"));
                }
                if (responseDTO.getUrl() != null) responseDTOS.add(responseDTO);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    
}
