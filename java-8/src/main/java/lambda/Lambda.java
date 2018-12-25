package lambda;

import pojo.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Author: Chen zheng you
 * CreateTime: 2018-12-25 17:08
 * Description:
 */
public class Lambda {


    public static void main(String[] args) {
        consumerTest();
        System.out.println(functionTest());
        System.out.println(bifunctionTest());
        asListTest();
    }

    /**
     * Consumer接口是Java8内置的函数式接口
     */
    public static void consumerTest() {
        // 字符串
        Consumer<String> consumer = str -> System.out.println(str);
        consumer.accept("This is one");
        consumer.accept("This is two");

        //  整数
        Consumer<Integer> intConsumer = str -> System.out.println(str);
        intConsumer.accept(1);
        intConsumer.accept(2);

        // 对象
        User user = new User(1, 1, "chenzhengyou", new BigDecimal(17.0));
        User userTwo = new User(2, 1, "czy", new BigDecimal(16.8));

        Consumer<User> userConsumer = userCon -> System.out.println(userCon);
        userConsumer.accept(user);
        userConsumer.accept(userTwo);
    }


    /**
     * Function接口的apply方法就是一个有一个参数并且有返回值的方法
     *
     * @return
     */
    public static int functionTest() {
        Function<String, Integer> f = s -> Integer.parseInt(s);
        Integer num = f.apply("888888");
        return num;
    }


    /**
     * BiFunction接口的apply方法就是一个有两个参数并且有返回值的方法
     *
     * @return
     */
    public static String bifunctionTest() {
        BiFunction<Integer, Integer, String> b = (Integer x, Integer y) -> x + y + "";
        String str = b.apply(1, 2);
        return str;
    }


    /**
     * asList 和 forEach
     *
     */
    public static void asListTest() {
        List features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        features.forEach(n -> System.out.println(n));
    }

}
