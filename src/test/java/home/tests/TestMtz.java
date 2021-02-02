package home.tests;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TestMtz {


    public static PageMain pageMain;
    public static PageProducts pageProducts;
    public static PageEducation pageEducation;
    public static WebDriver driver;

    public static  int numSubMenuWws = 12;
    public static  String[] smenuElWws = {
      "Students", "Textbook Rental","Instructors", "Book Authors", "Professionals",
      "Researchers", "Institutions", "Librarians", "Corporations",
      "Societies", "Journal Editors", "Bookstores", "Government"
    };
    public static  String poisk = "Java";

    public static int numRefEducation = 13;
    public static String[] nameRefEducation = {
            "Information & Library Science",
            "Education & Public Policy",
            "K-12 General",
            "Higher Education General",
            "Vocational Technology",
            "Conflict Resolution & Mediation (School settings)",
            "Curriculum Tools- General",
            "Special Educational Needs",
            "Theory of Education",
            "Education Special Topics",
            "Educational Research & Statistics",
            "Literacy & Reading",
            "Classroom Management"
    };



    @BeforeAll
     static void setup(){
        //определение пути к драйверу
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        //создание экземпляра драйвера (открывается браузер)
        driver = new ChromeDriver();
        pageMain = new PageMain(driver);
        pageProducts = new PageProducts(driver);
        pageEducation = new PageEducation(driver);
        //окно разворачивается на полный экран
        driver.manage().window().maximize();
        //неявное ожидание = 15 сек при загрузке страницы
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        //неявное ожидание = 1 сек при каждом поиске элемента
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        //получение ссылки на страницу (url берем из файла конфигурации)
        driver.get(ConfProperties.getProperty("startpage"));
    }

    @Test
    public void test1(){
      System.out.println("");
      System.out.println("test1");
      pageMain.clkBtnNo();
      pageMain.clkWhoWeServ();
      // Сколько элементов в подменю
      int num =  pageMain.numSubMenu();
      Assertions.assertEquals(numSubMenuWws,num - 1);
      System.out.println(num-1);
      // Названия элементов меню
      for (int i=0; i<num; i++) {
          String title = pageMain.getSubMenu(i);
          Assertions.assertEquals(smenuElWws[i],title);
          System.out.println(title);
      }
    }

    @Test
    public void test2(){
      System.out.println("");
      System.out.println("test2");
      pageMain.inFindString(poisk);
      Assertions.assertTrue(pageMain.paramAreas());
      System.out.println("ОК");
    }

    @Test
    public void test3(){
      System.out.println("");
      System.out.println("test3");
      pageMain.clickFindBtn();
      //Заданное количество продуктов на странице
      int numProdAdjust = pageProducts.numProdOnPage();
      //Общее количество тегов section в групповом элементе
      int numTags = pageProducts.numProdTags();
      //Фактическое количество продуктов на странице равно заданному.
      Assertions.assertEquals(numProdAdjust,numTags - numProdAdjust);
      // Все заголовки продуктов на странице включают строку поиска
      for (int i = 0; i < numTags; i+= 2){
          Assertions.assertEquals(poisk,pageProducts.nameInProdTitle(i));
      }
      // Соответствие наименования вкладки и кнопки
        for (int i = 0; i< numTags; i+= 2) {
            Assertions.assertTrue(pageProducts.tabNameAndButton(i));
        }
    }

    @Test
    public void test4(){
        System.out.println("");
        System.out.println("test4");
        //Выбрать Subjects->Education
        pageProducts.clkEducation();
        //Заголовок - Education
        Assertions.assertEquals("Education",pageEducation.getNameEducation());

        // Сколько ссылок в Subjects
        int num =  pageEducation.getNumRefSubjects();
        Assertions.assertEquals(numRefEducation,num );
        System.out.println("Количество ссылок: " + num);
        // Названия элементов меню
        for (int i=0; i<num; i++) {
            String title = pageEducation.getNameRefSubjects(i);
            Assertions.assertEquals(nameRefEducation[i], title);
            System.out.println(title);
        }

    }

    @AfterAll
    static void close() {
        driver.close();
    }

}
