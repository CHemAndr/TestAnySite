package home.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageEducation {

    public WebDriver driver;

    //Конструктор обращается к классу PageFactory, чтобы заработала аннотация @FindBy
    //Он инициализирует элементы страницы
    public PageEducation(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    //Элементы страницы

    //Заголовок Education
    @FindBy(xpath = "//span[text()='Education']")
    private WebElement h1Education;

    //Блок Subjects
    @FindBy (xpath = "//div[@class = \"category-landing-page\"]/div[4]/div")
    private WebElement blockSubject;

    //Групповой элемент ссылок в группе Subjects
    @FindBy(xpath = "//div[@class='side-panel']/child::ul")
    private WebElement groupSubjectsUl;

    //Методы работы с элементами страницы
    public String getNameEducation() {
        String nаmеEducation = h1Education.getText();
        System.out.println(nаmеEducation);
        return nаmеEducation.trim();
    }

    //Количество ссылок в группе Subjects
    public int getNumRefSubjects(){
        return groupSubjectsUl.findElements(By.tagName("li")).size();
    }

    //Получить имя ссылки в группе Subjects
    public  String getNameRefSubjects(int i){
        WebElement ulLiElement = groupSubjectsUl.findElements(By.tagName("li")).get(i);
        String nameReference = ulLiElement.findElement(By.xpath(".//a")).getText();
        return nameReference.trim();
    }

    //Получить параметры расположения блока Subjects
    public String[] getParamsBlockSubjects() {
        String[] paramsBlockSubjects = {""};
        paramsBlockSubjects[0] = blockSubject.getCssValue("float").trim();
        return paramsBlockSubjects;
    }
}
