package json.fastjson;

/**
 * @author Chenzy
 * @date 2017-12-10
 * @description:
 */
public class Program1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Person person = new Person();
        person.setAge(32);
        person.setName("wangyunpeng");
        String json = FastJSONHelper.serialize(person);
        System.out.println(json);

        person = FastJSONHelper.deserialize(json, Person.class);
        System.out.println(String.format("Name:%s,Age:%s",person.getName(),person.getAge()));
    }
}
