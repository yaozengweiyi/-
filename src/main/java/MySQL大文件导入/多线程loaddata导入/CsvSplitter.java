package MySQL大文件导入.多线程loaddata导入;

import java.io.*;
import java.nio.file.*;

public class CsvSplitter {
    public static void splitCsv(String inputPath, int linesPerFile, String outputPrefix) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputPath))) {
            String header = reader.readLine(); // 读取表头
            
            String line;
            int fileIndex = 0;
            int currentLine = 0;
            BufferedWriter writer = null;

            while ((line = reader.readLine()) != null) {
                if (currentLine % linesPerFile == 0) {
                    if (writer != null) writer.close();
                    String outputFile = outputPrefix + String.format("%03d.csv", fileIndex++);
                    writer = Files.newBufferedWriter(Paths.get(outputFile));
                    writer.write(header); // 每个文件都写表头
                    writer.newLine();
                }
                writer.write(line);
                writer.newLine();
                currentLine++;
            }
            if (writer != null) writer.close();
            System.out.println("✅ 拆分成 " + fileIndex + " 个文件");
        }
    }

    public static void main(String[] args) throws IOException {
        splitCsv("C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/test_data_1m.csv", 500000, "chunk_");
    }
}