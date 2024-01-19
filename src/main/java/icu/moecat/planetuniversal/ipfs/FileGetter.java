package icu.moecat.planetuniversal.ipfs;

import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;
import jakarta.annotation.PostConstruct;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class FileGetter {

    private final IPFS ipfs;

    public FileGetter(IPFS ipfs) {
        this.ipfs = ipfs;
    }


    public byte[] getFileByBase58(String base58) throws IOException {
        Multihash filePointer = Multihash.fromBase58(base58);
        return ipfs.cat(filePointer);
    }

    //@PostConstruct
    public void helloWorldFile() throws IOException {
        byte[] fileBytes = getFileByBase58("QmPZ9gcCEpqKTo6aq61g2nXGUhM4iCL3ewB6LDXZCtioEB");
        FileUtils.writeByteArrayToFile(
                new File("hello.txt"), fileBytes);
        System.out.println(
                FileUtils.readFileToString(new File("hello.txt"),
                        StandardCharsets.UTF_8));
    }

}
