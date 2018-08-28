package czy.io.iodata.byte_stream.outputStream;

import org.junit.Test;

import java.io.*;


/**
 * 文件的读取
 * 加缓冲速度变快、节省空间
 */
public class FlieOutputStream {

    // 创建文件对象
    // 与系统有关的默认名称分隔符。此字段被初始化为包含系统属性 file.separator 值的第一个字符。
    // 在 UNIX 系统上，此字段的值为 '/'；在 Microsoft Windows 系统上，它为 '\\'。
    File file = new File("E:"+File.separatorChar+"from"+File.separatorChar+"fos.txt");



    //输出流写出方式1
    //使用write(int b)方法,一次写出一个字节。
    //测试：将c盘下的a.txt文件删除，发现当文件不存在时，会自动创建一个，但是创建不了多级目录。
    //注意：使用write(int b)方法，虽然接收的是int类型参数，但是write 的常规协定是：向输出流写入一个字节。
    //要写入的字节是参数 b 的八个低位。b 的 24 个高位将被忽略。
    @Test
    public void writFileTest() throws FileNotFoundException, IOException {
        // 创建文件输出流
        FileOutputStream fos = new FileOutputStream(file);
        fos.write('c');
        fos.write('h');
        fos.write('e');
        fos.write('n');
        fos.write('z');
        fos.write('h');
        fos.write('e');
        fos.write('n');
        fos.write('g');
        fos.write('y');
        fos.write('o');
        fos.write('u');
        fos.close();
    }


    //输出流写出方式2
    //使用write(byte[] b),就是使用缓冲.提高效率.
    // 上述案例中的使用了OutputStram 的write方法，一次只能写一个字节。
    // 成功的向文件中写入了内容。但是并不高效，如和提高效率呢？是否应该使用缓冲，
    // 根据字节输入流的缓冲原理，是否可以将数据保存中字节数组中。通过操作字节数组来提高效率。
    // 查找API文档，在OutputStram类中找到了write(byte[] b)方法，将 b.length 个字节从指定的 byte 数组写入此输出流中。
    // 如何将字节数据保存在字节数组中，以字符串为例，”hello , world” 如何转为字节数组。显然通过字符串的getBytes方法即可。
    // 仔细查看a.txt文本文件发现上述程序每运行一次，老的内容就会被覆盖掉。
    // 那么如何不覆盖已有信息，能够往a.txt里追加信息呢。
    @Test
    public void writeTxtFile() throws IOException {
        // 1：打开文件输出流，流的目的地是指定的文件
        // 查看API文档，发现FileOutputStream类中的构造方法中有一个构造可以
        // 实现追加的功能FileOutputStream(File file, boolean append)
        // 第二个参数，append - 如果为 true，则将字节写入文件末尾处，而不是写入文件开始处
        FileOutputStream fos = new FileOutputStream(file,true);

        // 2：通过流向文件写数据
        byte[] byt = "里追加信息呢".getBytes();
        fos.write(byt);
        // 3:用完流后关闭流
        fos.close();
    }


    @Test
    public void writFileTest2() throws FileNotFoundException, IOException {
        //写入
        FileOutputStream out=null;
        BufferedOutputStream bos =null;
        FileInputStream in =null;
        BufferedInputStream bis =null;
        try {
            //写入、并且追加
            out = new FileOutputStream(file,true);
            bos = new BufferedOutputStream(out);
            //转化为字节
            byte buy[] = "这些是写入追加的文本！".getBytes();
            //写入
            bos.write(buy);
            bos.close();

            //读取
            in = new FileInputStream(file);
            bis = new BufferedInputStream(in);

            byte byt[] = new byte[1024];
            bis.read(byt);
            System.out.println("读取出来的信息： " + new String(byt));

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(bis != null){
                bis.close();
            }
            if(in != null){
                in.close();
            }
            if(bos != null){
                bos.close();
            }
            if(out != null){
                out.close();
            }
        }
    }
}

	