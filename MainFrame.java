/*@author zzy*/
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.*;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class MainFrame extends JFrame {
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField aTextField;
    private final JTextField bTextField;
    private final JTextField cTextField;
    private final JTextField dTextField;

    public MainFrame() {

//        JFrame jf = new JFrame();
//        jf.setSize(300, 150);
//        jf.setTitle("attention");
//        Container container = jf.getContentPane();
//        JLabel jl = new JLabel("connection SUCCESS");
//        jl.setHorizontalAlignment(SwingConstants.CENTER);
//        container.add(jl);
//        jf.setLocation(500, 500);
//        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        jf.setVisible(true);
//        jf.setAlwaysOnTop(true);

        //主窗口
        setTitle("PointBroad");
        setBounds(100, 100, 655, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JScrollPane scrollPane = new JScrollPane();
        getContentPane().add(scrollPane, BorderLayout.SOUTH);
        String[] columnNames = {"id", "point", "name", "date"};//定义表格列明数组
        //定义表格数据数组
        String[][] tableValues = {};

        //创建表格模型
        tableModel = new DefaultTableModel(tableValues, columnNames);
        table = new JTable(tableModel);
        //监听器、单选、排序器
        table.setRowSorter(new TableRowSorter<>(tableModel));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseAdapter() {
            //鼠标监听
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                Object oa = tableModel.getValueAt(selectedRow, 0);
                Object ob = tableModel.getValueAt(selectedRow, 1);
                Object oc = tableModel.getValueAt(selectedRow, 2);
                Object od = tableModel.getValueAt(selectedRow, 3);
                aTextField.setText(oa.toString());
                bTextField.setText(ob.toString());
                cTextField.setText(oc.toString());
                dTextField.setText(od.toString());

            }
        });

        //获取时间格式yyyy-MM-dd,与DB字段DATE格式相同
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String str = simpleDateFormat.format(new Date());
        scrollPane.setViewportView(table);
        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);

        //添加"列"
        panel.add(new Label("id"));
        aTextField = new JTextField("", 10);
        panel.add(aTextField);

        panel.add(new Label("name"));
        cTextField = new JTextField("", 10);
        panel.add(cTextField);

        panel.add(new JLabel("point"));
        bTextField = new JTextField("", 10);
        panel.add(bTextField);

        panel.add(new Label("date"));
        dTextField = new JTextField(str, 10);
        panel.add(dTextField);

        //按键：添加(add)
        JButton addButton = new JButton("add");
        addButton.addActionListener(e -> {
            String[] rowValues = {aTextField.getText(), bTextField.getText(), cTextField.getText(), dTextField.getText()};
            tableModel.addRow(rowValues);
            aTextField.setText(null);
            bTextField.setText(null);
            cTextField.setText(null);
            dTextField.setText(str);
            //(secure component)
            dTextField.setEnabled(false);

            //创建临时文件
            File file = new File("D:\\log.txt");
            //非空判断
            if (Objects.equals(rowValues[0], "") | Objects.equals(rowValues[1], "") | Objects.equals(rowValues[2], "")) {
                JFrame jFrame = new JFrame();
                jFrame.setSize(300, 150);
                jFrame.setTitle("ERROR");
                Container container1 = jFrame.getContentPane();
                JLabel jLabel = new JLabel("Values can't be null!");
                jLabel.setHorizontalAlignment(SwingConstants.CENTER);
                container1.add(jLabel);
                jFrame.setLocation(500, 500);
                jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                jFrame.setVisible(true);
                jFrame.setAlwaysOnTop(true);
                System.out.println("invalid syntax,countermand SQLCommand");
            } else {
                //写入临时文件
                try {
                    FileWriter fileWriter = new FileWriter(file, true);
                    fileWriter.write(" ");
                    fileWriter.write(rowValues[0] + " ");
                    fileWriter.write(rowValues[1] + " ");
                    fileWriter.write(rowValues[2] + " ");
                    fileWriter.write(rowValues[3] + " ");
                    System.out.println(Arrays.toString(rowValues) + "\tRecord write to file successfully, waiting for SQL validation read...");
                    fileWriter.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            //DOS命令(secure component)
            String settings = "attrib +H \"" + file.getAbsolutePath() + "\"";
            try {
                Runtime.getRuntime().exec(settings);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        panel.add(addButton);
        //按键：修改(change)
        JButton updButton = new JButton("change");
        updButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.setValueAt(aTextField.getText(), selectedRow, 0);
                tableModel.setValueAt(bTextField.getText(), selectedRow, 1);
                tableModel.setValueAt(cTextField.getText(), selectedRow, 2);
                tableModel.setValueAt(dTextField.getText(), selectedRow, 3);
            }
        });
        panel.add(updButton);
        //按键：删除(delete)
        JButton delButton = new JButton("delete");
        delButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
            }
        });
        panel.add(delButton);
        //按键：上传数据(Send data to MYSQL)
        JButton RankButton = new JButton("Send data to MYSQL");
        RankButton.addActionListener(e -> {
            JDBC demo = new JDBC();
            List<String> date = new ArrayList<>();
            List<Integer> point = new ArrayList<>();
            List<String> name = new ArrayList<>();
            List<Integer> id = new ArrayList<>();
            demo.initConnection();
            //读取临时文件信息
            File file = new File("D:\\log.txt");

            //DOS命令(secure component)
            String sets = "attrib -H \"" + file.getAbsolutePath() + "\"";
            try {
                Runtime.getRuntime().exec(sets);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            FileReader fileReader = null;
            try {
                fileReader = new FileReader(file);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            char[] cha = new char[1024];
            int len = 0;
            try {
                assert fileReader != null;
                len = fileReader.read(cha);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            String str1 = new String(cha, 0, len);
            String[] res = str1.split(" ", str1.length());
            try {
                fileReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            for (String re : res) {
                if (re.length() >= 10 & !Objects.equals(re, "")) {
                    date.add(re);
                }
                if (re.length() <= 3 & !Objects.equals(re, "")) {
                    try {
                        int Point = Integer.parseInt(re);
                        if (Point < 500) {
                            point.add(Point);
                        } else id.add(Point);

                    } catch (NumberFormatException exception) {
                        exception.printStackTrace();
                        name.add(re);
                    }

                }
            }
            for (String s : date) {
                System.out.println(s);
            }
            for (Integer integer : point) {
                System.out.println(integer);
            }
            for (String s : name) {
                System.out.println(s);
            }
            for (Integer integer : id) {
                System.out.println(integer);
            }

            //获取时间,格式与DB TIME相同
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm:ss");
            String time = simpleDateFormat1.format(new Date());

            //int id, String name, String date, int record, String time
            for (int i = 0; i < id.size(); i++) {
                demo.add(id.get(i), name.get(i), date.get(i), point.get(i), time);
            }
            //遍历DB中的所有数据
            demo.showAllData();
            demo.closeConnection();

            //删除临时文件
            try {
                System.gc();   //强制启动虚拟机垃圾回收
                if (file.delete()) {
                    System.out.println("TempFile is deleted successfully");
                } else {
                    System.out.println("Delete failed.");
                }
            } catch (Exception exception) {
                System.out.println("Exception occurred");
                exception.printStackTrace();
            }

        });
        panel.add(RankButton);

        JButton Calc = new JButton("Calculator");
        Calc.addActionListener(e -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
        });
        panel.add(Calc);

        //按键：退出(EXIT)
        JButton sumButton = new JButton("EXIT");
        sumButton.setVisible(true);
        sumButton.addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(null,
                    "Are you sure?",
                    "warning",
                    JOptionPane.YES_NO_CANCEL_OPTION);
            if (answer == 0) {
                System.exit(0);
            }
        });
        panel.add(sumButton);

    }
}
