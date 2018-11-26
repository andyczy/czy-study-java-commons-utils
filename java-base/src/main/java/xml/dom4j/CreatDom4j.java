package xml.dom4j;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

/**
 * Creat Class
 */
public class CreatDom4j {
    public static void main(String[] args) {
        String stringxml =
                "<book>"
                        + "<name ne='chen'>"
                        + "盗墓笔记"
                        + "</name>"
                        + "</book>";
        try {

            Document document = DocumentHelper.parseText(stringxml);
            System.out.println(document.asXML());

        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }
}
