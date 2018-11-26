package xml.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * DOM  Class  Reader
 */
public class ReaderDom {

    public static void main(String[] args) {

        ClassLoader classLoader = ReaderDom.class.getClassLoader();
        URL resource = classLoader.getResource("xml/Book.xml");
        String path = resource.getPath();
        System.out.println(path);

        try {
            //1.获取DOM 解析器的工厂实例。
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //2.获得具体的DOM解析器。
            DocumentBuilder builder = factory.newDocumentBuilder();
            //3.获取文件
            Document document = builder.parse(new File(path));

            //4.获取根节点元素
            Element root = document.getDocumentElement();
            System.out.println("cat=" + root.getAttribute("cat"));

            //5.获取节点集合
            NodeList list = root.getElementsByTagName("lan");
            for (int i = 0; i < list.getLength(); i++) {

//			    Element lan =  (Element) list.item(i);
//			    System.out.println("id=" + lan.getAttribute("id"));

                Node lan = list.item(i);
                System.out.println("id=" + lan.getNodeType());
                System.out.println("---------------");

                //获取节点的子节点集合
                NodeList listChild = lan.getChildNodes();
                for (int j = 0; j < listChild.getLength(); j++) {
                    //获取下标
                    Node child = listChild.item(j);
                    //把空格删除
                    if (child instanceof Element) {
                        System.out.println(child.getNodeName() + "=" + child.getTextContent());
                    }
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
