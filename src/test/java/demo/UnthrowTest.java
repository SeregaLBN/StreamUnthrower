package demo;

import org.junit.Test;
import utils.stream.Unthrow;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/** Test throw unchecker */
public class UnthrowTest {

    static class StreamHelper {
        /** {@link Enumeration} as {@link Stream} */
        public static <T> Stream<T> of(Enumeration<T> e) {
            return StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(
                            new Iterator<T>() {
                                public T next() { return e.nextElement(); }
                                public boolean hasNext() { return e.hasMoreElements(); }
                            },
                            Spliterator.ORDERED), false);
        }
    }

    @Test
    public void getMyIpTest() throws SocketException {
        StreamHelper.of(NetworkInterface.getNetworkInterfaces())
                .filter(iface -> Unthrow.wrap(() -> !iface.isLoopback() && iface.isUp())) // filters out 127.0.0.1 and inactive interfaces
                .map(NetworkInterface::getInetAddresses)
                .flatMap(StreamHelper::of)
                //.filter(ip -> ip instanceof Inet4Address) // only IPv4
                .map(InetAddress::getHostAddress)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        try {
            new UnthrowTest().getMyIpTest();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

}
