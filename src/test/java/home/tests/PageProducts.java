package home.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PageProducts {

    public WebDriver driver;

    //Конструктор обращается к классу PageFactory, чтобы заработала аннотация @FindBy
    //Он инициализирует элементы страницы
    public PageProducts(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    //Элементы страницы

    //Групповой элемент со списком продуктов
    @FindBy(xpath = "//*[@id=\"search-result-page-row\"]/div[3]/div/div[3]/div/div/div/div[3]")
    private WebElement prodList;

    //Элемент для задания количества продуктов на странице
    @FindBy(xpath = "//*[@id=\"ui-id-6-button\"]/span[2]")
    private WebElement prodOnPage;

    //Пункт меню Subjects
    @FindBy(xpath = "//a[text()='\n" +
            "                    SUBJECTS']")
    private WebElement menuSubjects;

    //Подпункт меню Subjects->Education
    @FindBy(xpath = "//a[text()='\n" +
            "            Education\n" +
            "        ']")
    private  WebElement menuEducation;

    //Методы работы с элементами страницы

    //Количество тегов section в групповом элементе (включая вложеннные)
    public int numProdTags() {
        return (prodList.findElements(By.tagName("section")).size());
    }

    //Заданное количество продуктов на странице
    public int numProdOnPage(){
        return Integer.parseInt(prodOnPage.getText());
    }

    //Заданное имя в заголовке продукта
    public  String nameInProdTitle(int i) {
        WebElement elem = prodList.findElements(By.tagName("section")).get(i);
        String st = elem.findElement(By.xpath(".//h3/a/span")).getText();
        //System.out.println("st="+st);
        return st.trim();
    }

    // Соответствие названия вкладки и наличия кнопки на ней
    public boolean tabNameAndButton(int i){

        WebElement prodElem = prodList.findElements(By.tagName("section")).get(i);
        WebElement prodContainer = prodElem.findElement(By.xpath(".//section[@id='productTableBodySection']"));
        WebElement prodTabpanel = prodContainer.findElement(By.xpath(".//div[@id='eBundlePlpTabMainTabPanel']"));

        WebElement prodTabListUl = prodTabpanel.findElement(By.xpath(".//ul[@id='eBundlePlpTab']"));
        WebElement prodTabContentListDiv = prodTabpanel.findElement(By.xpath(".//div[@id='tabContentStyle']"));

        int numContents = prodTabContentListDiv.findElements(By.xpath(".//div[@role='tabpanel']")).size();
        int numTabs = prodTabListUl.findElements(By.tagName("li")).size(); //Количество вкладок
        System.out.println("Продукт "+ i +" :   количество вкладок: "+ numTabs+"  количество контентов:" +numContents);

        if (numTabs == numContents) {
            for (int j = 0; j < numTabs; j++) {
                int    Nbtn = 0; // Количество кнопок
                String nameBtn = ""; // Имя кнопки
                //Имя вкладки
                String tagName = prodTabListUl.findElements(By.tagName("li")).get(j).
                                 findElement(By.xpath(".//a/div")).getText();
                //Есть ли кнопка на вкладках E-BOOK и PRINT
                if (tagName.trim().equals("E-BOOK")  || tagName.trim().equals("PRINT") ) {
                    List NumBtn = prodTabContentListDiv.findElements(By.xpath(".//div[@role='tabpanel']")).get(j).
                            findElements(By.xpath(".//form/Button"));
                    Nbtn = NumBtn.size();
                    //Есть кнопка
                    if (Nbtn > 0 ) {
                        //Имя кнопки
                        nameBtn = prodTabContentListDiv.findElements(By.xpath(".//div[@role='tabpanel']")).get(j).
                                findElement(By.xpath(".//form/button")).getAttribute("innerHTML");
                        if (!nameBtn.trim().equals("Add to cart")) {return false;}
                    }
                    //Нет кнопки
                    if (Nbtn == 0) {
                        //Когда нет товара в наличии
                        nameBtn = prodTabContentListDiv.findElements(By.xpath(".//div[@role='tabpanel']")).get(j).
                                  findElement(By.xpath(".//div[@class='product-button']/span")).
                                  getAttribute("innerHTML");
                        if (!nameBtn.trim().equals("Product not available<br> for purchase")) {return false;}
                    }
                }
                //Есть ли кнопка на вкладкем O-BOOK
                if (tagName.trim().equals("O-BOOK") ){
                    List NumBtn = prodTabContentListDiv.findElements(By.xpath(".//div[@role='tabpanel']")).get(j).
                            findElements(By.xpath(".//div[@class='product-button']/a"));
                    Nbtn = NumBtn.size();
                    if (Nbtn > 0 ) {
                        nameBtn = prodTabContentListDiv.findElements(By.xpath(".//div[@role='tabpanel']")).get(j).
                                findElement(By.xpath(".//div[@class='product-button']/a")).getAttribute("innerHTML");
                        if (!nameBtn.trim().equals("View on Wiley Online Library")) {return false;}
                    }
                    if (Nbtn == 0) {nameBtn = "Нет кнопки";}
                }
                System.out.println("Имя вкладки: " + tagName+ " Кол-во кнопок на вкладке: "+ Nbtn +
                                   " Имя кнопки: " + nameBtn.trim());

            }
        } else return false;

        return true;
    }
    //Перейти по меню Subjects->Education
    public void clkEducation() {
        Actions action = new Actions(driver);
        action.moveToElement(menuSubjects).build().perform();
        (new WebDriverWait(driver,10))
                .until(ExpectedConditions.elementToBeClickable(menuEducation));
        action.moveToElement(menuEducation).build().perform();
        menuEducation.click();
    }
}
