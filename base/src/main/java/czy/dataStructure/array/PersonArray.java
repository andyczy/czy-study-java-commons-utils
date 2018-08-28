package czy.dataStructure.array;


import base.factoryDom.test02.Person;

/**
 * 对象数组操作
 */
public class PersonArray {
    //对象数组
    private Person[] arr;

    //数量
    private int elems;

    //构造方法
    public PersonArray() {
        arr = new Person[50];
    }

    public PersonArray(int max) {
        arr = new Person[max];
    }

    // 插入
    public void insert(Person person) {
        arr[elems] = person;
        elems++;
    }

    //显示
    public void display() {
        for (int i = 0; i < elems; i++) {
            arr[i].display();
        }
        System.out.println();
    }

    //查找
    public int find(String name) {
        int i;
        for (i = 0; i < elems; i++) {
            if (name.equals(arr[i].getName())) {
                break;
            }
        }

        if (i == arr.length) {
            return -1;
        } else {
            return i;
        }
    }

    //删除
    public void delete(Person person) {

        if (find(person.getName()) == -1) {
            System.out.println("error！");
        } else {
            for (int i = find(person.getName()); i < elems; i++) {
                arr[i] = arr[i + 1];
            }
        }
        elems--;
    }

    //删除���
    public void delete(String name) {

        if (find(name) == -1) {
            System.out.println("error！");
        } else {
            for (int i = find(name); i < elems; i++) {
                arr[i] = arr[i + 1];
            }
        }
        elems--;
    }

    //修改
    public void change(Person oldPerson, Person newPerson) {
        if (find(oldPerson.getName()) == -1) {
            System.out.println("error！");
        } else {
            arr[find(oldPerson.getName())] = newPerson;
        }
    }
}
