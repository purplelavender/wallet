package share.exchange.framework.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @ClassName:      FileUtil
 * @Description:    文件操作工具包
 * @Author:         ZL
 * @CreateDate:     2019/08/02 16:35
 */
public class FileUtil {

    public final static String TAG = "FileUtil";

    private FileUtil() {
    }

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    public static File createTmpFile(Context context) throws IOException {
        File dir = null;
        if (TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
            dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            if (!dir.exists()) {
                dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera");
                if (!dir.exists()) {
                    dir = getCacheDirectory(context, true);
                }
            }
        } else {
            dir = getCacheDirectory(context, true);
        }
        return File.createTempFile(JPEG_FILE_PREFIX, JPEG_FILE_SUFFIX, dir);
    }

    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    /**
     * Returns application cache directory. Cache directory will be created on
     * SD card <i>("/Android/data/[app_package_name]/cache")</i> if card is
     * mounted and app has appropriate permission. Else - Android defines cache
     * directory on device's file system.
     *
     * @param context Application context
     * @return Cache {@link File directory}.<br />
     * <b>NOTE:</b> Can be null in some unpredictable cases (if SD card
     * is unmounted and {@link Context#getCacheDir()
     * Context.getCacheDir()} returns null).
     */
    public static File getCacheDirectory(Context context) {
        return getCacheDirectory(context, true);
    }

    /**
     * Returns application cache directory. Cache directory will be created on
     * SD card <i>("/Android/data/[app_package_name]/cache")</i> (if card is
     * mounted and app has appropriate permission) or on device's file system
     * depending incoming parameters.
     *
     * @param context        Application context
     * @param preferExternal Whether prefer external location for cache
     * @return Cache {@link File directory}.<br />
     * <b>NOTE:</b> Can be null in some unpredictable cases (if SD card
     * is unmounted and {@link Context#getCacheDir()
     * Context.getCacheDir()} returns null).
     */
    public static File getCacheDirectory(Context context, boolean preferExternal) {
        File appCacheDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) { // (sh)it happens (Issue #660)
            externalStorageState = "";
        } catch (IncompatibleClassChangeError e) { // (sh)it happens too (Issue
            // #989)
            externalStorageState = "";
        }
        if (preferExternal && Environment.MEDIA_MOUNTED.equals(externalStorageState) && hasExternalStoragePermission(context)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            @SuppressLint("SdCardPath")
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }

