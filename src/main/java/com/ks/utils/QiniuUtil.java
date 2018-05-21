package com.ks.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.thinkgem.jeesite.common.utils.StringUtils;

public class QiniuUtil {
	
	/*****************测试环境(华南)*************************/
	// 设置需要操作的账号的AK和SK
	/*public static String ACCESS_KEY = "RDlO9kBTT5LxdAHqwDbRZ753Y8ERwYkxm-Ey9jqe";
	public static String SECRET_KEY = "wJwRlqCH2ph1JyP92TFiEgZvqRxYf-jnZrfgU_Fx";
	public static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	// 要测试的空间和key，并且这个key在你空间中存在
	public static String bucket = "buddhism-file";

	//测试
	//public static String domain = "http://oozuk6mti.bkt.clouddn.com/";
	//测试加速
	public static String domain = "http://app.cskuaishou.com/";
	
	public static String old_domain = "http://oozuk6mti.bkt.clouddn.com/";
	public static Zone z = Zone.zone2();
	*/
	
	/*****************************正式环境(华北)*********************/
	// 设置需要操作的账号的AK和SK
	public static String ACCESS_KEY = "ihDWaOw6TCby3NAsHWhJSckG8t72fh3kXVTC4hxt";
	public static String SECRET_KEY = "L-2bU2tiMjMzKiii5MHGu0iuAlYiFXdumVEuAmFd";
	public static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	// 要测试的空间和key，并且这个key在你空间中存在
	public static String bucket = "buddhism-file";
	//正式加速
	public static String domain = "http://app.fo789.com/";
	
	public static String old_domain = "http://oozuk6mti.bkt.clouddn.com/";

	public static Zone z = Zone.zone1();
	public static Configuration c = new Configuration(z);

	public static void copy(String key) {
		// 设置需要操作的账号的AK和SK
		/*
		 * String ACCESS_KEY = "Access_Key"; String SECRET_KEY = "Secret_Key";
		 * Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		 * 
		 * Zone z = Zone.zone0(); Configuration c = new Configuration(z);
		 */

		// 实例化一个BucketManager对象
		BucketManager bucketManager = new BucketManager(auth, c);
		// 要测试的空间和key，并且这个key在你空间中存在
		// String bucket = "Bucket_Name";
		// String key = "Bucket_key";
		// 将文件从文件key 复制到文件key2。 可以在不同bucket复制
		String key2 = "yourjavakey";
		try {
			// 调用copy方法移动文件
			bucketManager.copy(bucket, key, bucket, key2);
		} catch (QiniuException e) {
			// 捕获异常信息
			Response r = e.response;
			System.out.println(r.toString());
		}
	}

	public static void delete(String key) {
		// 实例化一个BucketManager对象
		BucketManager bucketManager = new BucketManager(auth, c);
		// 要测试的空间和key，并且这个key在你空间中存在
		// String bucket = "Bucket_Name";
		// String key = "Bucket_key";
		try {
			// 调用delete方法移动文件
			bucketManager.delete(bucket, key);
		} catch (QiniuException e) {
			// 捕获异常信息
			Response r = e.response;
			System.out.println(r.toString());

		}
	}

	public static String getPrivateDownloadUrlByKey(String key) {
		if(StringUtils.isBlank(key)){
			return "";
		}
		// 构造私有空间的需要生成的下载的链接
		String URL = domain + key;
		return getPrivateDownloadUrl(URL);
	}
	public static String getPrivateDownloadUrl(String url) {
		if(StringUtils.isBlank(url)){
			return "";
		}
		
		// 调用privateDownloadUrl方法生成下载链接,第二个参数可以设置Token的过期时间
		String downloadRUL = auth.privateDownloadUrl(url);
		System.out.println(downloadRUL);
		return downloadRUL;
	}
	public static String getPublicDownloadUrl(String url) {
		if(StringUtils.isBlank(url)){
			return "";
		}
		
		//兼容老的地址
		url = url.indexOf(old_domain)==0 ? url.replaceAll(old_domain, domain):url;
		System.out.println(url);
		return url;
	}

