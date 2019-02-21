package apache.commons.test;

import org.apache.commons.collections.Bag;
import org.apache.commons.collections.BagUtils;
import org.apache.commons.collections.bag.HashBag;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Chenzhengyou
 * @date 2018-08-31
 * @description:
 */
public class CollectionsBagUsage {
    public static void main(String[] args) {

        demoBagUsage();

    }

    public static void demoBagUsage() {

        System.out.println("demoBagUsage:"+StringUtils.center( " demoBagUsage " , 50, "=" ));

        // data setup
        Book book1 = new Book( "RefactoringWorkbook" , "7-5083-2208-8" ,29.8);
        Book book2 = new Book( "J2EEDesign Patterns" , "7-5083-3099-4" ,45);
        Book book3 = new Book( "AgileSoftware Development" , "7-5083-1503-0" ,59);

        // create a bag
        Bag myBag = BagUtils.typedBag( new HashBag(), Book. class );
        myBag.add(book1, 360);
        myBag.add(book2, 500);
        myBag.add(book3, 170);

        // calculations for a bag
        double price1 = book1.getRetailPrice();
        double price2 = book2.getRetailPrice();
        double price3 = book3.getRetailPrice();

        int book1Count = myBag.getCount(book1);
        int book2Count = myBag.getCount(book2);
        int book3Count = myBag.getCount(book3);
        double totalValue = (price1 * book1Count) +(price2 * book2Count) + (price3 * book3Count);

        // dispaly results
        System.out.println( "There are " + book1Count + " copies of " + book1.getName() + "." );
        System.out.println( "There are " + book2Count + " copies of " + book2.getName() + "." );
        System.out.println( "There are " + book3Count + " copies of " + book3.getName() + "." );
        System.out.println( "The total value of these books is: " +totalValue);
        System.out.println();

    }
}
