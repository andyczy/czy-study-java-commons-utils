package apache.commons;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.Factory;
import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chenzhengyou
 * @date 2018-08-31
 * @description:
 */
public class CollextionsBidiMap {

    public static void main(String[] args) {
        System.out.println(StringUtils.center(" demoBidiMap ",40,"="));
        BidiMap bidiMap = new DualHashBidiMap();
        bidiMap.put("BJ","Beijing");
        bidiMap.put("SH","Shanghai");
        bidiMap.put("GZ","Guangzhou");
        bidiMap.put("CD","Chengdu");

        System.out.println("Key-Value: BJ = "+bidiMap.get("BJ"));
        System.out.println("Value-Key: Chengdu ="+bidiMap.getKey("Chengdu"));
        System.out.println(StringUtils.repeat("=",40));


        System.out.println(StringUtils.center( " demoMultiMap " , 40, "=" ));
        MultiMap multiMap = new MultiHashMap();
        multiMap.put( "Sean" , "C/C++" );
        multiMap.put( "Sean" , "OO" );
        multiMap.put( "Sean" , "Java" );
        multiMap.put( "Sean" , ".NET" );
        multiMap.remove( "Sean" , "C/C++" );

        System.out.println( "Sean's skill set: " + multiMap.get( "Sean" ));
        System.out.println(StringUtils.repeat( "=" , 40));

        System.out.println(StringUtils.center( " demoLazyMap " , 40, "=" ));
        // borrowed from Commons Collection'sJavadoc
        Factory factory = new Factory() {
            @Override
            public Object create() {
                return new Date();
            }
        };

        Map lazy = LazyMap.decorate( new HashMap(), factory);
        System.out.println(lazy.get( "NOW" ));
        System.out.println(StringUtils.repeat( "=" , 40));
    }

}
