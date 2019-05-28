package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.*;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$$;

public class ProductsPage extends SimplePage{
    String productsXpath = "//*/div[contains(@class, \"g-i-tile g-i-tile-catalog\")]/div[@class=\"over-wraper\"]";


    @FindBy(xpath="//*/div[contains(@class, \"g-i-tile g-i-tile-catalog\")]/div[@class=\"over-wraper\"]")
    List<Product> products;
	@FindBy(id="price[min]")
	private SelenideElement minPrice;
	@FindBy(id="price[max]")
	private SelenideElement maxPrice;
	@FindBy(id="submitprice")
	private SelenideElement submitprice;
    @FindBy(xpath="//*[@class=\"g-tag-icon-small-popularity g-tag-i sprite\"]")
    private SelenideElement isPopular;
	@FindBy(xpath="//*[@class=\"paginator-catalog-l-i pos-fix\"]")
	private ElementsCollection paginator;

    public List<String> getAllTitles(){

        List<String> titles = new ArrayList<>();
        ListIterator<Product> listIter = products.listIterator();
        while (listIter.hasNext()){
            titles.add(listIter.next().getTitile());
        }
        return titles;
    }

    public Map<String, String> getProductsAndPricesFromPage(){
        return getProductsAndPrices(products);
    }

    private  Map<String, String> getProductsAndPrices(List<Product> p1){
        Map<String, String> values = new HashMap<>();
        ListIterator<Product> listIter = p1.listIterator();
        while (listIter.hasNext()){
            Product p = listIter.next();
            values.put(p.getTitile(), p.getPrice());
        }
        return values;
    }

    public void filterWithMinPrice(int min){
        minPrice.setValue(Integer.toString(min));
        submitprice.click();
    }

    public void filterWithMaxPrice(int max){
        maxPrice.setValue(Integer.toString(max));
        submitprice.click();
    }

    public void filterWithPrice(int min, int max){
        maxPrice.setValue(Integer.toString(max));
        minPrice.click();
        while (minPrice.getValue().length()>0){
            minPrice.sendKeys("\uE003");
        }


        minPrice.setValue(Integer.toString(min));
        submitprice.click();
        refreshProducts();
    }

    public Map<String, String>  getPopularProductsFromPage(){
        List<Product> filtered = products.stream()
                .filter(Product::getIsPopular).collect(Collectors.toList());
        return getProductsAndPrices(filtered);

    }

    public void refreshProducts(){
        List<SelenideElement> elements2 = new ArrayList<>();
        ElementsCollection coll = $$(By.xpath(productsXpath));
        elements2.addAll(coll);
        List<Product> product2s = new ArrayList<>();
        for (SelenideElement el:elements2) {
            product2s.add(new Product(el));
        }
        products = product2s;

    }

    public void gotoPage(int i){
        for (int y = 0;y<paginator.size();y++){
            String value = paginator.get(y).text();
            int z = Integer.parseInt(value);
            if (z==i){
                paginator.get(y).click();
                refreshProducts();
                break;
            }
        }
    }

    public Map<String, String> getProductsFromCurAndToPage(int fromPage, int toPage){
        Map<String, String> items = new HashMap<>();
        for (int i=fromPage;i<=toPage;i++)
        {
            gotoPage(i);
            Map<String, String> p = getProductsAndPricesFromPage();
            items.putAll(p);
        }
        return items;
    }

    public Map<String, String> getPopularProductsFromCurAndToPage(int fromPage, int toPage){
        Map<String, String> currentPage = getProductsAndPricesFromPage();
        for (int i=fromPage;i<=toPage;i++)
        {
            gotoPage(i);
            Map<String, String> p = getProductsAndPricesFromPage();
            currentPage.putAll(p);
        }
        return currentPage;
    }



}
