package MySQL大文件导入;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.*;

public class FastCsvImporter {

    // === 配置区（按你的环境修改）===
    private static final String DB_URL = 
        "jdbc:mysql://localhost:3306/mydb?" +
        "useSSL=false&" +
        "allowPublicKeyRetrieval=true&" +
        "rewriteBatchedStatements=true&" +     // ⭐ 关键：开启批量重写
        "sessionVariables=sql_mode=''";
    
    private static final String USER = "root";
    private static final String PASS = "123456"; // ← 改成你的密码

    private static final String CSV_FILE = "C:\\Users\\姚\\PycharmProjects\\PythonProject11\\test_data_1m.csv";
    private static final int BATCH_SIZE = 20000; // 调大批次（1万~5万最佳）

    public static void main(String[] args) {
        long totalStart = System.currentTimeMillis();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // 🔥 极速模式：关闭所有检查
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("SET autocommit=0");
                stmt.execute("SET unique_checks=0");
                stmt.execute("SET foreign_key_checks=0");
                stmt.execute("SET sql_log_bin=0"); // 如果不需要 binlog（主从复制）
            }

            // 🗑️ 清空表（用 TRUNCATE）
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("TRUNCATE TABLE employees");
                System.out.println("✅ 表已清空");
            }

            // 📥 开始高速导入
            importCsv(conn);

            // 🔒 恢复安全设置
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("SET unique_checks=1");
                stmt.execute("SET foreign_key_checks=1");
                stmt.execute("SET sql_log_bin=1");
                stmt.execute("COMMIT");
            }

            long totalEnd = System.currentTimeMillis();
            System.out.println("\n🎉 全程完成！");
            System.out.println("总耗时: " + (totalEnd - totalStart) + " ms");

        } catch (Exception e) {
            System.err.println("❌ 导入失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void importCsv(Connection conn) throws Exception {
        long batchStart = System.currentTimeMillis();
        long globalStart = batchStart;
        int totalRows = 0;

        String sql = "INSERT INTO employees (id, name, age, email, salary, create_time) VALUES (?, ?, ?, ?, ?, ?)";
        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE));
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 跳过表头
            reader.readNext();

            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line.length < 6) continue;

                // 精准设置参数（避免类型转换）
                pstmt.setInt(1, Integer.parseInt(line[0]));           // id
                pstmt.setString(2, line[1]);                         // name
                pstmt.setByte(3, Byte.parseByte(line[2]));           // age
                pstmt.setString(4, line[3]);                         // email
                pstmt.setBigDecimal(5, new BigDecimal(line[4]));     // salary
                pstmt.setTimestamp(6, Timestamp.valueOf(line[5]));   // create_time

                pstmt.addBatch();
                totalRows++;

                // 每 BATCH_SIZE 行提交一次
                if (totalRows % BATCH_SIZE == 0) {
                    pstmt.executeBatch();
                    conn.commit();
                    pstmt.clearBatch();

                    long now = System.currentTimeMillis();
                    long batchTime = now - batchStart;
                    double speed = (BATCH_SIZE * 1000.0) / batchTime;

                    System.out.printf("📊 %d 行已导入 | 本批耗时: %d ms | 速度: %.0f 行/秒 | 累计: %.1f 秒%n",
                            totalRows, batchTime, speed, (now - globalStart) / 1000.0);

                    batchStart = now;
                }
            }

            // 提交剩余行
            if (totalRows % BATCH_SIZE != 0) {
                pstmt.executeBatch();
                conn.commit();
            }

            System.out.println("\n✅ 数据导入完成！总行数: " + totalRows);
        }
    }
}