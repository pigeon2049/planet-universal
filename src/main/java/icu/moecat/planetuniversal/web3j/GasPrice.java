package icu.moecat.planetuniversal.web3j;


import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

import java.io.IOException;
import java.math.BigInteger;

@Service
public class GasPrice {

    private final Web3j web3j;


    public GasPrice(Web3j web3j) {
        this.web3j = web3j;
    }


    public String gasGweiPrice() throws IOException {

        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
        double price = gasPrice.longValue() * 0.000000001;
        return String.format("%.2f",price);
    }

    public BigInteger gasGasPrice() throws IOException {

        return web3j.ethGasPrice().send().getGasPrice();
    }



}
