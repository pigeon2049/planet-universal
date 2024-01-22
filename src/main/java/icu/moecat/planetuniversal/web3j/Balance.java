package icu.moecat.planetuniversal.web3j;

import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class Balance {

    private final Web3j web3j;

    public Balance(Web3j web3j) {
        this.web3j = web3j;
    }

    public BigDecimal getBalance(String address) throws IOException {
        BigInteger balance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().getBalance();
        BigDecimal ether = Convert.fromWei(String.valueOf(balance), Convert.Unit.ETHER);
        System.out.println(address+" balance:"+ether.toString());
        return  ether;
    }

}
