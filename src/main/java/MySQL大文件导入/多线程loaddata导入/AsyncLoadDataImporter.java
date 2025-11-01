package MySQL大文件导入.多线程loaddata导入;

import java.sql.*;
import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class AsyncLoadDataImporter {

    private static final String DB_URL = 
        "jdbc:mysql://localhost:3306/mydb?allowLoadLocalInfile=true";
    private static final String USER = "root";
    private static final String PASS = "123456";

    private static final int THREAD_COUNT = 2;
    private static final String CHUNK_PREFIX = "chunk_";

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();

        // 1. 清空目标表
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            conn.createStatement().execute("TRUNCATE TABLE employees");
        }

        // 2. 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // 3. 提交异步任务
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int index = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                loadChunk(index);
            }, executor).exceptionally(ex -> {
                System.err.println("Chunk " + index + " 导入失败: " + ex.getMessage());
                return null;
            });
            futures.add(future);
        }

        // 4. 等待所有完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        executor.shutdown();
        long endTime = System.currentTimeMillis();
        System.out.println("\n🎉 全部导入完成！总耗时: " + (endTime - startTime) + " ms");
    }

    private static void loadChunk(int index) {
        String filename = CHUNK_PREFIX + String.format("%03d.csv", index);
        String sql = "LOAD DATA LOCAL INFILE '" + filename + "' " +
                "INTO TABLE employees " +
                "FIELDS TERMINATED BY ',' " +
                "LINES TERMINATED BY '\\n' " +
                "IGNORE 1 LINES " +
                "(id, name, age, email, salary, @create_time) " +
                "SET create_time = STR_TO_DATE(@create_time, '%Y-%m-%d %H:%i:%s')";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            long start = System.currentTimeMillis();
            conn.createStatement().execute(sql);
            long end = System.currentTimeMillis();
            System.out.println("📦 Chunk " + index + " 完成，耗时: " + (end - start) + " ms");
        } catch (SQLException e) {
            // 🔥 关键：打印完整堆栈，不要包装成 RuntimeException
            System.err.println("❌ Chunk " + index + " SQL 错误:");
            e.printStackTrace(); // 这会显示真正原因！
            throw new RuntimeException(e); // 如果必须抛出，保留 cause
        }
    }
}