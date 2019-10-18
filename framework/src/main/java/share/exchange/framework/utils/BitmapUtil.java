package share.exchange.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片处理工具类
 */
public class BitmapUtil {


    private BitmapUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 将指定路径的图片等比例缩小为Bitmap
     *
     * @param filePath
     * @param maxWidth
     * @param maxHeight
     * @param bitmapConfig
     * @return
     */
    public static Bitmap getScaledBitmap(String filePath, float maxWidth, float maxHeight, Bitmap.Config bitmapConfig) {
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

        //by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
        //you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
        if (bmp == null) {
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(filePath);
                BitmapFactory.decodeStream(inputStream, null, options);
                inputStream.close();
            } catch (FileNotFoundException exception) {
                exception.printStackTrace();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        if (actualHeight == -1 || actualWidth == -1) {
            try {
                ExifInterface exifInterface = new ExifInterface(filePath);
                actualHeight = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, ExifInterface.ORIENTATION_NORMAL);//获取图片的高度
                actualWidth = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.ORIENTATION_NORMAL);//获取图片的宽度
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (actualWidth <= 0 || actualHeight <= 0) {
            Bitmap bitmap2 = BitmapFactory.decodeFile(filePath);
            if (bitmap2 != null) {
                actualWidth = bitmap2.getWidth();
                actualHeight = bitmap2.getHeight();
            } else {
                return null;
            }
        }

        float imgRatio = (float) actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        //width and height values are set maintaining the aspect ratio of the image
        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;
            }
        }

        //setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

        //inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

