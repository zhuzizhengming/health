package com.tao.health.utils;

import com.google.gson.Gson;
import com.tao.health.constant.MessageConstant;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用七牛云的工具类：
 */

public class QiNiuUtils {

    private static final String ACCESSKEY = "D2j3SDIhKH93Ic7z_bvHWF_Tx6sEEgvHpbabtef8";
    private static final String SECRETKEY = "DKvp_o2sSekmVk8YLsXWJq6cLgywKzRIvbev396G";
    private static final String BUCKET = "tao106";
    public static final String DOMAIN = "http://qmleenb75.hn-bkt.clouddn.com/";
    private static long logTimestemp4Toke = 0l;
    private static String token = null;

    public static void main(String[] args) {
//        //这里是上传文件的地方需要把文件的url给出来
        uploadFile("D:\\java129期基础班\\day54_传智健康day03\\资料\\图片资源\\xh.png","dd2.jpg");
        //removeFiles("20190529083159.jpg","20190529083241.jpg");
        //listFile();
    }

    /**
     * 遍历7牛上的所有图片
     * @return
     */
    public static List<String> listFile(){
        BucketManager bucketManager = getBucketManager();
        //列举空间文件列表, 第一个参数：图片的仓库（空间名）,第二个参数，文件名前缀过滤。“”代理所有
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(BUCKET,"");
        List<String> imageFiles = new ArrayList<String>();
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                // item.key 文件名
                imageFiles.add(item.key);
                System.out.println(item.key);
            }
        }
        return imageFiles;
    }

    /**
     * 批量删除
     * @param filenames 需要删除的文件名列表
     * @return 删除成功的文件名列表
     */
    public static List<String> removeFiles(String... filenames){
        // 删除成功的文件名列表
        List<String> removeSuccessList = new ArrayList<String>();
        if(filenames.length > 0){
            // 创建仓库管理器
            BucketManager bucketManager = getBucketManager();
            // 创建批处理器
            BucketManager.Batch batch = new BucketManager.Batch();
            // 批量删除多个文件
            batch.delete(BUCKET,filenames);
            try {
                // 获取服务器的响应
                Response res = bucketManager.batch(batch);
                // 获得批处理的状态
                BatchStatus[] batchStatuses = res.jsonToObject(BatchStatus[].class);
                for (int i = 0; i < filenames.length; i++) {
                    BatchStatus status = batchStatuses[i];
                    String key = filenames[i];
                    System.out.print(key + "\t");
                    if (status.code == 200) {
                        removeSuccessList.add(key);
                        System.out.println("delete success");
                    } else {
                        System.out.println("delete failure");
                    }
                }
            } catch (QiniuException e) {
                e.printStackTrace();
                throw new RuntimeException(MessageConstant.PIC_UPLOAD_FAIL);
            }
        }
        return removeSuccessList;
    }

    public static void uploadFile(String localFilePath, String savedFilename){
        UploadManager uploadManager = getUploadManager();
        String upToken = getToken();
        try {
            Response response = uploadManager.put(localFilePath, savedFilename, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(String.format("key=%s, hash=%s",putRet.key, putRet.hash));
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
            throw new RuntimeException(MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    public static void uploadViaByte(byte[] bytes, String savedFilename){
        UploadManager uploadManager = getUploadManager();
        String upToken = getToken();
        try {
            Response response = uploadManager.put(bytes, savedFilename, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
            throw new RuntimeException(MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    private static String getToken(){
        if(System.currentTimeMillis() > logTimestemp4Toke) {
            // 创建授权
            Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
            // 获得认证后的令牌
            token = auth.uploadToken(BUCKET);
            logTimestemp4Toke = System.currentTimeMillis() + 60*60*1000l;
        }
        return token;
    }

    private static UploadManager getUploadManager(){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //构建上传管理器
        return new UploadManager(cfg);
    }

    private static BucketManager getBucketManager(){
        // 创建授权信息
        Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
        // 创建操作某个仓库的管理器
        return new BucketManager(auth, new Configuration(Zone.zone2()));
    }

}
