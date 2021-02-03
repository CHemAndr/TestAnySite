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
        String nаmеEd = h1Education.getText();
        System.out.println(nаmеEd);
        return nаmеEd.trim();
    }

    //Количество ссылок в группе Subjects
    public int getNumRefSubjects(){
        return groupSubjectsUl.findElements(By.tagName("li")).size();
    }

    //Получить имя ссылки в группе Subjects
    public  String getNameRefSubjects(int i){
        WebElement ulli = groupSubjectsUl.findElements(By.tagName("li")).get(i);
        String nameRef = ulli.findElement(By.xpath(".//a")).getText();
        return nameRef.trim();
    }

    //Параметры расположения блока Subject
    //public String
}
