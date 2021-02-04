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
    @FindBy(xpath = "//div[@class = 'product-list-wrapper']")
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
    private WebElement menuEducation;

    //Методы работы с элементами страницы

    //Получить количество тегов section в групповом элементе (включая вложеннные)
    public int getProductTagsCount() {
        return (prodList.findElements(By.tagName("section")).size());
    }

    //Получить заданное количество продуктов на странице
    public int getCountOfProductsOnPage(){
        return Integer.parseInt(prodOnPage.getText());
    }

    //Получить имя в заголовке продукта
    public String getNameInProductTitle(int i) {
        WebElement elem = prodList.findElements(By.tagName("section")).get(i);
        String name = elem.findElement(By.xpath(".//h3/a/span")).getText();
        return name.trim();
    }

    // Соответствие названия вкладки и наличия кнопки на ней для продукта
    public boolean checkTabsAndButtonsForProduct(int i){
        WebElement prodElem = prodList.findElements(By.tagName("section")).get(i);
        WebElement prodContainer = prodElem.findElement(By.xpath(".//section[@id='productTableBodySection']"));
        WebElement prodTabPanel = prodContainer.findElement(By.xpath(".//div[@id='eBundlePlpTabMainTabPanel']"));

        WebElement prodTabListUl = prodTabPanel.findElement(By.xpath(".//ul[@id='eBundlePlpTab']"));
        WebElement prodTabContentListDiv = prodTabPanel.findElement(By.xpath(".//div[@id='tabContentStyle']"));

        int numContents = prodTabContentListDiv.findElements(By.xpath(".//div[@role='tabpanel']")).size();
        int numTabs = prodTabListUl.findElements(By.tagName("li")).size(); //Количество вкладок
        System.out.println("Продукт: "+ "  количество вкладок: " + numTabs + "  количество контентов: " + numContents);

        if (numTabs == numContents) {
            for (int j = 0; j < numTabs; j++) {
                int buttonsCount = 0; // Количество кнопок
                String buttonName = ""; // Имя кнопки
                //Имя вкладки
                String tagName = prodTabListUl.findElements(By.tagName("li")).get(j)
                                              .findElement(By.xpath(".//a/div")).getText();
                //Есть ли кнопка на вкладках E-BOOK и PRINT?
                if (tagName.trim().equals("E-BOOK")  || tagName.trim().equals("PRINT") ) {
                    List<WebElement> buttonsInTab = prodTabContentListDiv
                                                    .findElements(By.xpath(".//div[@role='tabpanel']")).get(j)
                                                    .findElements(By.xpath(".//form/Button"));
                    buttonsCount = buttonsInTab.size();
                    //Есть кнопка
                    if (buttonsCount > 0) {
                        //Имя кнопки
                        buttonName = prodTabContentListDiv.findElements(By.xpath(".//div[@role='tabpanel']")).get(j)
                                                          .findElement(By.xpath(".//form/button"))
                                                          .getAttribute("innerHTML");
                        if (!buttonName.trim().equals("Add to cart")) {
                            return false;
                        }
                    }
                    //Нет кнопки
                    if (buttonsCount == 0) {
                        //Когда нет товара в наличии
                        buttonName = prodTabContentListDiv.findElements(By.xpath(".//div[@role='tabpanel']")).get(j)
                                                          .findElement(By.xpath(".//div[@class='product-button']/span"))
                                                          .getAttribute("innerHTML");
                        if (!buttonName.trim().equals("Product not available<br> for purchase")) {
                            return false;
                        }
                    }
                }
                //Есть ли кнопка на вкладкем O-BOOK?
                if (tagName.trim().equals("O-BOOK") ){
                    List<WebElement> buttonsInTab = prodTabContentListDiv
                                                    .findElements(By.xpath(".//div[@role='tabpanel']")).get(j)
                                                    .findElements(By.xpath(".//div[@class='product-button']/a"));
                    buttonsCount = buttonsInTab.size();
                    //Есть кнопка
                    if (buttonsCount > 0) {
                        buttonName = prodTabContentListDiv.findElements(By.xpath(".//div[@role='tabpanel']")).get(j)
                                                          .findElement(By.xpath(".//div[@class='product-button']/a"))
                                                          .getAttribute("innerHTML");
                        if (!buttonName.trim().equals("View on Wiley Online Library")) {
                            return false;
                        }
                    }
                    //Нет кнопки
                    if (buttonsCount == 0) {
                        buttonName = "Нет кнопки";
                    }
                }
                System.out.println("Имя вкладки: " + tagName+ " Кол-во кнопок на вкладке: "+ buttonsCount +
                                   " Имя кнопки: " + buttonName.trim());

            }
        } else {
            return false;
          }
        return true;
    }

    //Перейти по меню Subjects->Education
    public void clickEducation() {
        Actions action = new Actions(driver);
        action.moveToElement(menuSubjects).build().perform();
        (new WebDriverWait(driver,10))
                .until(ExpectedConditions.elementToBeClickable(menuEducation));
        action.moveToElement(menuEducation).build().perform();
        menuEducation.click();
    }
}
