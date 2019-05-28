import org.junit.Test;
import pages.CategoryPage;
import pages.DepartmentPage;
import pages.MainPage;
import pages.ProductsPage;

import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;

public class TestScrapping {

    @Test
    public void TestParsePagesAndSendEmail(){
        MainPage mainPage = open("https://rozetka.com.ua/", MainPage.class);
        DepartmentPage departmentPage = mainPage.openItem("Смартфоны, ТВ и электроника");
        CategoryPage categoryPage = departmentPage.openItem("Телефоны");
        ProductsPage productsPage = categoryPage.openItem("Смартфоны");
        List<String> captions = productsPage.getAllTitles();
        productsPage.gotoPage(2);
        captions.addAll(productsPage.getAllTitles());
        productsPage.gotoPage(3);
        captions.addAll(productsPage.getAllTitles());
        String fn = Utils.saveToFile(captions);
        Utils.sendFileByEmail(fn);
    }

    @Test
    public void TestParsePagesAndSaveToMySql(){
        MainPage mainPage = open("https://rozetka.com.ua/", MainPage.class);
        DepartmentPage departmentPage = mainPage.openItem("Товары для дома");
        CategoryPage categoryPage = departmentPage.openItem("Бытовая химия");
        ProductsPage productsPage = categoryPage.openSubItem("Порошок");

        productsPage.filterWithPrice(100, 300);
        Map<String, String> page1 = productsPage.getProductsAndPricesFromPage();
        Map<String, String> page2 = productsPage.getProductsFromCurAndToPage(2, 5);
        page1.putAll(page2);
        Utils.saveToDatabase(page1);
    }

    @Test
    public void TestParsePagesAndSendEmail2(){
        MainPage mainPage = open("https://rozetka.com.ua/", MainPage.class);
        DepartmentPage departmentPage = mainPage.openItem("Смартфоны, ТВ и электроника");
        CategoryPage categoryPage = departmentPage.openItem("Телефоны");
        ProductsPage productsPage = categoryPage.openItem("Смартфоны");
        Map<String, String> popularProducts = productsPage.getPopularProductsFromPage();
        Map<String, String> popularProducts2 = productsPage.getPopularProductsFromCurAndToPage(2, 3);
        popularProducts.putAll(popularProducts2);

        productsPage.filterWithPrice(3000, 6000);
        Map<String, String> products = productsPage.getProductsAndPricesFromPage();
        Map<String, String> products2 = productsPage.getProductsFromCurAndToPage(2, 5);
        products.putAll(products2);

        String s = Utils.saveProductToXLSEx(products, popularProducts2);
        Utils.sendFileByEmail(s);
    }
}
