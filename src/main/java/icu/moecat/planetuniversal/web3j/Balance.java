package icu.moecat.planetuniversal.web3j;

import org.springframework.stereotype.Service;
import org.web3j.contracts.eip20.generated.ERC20;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
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


    public BigDecimal getErc20Balance(String address,String tokenContractAddress) throws Exception {

        // Use ReadOnlyTransactionManager for read-only operations
        ReadonlyTransactionManager txManager = new ReadonlyTransactionManager(web3j, null);
        DefaultGasProvider gasPriceProvider = new DefaultGasProvider();
        // ERC20 is your generated ERC20 contract wrapper class
        ERC20 contract = ERC20.load(tokenContractAddress, web3j, txManager, gasPriceProvider);

        // Call the balanceOf function on the ERC-20 contract
        BigInteger balance = contract.balanceOf(address).send();
        BigDecimal ether = Convert.fromWei(String.valueOf(balance), Convert.Unit.ETHER);
        System.out.println("Balance of " + address + ": " + ether);

        return ether;
    }


}
