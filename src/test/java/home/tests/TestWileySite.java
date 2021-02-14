package home.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class TestWileySite {

    public static PageMain pageMain;
    public static PageProducts pageProducts;
    public static PageEducation pageEducation;
    public static WebDriver driver;

    public static int subMenuWwsElementsCount = 12;
    public static String[] subMenuWwsElements = {
      "Students", "Textbook Rental","Instructors", "Book Authors", "Professionals",
      "Researchers", "Institutions", "Librarians", "Corporations",
      "Societies", "Journal Editors", "Bookstores", "Government"
    };
    public static String searchInputCriteria = "Java";
    public static int referencesInEducationBlockCount = 13;
    public static String[] referencesInEducationBlock = {
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
      System.out.println();
      System.out.println("test1 started");
      //pageMain.clkBtnNo(); //всплывающее окно переехало на страницу с продуктами
      pageMain.clkWhoWeServ();
      // Сколько элементов в подменю 1 уровня (за вычетом вложенных)
      int num = pageMain.getSubMenuElementsCounts() - 1;
      Assertions.assertEquals(subMenuWwsElementsCount,num);
      System.out.println("Количество элементов 1 уровня: " + num);
      // Названия элементов меню 1 уровня
      for (int i=0; i<num; i++) {
          String title = pageMain.getSubMenuElementName(i);
          Assertions.assertEquals(subMenuWwsElements[i],title);
          System.out.println(title);
      }
      System.out.println("test1 finished");
    }

    @Test
    public void test2(){
      System.out.println();
      System.out.println("test2 started");
      pageMain.inputSearchString(searchInputCriteria);
      Assertions.assertTrue(pageMain.checkSearchAreaLocation());
      System.out.println("ОК");
      System.out.println("test2 finished");
    }

    @Test
    public void test3(){
      System.out.println();
      System.out.println("test3 started" );
      pageMain.clickFindBtn(); //всплывающее окно переехало на страницу с продуктами
      pageMain.clkBtnNo();
      //Заданное количество продуктов на странице
      int numProdAdjust = pageProducts.getCountOfProductsOnPage();
      //Общее количество тегов section в групповом элементе
      int numTags = pageProducts.getProductTagsCount();
      //Фактическое количество продуктов на странице равно заданному.
      Assertions.assertEquals(numProdAdjust,numTags - numProdAdjust);
      // Все заголовки продуктов на странице включают строку поиска
      for (int i = 0; i < numTags; i+= 2){
          Assertions.assertEquals(searchInputCriteria,pageProducts.getNameInProductTitle(i));
      }
      // Соответствие наименования вкладки и кнопки
      for (int i = 0; i < numTags; i+= 2) {
          Assertions.assertTrue(pageProducts.checkTabsAndButtonsForProduct(i));
      }
      System.out.println("test3 finished");
    }

    @Test
    public void test4(){
        System.out.println();
        System.out.println("test4 started");
        //Выбрать Subjects->Education
        pageProducts.clickEducation();
        //Заголовок - Education
        Assertions.assertEquals("Education",pageEducation.getNameEducation());
        // Сколько ссылок в Subjects
        int num =  pageEducation.getNumRefSubjects();
        Assertions.assertEquals(referencesInEducationBlockCount,num );
        System.out.println("Количество ссылок: " + num);
        // Названия элементов меню
        for (int i=0; i<num; i++) {
            String title = pageEducation.getNameRefSubjects(i);
            Assertions.assertEquals(referencesInEducationBlock[i], title);
            System.out.println(title);
        }
        //Блок Subjects расположен слева на странице
        String valueCssParamFloat = pageEducation.getParamsBlockSubjects()[0];
        Assertions.assertEquals("left", valueCssParamFloat);
        System.out.println("Свойство css float: " + valueCssParamFloat);
        System.out.println("test4 finished");
    }

    @AfterAll
    static void close() {
        driver.close();
    }
}
