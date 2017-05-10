package filetransfer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by skeletrox on 8/5/17.
 */
public class Decompressor {
    public void decompress(File f) throws IOException
    {
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(f));
        ZipEntry ze = zis.getNextEntry();
        while (ze != null){
            File newFile = new File("OUTPUT");
            FileOutputStream fos  = new FileOutputStream(newFile);

            int len;
            while ((len = zis.read(buffer)) > 0)
            {
                fos.write(buffer, 0, len);
            }
            fos.close();
            ze = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
        System.out.println("Finished Decompressing");
    }

}
