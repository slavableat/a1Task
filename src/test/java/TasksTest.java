import org.junit.Test;
import com.example.demo.service.FunctionCalculator;
import com.example.demo.service.IpConverter;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TasksTest {
    Map<Integer, String> int32AndIPv4 = new HashMap<>(Map.of(
            IpConverter.longToInt32(2149583360L), "128.32.10.0",
            IpConverter.longToInt32(255L), "0.0.0.255",
            IpConverter.longToInt32(497L), "0.0.1.241"
    ));
    Map<Integer, String> parameterAndValue = new HashMap<>(Map.of(
            6, "1.212500",
            100, "1.010102",
            120, "1.008404",
            190, "1.005291",
            700, "1.001431",
            1000, "1.001001"
    ));
//    10000, "1.000100" TODO для проверки на большом значении


    @Test
    public void checkInt32ToIPv4() {
        int32AndIPv4.forEach((int32, iPv4) -> assertEquals(
                IpConverter.int32ToIp(int32),
                iPv4));
    }

    @Test
    public void checkIPv4ToInt32() {
        int32AndIPv4.forEach((int32, iPv4) -> assertEquals(
                IpConverter.ipToInt32(iPv4),
                IpConverter.int32ToLong(int32)));
    }

    @Test
    public void checkSecondTask() {
        parameterAndValue.forEach((parameter, expected) -> assertEquals(
                FunctionCalculator.getYByX(parameter).toString(),
                expected
        ));
    }
}
