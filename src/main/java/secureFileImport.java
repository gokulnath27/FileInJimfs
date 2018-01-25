import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;


public class secureFileImport {


    public static void main(String[] args) throws Exception {

        // getting file data from url


        URL url = new URL("http://someurl/someFile");// this is a sample url which doesnt work , hence provide a valid url
        InputStream is = null;
        is = url.openStream();
        byte[] b = new byte[8];
        String read;
        StringBuilder reader = new StringBuilder();
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is));


        // reading the data from the file in the url
        while (is.read(b) != -1) {

            //System.out.print(new String(b));
            reader.append(bufferReader.readLine());
            reader.append("\n");
        }


        //creating a file system using jimfs
        FileSystem fs = Jimfs.newFileSystem(Configuration.unix());
        Path foo = fs.getPath("/testDirectory");
        Files.createDirectory(foo);
        Path path = foo.resolve("sample.txt"); // /foo/hello.txt


       //writing the data into created jimfs file
        Files.write(path, ImmutableList.of(reader), StandardCharsets.UTF_8);


        InputStream inputStream = Files.newInputStream(path);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = buffer.readLine()) != null) {
            System.out.println(line);
        }


    }
}
