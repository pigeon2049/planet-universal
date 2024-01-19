package icu.moecat.planetuniversal.web3j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.util.concurrent.ExecutionException;

@Configuration
public class Web3jConfig {

    @Value("${web3j.node.address}")
    private String ethNode;

    @Bean
    public Web3j web3j() throws ExecutionException, InterruptedException {
        Web3j build = Web3j.build(new HttpService(ethNode));
        Web3ClientVersion web3ClientVersion = build.web3ClientVersion().sendAsync().get();
        System.out.println(web3ClientVersion.getWeb3ClientVersion());
        return build;
    }
}
