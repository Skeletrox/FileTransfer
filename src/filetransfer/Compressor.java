package filetransfer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by skeletrox on 8/5/17.
 */

public class Compressor {
    public void compress(File f) throws IOException
    {
        String fileName =  f.getName().concat(".zip");
        FileOutputStream fos = new FileOutputStream(fileName);
        ZipOutputStream zos = new ZipOutputStream(fos);
        zos.putNextEntry(new ZipEntry(f.getName()));

        byte[] bytes = Files.readAllBytes(Paths.get(f.getAbsolutePath()));
        zos.write(bytes, 0, bytes.length);
        System.out.println ("Finished Compressing");
        zos.closeEntry();
        zos.close();
    }
}
