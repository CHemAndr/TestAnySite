package home.tests;

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


    //Методы работы с элементами страницы
    public String getNameEducation() {
        String nамеEd = h1Education.getText();
        System.out.println(nамеEd);
        return nамеEd.trim();
    }

    //Количество ссылок в группе Subjects
    public int getNumRefSubjects(){
        return 0;
    }

    //Получить имя ссылки в группе Subjects
    public  String getNameRefSubjects(int i){
        return "";
    }
}