    public static File getFileDirectory(Context context, boolean preferExternal) {
        File appFileDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) { // (sh)it happens (Issue #660)
            externalStorageState = "";
        } catch (IncompatibleClassChangeError e) { // (sh)it happens too (Issue
            // #989)
            externalStorageState = "";
        }
        if (preferExternal && Environment.MEDIA_MOUNTED.equals(externalStorageState) && hasExternalStoragePermission(context)) {
            appFileDir = getExternalFileDir(context);
        }
        if (appFileDir == null) {
            appFileDir = context.getFilesDir();
        }
        if (appFileDir == null) {
            @SuppressLint("SdCardPath")
            String fileDirPath = "/data/data/" + context.getPackageName() + "/files/";
            appFileDir = new File(fileDirPath);
        }
        return appFileDir;
    }

    /**
     * Returns individual application cache directory (for only image caching
     * from ImageLoader). Cache directory will be created on SD card
     * <i>("/Android/data/[app_package_name]/cache/uil-images")</i> if card is
     * mounted and app has appropriate permission. Else - Android defines cache
     * directory on device's file system.
     *
     * @param context  Application context
     * @param cacheDir Cache directory path (e.g.: "AppCacheDir",
     *                 "AppDir/cache/images")
     * @return Cache {@link File directory}
     */
    public static File getIndividualCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = getCacheDirectory(context);
        File individualCacheDir = new File(appCacheDir, cacheDir);
        if (!individualCacheDir.exists()) {
            if (!individualCacheDir.mkdir()) {
                individualCacheDir = appCacheDir;
            }
        }
        return individualCacheDir;
    }

    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
            }
        }
        return appCacheDir;
    }

    private static File getExternalFileDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appFileDir = new File(new File(dataDir, context.getPackageName()), "files");
        if (!appFileDir.exists()) {
            if (!appFileDir.mkdirs()) {
                return null;
            }
            try {
                new File(appFileDir, ".nomedia").createNewFile();
            } catch (IOException e) {
            }
        }
        return appFileDir;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 获取文件的MD5校验码
     *
     * @param filePath 文件路径
     * @return 文件的MD5校验码
     */
    public static String getFileMD5ToString(final String filePath) {
        File file = TextUtils.isEmpty(filePath) ? null : new File(filePath);
        return getFileMD5ToString(file);
    }

    /**
     * 获取文件的MD5校验码
     *
     * @param filePath 文件路径
     * @return 文件的MD5校验码
     */
    public static byte[] getFileMD5(final String filePath) {
        File file = TextUtils.isEmpty(filePath) ? null : new File(filePath);
        return getFileMD5(file);
    }

    /**
     * 获取文件的MD5校验码
     *
     * @param file 文件
     * @return 文件的MD5校验码
     */
    public static String getFileMD5ToString(final File file) {
        return StringUtil.bytes2HexString(getFileMD5(file));
    }

    /**
     * 获取文件的MD5校验码
     *
     * @param file 文件
     * @return 文件的MD5校验码
     */
    public static byte[] getFileMD5(final File file) {
        if (file == null) return null;
        DigestInputStream dis = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            dis = new DigestInputStream(fis, md);
            byte[] buffer = new byte[1024 * 256];
            while (true) {
                if (!(dis.read(buffer) > 0)) break;
            }
            md = dis.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(dis);
        }
        return null;
    }

    /**
     * 获取全路径中的文件拓展名
     *
     * @param filePath 文件路径
     * @return 文件拓展名
     */
    public static String getFileExtension(final String filePath) {
        if (TextUtils.isEmpty(filePath)) return filePath;
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep >= lastPoi) return "";
        return filePath.substring(lastPoi + 1);
    }

    /**
     * 获取全路径中的文件拓展名
     *
     * @param file 文件
     * @return 文件拓展名
     */
    public static String getFileExtension(final File file) {
        if (file == null) return null;
        return getFileExtension(file.getPath());
    }

    /**
     * 根据文件绝对路径获取文件名
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        if (StringUtil.isEmpty(filePath))
            return "";
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    }

    /**
     * 获取全路径中的不带拓展名的文件名
     *
     * @param file 文件
     * @return 不带拓展名的文件名
     */
    public static String getFileNameNoExtension(final File file) {
        if (file == null) return null;
        return getFileNameNoExtension(file.getPath());
    }

    /**
     * 获取全路径中的不带拓展名的文件名
     *
     * @param filePath 文件路径
     * @return 不带拓展名的文件名
     */
    public static String getFileNameNoExtension(final String filePath) {
        if (TextUtils.isEmpty(filePath)) return filePath;
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastSep == -1) {
            return (lastPoi == -1 ? filePath : filePath.substring(0, lastPoi));
        }
        if (lastPoi == -1 || lastSep > lastPoi) {
            return filePath.substring(lastSep + 1);
        }
        return filePath.substring(lastSep + 1, lastPoi);
    }

    /**
     * 判断SD卡上的文件夹是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean isFileExist(String fileName) {
        if (StringUtil.isEmpty(fileName)) {
            return false;
        }
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * 写文本文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
     *
     * @param context
     * @param fileName
     * @param content
     */
    public static void write(Context context, String fileName, String content) {
        if (content == null)
            content = "";
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 读取文本文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String read(Context context, String fileName) {
        try {
            FileInputStream in = context.openFileInput(fileName);
            return readInStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param inStream
     * @return
     */
    private static String readInStream(FileInputStream inStream) {
        ByteArrayOutputStream outStream = null;
        try {
            outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, length);
            }
            return outStream.toString();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
                if (inStream != null) {
                    inStream.close();
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static String readString(InputStream instream, String charset) {
        String result = null;
        BufferedReader reader = null;
        if (instream == null)
            return null;
        try {
            reader = new BufferedReader(new InputStreamReader(instream, charset));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                line += "\n";
                sb.append(line);
            }
            result = sb.toString();
        } catch (Exception e) {
        } finally {
            try {
                instream.close();
                reader.close();
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * 创建目录
     *
     * @param folderPath
     * @param fileName
     * @return
     */
    public static File createFile(String folderPath, String fileName) {
        File destDir = new File(folderPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return new File(folderPath, fileName);
    }

    /**
     * 将图片内容解析成字节数组
     *
     * @param
     * @param inStream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }

    public static byte[] readFile(String filePath) {
        byte[] buffer = null;
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(new File(filePath));
            buffer = readStream(inStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 获取文件大小
     *
     * @param filePath
     * @return
     */
    public static long getFileSize(String filePath) {
        long size = 0;

        File file = new File(filePath);
        if (file != null && file.exists()) {
            size = file.length();
        }
        return size;
    }

    /**
     * 获取文件大小
     *
     * @param size 字节
     * @return
     */
    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";
        java.text.DecimalFormat df = new java.text.DecimalFormat("##.##");
        float temp = (float) size / 1024;
        if (temp >= 1024) {
            return df.format(temp / 1024) + "M";
        } else {
            return df.format(temp) + "K";
        }
    }

    public static long getFileOrDirSize(File file) {
        if (!file.exists()) return 0;
        if (!file.isDirectory()) return file.length();

        long length = 0;
        File[] list = file.listFiles();
        if (list != null) { // 文件夹被删除时, 子文件正在被写入, 文件属性异常返回null.
            for (File item : list) {
                length += getFileOrDirSize(item);
            }
        }
        return length;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = null;
        try {
            files = dir.listFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != files && files.length > 0) {
            for (File file : files) {
                try {
                    if (file.isFile()) {
                        dirSize += file.length();
                    } else if (file.isDirectory()) {
                        dirSize += file.length();
                        dirSize += getDirSize(file); // 递归调用继续统计
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
        return dirSize;
    }

    /**
     * 获取目录文件个数
     *
     * @param dir
     * @return
     */
    public long getFileList(File dir) {
        long count = 0;
        File[] files = dir.listFiles();
        if (null != files) {
            count = files.length;
            for (File file : files) {
                if (file.isDirectory()) {
                    count = count + getFileList(file);// 递归
                    count--;
                }
            }
        }
        return count;
    }

    public static byte[] toBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            out.write(ch);
        }
        byte buffer[] = out.toByteArray();
        out.close();
        return buffer;
    }

    /**
     * 计算SD卡的剩余空间
     *
     * @return 返回-1，说明没有安装sd卡
     */
    @SuppressWarnings("deprecation")
    public static long getFreeDiskSpace() {
        String status = Environment.getExternalStorageState();
        long freeSpace = 0;
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                freeSpace = availableBlocks * blockSize;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return -1;
        }
        return (freeSpace);
    }

    /**
     * 计算SD卡的总空间
     *
     * @return 返回-1，说明没有安装sd卡
     */
    @SuppressWarnings("deprecation")
    public static long getTotalDiskSpace() {
        String status = Environment.getExternalStorageState();
        long totalSpace = 0;
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long totalBlocks = stat.getBlockCount();
                totalSpace = blockSize * totalBlocks;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return -1;
        }
        return (totalSpace);
    }

    /**
     * 检查是否安装SD卡
     *
     * @return
     */
    public static boolean checkSDCARDExists(Context mContext) {
        String sDCardStatus = Environment.getExternalStorageState();
        return sDCardStatus.equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable();
    }

    /**
     * 删除文件集合
     *
     * @param files
     */
    public synchronized static void deleteFiles(List<String> files) {
        if (null != files && files.size() > 0) {
            for (String f : files) {
                new File(f).delete();
            }
        }
    }

    /**
     * 删除目录(包括：目录里的所有子目录和文件)
     *
     * @param file
     */
    public synchronized static void deleteDirectory(File file) {
        if (null != file && file.exists()) { //指定文件是否存在
            if (file.isFile()) { //该路径名表示的文件是否是一个标准文件
                file.delete(); //删除该文件
            } else if (file.isDirectory()) { //该路径名表示的文件是否是一个目录（文件夹）
                File[] files = file.listFiles(); //列出当前文件夹下的所有文件
                for (File f : files) {
                    deleteDirectory(f); //递归删除
                }
                file.delete();
            }
            file.delete(); //删除文件夹（song,art,lyric）
        }
    }

    /**
     * 删除目录里的所有子目录和文件，不删除当前目录
     *
     * @param file
     */
    public synchronized static void clearDirectory(File file) {
        if (null != file && file.exists()) { //指定文件是否存在
            if (file.isFile() && !file.getName().equals(".nomedia")) { //该路径名表示的文件是否是一个标准文件
                file.delete(); //删除该文件
            } else if (file.isDirectory()) { //该路径名表示的文件是否是一个目录（文件夹）
                File[] files = file.listFiles(); //列出当前文件夹下的所有文件
                for (File f : files) {
                    clearDirectory(f); //递归删除
                }
                file.delete();
            }
        }
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    public synchronized static boolean deleteFile(String fileName) {
        boolean status;
        SecurityManager checker = new SecurityManager();
        if (!fileName.equals("")) {
            File path = Environment.getExternalStorageDirectory();
            File newPath = new File(path.toString() + fileName);
            checker.checkDelete(newPath.toString());
            if (newPath.isFile()) {
                try {
                    newPath.delete();
                    status = true;
                } catch (SecurityException se) {
                    Log.e(TAG, "SecurityManager deleteFile:" + fileName);
                    se.printStackTrace();
                    status = false;
                }
            } else
                status = false;
        } else
            status = false;
        return status;
    }

    /**
     * 目录copy
     *
     * @param fromFile
     * @param toFile
     * @return
     */
    public synchronized static int copyFolder(String fromFile, String toFile) {
        // 要复制的文件目录
        File[] currentFiles;
        File root = new File(fromFile);
        // 如同判断SD卡是否存在或者文件是否存在
        // 如果不存在则 return出去
        if (!root.exists()) {
            return -1;
        }
        // 如果存在则获取当前目录下的全部文件 填充数组
        currentFiles = root.listFiles();
        // 目标目录
        File targetDir = new File(toFile);
        // 创建目录
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        if (null != currentFiles) {
            // 遍历要复制该目录下的全部文件
            for (File currentFile : currentFiles) {
                if (currentFile.isDirectory())// 如果当前项为子目录 进行递归
                {
                    copyFolder(currentFile.getPath() + "/", toFile + currentFile.getName() + "/");
                } else {// 如果当前项为文件则进行文件拷贝
                    copyFile(currentFile.getPath(), toFile + currentFile.getName());
                }
            }
        }
        return 0;
    }

    /**
     * 移动文件
     *
     * @param fromFile
     * @param toFile
     * @return
     */
    public synchronized static boolean moveFile(String fromFile, String toFile) {
        int flag = copyFile(fromFile, toFile);
        if (flag >= 0) {
            delFile(fromFile);
        } else {
            return false;
        }
        return true;
    }

    /**
     * 文件拷贝
     *
     * @param fromFile
     * @param toFile
     * @return
     */
    public synchronized static int copyFile(String fromFile, String toFile) {
        try {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return 0;
        } catch (Exception ex) {
            return -1;
        }
    }

    /**
     * ReName any file
     *
     * @param oldName
     * @param newName
     */
    public synchronized static File renameFile(String oldName, String newName) {
        File dir = Environment.getExternalStorageDirectory();
        if (dir.exists()) {
            File from = new File(dir, oldName);
            File to = new File(dir, newName);
            if (from.exists())
                from.renameTo(to);
            return to;
        }
        return null;
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    public synchronized static boolean delFile(String fileName) {
        boolean status;
        if (fileName != null && !fileName.equals("")) {
            File newPath = new File(fileName);
            if (newPath.isFile()) {
                try {
                    newPath.delete();
                    status = true;
                } catch (SecurityException se) {
                    se.printStackTrace();
                    status = false;
                }
            } else
                status = false;
        } else
            status = false;
        return status;
    }

    /**
     * 创建一个图片文件
     *
     * @param prefix
     * @param suffix
     * @param directory
     * @return
     * @throws IOException
     */
    @SuppressLint("SimpleDateFormat")
    public static File createImageFile(String prefix, String suffix, File directory) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date());
        String imageFileName = prefix + timeStamp + suffix;
        String path = directory + imageFileName;
        if (!directory.getPath().endsWith(File.separator)) {
            path = directory + File.separator + imageFileName;
        }
        File imageF = new File(path);
        imageF.createNewFile();
        return imageF;
    }

    /**
     * @param mContext
     * @return
     * @description 获取根目录
     * @modifier
     * @modifier_date
     */
    public static String getROOTDIR(Context mContext) {
        String rootDir = "";
        try {
            StorageManager sm = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
            String[] paths = (String[]) sm.getClass().getMethod("getVolumePaths", new Class[0]).invoke(sm, new Object[]{});
            for (String path : paths) {
                File dirFile = new File(path);
                if (dirFile.canWrite()) {
                    rootDir = dirFile.getPath();
                    break;
                }
            }
        } catch (Exception e1) {
        }
        return rootDir;
    }

    /**
     * 清除dir下面的、LastModifiedTime早于deleteFromTime的文件
     *
     * @param dir
     * @param deleteFromTime
     * @return
     */
    public static long clearCacheFolder(File dir, long deleteFromTime) {
        long deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, deleteFromTime);
                    }
                    if (child.lastModified() < deleteFromTime) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

    /**
     * 清除dir下面的、LastModifiedTime早于deleteFromTime的文件
     *
     * @param dir
     * @param deleteFromTime
     * @return
     */
    public synchronized static long clearCacheFolder(File dir, long maxCacheSize, long deleteFromTime) {
        long deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    long dirSize = FileUtil.getDirSize(dir);
                    if (dirSize < maxCacheSize) {
                        break;
                    }
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, maxCacheSize, deleteFromTime);
                    }
                    if (child.lastModified() < deleteFromTime) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

    public static String getFileParentPath(String filePath) {
        return filePath.substring(0, filePath.lastIndexOf(File.separator));
    }

    /**
     * 关闭IO
     *
     * @param closeables closeables
     */
    public static void closeIO(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 安静关闭IO
     *
     * @param closeables closeables
     */
    public static void closeIOQuietly(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * 获取全路径
     *
     * @param fname 目录或文件的相对路径
     * @return 全路径
     */
    public static String getFullPath(Context context, String fname) {
        if (null == fname) {
            return null;
        }
        // 统一folder为 '/somepath'的格式
        if (fname.startsWith(File.separator)) {
            fname = fname.substring(1, fname.length());
        }
        String fatherDirPath;
        if (FileUtil.checkSDCARDExists(context)) {
            try {
                fatherDirPath = context.getExternalFilesDir("").getParentFile().getPath() + File.separator;
            } catch (Exception e) {
                e.printStackTrace();
                fatherDirPath = Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName() + File.separator;
            }
        } else {
            try {
                fatherDirPath = context.getCacheDir().getParentFile().getPath() + File.separator;
            } catch (Exception e) {
                e.printStackTrace();
                fatherDirPath = Environment.getDataDirectory() + "/data/" + context.getPackageName() + File.separator;
            }
        }
        File file = new File(fatherDirPath + fname);
        if (!file.exists()) {
            file.mkdirs();
        }
        return fatherDirPath + fname;
    }
}
