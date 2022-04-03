/*@author zzy*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//Project Structure mysql connection
public class JDBC {

    Connection con;// 声明数据库连接对象
    //初始数据库连接
    public void initConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");        //加载数据库驱动
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=CONVERT_TO_NULL&allowPublicKeyRetrieval=true",
                    "root",
                    "20041118Zzyu");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    //关闭数据库连接
    public void closeConnection() {// 关闭数据库连接
        if (con != null) {
            try {
                con.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    public void showAllData() { //遍历所有学生数据
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from tb_stu");
            while (rs.next()) { // 如果当前语句不是最后一条，则进入循环
                System.out.print("编号：" + rs.getString("id")); // 将列值输出
                System.out.print(" 姓名:" + rs.getString("name"));
                System.out.print(" 日期:" + rs.getString("date"));
                System.out.println(" 计分：" + rs.getString("record"));
                System.out.println(" 时间: " + rs.getString("time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(int id, String name, String date, int record, String time) {// 添加新记录
        try {
            String sql = "insert into tb_stu values(?,?,?,?,?) ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id); // 设置编号
            ps.setString(2, name); // 设置名字
            ps.setString(3, date); // 设置日期
            ps.setInt(4, record); // 设置分数
            ps.setString(5, time); //设置时间
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(int id) {// 删除指定ID的学生记录
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("delete from tb_stu where id =" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
