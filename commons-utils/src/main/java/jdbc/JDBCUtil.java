package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @auther 陈郑游
 * @create 2017/4/28 0028
 * @功能
 * @问题
 * @说明
 * @URL地址
 * @进度描述
 */
public class JDBCUtil {

    public static Connection open(String driver, String url, String name, String pw) {
//        String Driver = "swing.mysql.base.jdbc.Driver";
//        String URL = "base.jdbc:mysql://localhost:3306/atm";
//        String Name = "root";
//        String PassWrod = "123456";
        Connection conn = null;
        try {
            //获取驱动
            Class.forName(driver);
            //链接
            conn = DriverManager.getConnection(url, name, pw);
        } catch (Exception e) {
            System.out.println("链接失败！");
        }
        return conn;
    }

    /**
     * 关闭资源
     *
     * @param conn
     * @param ps
     */
    public static void closeRes(Connection conn, PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("关闭资源！");
        }
    }

    /**
     * 关闭资源
     *
     * @param conn
     * @param ps
     * @param rs
     */
    public static void closeRes(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            closeRes(conn, ps);
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("关闭资源！");
        }
    }


}
