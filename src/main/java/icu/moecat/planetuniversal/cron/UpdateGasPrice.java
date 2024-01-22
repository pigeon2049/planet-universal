package icu.moecat.planetuniversal.cron;

import com.influxdb.LogLevel;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.WriteOptions;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import icu.moecat.planetuniversal.web3j.GasPrice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class UpdateGasPrice implements CommandLineRunner {

    @Value("${influx.url}")
    String url;

    @Value("${influx.token}")
    String token;

    @Value("${influx.org}")
    String org ;

    @Value("${influx.bucket}")
    String bucket ;

    private final GasPrice gasPrice;

    public UpdateGasPrice(GasPrice gasPrice) {
        this.gasPrice = gasPrice;
    }

    @Override
    public void run(String... args) throws Exception {
        InfluxDBClient client = InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket)
                .setLogLevel(LogLevel.BASIC);
        WriteApi writeApi = client.makeWriteApi(WriteOptions.builder().flushInterval(2_000).build());

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                try {
                    BigInteger gas = gasPrice.gasGasPrice();
                    if (gas!=null){
                        BigDecimal value= BigDecimal.valueOf(gas.longValue() * 0.000000001).setScale(2, RoundingMode.HALF_UP);
                        Point point = Point
                                .measurement("eth")
                                .addField("gas", gas.longValue())
                                .addField("gasGwei", value)
                                .time(Instant.now(), WritePrecision.S);

                        System.out.println("Produced DataPoint: " + point.toLineProtocol());

                        writeApi.writePoint(point);
                    }


                } catch (IOException ignored) {}

            }
        }, 0, 1000);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Close Client and Producer");
            timer.cancel();
            client.close();
        }));
    }
}
