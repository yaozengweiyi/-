package MySQL大文件导入;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CsvImportDemo {
    // 数据库配置（改成你的）
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydb?useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true";
    private static final String USER = "root";
    private static final String PASS = "123456"; // 改成你的密码

    // CSV 文件路径
    private static final String CSV_FILE = "C:\\Users\\姚\\PycharmProjects\\PythonProject11\\test_data_1m.csv";

    // 每批插入多少行（调大可提速，但别超过 10000）
    private static final int BATCH_SIZE = 1000;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis(); // 开始计时

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // 关键：开启批量重写（大幅提升速度）
            conn.setAutoCommit(false);

            String sql = "INSERT INTO users (id, name, age,) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE))) {
                String[] line;
                int totalRows = 0;
                long batchStartTime = System.currentTimeMillis();

                while ((line = reader.readNext()) != null) {
                    if (line.length < 3) continue; // 跳过无效行

                    pstmt.setInt(1, Integer.parseInt(line[0]));
                    pstmt.setString(2, line[1]);
                    pstmt.setInt(3, Integer.parseInt(line[2]));
                    pstmt.addBatch(); // 加入批次

                    totalRows++;

                    // 每 BATCH_SIZE 行执行一次
                    if (totalRows % BATCH_SIZE == 0) {
                        long currentBatchTime = System.currentTimeMillis() - batchStartTime;
                        pstmt.executeBatch();
                        conn.commit();
                        pstmt.clearBatch();

                        long batchEndTime = System.currentTimeMillis();
                        System.out.printf("已插入 %d 行，本批耗时: %d ms, 速度: %.0f 行/秒%n",
                                totalRows,
                                currentBatchTime,
                                (BATCH_SIZE * 1000.0) / currentBatchTime);

                        batchStartTime = System.currentTimeMillis();
                    }
                }

                // 插入剩余不足 BATCH_SIZE 的行
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