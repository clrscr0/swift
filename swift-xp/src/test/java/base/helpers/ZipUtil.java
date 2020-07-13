package base.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	public ZipUtil() {
    }

    public static void main(String[] args) throws IOException {
    	//String zipfile = "temp\\Folder.zip";
    	//String src = "src\\test\\resources\\report\\test_suites\\2017-0414_04-25-04";
    	String zipFilePath = "temp\\result.zip";
    	String sourceDirPath= "RunResults";
    	
        ZipUtil.pack(sourceDirPath, zipFilePath);
    }

    public static void pack(String sourceDirPath, String zipFilePath) throws IOException {
        Path p = Files.createFile(Paths.get(zipFilePath));
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
            Path pp = Paths.get(sourceDirPath);
            Files.walk(pp)
              .filter(path -> !Files.isDirectory(path))
              .forEach(path -> {
                  ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                  try {
                      zs.putNextEntry(zipEntry);
                      zs.write(Files.readAllBytes(path));
                      zs.closeEntry();
                } catch (Exception e) {
                    System.err.println(e);
                }
              });
        }
    }
}
