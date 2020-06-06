package com.open.custom.api.factory;

import com.jcraft.jsch.ChannelSftp;
import com.open.custom.api.config.SFtpConfig;
import com.open.custom.api.utils.SFtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SftpSessionFactory {

    @Autowired
    private SFtpConfig sFtpConfig;


    private static Map<String, ChannelSftp> sftpMap = new ConcurrentHashMap<>();


    public ChannelSftp getSession(String fileDir) {
        ChannelSftp channelSftp = null;
        if (StringUtils.isEmpty(fileDir)) {
            return null;
        }

        if (sftpMap.containsKey(fileDir)) {
            channelSftp = sftpMap.get(fileDir);
            if (channelSftp != null && channelSftp.isConnected()) {
                return channelSftp;
            }
        }

        try {
            ChannelSftp sftp = SFtpUtil.enterDir(sFtpConfig, fileDir, true);
            sftpMap.put(fileDir, sftp);
            return sftp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
