package service;

public class IpConverter {
    public static int longToInt32(long value) {
        return (int) (value & 0xffffffffL);// Integer.toUnsignedLong(result);
    }

    public static long int32ToLong(int value) {
        return ((long) value) & 0xffffffffL;// Integer.toUnsignedLong(result);
    }

    public static long ipToInt32(String ipAddress) {
        String[] octets = ipAddress.split("\\.");
        int result = 0;
        for (int i = 0; i < octets.length; i++) {
            result |= Integer.parseInt(octets[i]) << ((3 - i) * 8);
        }
        return int32ToLong(result); // Integer.toUnsignedLong(result);
    }

    public static String int32ToIp(int ipAddress) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append((ipAddress >>> (24 - (8 * i))) & 0xff);
            if (i < 3) {
                sb.append(".");
            }
        }
        return sb.toString();
    }
}