package pages;

import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.support.FindBy;

public class CategoryPage extends SimplePage{
	@FindBy(xpath="//*[@id=\"menu_categories_left\"]/li[*]/div/a[1]")
	private ElementsCollection presets;

    @FindBy(xpath="//*[@id=\"menu_categories_left\"]/li[*]/ul/li[*]/a")
    private ElementsCollection subitems;

    public ProductsPage openItem(String s){
        return openItem(s, presets, ProductsPage.class);
    }

    public ProductsPage openSubItem(String s){
        return openItem(s, subitems, ProductsPage.class);
    }
}