	public static void fops() throws QiniuException {
		// 设置账号的AK,SK
		/*
		 * String ACCESS_KEY = "Access_Key"; String SECRET_KEY = "Secret_Key";
		 * Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		 * 
		 * Zone z = Zone.zone0(); Configuration c = new Configuration(z);
		 */

		// 新建一个OperationManager对象
		OperationManager operater = new OperationManager(auth, c);
		// 设置要转码的空间和key，并且这个key在你空间中存在
		// String bucket = "Bucket_Name";
		String key = "Bucket_key";
		// 设置转码操作参数
		String fops = "avthumb/mp4/s/640x360/vb/1.25m";
		// 设置转码的队列
		String pipeline = "yourpipelinename";
		// 可以对转码后的文件进行使用saveas参数自定义命名，当然也可以不指定文件会默认命名并保存在当前空间。
		String urlbase64 = UrlSafeBase64
				.encodeToString("目标Bucket_Name:自定义文件key");
		String pfops = fops + "|saveas/" + urlbase64;
		// 设置pipeline参数
		StringMap params = new StringMap().putWhen("force", 1, true)
				.putNotEmpty("pipeline", pipeline);
		try {
			String persistid = operater.pfop(bucket, key, pfops, params);
			// 打印返回的persistid
			System.out.println(persistid);
		} catch (QiniuException e) {
			// 捕获异常信息
			Response r = e.response;
			// 请求失败时简单状态信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
	}

	// 获取存储空间的文件列表
	public static void listFile() {
		// 设置需要操作的账号的AK和SK
		/*
		 * String ACCESS_KEY = "Access_Key"; String SECRET_KEY = "Secret_Key";
		 * Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		 * 
		 * Zone z = Zone.zone0(); Configuration c = new Configuration(z);
		 */

		// 实例化一个BucketManager对象
		BucketManager bucketManager = new BucketManager(auth, c);

		// 要列举文件的空间名
		// String bucket = "yourbucket";

		try {
			// 调用listFiles方法列举指定空间的指定文件
			// 参数一：bucket 空间名
			// 参数二：prefix 文件名前缀
			// 参数三：marker 上一次获取文件列表时返回的 marker
			// 参数四：limit 每次迭代的长度限制，最大1000，推荐值 100
			// 参数五：delimiter 指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
			FileListing fileListing = bucketManager.listFiles(bucket, null,
					null, 10, null);
			FileInfo[] items = fileListing.items;
			for (FileInfo fileInfo : items) {
				System.out.println(fileInfo.key);
			}
		} catch (QiniuException e) {
			// 捕获异常信息
			Response r = e.response;
			System.out.println(r.toString());
		}
	}

	// 移动文件
	public static void move() {
		// 设置需要操作的账号的AK和SK
		/*
		 * String ACCESS_KEY = "Access_Key"; String SECRET_KEY = "Secret_Key";
		 * Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		 * 
		 * Zone z = Zone.zone0(); Configuration c = new Configuration(z);
		 */

		// 实例化一个BucketManager对象
		BucketManager bucketManager = new BucketManager(auth, c);
		// 要测试的空间和key，并且这个key在你空间中存在
		String bucket = "Bucket_Name";
		String key = "Bucket_key";
		// 将文件从文件key移动到文件key2, 可以在不同bucket移动，同空间移动相当于重命名
		String key2 = "yourjavakey";
		try {
			// 调用move方法移动文件
			bucketManager.move(bucket, key, bucket, key2);
		} catch (QiniuException e) {
			// 捕获异常信息
			Response r = e.response;
			System.out.println(r.toString());

		}
	}

	public static void pfop() throws QiniuException {
		// 新建一个OperationManager对象
		OperationManager operater = new OperationManager(auth, c);
		// 设置要转码的空间和key，并且这个key在你空间中存在
		String bucket = "Bucket_Name";
		String key = "Bucket_key";
		// 设置转码操作参数
		String fops = "vframe/jpg/offset/1/w/480/h/360/rotate/90";
		// 设置转码的队列
		String pipeline = "yourpipelinename";
		// 可以对转码后的文件进行使用saveas参数自定义命名，当然也可以不指定文件会默认命名并保存在当前空间。
		String urlbase64 = UrlSafeBase64
				.encodeToString("目标Bucket_Name:自定义文件key");
		String pfops = fops + "|saveas/" + urlbase64;
		// 设置pipeline参数
		StringMap params = new StringMap().putWhen("force", 1, true)
				.putNotEmpty("pipeline", pipeline);
		try {
			String persistid = operater.pfop(bucket, key, pfops, params);
			// 打印返回的persistid
			System.out.println(persistid);
		} catch (QiniuException e) {
			// 捕获异常信息
			Response r = e.response;
			// 请求失败时简单状态信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
	}

	// 获取文件信息
	public static FileInfo stat(String key) {
		// 实例化一个BucketManager对象
		BucketManager bucketManager = new BucketManager(auth, c);
		try {
			// 调用stat()方法获取文件的信息
			FileInfo info = bucketManager.stat(bucket, key);
			System.out.println(info.hash);
			System.out.println(info.key);
			return info;
		} catch (QiniuException e) {
			// 捕获异常信息
			Response r = e.response;
			System.out.println(r.toString());

		}
		return null;
	}

	// 设置callbackUrl以及callbackBody,七牛将文件名和文件大小回调给业务服务器
	public static String getUpToken(String key) {
		return auth.uploadToken(bucket, key, 3600, null);
		/*
		 * return auth.uploadToken(bucket,key,3600,new StringMap()
		 * .put("callbackUrl","http://your.domain.com/callback")
		 * .put("callbackBody", "filename=$(fname)&filesize=$(fsize)"));
		 */}

	public static String upload(String filePath, String key){
		try {
			// 创建上传对象
			UploadManager uploadManager = new UploadManager(c);
			// 调用put方法上传
			Response res = uploadManager.put(filePath, key, getUpToken(key));
			// 打印返回的信息
			System.out.println(res.bodyString());
			return domain+key;
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
		return null;
	}
	public static String uploadFile(File file, String key){
		try {
			// 创建上传对象
			UploadManager uploadManager = new UploadManager(c);
			// 调用put方法上传
			Response res = uploadManager.put(file, key, getUpToken(key));
			// 打印返回的信息
			System.out.println(res.bodyString());
			return domain+key;
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
		return null;
	}
	public static String uploadFileBytes(byte[] data, String key){
		try {
			// 创建上传对象
			UploadManager uploadManager = new UploadManager(c);
			// 调用put方法上传
			Response res = uploadManager.put(data, key, getUpToken(key));
			// 打印返回的信息
			System.out.println(res.bodyString());
			return domain+key;
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
		return null;
	}
	public static String uploadMultipartFile(MultipartFile file, String key){
		try {
			return uploadFileBytes(file.getBytes(), key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*// 获得下载地址
	public String getDownloadFileUrl(String key) {
		FileInfo info = stat(key);
		if (info != null) {
			return domain + info.key;
		}
		return null;
	}*/

	public static String uploadNet(String netUrl, String savePath) {
		// 实例化一个BucketManager对象
		BucketManager bucketManager = new BucketManager(auth, c);

		try {
			// 调用fetch方法抓取文件
			bucketManager.fetch(netUrl, bucket, savePath);
			return domain+savePath;
		} catch (QiniuException e) {
			// 捕获异常信息
			Response r = e.response;
			System.out.println(r.toString());
		}
		return null;
	}

	/*public static String getSavePathName(String type,String fileName){
		String path = "";
		switch (type) {
			case "1"://用户
				path = Config.getUploadUserpath()+(StringUtils.isBlank(fileName) ? IdGen.uuid()+".jpg":fileName);
				break;
			case "2"://帖子视频
				path = Config.getUploadArticlepath()+(StringUtils.isBlank(fileName) ? IdGen.uuid()+"."+Constant.VIDEO_EXTENTION:fileName);
				break;
			case "4"://媒体音乐
				path = Config.getUploadMusicpath()+(StringUtils.isBlank(fileName) ? IdGen.uuid()+"."+Constant.MADIA_EXTENTION:fileName);
				break;
			case "5"://媒体视频
				path = Config.getUploadVideopath()+(StringUtils.isBlank(fileName) ? IdGen.uuid()+"."+Constant.VIDEO_EXTENTION:fileName);
				break;
			default:
				break;
		}
		return path;
	}*/
	public static void main(String args[]) {
		// "D:\\usr\\local\\police\\upload\\user\\05dda217-f112-4ee9-8b39-054cedecb594.png";
		// String key = "resources/img/1112.png";
		/*String filePath = "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0";
		String savePath = "resources/img/1112.png";
		try {
			// upload(filePath, key);
			uploadNet(filePath, savePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		String url = "http://oozuk6mti.bkt.clouddn.com/article/56b60c3a2cc04ed189b23217552a518b.mp4";
		System.out.println(getPublicDownloadUrl(url));
	}
}