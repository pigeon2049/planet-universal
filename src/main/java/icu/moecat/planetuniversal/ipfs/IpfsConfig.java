package icu.moecat.planetuniversal.ipfs;

import io.ipfs.api.IPFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IpfsConfig {

    @Value("${ipfs.api.endpoint}")
    private String endPoint;

    @Value("${ipfs.api.gateway}")
    private String gateWay;


    @Bean
    public IPFS ipfs(){

        return new IPFS(endPoint);
    }

}
