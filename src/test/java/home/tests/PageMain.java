package home.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageMain {

    public String[] submemu;
    public WebDriver driver;

    //Конструктор обращается к классу PageFactory, чтобы заработала аннотация @FindBy
    //Он инициализирует элементы страницы
      public PageMain(WebDriver driver){
         PageFactory.initElements(driver, this);
         this.driver = driver;
      }


    // Элементы страницы

    //Кнопка NO на форме Undetected location
    @FindBy(xpath = "//*[@id=\"country-location-form\"]/div[3]/button[1]")
    private WebElement buttonNO;

    // Пункт меню Who We Serve
    @FindBy(xpath = "//*[@id=\"main-header-navbar\"]/ul[1]/li[1]/a")
    private WebElement whoWeServ;

    //Групповой элемент списка пунктов подменю
    @FindBy(xpath = "//*[@id=\"Level1NavNode1\"]/ul")
    private WebElement ulTagList;

    //Строка поиска
    @FindBy(xpath = "//*[@id=\"js-site-search-input\"]")
    private WebElement stFind;


    //Область со строкой ввода Input
    @FindBy(xpath = "//*[@id=\"main-header-container\"]/div/div[2]/div/form/div")
    private WebElement areaOfInput;

    //Область с релевантным контентом критерия поиска
    @FindBy(xpath = "//*[@id=\"ui-id-2\"]")
    private WebElement areaOfRelCont;

    //Кнопка поиск
    @FindBy (xpath = "//*[@id=\"main-header-container\"]/div/div[2]/div/form/div/span/button")
    private  WebElement findBtn;


   //Методы работы с элементами страницы

   //Нажать кнопку NO на форме Undetected location
    public void clkBtnNo(){ buttonNO.click();}

    //Нажать пункт меню  Who We Serve
    public void clkWhoWeServ() {whoWeServ.click();}

    //Получть количество пунктов подменю (включая вложенные)
    public int numSubMenu(){
        return (ulTagList.findElements(By.tagName("li")).size());
    }

    //Получить имена пунктов подменю (включая вложенные)
    public String getSubMenu(int i) {
        WebElement ulli = ulTagList.findElements(By.tagName("li")).get(i);
        String st = ulli.findElement(By.xpath(".//a")).getAttribute("innerHTML");
        return st.trim();
    }

    // Ввести текст в строку поиска
    public void inFindString(String string) {
        stFind.click();
        stFind.sendKeys(string);
    }

    // Получение и сравнение параметров областей
    public boolean paramAreas(){
        int yi = areaOfInput.getLocation().y; //Координата Y области со строкой ввода
        int hi = areaOfInput.getRect().getDimension().height; //Высота области со строкой ввода
        String pi = areaOfInput.getCssValue("padding"); // Padding области со строкой ввода (String)
        int pad = Integer.parseInt(pi.split("px")[0]);// Padding области со строкой ввода (int)
        int yr =0;//Координата Y области с релевантными значениями
        //Задержка на ответ по запросу
        (new WebDriverWait(driver,10))
                .until(ExpectedConditions.elementToBeClickable(areaOfRelCont));
        yr = areaOfRelCont.getLocation().y;//Координата Y области со списком релевантных значений
        System.out.println("Y_input="+yi+ " + " +" H_input="+hi+ " - " +" Padding_input="+pad+ "  = "+" Y_area="+yr);
        return (yr == yi + hi - pad);
    }

    //Нажатие на кнопку "Поиск"
    public void clickFindBtn(){findBtn.click();}
}
