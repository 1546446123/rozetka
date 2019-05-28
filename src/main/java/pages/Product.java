package pages;

import com.codeborne.selenide.ElementsContainer;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;

import static java.lang.Boolean.FALSE;

public class Product extends ElementsContainer {
	@FindBy(xpath=".//*/div[@class=\"g-price-uah\"]")
	private SelenideElement price;
	@FindBy(xpath=".//*/div[@class=\"g-i-tile-i-title clearfix\"]/a")
	private SelenideElement title;
	@FindBy(xpath=".//*[@class=\"g-tag-icon-small-popularity g-tag-i sprite\"]")
	private SelenideElement isPopular;


	Product(){

	}
	Product(SelenideElement s){
		title = s.$(By.xpath(".//*/div[@class=\"g-i-tile-i-title clearfix\"]/a"));
		price = s.$(By.xpath(".//*/div[@class=\"g-price-uah\"]"));
		isPopular = s.$(By.xpath(".//*[@class=\"g-tag-icon-small-popularity g-tag-i sprite\"]"));
	}
	public String getTitile(){
		return title.text();
	}

	public String getPrice(){
		return price.text();
	}

	public Boolean getIsPopular(){
		Boolean result = FALSE;
		try
		{
			result = isPopular.exists();
		}
		catch (NoSuchElementException e){
			//
		}
		finally {
			return result;
		}
	}

}
