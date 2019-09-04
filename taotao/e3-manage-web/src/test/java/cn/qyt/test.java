package cn.qyt;

import org.apache.commons.lang3.time.FastDateFormat;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qyt.pojo.TbItem;
import com.qyt.utils.FastDFSClient;

public class test {
	
	@Test
	public void testUpload() throws Exception {
		//创建一个配置文件。文件名任意。内容就是tracker服务器的地址。
		//使用全局对象加载配置文件。
		ClientGlobal.init("F:\\maven-newProject\\e3-manage-web\\src\\main\\resources\\conf\\client.conf");
		//创建一个TrackerClient对象
		TrackerClient trackerClient = new TrackerClient();
		//通过TrackClient获得一个TrackerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		//创建一个StrorageServer的引用，可以是null
		StorageServer storageServer = null;
		//创建一个StorageClient，参数需要TrackerServer和StrorageServer
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		//使用StorageClient上传文件。
		String[] strings = storageClient.upload_file("F:\\tx4.jpg", "jpg", null);
		for (String string : strings) {
			System.out.println(string);
		}
	}
	
	
	@Test
	public  void  testFastDFS() throws Exception {
		FastDFSClient client=new  FastDFSClient("F:\\maven-newProject\\e3-manage-web\\src\\main\\resources\\conf\\client.conf");
		
		
		
		String  string=client.uploadFile("F://timg.jpg");
		System.out.println(string);
		
	}
	
	
}
