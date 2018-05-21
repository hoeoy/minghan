package com.thinkgem.jeesite.common.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.ks.utils.Config;

/**
 * 图片处理工具类：<br>
 * 功能：缩放图像、切割图像、图像类型转换、彩色转黑白、文字水印、图片水印等
 * 
 * @author Administrator
 */
public class ImageUtils {
	/**
	 * 几种常见的图片格式
	 */
	public static String IMAGE_TYPE_GIF = "gif";// 图形交换格式
	public static String IMAGE_TYPE_JPG = "jpg";// 联合照片专家组
	public static String IMAGE_TYPE_JPEG = "jpeg";// 联合照片专家组
	public static String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
	public static String IMAGE_TYPE_PNG = "png";// 可移植网络图形
	public static String IMAGE_TYPE_PSD = "psd";// Photoshop的专用格式Photoshop

	/**
	 * 程序入口：用于测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 给图片添加图片水印：
		// ImageUtils.pressImage(ImageUtils.class.getClassLoader().getResource("").getPath() + "/conf/main_logo.png","D://upload//article//201504//9//31_1_155859706.jpeg","D://upload//article//201504//9//31_1_155859706_sy.jpeg", 250, 100, 1f);
	}

	public static void pngPress(String srcImageFile, String result,int width,int height) throws Exception{
		try {  
            File f2 = new File(srcImageFile);  
             
            BufferedImage bi2 = ImageIO.read(f2);  
            int width2 = bi2.getWidth(), height2 = bi2.getHeight();
            if ((height2 < height) || (width2 < width)) {
				height = height2;
				width = width2;
			}
			// 计算比例
			if ((height2 >= height) || (width2 >= width)) {
				if (height2 / height > width2 / width) {
					double ratio = (new Integer(height)).doubleValue()
							/ height2;
					width = (int) (width2 * ratio);
				} else {
					double ratio = (new Integer(width)).doubleValue() / width2;
					height = (int) (height2 * ratio);
				}
			}
            BufferedImage to = new BufferedImage(width, height,  
 
                    BufferedImage.TYPE_INT_RGB);  
 
            Graphics2D g2d = to.createGraphics();  
 
            to = g2d.getDeviceConfiguration().createCompatibleImage(width, height,  
 
                    Transparency.TRANSLUCENT);  
 
            g2d.dispose();  
 
            g2d = to.createGraphics();  
 
            Image from = bi2.getScaledInstance(width, height, BufferedImage.SCALE_AREA_AVERAGING);  
            g2d.drawImage(from, 0, 0, null);
            g2d.dispose();  
 
            ImageIO.write(to, "png", new File(result));  
 
        } catch (IOException e) {  
 
            e.printStackTrace();  
 
        } 
	}
	/**
	 * 缩放图像（按高度和宽度缩放）
	 * 
	 * @param srcImageFile
	 *            源图像文件地址
	 * @param result
	 *            缩放后的图像地址
	 * @param height
	 *            缩放后的高度
	 * @param width
	 *            缩放后的宽度
	 * @param bb
	 *            比例不对时是否需要补白：true为补白; false为不补白;
	 */
	public final static void scale(String srcImageFile, String result,
			int height, int width, boolean bb) {
		try {
			String type = FileUtils.getFileExtension(srcImageFile);
			if(type.equalsIgnoreCase(IMAGE_TYPE_PNG)){
				pngPress(srcImageFile, result, width, height);
				return;
			}
			double ratio = 0.0; // 缩放比例
//			File f = new File(srcImageFile);
//			BufferedImage bi = ImageIO.read(f);
			Image img = Toolkit.getDefaultToolkit().getImage(srcImageFile);
			BufferedImage bi = toBufferedImage(img,type);
			Image itemp = bi.getScaledInstance(width, height,
					BufferedImage.SCALE_SMOOTH);
//			Image itemp = bi.getScaledInstance(width, height,
//					bi.getType());
			// 防止实际大小小于压缩大小时程序报错
			if ((bi.getHeight() < height) || (bi.getWidth() < width)) {
				height = bi.getHeight();
				width = bi.getWidth();
			}
			// 计算比例
			if ((bi.getHeight() >= height) || (bi.getWidth() >= width)) {
				if (bi.getHeight() / height > bi.getWidth() / width) {
					ratio = (new Integer(height)).doubleValue()
							/ bi.getHeight();
				} else {
					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(
						AffineTransform.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(bi, null);
			}
			if (bb) {// 补白
//				BufferedImage image = new BufferedImage(width, height,
//						bi.getType());
				BufferedImage image = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				// ---------- 增加下面的代码使得背景透明 -----------------
				image = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
				g.dispose();
				g = image.createGraphics();
				// ---------- 背景透明代码结束 -----------------
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null))
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				else
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				g.dispose();
				itemp = image;
			}
//			ImageIO.write((BufferedImage) itemp, "jpeg", new File(result));
			ImageIO.write((BufferedImage) itemp, type, new File(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 缩放图像（按比例缩放）
	 * 
	 * @param srcImageFile
	 *            源图像文件地址
	 * @param result
	 *            缩放后的图像地址
	 * @param scale
	 *            缩放比例
	 * @param flag
	 *            缩放选择:true 放大; false 缩小;
	 */
	public final static void scale2(String srcImageFile, String result,
			int scale, boolean flag) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长
			if (flag) {// 放大
				width = width * scale;
				height = height * scale;
			} else {// 缩小
				width = width / scale;
				height = height / scale;
			}
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, "jpeg", new File(result));// 输出到文件流
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像切割(按指定起点坐标和宽高切割)
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param result
	 *            切片后的图像地址
	 * @param x
	 *            目标切片起点坐标X
	 * @param y
	 *            目标切片起点坐标Y
	 * @param width
	 *            目标切片宽度
	 * @param height
	 *            目标切片高度
	 */
	public final static void cut(String srcImageFile, String result, int x,
			int y, int width, int height) {
		try {
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > 0 && srcHeight > 0) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				// 四个参数分别为图像起点坐标和宽高
				// 即: CropImageFilter(int x,int y,int width,int height)
				ImageFilter cropFilter = new CropImageFilter(x, y, width,
						height);
				Image img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
				g.dispose();
				// 输出为文件
				ImageIO.write(tag, "jpeg", new File(result));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像切割（指定切片的行数和列数）
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param descDir
	 *            切片目标文件夹
	 * @param rows
	 *            目标切片行数。默认2，必须是范围 [1, 20] 之内
	 * @param cols
	 *            目标切片列数。默认2，必须是范围 [1, 20] 之内
	 */
	public final static void cut2(String srcImageFile, String descDir,
			int rows, int cols) {
		try {
			if (rows <= 0 || rows > 20)
				rows = 2; // 切片行数
			if (cols <= 0 || cols > 20)
				cols = 2; // 切片列数
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > 0 && srcHeight > 0) {
				Image img;
				ImageFilter cropFilter;
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				int destWidth = srcWidth; // 每张切片的宽度
				int destHeight = srcHeight; // 每张切片的高度
				// 计算切片的宽度和高度
				if (srcWidth % cols == 0) {
					destWidth = srcWidth / cols;
				} else {
					destWidth = (int) Math.floor(srcWidth / cols) + 1;
				}
				if (srcHeight % rows == 0) {
					destHeight = srcHeight / rows;
				} else {
					destHeight = (int) Math.floor(srcWidth / rows) + 1;
				}
				// 循环建立切片
				// 改进的想法:是否可用多线程加快切割速度
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						// 四个参数分别为图像起点坐标和宽高
						// 即: CropImageFilter(int x,int y,int width,int height)
						cropFilter = new CropImageFilter(j * destWidth, i
								* destHeight, destWidth, destHeight);
						img = Toolkit.getDefaultToolkit().createImage(
								new FilteredImageSource(image.getSource(),
										cropFilter));
						BufferedImage tag = new BufferedImage(destWidth,
								destHeight, BufferedImage.TYPE_INT_RGB);
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
						g.dispose();
						// 输出为文件
						ImageIO.write(tag, "jpeg", new File(descDir + "_r" + i
								+ "_c" + j + ".jpg"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像切割（指定切片的宽度和高度）
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param descDir
	 *            切片目标文件夹
	 * @param destWidth
	 *            目标切片宽度。默认200
	 * @param destHeight
	 *            目标切片高度。默认150
	 */
	public final static void cut3(String srcImageFile, String descDir,
			int destWidth, int destHeight) {
		try {
			if (destWidth <= 0)
				destWidth = 200; // 切片宽度
			if (destHeight <= 0)
				destHeight = 150; // 切片高度
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > destWidth && srcHeight > destHeight) {
				Image img;
				ImageFilter cropFilter;
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				int cols = 0; // 切片横向数量
				int rows = 0; // 切片纵向数量
				// 计算切片的横向和纵向数量
				if (srcWidth % destWidth == 0) {
					cols = srcWidth / destWidth;
				} else {
					cols = (int) Math.floor(srcWidth / destWidth) + 1;
				}
				if (srcHeight % destHeight == 0) {
					rows = srcHeight / destHeight;
				} else {
					rows = (int) Math.floor(srcHeight / destHeight) + 1;
				}
				// 循环建立切片
				// 改进的想法:是否可用多线程加快切割速度
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						// 四个参数分别为图像起点坐标和宽高
						// 即: CropImageFilter(int x,int y,int width,int height)
						cropFilter = new CropImageFilter(j * destWidth, i
								* destHeight, destWidth, destHeight);
						img = Toolkit.getDefaultToolkit().createImage(
								new FilteredImageSource(image.getSource(),
										cropFilter));
						BufferedImage tag = new BufferedImage(destWidth,
								destHeight, BufferedImage.TYPE_INT_RGB);
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
						g.dispose();
						// 输出为文件
						ImageIO.write(tag, "jpeg", new File(descDir + "_r" + i
								+ "_c" + j + ".jpg"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param formatName
	 *            包含格式非正式名称的 String：如JPG、JPEG、GIF等
	 * @param destImageFile
	 *            目标图像地址
	 */
	public final static void convert(String srcImageFile, String formatName,
			String destImageFile) {
		try {
			File f = new File(srcImageFile);
			f.canRead();
			f.canWrite();
			BufferedImage src = ImageIO.read(f);
			ImageIO.write(src, formatName, new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 彩色转为黑白
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param destImageFile
	 *            目标图像地址
	 */
	public final static void gray(String srcImageFile, String destImageFile) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile));
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
			src = op.filter(src, null);
			ImageIO.write(src, "jpeg", new File(destImageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给图片添加文字水印
	 * 
	 * @param pressText
	 *            水印文字
	 * @param srcImageFile
	 *            源图像地址
	 * @param destImageFile
	 *            目标图像地址
	 * @param fontName
	 *            水印的字体名称
	 * @param fontStyle
	 *            水印的字体样式
	 * @param color
	 *            水印的字体颜色
	 * @param fontSize
	 *            水印的字体大小
	 * @param x
	 *            修正值
	 * @param y
	 *            修正值
	 * @param alpha
	 *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 */
	public final static void pressText(String pressText, String srcImageFile,
			String destImageFile, String fontName, int fontStyle, Color color,
			int fontSize, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			// 在指定坐标绘制水印文字
			g.drawString(pressText,
					(width - (getLength(pressText) * fontSize) - x), (height
							- fontSize - y));
			g.dispose();
			ImageIO.write((BufferedImage) image, "jpeg",
					new File(destImageFile));// 输出到文件流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给图片添加图片水印
	 * 
	 * @param pressImg
	 *            水印图片
	 * @param srcImageFile
	 *            源图像地址
	 * @param destImageFile
	 *            目标图像地址
	 * @param x
	 *            修正值。 默认在中间
	 * @param y
	 *            修正值。 默认在中间
	 * @param alpha
	 *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 */
	public final static void pressImage(String pressImg, String srcImageFile,
			String destImageFile, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			// 水印文件
			Image src_biao = ImageIO.read(new File(pressImg));
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			g.drawImage(src_biao, (wideth - x), (height - y), wideth_biao, height_biao, null);
			// 水印文件结束
			g.dispose();
			ImageIO.write((BufferedImage) image, "jpeg",
					new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 计算text的长度（一个中文算两个字符）
	 * 
	 * @param text
	 * @return
	 */
	public final static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}
	public static BufferedImage toBufferedImage(Image image,String fileExt) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
         }
    
        // This code ensures that all the pixels in the image are loaded
         image = new ImageIcon(image).getImage();
    
        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent Pixels
        //boolean hasAlpha = hasAlpha(image);
    
        // Create a buffered image with a format that's compatible with the screen
         BufferedImage bimage = null;
         /* GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
            if (hasAlpha) {
                 transparency = Transparency.BITMASK;
             }
    
            // Create the buffered image
             GraphicsDevice gs = ge.getDefaultScreenDevice();
             GraphicsConfiguration gc = gs.getDefaultConfiguration();
             bimage = gc.createCompatibleImage(
                 image.getWidth(null), image.getHeight(null), transparency);
         } catch (HeadlessException e) {
            // The system does not have a screen
        	 e.printStackTrace();
         }*/
    
        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            //int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
            /*if (hasAlpha) {
                 type = BufferedImage.TYPE_INT_ARGB;
             }*/
             bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
         }
    
        if(fileExt.equalsIgnoreCase(IMAGE_TYPE_PNG)){
        	//这里是关键部分,背景不变黑
     		/*Graphics2D g=bimage.createGraphics();
     		bimage = g.getDeviceConfiguration().createCompatibleImage(image.getWidth(null), image.getHeight(null), Transparency.TRANSLUCENT);
     		g = bimage.createGraphics();
     		// Paint the image onto the buffered image
            g.drawImage(image, 0, 0, null);
            g.dispose();*/
		}else{
			// Copy image to buffered image
			Graphics g = bimage.createGraphics();
			// Paint the image onto the buffered image
	        g.drawImage(image, 0, 0, null);
	        g.dispose();
		}
    
        return bimage;
     }
	
	public static String getCacheAdvertThumb(String advertId,Integer width,Integer height,String imageName,String prePath,boolean isAll){
		/*Map<String, String> advertThumbMap = (Map<String, String>)CacheUtils.get("advertThumbMap");
		if (advertThumbMap==null){
			advertThumbMap = Maps.newHashMap();
			CacheUtils.put("advertThumbMap", advertThumbMap);
		}
		
		String key = advertId+"_"+width+"_"+height;
		
		String thumbPath = advertThumbMap.get(key);
		if (StringUtils.isBlank(thumbPath)){
			String basePath = Config.getUploadBasepath();
			String sourceFilePath = basePath+imageName;
			File file = new File(sourceFilePath);
			if(file.exists()){
				String dFilePath = FileUtils.getUploadPath(Config.getUploadAdvertpath());
				String thumbName = FileUtils.scaleImg(sourceFilePath, basePath+dFilePath, null, width, height);
				
				thumbPath = dFilePath+"/"+thumbName;
				advertThumbMap.put(key, thumbPath);
			}
		}
		if(isAll && StringUtils.isNotBlank(thumbPath)){
			thumbPath = SystemPath.getRealPath(thumbPath, prePath);
		}
		return thumbPath;*/
		Integer[] scale = ImageUtils.getImgScale(width,height,310,310);
		width = scale[0];
		height = scale[1];
		return getCacheThumb(advertId, width, height, imageName, prePath, isAll,Config.getUploadAdvertpath());
	}
	public static String getCacheTempleThumb(String templeId,Integer width,Integer height,String imageName,String prePath,boolean isAll){
		/*Map<String, String> templeThumbMap = (Map<String, String>)CacheUtils.get("templeThumbMap");
		if (templeThumbMap==null){
			templeThumbMap = Maps.newHashMap();
			CacheUtils.put("templeThumbMap", templeThumbMap);
		}
		
		String key = templeId+"_"+width+"_"+height;
		
		String thumbPath = templeThumbMap.get(key);
		if (StringUtils.isBlank(thumbPath)){
			String basePath = Config.getUploadBasepath();
			String sourceFilePath = basePath+imageName;
			File file = new File(sourceFilePath);
			if(file.exists()){
				String dFilePath = FileUtils.getUploadPath(Config.getUploadTemplepath());
				String thumbName = FileUtils.scaleImg(sourceFilePath, basePath+dFilePath, null, width, height);
				
				thumbPath = dFilePath+"/"+thumbName;
				templeThumbMap.put(key, thumbPath);
			}
		}
		if(isAll && StringUtils.isNotBlank(thumbPath)){
			thumbPath = SystemPath.getRealPath(thumbPath, prePath);
		}
		return thumbPath;*/
		Integer[] scale = ImageUtils.getImgScale(width,height,310,310);
		width = scale[0];
		height = scale[1];
		return getCacheThumb(templeId, width, height, imageName, prePath, isAll,Config.getUploadTemplepath());
	}
	public static String getCacheGoodsThumb(String goodsId,Integer width,Integer height,String imageName,String prePath,boolean isAll){
		Integer[] scale = ImageUtils.getImgScale(width,height,310,310);
		width = scale[0];
		height = scale[1];
		return getCacheThumb(goodsId, width, height, imageName, prePath, isAll,Config.getUploadGoodsapath());
	}
	public static String getCacheThumb(String id,Integer width,Integer height,String imageName,String prePath,boolean isAll,String type){
		
		String fileExt = FileUtils.getFileExtension(imageName);
		String fileName = FileUtils.getFileName(imageName);
		if(!id.contains("_") && StringUtils.isNotBlank(fileName)){
			String name = fileName.substring(0,fileName.lastIndexOf("."));
			id+="_"+name;
		}
		String thumbFileName = id+"_"+width+"_"+height+"."+fileExt;
		String basePath = Config.getUploadBasepath();
		String sourceFileDirectory = FileUtils.getFileDirectoryPath(imageName,type);
		String thumbPath = "";
		if(StringUtils.isNotBlank(sourceFileDirectory)){
			thumbPath = sourceFileDirectory+thumbFileName;
			File file = new File(basePath+thumbPath);
			if(!file.exists()){
				String sourceFilePath = basePath+sourceFileDirectory+fileName;
				File sfile = new File(sourceFilePath);
				if(sfile.exists()){
					String thumbName = scaleImg(sourceFilePath, basePath+sourceFileDirectory, thumbFileName, width, height);
					
					thumbPath = Config.getUploadBasePrepath()+sourceFileDirectory+thumbName;
				}else{
					thumbPath = Config.getUploadBasePrepath()+thumbPath;
				}
			}else{
				thumbPath = Config.getUploadBasePrepath()+thumbPath;
			}
		}
		if(isAll && StringUtils.isNotBlank(thumbPath)){
			thumbPath = SystemPath.getRealPath(thumbPath, prePath);
		}
		return thumbPath;
	}
	public static Integer[] getImgScale(Integer width,Integer height,Integer defaultWidth,Integer defaultHeight){
		if(width == null && height == null){
			return new Integer[]{defaultWidth,defaultHeight};
		}
		if(width == null){
			return new Integer[]{0,height};
		}
		if(height == null){
			return new Integer[]{width,0};
		}
		return new Integer[]{width,height};
	}
	public static String scaleImg(String sourceFilePath,String dFilePath,String thumbFileName,int width,int height){
		File file = new File(dFilePath);
		if(!file.exists()){
			file.mkdirs();
		}
		if(StringUtils.isBlank(thumbFileName)){
			thumbFileName = IdGen.uuid()+"."+FileUtils.getFileExtension(sourceFilePath);
		}
		String saveThumbFile = dFilePath + File.separator + thumbFileName;
		
		ImageUtils.scale(sourceFilePath, saveThumbFile, height, width, false);
		return thumbFileName;
	}
	
	public static String getErweimaThumb(String userId,Integer width,Integer height,String imageName,String prePath,boolean isAll){
		Integer[] scale = ImageUtils.getImgScale(width,height,310,310);
		width = scale[0];
		height = scale[1];
		return getCacheThumb(userId, width, height, imageName, prePath, isAll,Config.getUploadErweimapath());    
	}	
}
