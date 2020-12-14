package com.open.custom.api.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class SFtpConfig {
    /**
     * IP
     */
    @Value("${sftp.host}")
    private String host;

    /**
     * 账号
     */
    @Value("${sftp.userName}")
    private String userName;

    /**
     * 密码
     */
    @Value("${sftp.password}")
    private String password;

    /**
     * 基础路径
     */
    @Value("${sftp.basePath}")
    private String basePath;

    /**
     * 协议
     */
    @Value("${sftp.protocol}")
    private String protocol;

    /**
     * 端口
     */
    @Value("${sftp.port}")
    private Integer port;

    /**
     * session连接超时时间
     */
    @Value("${sftp.sessionConnectTimeout}")
    private Integer sessionConnectTimeout;

    /**
     * channel连接超时时间
     */
    @Value("${sftp.channelConnectedTimeout}")
    private Integer channelConnectedTimeout;

}
