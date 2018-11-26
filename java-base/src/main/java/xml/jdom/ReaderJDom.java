package xml.jdom;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.*;
import java.util.List;


/**
 * JDOM Class Reader
 */
public class ReaderJDom {

    final static String str = "E:\\Learning\\testJava\\xml\\";
    final static String strFile = "Booknews.xml";

    public static void main(String[] args) {

        try {

            //1.创建一个SAXBuilder的对象
            SAXBuilder saxBuilder = new SAXBuilder();
            //2.创建一个输入流，将xml文件加载到输入流中
            InputStream in = new FileInputStream(str + strFile);
            InputStreamReader isr = new InputStreamReader(in, "UTF-8");
            //3.通过saxBuilder的build方法，将输入流加载到saxBuilder中
            Document document = saxBuilder.build(isr);

            //4.通过document对象获取xml文件的根节点
            Element root = document.getRootElement();

            //5.获取根节点下的子节点的List集合
            List<Element> List = root.getChildren();
            for (Element element : List) {
                // 解析文件的属性集合
                List<Attribute> list = element.getAttributes();
                for (Attribute attr : list) {
                    // 获取属性名、属性值
                    String attrName = attr.getName();
                    String attrValue = attr.getValue();
                    System.out.println(attrName + "=" + attrValue);

                    // 对book节点的子节点的节点名以及节点值的遍历
                    List<Element> listChild = element.getChildren();
                    for (Element child : listChild) {
                        System.out.println(child.getName() + "=" + child.getValue());
                    }

                }
                System.out.println("——————————————————————");
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
