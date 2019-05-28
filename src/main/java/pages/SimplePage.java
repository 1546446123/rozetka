package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.page;

public class SimplePage {

    public static <T> T openItem(String s, ElementsCollection elements, Class<T> clazz){
        T result = null;
        for (int i = 0; i< elements.size(); i++){
            SelenideElement element = elements.get(i);
            String caption = element.text();
            if (caption.equals(s)){
                element.click();
                result = page(clazz);
                break;
            }
        }
        return  result;
    }
}
