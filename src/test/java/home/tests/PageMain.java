package home.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageMain {

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

    //Строка поиска Input
    @FindBy(xpath = "//*[@id=\"js-site-search-input\"]")
    private WebElement searchString;

    //Область со списком релевантных значений критерия поиска
    @FindBy(xpath = "//*[@id=\"ui-id-2\"]")
    private WebElement areaOfRelContent;

    //Кнопка поиск
    @FindBy(xpath = "//button[text() = 'Search']")
    private WebElement findButton;

   //Методы работы с элементами страницы

   //Нажать кнопку NO на форме Undetected location
    public void clkBtnNo(){ buttonNO.click();}

    //Нажать пункт меню  Who We Serve
    public void clkWhoWeServ() {whoWeServ.click();}

    //Получть количество пунктов подменю (включая вложенные)
    public int getSubMenuElementsCounts(){
        return (ulTagList.findElements(By.tagName("li")).size());
    }

    //Получить имена пунктов подменю (включая вложенные)
    public String getSubMenuElementName(int i) {
        WebElement ulli = ulTagList.findElements(By.tagName("li")).get(i);
        String st = ulli.findElement(By.xpath(".//a")).getAttribute("innerHTML");
        return st.trim();
    }

    // Ввести текст в строку поиска
    public void inputSearchString(String string) {
        searchString.click();
        searchString.sendKeys(string);
    }

    // Сравнить локацию строки ввода и области со списком релевантных значений
    public boolean checkSearchAreaLocation(){
        int yCoordInputString = searchString.getLocation().y; //Координата Y строки ввода
        int heightInputString = searchString.getRect().getDimension().height; //Высота строки ввода
        int paddingInputString = Integer.parseInt(searchString.getCssValue("padding")
                                        .split("px")[0]); // Padding строки ввода
        //Задержка на ответ по запросу
        (new WebDriverWait(driver,10))
                .until(ExpectedConditions.elementToBeClickable(areaOfRelContent));
        int yCoordRelArea = areaOfRelContent.getLocation().y; //Координата Y области со списком релевантных значений
        System.out.println("Y_input="+yCoordInputString+ " + " +" H_input="+heightInputString+ " - " +
                           " Padding_input= " + paddingInputString + "  = "+" Y_area="+yCoordRelArea);
        return (yCoordRelArea == yCoordInputString + heightInputString - paddingInputString);
    }

    //Нажатие на кнопку "Поиск"
    public void clickFindBtn(){
        findButton.click();
    }
}
