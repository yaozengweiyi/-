package MySQL大文件导入;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.sql.*;

public class CsvImportEmployees {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydb?" +
            "useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true";
    private static final String USER = "root";
    private static final String PASS = "123456"; // ← 改成你的密码

    private static final String CSV_FILE = "C:\\Users\\姚\\PycharmProjects\\PythonProject11\\test_data_1m.csv";
    private static final int BATCH_SIZE = 100000; // 每批 5000 行（平衡速度和内存）

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            conn.setAutoCommit(false); // 关闭自动提交

            String sql = "INSERT INTO employees (id, name, age, email, salary, create_time) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE))) {
                String[] line;
                int totalRows = 0;

                // 跳过表头
                reader.readNext();

                while ((line = reader.readNext()) != null) {
                    if (line.length < 6) continue; // 跳过无效行

                    pstmt.setInt(1, Integer.parseInt(line[0]));     // id
                    pstmt.setString(2, line[1]);                   // name
                    pstmt.setByte(3, Byte.parseByte(line[2]));     // age (TINYINT)
                    pstmt.setString(4, line[3]);                   // email
                    pstmt.setBigDecimal(5, new java.math.BigDecimal(line[4])); // salary
                    pstmt.setTimestamp(6, Timestamp.valueOf(line[5])); // create_time

                    pstmt.addBatch();
                    totalRows++;

                    if (totalRows % BATCH_SIZE == 0) {
                        pstmt.executeBatch();
                        conn.commit();
                        pstmt.clearBatch();
                        System.out.println("已插入 " + totalRows + " 行");
                    }
                }

                // 插入剩余行
                if (totalRows % BATCH_SIZE != 0) {
                    pstmt.executeBatch();
                    conn.commit();
                }

                long totalTime = System.currentTimeMillis() - startTime;
                System.out.println("\n✅ 导入完成！");
                System.out.println("总行数: " + totalRows);
                System.out.println("总耗时: " + totalTime + " ms");
                System.out.printf("平均速度: %.0f 行/秒%n", (totalRows * 1000.0) / totalTime);

            } catch (Exception e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            System.err.println("数据库错误: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("其他错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
}