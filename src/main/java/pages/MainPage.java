package pages;

import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.support.FindBy;

public class MainPage extends SimplePage{

    @FindBy(xpath="/html/body/app-root/div/div[1]/app-rz-main-page/div/aside/main-page-sidebar/sidebar-fat-menu/div/ul/li[*]/a")
    private ElementsCollection items;


    public DepartmentPage openItem(String s){
        return openItem(s, items, DepartmentPage.class);
    }
}
