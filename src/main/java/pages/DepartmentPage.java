package pages;

import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.support.FindBy;

public class DepartmentPage extends SimplePage {
	@FindBy(xpath="//*[@id=\"menu_categories_left\"]/li[*]/*/a")
	private ElementsCollection items;

    public CategoryPage openItem(String s){
        return openItem(s, items, CategoryPage.class);
    }
}
