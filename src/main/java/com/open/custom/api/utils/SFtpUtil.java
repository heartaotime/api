package com.open.custom.api.utils;

import com.jcraft.jsch.*;
import com.open.custom.api.config.SFtpConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Arrays;

public class SFtpUtil {

    /**
     * 初始化日志对象
     */
    private static final Logger log = LoggerFactory.getLogger(SFtpUtil.class);

    /**
     * 功能描述： 实现文件上传
     *
     * @param fileDir     文件所在的路径
     * @param fileName    文件的名称
     * @param inputStream 文件流
     * @param sFtpConfig  文件相关的配置信息
     * @return 返回上传结果
     * @throws Exception
     */
    public static boolean uploadFile(String fileDir, String fileName, InputStream inputStream, SFtpConfig sFtpConfig) throws Exception {
        ChannelSftp sftp = getSftp(sFtpConfig);
        try {
            fileDir = sFtpConfig.getBasePath() + fileDir;
            boolean dirs = createDirs(fileDir, sftp);
            if (!dirs) {
                log.info("创建文件目录失败！");
                return false;
            }
            sftp.put(inputStream, fileName);
            return true;
        } catch (Exception e) {
            log.info("文件上传失败：{}", e.getMessage());
            return false;
        } finally {
            disconnect(sftp);
        }
    }


    public static boolean checkFileExist(String fileDir, String fileName, SFtpConfig sFtpConfig) throws Exception {
        ChannelSftp sftp = getSftp(sFtpConfig);
        try {
            fileDir = sFtpConfig.getBasePath() + fileDir;
            String[] dirs = Arrays.stream(fileDir.split("/"))
                    .filter(a -> a != null && !a.equals(""))
                    .toArray(String[]::new);
            for (String dir : dirs) {
                try {
                    sftp.cd(dir);
                } catch (Exception e) {
                    return false;
                }
            }
            SftpATTRS lstat = sftp.lstat(fileName);
            return lstat != null;
        } catch (SftpException e) {
            if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                // file doesn't exist
                return false;
            }
            return false;
        } catch (Exception e) {
            log.info("checkFileExist 失败：{}", e.getMessage());
            return false;
        } finally {
            disconnect(sftp);
        }
    }

    /**
     * 功能描述： 创建文件夹
     *
     * @param dirPath 需要创建文件夹的路径
     * @param sftp    sftp对象
     * @return 返回创建的结果
     */
    private static boolean createDirs(String dirPath, ChannelSftp sftp) {
        if (dirPath != null && !dirPath.isEmpty() && sftp != null) {
            String[] dirs = Arrays.stream(dirPath.split("/"))
                    .filter(a -> a != null && !a.equals(""))
                    .toArray(String[]::new);
            for (String dir : dirs) {
                try {
                    sftp.cd(dir);
//                    log.info("进入的目录是 {}", dir);
                } catch (Exception e) {
                    try {
                        sftp.mkdir(dir);
                        log.info("创建的目录是 {}", dir);
                    } catch (SftpException e1) {
                        log.error("创建失败的目录是:{}", dir, e1);
                        e1.printStackTrace();
                    }
                    try {
                        sftp.cd(dir);
                        log.info("进入的目录是 {}", dir);
                    } catch (SftpException e1) {
                        log.error("进入失败的目录是:{}", dir, e1);
                        e1.printStackTrace();
                    }
                }
            }
            return true;
        }
        return false;
    }


    /**
     * 功能描述： 创建sftp连接
     *
     * @param sFtpConfig sftp连接对象
     * @return 返回 sftp通道对象
     * @throws Exception
     */
    public static ChannelSftp getSftp(SFtpConfig sFtpConfig) throws JSchException {
        JSch jsch = new JSch();
        Session session = getSession(jsch, sFtpConfig.getHost(), sFtpConfig.getUserName(), sFtpConfig.getPort());
        session.setPassword(sFtpConfig.getPassword());
        session.connect(sFtpConfig.getSessionConnectTimeout());
        Channel channel = session.openChannel(sFtpConfig.getProtocol());
        channel.connect(sFtpConfig.getChannelConnectedTimeout());
        return (ChannelSftp) channel;
    }

    /**
     * 创建session
     *
     * @param jsch     jsch对象
     * @param host     sftpIP地址
     * @param username sftp账号
     * @param port     sftp端口
     * @return 返回 session对象
     * @throws Exception
     */
    private static Session getSession(JSch jsch, String host, String username, Integer port) throws JSchException {
        Session session;
        if (port <= 0) {
            session = jsch.getSession(username, host);
        } else {
            session = jsch.getSession(username, host, port);
        }
        if (session == null) {
            return null;
        }
        session.setConfig("StrictHostKeyChecking", "no");
        return session;
    }

    /**
     * 功能描述： 关闭连接
     *
     * @param sftp sftp对象
     */
    public static void disconnect(ChannelSftp sftp) {
        try {
            if (sftp != null) {
                if (sftp.isConnected()) {
                    sftp.disconnect();
                } else if (sftp.isClosed()) {
                    log.info("sftp已经关闭");
                }
                if (null != sftp.getSession()) {
                    sftp.getSession().disconnect();
                }
            }
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public static ChannelSftp enterDir(SFtpConfig sFtpConfig, String fileDir, boolean isMkdir) throws Exception {
        ChannelSftp sftp = getSftp(sFtpConfig);

        fileDir = sFtpConfig.getBasePath() + fileDir;
        String[] dirs = Arrays.stream(fileDir.split("/"))
                .filter(a -> a != null && !a.equals(""))
                .toArray(String[]::new);
        for (String dir : dirs) {
            try {
                sftp.cd(dir);
            } catch (Exception e) {
                try {
                    if (isMkdir) {
                        sftp.mkdir(dir);
                        log.info("创建的目录是 {}", dir);
                    } else {
                        throw new Exception("该目录不存在 [" + dir + "]");
                    }
                } catch (SftpException e1) {
                    log.error("创建失败的目录是:{}", dir, e1);
                    e1.printStackTrace();
                }
                try {
                    sftp.cd(dir);
                    log.info("进入的目录是 {}", dir);
                } catch (SftpException e1) {
                    log.error("进入失败的目录是:{}", dir, e1);
                    e1.printStackTrace();
                }
            }
        }
        return sftp;
    }


}