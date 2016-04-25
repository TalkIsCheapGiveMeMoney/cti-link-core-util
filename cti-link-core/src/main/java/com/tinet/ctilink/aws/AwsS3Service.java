package com.tinet.ctilink.aws;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.tinet.ctilink.util.MD5Encoder;
import org.apache.commons.lang3.StringUtils;

/**
 * aws s3 操作类
 * 
 */
public class AwsS3Service {

	private AmazonS3Client awsS3Client;
	private BasicAWSCredentials awsCredentials;

	public void setAwsS3Client(AmazonS3Client awsS3Client) {
		this.awsS3Client = awsS3Client;
		this.awsS3Client.setRegion(Region.getRegion(Regions.CN_NORTH_1));
	}

	public void setAwsCredentials(BasicAWSCredentials awsCredentials) {
		this.awsCredentials = awsCredentials;
	}

	/**
	 * 解析AWS S3访问链接
	 * 
	 * @param bucket
	 * @param key
	 * @return
	 */
	public String getAwsS3Url(String bucket, String key) {
		Date expiration = new Date();
		long milliSeconds = expiration.getTime();
		Integer minute = 2;
		milliSeconds += 1000 * 60 * minute; // Add 2 min.
		expiration.setTime(milliSeconds);

		// 用 bucket 和 object 创建一个用于生成访问aws资源url的请求
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, key);

		// 设置请求的方式
		generatePresignedUrlRequest.setMethod(HttpMethod.GET);

		// 设置请求的url过期时间
		generatePresignedUrlRequest.setExpiration(expiration);

		String fileName = key.substring(key.lastIndexOf('/') + 1);
		ResponseHeaderOverrides header = new ResponseHeaderOverrides();
		header.setContentDisposition("attachment; filename=" + fileName);
		generatePresignedUrlRequest.setResponseHeaders(header);

		// 获取url
		URL url = awsS3Client.generatePresignedUrl(generatePresignedUrlRequest);

		return url.toString();
	}

	/**
	 * 上传文件至AWS S3
	 * 
	 * @param bucket
	 * @param key
	 * @param file
	 * @return
	 * @throws AmazonServiceException
	 * @throws AmazonClientException
	 * @throws Exception
	 */
	public PutObjectResult uploadFile(String bucket, String key, File file) {
		PutObjectResult rs = null;

		// 设置访问权限
		AccessControlList acl = new AccessControlList();
		acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);

		PutObjectRequest request = new PutObjectRequest(bucket, key, file);
		request = request.withAccessControlList(acl);

		rs = awsS3Client.putObject(request);

		return rs;
	}

	/**
	 * 生成打包下载时的校验Token
	 * 
	 * @param bucket
	 * @param keys
	 * @return
	 */
	public String generateZipToken(String bucket, String... keys) {
		String md5 = MD5Encoder.encode(
				awsCredentials.getAWSAccessKeyId() + awsCredentials.getAWSSecretKey() + bucket + StringUtils.join(keys));
		String base64 = Base64.getEncoder().encodeToString(md5.getBytes());
		return base64;
	}
	
	/**
	 * AWS S3上不同目录之间的文件复制
	 * 
	 * @param sourceBucketName
	 * @param sourceKey
	 * @param destinationBucketName
	 * @param destinationKey
	 * @return
	 */
	public void copyObject(String sourceBucketName,String sourceKey, String destinationBucketName,String destinationKey){
		// 设置访问权限
		AccessControlList acl = new AccessControlList();
		acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
		CopyObjectRequest copyObjRequest = new CopyObjectRequest(sourceBucketName, sourceKey, destinationBucketName, destinationKey);
		copyObjRequest = copyObjRequest.withAccessControlList(acl);
		awsS3Client.copyObject(copyObjRequest);
	}
	
	/**
	 * AWS S3文件删除
	 * 
	 * @param bucketName 桶的名字
	 * @param Key 桶后面的文件路径
	 * @return
	 */
	public void deleteObject(String bucketName,String Key){
		awsS3Client.deleteObject(new DeleteObjectRequest(bucketName,Key));
	}
	
	/**
	 * AWS S3多个文件删除
	 * 
	 * @param bucketName 桶的名字
	 * @param keys 文件路径集合
	 * @return
	 */
	public void deleteMultiObject(String bucketName,List<String> keys){
		 DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(bucketName);
		 List<KeyVersion> KeyVersions = new ArrayList<KeyVersion>();
		 for(String key:keys){
			 KeyVersions.add(new KeyVersion(key));
		 }
     	 multiObjectDeleteRequest.setKeys(KeyVersions);   
     	 awsS3Client.deleteObjects(multiObjectDeleteRequest);
	}

}