        //this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            // load the bitmap getTempFile its path
            bmp = BitmapFactory.decodeFile(filePath, options);
            if (bmp == null) {
                InputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(filePath);
                    BitmapFactory.decodeStream(inputStream, null, options);
                    inputStream.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        if (actualHeight <= 0 || actualWidth <= 0) {
            return null;
        }

        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, bitmapConfig);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, 0, 0);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));

        //check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(),
                    matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scaledBitmap;
    }

    /**
     * 将图片的四角圆弧化
     *
     * @param bitmap      原图
     * @param roundPixels 弧度
     * @param half        （上/下/左/右）半部分圆角
     * @return
     */
    public static Bitmap getRoundCornerImage(Bitmap bitmap, int roundPixels, HalfType half) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap roundConcerImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 创建一个和原始图片一样大小的位图
        Canvas canvas = new Canvas(roundConcerImage);// 创建位图画布
        Paint paint = new Paint();// 创建画笔

        Rect rect = new Rect(0, 0, width, height);// 创建一个和原始图片一样大小的矩形
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);// 抗锯齿

        canvas.drawRoundRect(rectF, roundPixels, roundPixels, paint);// 画一个基于前面创建的矩形大小的圆角矩形
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置相交模式
        canvas.drawBitmap(bitmap, null, rect, paint);// 把图片画到矩形去

        switch (half) {
            case LEFT:
                return Bitmap.createBitmap(roundConcerImage, 0, 0, width - roundPixels, height);
            case RIGHT:
                return Bitmap.createBitmap(roundConcerImage, width - roundPixels, 0, width - roundPixels, height);
            case TOP:
                // 上半部分圆角化 “-
                // roundPixels”实际上为了保证底部没有圆角，采用截掉一部分的方式，就是截掉和弧度一样大小的长度
                return Bitmap.createBitmap(roundConcerImage, 0, 0, width, height - roundPixels);
            case BOTTOM:
                return Bitmap.createBitmap(roundConcerImage, 0, height - roundPixels, width, height - roundPixels);
            case ALL:
                return roundConcerImage;
            default:
                return roundConcerImage;
        }
    }

    public enum HalfType {
        LEFT, // 左上角 + 左下角
        RIGHT, // 右上角 + 右下角
        TOP, // 左上角 + 右上角
        BOTTOM, // 左下角 + 右下角
        ALL // 四角
    }

    /**
     * 计算inSampleSize
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    /**
     * 将bitmap旋转angle值的角度。
     *
     * @param bm
     * @param angle
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bm, int angle) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(angle);
        return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);// 将图片旋转90度
    }

    /**
     * 获取屏幕截图
     *
     * @param activity
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Bitmap takeScreenShot(Activity activity) {

        // View是你须要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状况栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();

        // 去掉题目栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    /**
     * 获取bitmap
     *
     * @param filePath
     * @param opts
     * @return
     */
    public static Bitmap getBitmapByPath(String filePath, BitmapFactory.Options opts) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                fis = new FileInputStream(file);
                bitmap = BitmapFactory.decodeStream(fis, null, opts);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
            }
        }
        return bitmap;
    }

    /**
     * 获取bitmap
     *
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapByPath(String filePath) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        return getBitmapByPath(filePath, opt);
    }


    /**
     * 获取bitmap
     *
     * @param file
     * @return
     */
    public static Bitmap getBitmapByFile(File file) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
            }
        }
        return bitmap;
    }

    /**
     * 按指定宽高放大缩小Bitmap
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        Bitmap newbmp = null;
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Matrix matrix = new Matrix();
            float scaleWidht = ((float) w / width);
            float scaleHeight = ((float) h / height);
            matrix.postScale(scaleWidht, scaleHeight);
            newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        }
        return newbmp;
    }

    /**
     * 将URI地址转化为Bitmap
     *
     * @param context
     * @param uri
     * @return
     */
    public static Bitmap decodeUriAsBitmap(Activity context, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * Bitmap转化为二进制byte数组
     *
     * @param bm
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 不加载图片的前提下获得图片的宽高
     *
     * @param imagePath
     * @return
     */
    public static int[] getImageWidthAndHeight(String imagePath) {
        int[] results = new int[2];
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            results[0] = -1;
            results[1] = -1;
            return results;
        }
        Bitmap bitmap = createBitmapByFile(imageFile);
        results[0] = bitmap.getWidth();
        results[1] = bitmap.getHeight();
        return results;
    }

    /**
     * 获取指定图片宽度高度 单位:像素
     *
     * @param context
     * @param resourcesID 图片资源
     * @return
     */
    public static int[] getImageWidthAndHeight(Context context, int resourcesID) {
        int[] results = new int[2];
        Bitmap bitmap = createBitmapByRes(context, resourcesID);
        results[0] = bitmap.getWidth();
        results[1] = bitmap.getHeight();
        return results;
    }

    /**
     * 写图片文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
     *
     * @throws java.io.IOException
     */
    public static void saveImage(Context context, String fileName, Bitmap bitmap) throws IOException {
        saveImage(context, fileName, bitmap, 100);
    }

    public static void saveImage(Context context, String fileName, Bitmap bitmap, int quality) throws IOException {
        if (bitmap == null)
            return;
        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        byte[] bytes = stream.toByteArray();
        fos.write(bytes);
        fos.close();
    }

    /**
     * 写图片文件到新路径
     *
     * @param newPath 目标路径
     * @param bitmap
     * @param quality 可选择压缩质量（画质 1-100 ）0表示最低画质压缩，100最高画质压缩
     * @throws java.io.IOException
     */
    public static void saveImageToNewPath(String newPath, Bitmap bitmap, int quality) {
        if (bitmap == null)
            return;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(newPath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
            byte[] bytes = stream.toByteArray();
            fos.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
                bitmap = null;
                System.gc();
            }
        }
    }

    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 获取视频的缩略图
     *
     * @param videoPath 视频路径
     * @param width     图片宽度
     * @param height    图片高度
     * @param kind      eg:MediaStore.Video.Thumbnails.MICRO_KIND   MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        // 获取视频的缩略图
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        //extractThumbnail 方法二次处理,以指定的大小提取居中的图片,获取最终我们想要的图片
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * 检查图片是否损坏
     *
     * @param path
     * @return
     */
    public synchronized static boolean checkImageValid(String path) {
        boolean result = false;
        FileInputStream inputStream = null;
        try {
            File file = new File(path);
            inputStream = new FileInputStream(file);
            byte[] buffer = new byte[2];
            String filecode = "";//文件类型代码
            String fileType = ""; //文件类型
            //通过读取出来的前两个字节来判断文件类型
            if (inputStream.read(buffer) != -1) {
                for (byte aBuffer : buffer) {
                    //获取每个字节与0xFF进行与运算来获取高位，这个读取出来的数据不是出现负数
                    //并转换成字符串
                    filecode += Integer.toString((aBuffer & 0xFF));
                }
                // 把字符串再转换成Integer进行类型判断
                switch (Integer.parseInt(filecode)) {
                    case 8273:
//                        fileType = "webp";
                        result = true;
                        break;
                    case 255216:
//                        fileType = "jpg";
                        result = true;
                        break;
                    case 7173:
//                        fileType = "gif";
                        result = true;
                        break;
                    case 6677:
//                        fileType = "bmp";
                        result = true;
                        break;
                    case 13780:
//                        fileType = "png";
                        result = true;
                        break;
                    default:
//                        fileType = "unknown";
                        Log.d("tt", "unknown type: " + filecode);
                        result = false;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            result = true;
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 计算出图片初次显示需要放大倍数(大长图用)
     */
    public static float getInitImageScale(int dw, int dh, Context context) {
        Point point = EnvironmentUtil.getScreenSize(context);
        int width = point.x;
        int height = point.y;
        float scale = 1.0f;
        //图片宽度大于屏幕，但高度小于屏幕，则缩小图片至填满屏幕宽
        if (dw > width && dh <= height) {
            scale = width * 1.0f / dw;
        }
        //图片宽度小于屏幕，但高度大于屏幕，则放大图片至填满屏幕宽
        if (dw <= width && dh > height) {
            scale = width * 1.0f / dw;
        }
        //图片高度和宽度都小于屏幕，则放大图片至填满屏幕宽
        if (dw < width && dh < height) {
            scale = width * 1.0f / dw;
        }
        //图片高度和宽度都大于屏幕，则缩小图片至填满屏幕宽
        if (dw > width && dh > height) {
            scale = width * 1.0f / dw;
        }
        return scale;
    }

    /**
     * 通过资源ID获取Bitmap
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap createBitmapByRes(Context context, int resId) {
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }

    /**
     * 通过二进制数组获取Bitmap
     *
     * @param b
     * @return
     */
    public static Bitmap createBitmapByByte(byte[] b) {
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }


    /**
     * 通过文件地址获取Bitmap
     *
     * @param path
     * @return
     */
    public static Bitmap createBitmapByPath(String path) {
        File file = new File(path);
        if (file.exists()) {
            return createBitmapByFile(file);
        } else {
            return null;
        }
    }

    /**
     * 通过文件获取Bitmap
     *
     * @param file
     * @return
     */
    public static Bitmap createBitmapByFile(File file) {
        Bitmap bitmap = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 根据流生成Bitmap
     *
     * @param context
     * @param inputStream
     * @return
     */
    public static Bitmap createBitmapByStream(Context context, InputStream inputStream) {
        return BitmapFactory.decodeStream(inputStream);
    }

}
