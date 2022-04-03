/*@author zzy*/
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class sqlServes {
    Connection connection;

    //初始化DB连接
    public void initConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=CONVERT_TO_NULL&allowPublicKeyRetrieval=true",
                    "root",
                    "20041118Zzyu");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }


    public void getPoint(int id) throws IOException {
        //创建临时文件
        File file = new File("D:Rank.txt");
        FileWriter fileWriter = new FileWriter(file, true);
        int sum = 0;
        //新建两个数组 Rank PointRank,分别记录id排名与record排名
        List<Integer> Rank = new ArrayList<>();
        List<Integer> PointRank = new ArrayList<>();
        //从数据库获取id record
        String sql = "select id,record from tb_stu where id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            //获取id
            while (resultSet.next()) {
                int record = resultSet.getInt("record");
                PointRank.add(record);
            }
            //将record求和赋值给sum
            sum = PointRank.stream().reduce(Integer::sum).orElse(0);
            System.out.println(id + " points:  " + sum);
            Rank.add(sum);//将sum加入到record排名(Rank)
            //将数据写入临时文件Rank.txt
            fileWriter.write(id + " ");
            fileWriter.write(sum + " ");
            fileWriter.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        sqlServes sqlServes = new sqlServes();
        sqlServes.initConnection();
        sqlServes.getPoint(501);
        sqlServes.getPoint(534);
        sqlServes.getPoint(521);
        sqlServes.closeConnection();

    }
}
