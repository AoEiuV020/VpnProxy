package cc.aoeiuv020.vpnproxy.core;

import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import cc.aoeiuv020.vpnproxy.tunnel.Config;
import cc.aoeiuv020.vpnproxy.tunnel.RawTunnel;
import cc.aoeiuv020.vpnproxy.tunnel.Tunnel;
import cc.aoeiuv020.vpnproxy.tunnel.httpconnect.HttpConnectConfig;
import cc.aoeiuv020.vpnproxy.tunnel.httpconnect.HttpConnectTunnel;

public class TunnelFactory {

    public static Tunnel wrap(SocketChannel channel, Selector selector) {
        return new RawTunnel(channel, selector);
    }

    public static Tunnel createTunnelByConfig(InetSocketAddress destAddress, Selector selector) throws Exception {
        if (destAddress.isUnresolved()) {
            Config config = ProxyConfig.Instance.getDefaultTunnelConfig(destAddress);
            if (config instanceof HttpConnectConfig) {
                return new HttpConnectTunnel((HttpConnectConfig) config, selector);
            }
            throw new Exception("The config is unknow.");
        } else {
            return new RawTunnel(destAddress, selector);
        }
    }

}
