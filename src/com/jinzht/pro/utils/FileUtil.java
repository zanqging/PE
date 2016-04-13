package com.jinzht.pro.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import com.jinzht.pro.R;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

public class FileUtil {

	public static boolean makeDirs(String filePath) {
		String folderName = getFolderName(filePath);
		if (StringUtils.isEmpty(folderName)) {
			return false;
		}

		File folder = new File(folderName);
		return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
	}
	public static void  createFolder(String file_folder){
		File mydir= new File(file_folder);
		if(!mydir.exists()){
			mydir.mkdir();
			if(!mydir.mkdir()){
				mydir.mkdir();
				Log.e("sss", "Cannot create the dir!");
			}
		}
	}
	public static String getFolderName(String filePath) {

		if (StringUtils.isEmpty(filePath)) {
			return filePath;
		}

		int filePosi = filePath.lastIndexOf(File.separator);
		return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
	}

	public static boolean makeFolders(String filePath) {
		return makeDirs(filePath);
	}
	public static boolean isFileExist(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return false;
		}

		File file = new File(filePath);
		return (file.exists() && file.isFile());
	}
	public static boolean isFolderExist(String directoryPath) {
		if (StringUtils.isBlank(directoryPath)) {
			return false;
		}

		File dire = new File(directoryPath);
		return (dire.exists() && dire.isDirectory());
	}
	public static boolean deleteFile(String path) {
		if (StringUtils.isBlank(path)) {
			return true;
		}

		File file = new File(path);
		if (!file.exists()) {
			return true;
		}
		if (file.isFile()) {
			return file.delete();
		}
		if (!file.isDirectory()) {
			return false;
		}
		for (File f : file.listFiles()) {
			if (f.isFile()) {
				f.delete();
			} else if (f.isDirectory()) {
				deleteFile(f.getAbsolutePath());
			}
		}
		return file.delete();
	}
	public static long getFileSize(String path) {
		if (StringUtils.isBlank(path)) {
			return -1;
		}

		File file = new File(path);
		return (file.exists() && file.isFile() ? file.length() : -1);
	}
	public static final String[] VIDEO_EXTENSIONS = { "264", "3g2", "3gp",
			"3gp2", "3gpp", "3gpp2", "3mm", "3p2", "60d", "aep", "ajp", "amv",
			"amx", "arf", "asf", "asx", "avb", "avd", "avi", "avs", "avs",
			"axm", "bdm", "bdmv", "bik", "bin", "bix", "bmk", "box", "bs4",
			"bsf", "byu", "camre", "clpi", "cpi", "cvc", "d2v", "d3v", "dat",
			"dav", "dce", "dck", "ddat", "dif", "dir", "divx", "dlx", "dmb",
			"dmsm", "dmss", "dnc", "dpg", "dream", "dsy", "dv", "dv-avi",
			"dv4", "dvdmedia", "dvr-ms", "dvx", "dxr", "dzm", "dzp", "dzt",
			"evo", "eye", "f4p", "f4v", "fbr", "fbr", "fbz", "fcp", "flc",
			"flh", "fli", "flv", "flx", "gl", "grasp", "gts", "gvi", "gvp",
			"hdmov", "hkm", "ifo", "imovi", "imovi", "iva", "ivf", "ivr",
			"ivs", "izz", "izzy", "jts", "lsf", "lsx", "m15", "m1pg", "m1v",
			"m21", "m21", "m2a", "m2p", "m2t", "m2ts", "m2v", "m4e", "m4u",
			"m4v", "m75", "meta", "mgv", "mj2", "mjp", "mjpg", "mkv", "mmv",
			"mnv", "mod", "modd", "moff", "moi", "moov", "mov", "movie",
			"mp21", "mp21", "mp2v", "mp4", "mp4v", "mpe", "mpeg", "mpeg4",
			"mpf", "mpg", "mpg2", "mpgin", "mpl", "mpls", "mpv", "mpv2", "mqv",
			"msdvd", "msh", "mswmm", "mts", "mtv", "mvb", "mvc", "mvd", "mve",
			"mvp", "mxf", "mys", "ncor", "nsv", "nvc", "ogm", "ogv", "ogx",
			"osp", "par", "pds", "pgi", "piv", "playlist", "pmf", "prel",
			"pro", "prproj", "psh", "pva", "pvr", "pxv", "qt", "qtch", "qtl",
			"qtm", "qtz", "rcproject", "rdb", "rec", "rm", "rmd", "rmp", "rms",
			"rmvb", "roq", "rp", "rts", "rts", "rum", "rv", "sbk", "sbt",
			"scm", "scm", "scn", "sec", "seq", "sfvidcap", "smil", "smk",
			"sml", "smv", "spl", "ssm", "str", "stx", "svi", "swf", "swi",
			"swt", "tda3mt", "tivo", "tix", "tod", "tp", "tp0", "tpd", "tpr",
			"trp", "ts", "tvs", "vc1", "vcr", "vcv", "vdo", "vdr", "veg",
			"vem", "vf", "vfw", "vfz", "vgz", "vid", "viewlet", "viv", "vivo",
			"vlab", "vob", "vp3", "vp6", "vp7", "vpj", "vro", "vsp", "w32",
			"wcp", "webm", "wm", "wmd", "wmmp", "wmv", "wmx", "wp3", "wpl",
			"wtv", "wvx", "xfl", "xvid", "yuv", "zm1", "zm2", "zm3", "zmv" };

	private static final HashSet<String> vHashSet = new HashSet<String>(
			Arrays.asList(VIDEO_EXTENSIONS));

	/**
	 * 检测是否是视频文件
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isVideo(String path) {
		path=getFileExtension(path);
		return vHashSet.contains(path);
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param path
	 * @return
	 */
	public static String getFileExtension(String path) {
		if (null != path) {
			// 后缀点 的位置
			int dex = path.lastIndexOf(".");
			// 截取后缀名
			return path.substring(dex + 1);
		}
		return null;
	}

	public static void deleteAllFiles(File root) {
		File files[] = root.listFiles();
		if (files != null)
			for (File f : files) {
				if (f.isDirectory()) { // 判断是否为文件夹
					deleteAllFiles(f);
					try {
						f.delete();
					} catch (Exception e) {
					}
				} else {
					if (f.exists()) { // 判断是否存在
						deleteAllFiles(f);
						try {
							f.delete();
						} catch (Exception e) {
						}
					}
				}
			}
	}

	public static void copyFile(Context activity, String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (!oldfile.exists()) { //文件不存在时
				InputStream inStream = new FileInputStream(oldPath); //读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ( (byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; //字节数 文件大小
					Log.e("bytesum",bytesum+"");
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
			SuperToastUtils.showSuperToast(activity,1,activity.getResources().getString(R.string.save_success));
		}
		catch (Exception e) {
			SuperToastUtils.showSuperToast(activity,1,activity.getResources().getString(R.string.save_fail));
			e.printStackTrace();

		}

	}
	public static String getCharacterAndNumber() {
		String rel="";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date curDate = new Date(System.currentTimeMillis());
		rel = formatter.format(curDate);
		return rel;
	}

	public static void saveFile(Context context,Bitmap bm,String subForder) throws IOException {
		File myCaptureFile = new File(subForder);
		if (!myCaptureFile.exists()) {
			myCaptureFile.createNewFile();
		}
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
		bos.flush();
		bos.close();
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri = Uri.fromFile(myCaptureFile);
		intent.setData(uri);
		context.sendBroadcast(intent);//这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！，记得要传你更新的file哦
	}
}
