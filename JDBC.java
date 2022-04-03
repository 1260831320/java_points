/*@author zzy*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//Project Structure mysql connection
public class JDBC {

    Connection con;// �������ݿ����Ӷ���
    //��ʼ���ݿ�����
    public void initConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");        //�������ݿ�����
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

    //�ر����ݿ�����
    public void closeConnection() {// �ر����ݿ�����
        if (con != null) {
            try {
                con.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    public void showAllData() { //��������ѧ������
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from tb_stu");
            while (rs.next()) { // �����ǰ��䲻�����һ���������ѭ��
                System.out.print("��ţ�" + rs.getString("id")); // ����ֵ���
                System.out.print(" ����:" + rs.getString("name"));
                System.out.print(" ����:" + rs.getString("date"));
                System.out.println(" �Ʒ֣�" + rs.getString("record"));
                System.out.println(" ʱ��: " + rs.getString("time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(int id, String name, String date, int record, String time) {// ����¼�¼
        try {
            String sql = "insert into tb_stu values(?,?,?,?,?) ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id); // ���ñ��
            ps.setString(2, name); // ��������
            ps.setString(3, date); // ��������
            ps.setInt(4, record); // ���÷���
            ps.setString(5, time); //����ʱ��
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(int id) {// ɾ��ָ��ID��ѧ����¼
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("delete from tb_stu where id =" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
