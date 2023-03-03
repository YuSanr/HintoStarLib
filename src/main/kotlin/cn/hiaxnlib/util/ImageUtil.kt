package cn.hiaxnlib.util

import cn.hiaxnlib.particle.RelativeLocation
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object ImageUtil {
    /**
     * 从文件加载一张图片
     */
    fun loadImage(fileImage:File):BufferedImage?{
        if (!fileImage.exists()){
            return null
        }
        val imageIO = ImageIO.read(fileImage)
        return imageIO
    }

    /**
     * @param ignoredRGB 屏蔽的颜色 一般为0X000000
     * @param precision 精度 粒子的密集程度 建议<0.1 因为图片不是一般的大
     * @param image 被加载的图片
     */
    fun imageToRelativeLocationList(image:BufferedImage,ignoredRGB:Int = 0X000000,precision:Double):List<RelativeLocation>{
        val result = ArrayList<RelativeLocation>()
        val height = image.height
        val width = image.width
        for (z in 0 .. width){
            for (y in 0..height){
                // 由于Relative的旋转是以X轴为对称轴的 因此使用YZ平面
                val rgb = Color(image.getRGB(z,y))
                val ignoredColor = Color(ignoredRGB)
                if (rgb != ignoredColor){
                    result.add(RelativeLocation(0.0,(y-height/2)*precision,(z-width/2)*precision))
                }
            }
        }
        return result
    }
    /**
     * @param ignoredRGB 屏蔽的颜色 一般为0X000000
     * @param precision 精度 粒子的密集程度 建议<0.1 因为图片不是一般的大
     * @param image 被加载的图片
     * @return 一个map Key代表相对位置 value代表这个位置对应的RGB值
     */
    fun imageToRGBRelativeMap(image:BufferedImage,ignoredRGB:Int = 0X000000,precision:Double):Map<RelativeLocation,IntArray>{
        val result = HashMap<RelativeLocation,IntArray>()
        val height = image.height
        val width = image.width
        for (z in 0 .. width){
            for (y in 0..height){
                // 由于Relative的旋转是以X轴为对称轴的 因此使用YZ平面
                val rgb = Color(image.getRGB(z,y))
                val ignoredColor = Color(ignoredRGB)
                if (rgb != ignoredColor){
                    result[RelativeLocation(0.0,(y-height/2)*precision,(z-width/2)*precision)]= IntArray(4).also{
                        it[0] = rgb.red
                        it[1] = rgb.green
                        it[2] = rgb.blue
                        it[3] = rgb.alpha
                    }
                }
            }
        }
        return result
    }
    /**
     * @param ignoredRGB 屏蔽的颜色 一般为0X000000
     * @param precision 精度 粒子的密集程度 建议<0.1 因为图片不是一般的大
     * @param image 被加载的图片
     * @param newHeight 将图片压缩成该高度
     * @param newWeight 将图片压缩成这个宽度
     * @return 一个map Key代表相对位置 value代表这个位置对应的RGB值
     */
    fun imageToRGBRelativeMap(image:BufferedImage,ignoredRGB:Int = 0X000000,precision:Double,newWeight:Int,newHeight:Int):Map<RelativeLocation,IntArray>{
        val result = HashMap<RelativeLocation,IntArray>()
        val height = image.height
        val width = image.width
        var realHeight = newHeight
        var realWidth = newWeight
        if (width/height > newWeight/newHeight){
            realHeight = (height * newWeight/width).toInt()
        }else{
            realWidth = (width*newHeight/height).toInt()
        }
        var newImage = BufferedImage(realWidth,realHeight,BufferedImage.TYPE_INT_RGB)
        newImage.graphics.drawImage(image,0,0,realWidth,realHeight,null)
        newImage = rotateImage(newImage,180)
        for (z in 0 until  realWidth){
            for (y in 0 until realHeight){
                // 由于Relative的旋转是以X轴为对称轴的 因此使用YZ平面
                val rgb = Color(newImage.getRGB(z,y))
                val ignoredColor = Color(ignoredRGB)
                if (rgb != ignoredColor){
                    result[RelativeLocation(0.0,(y-realHeight/2)*precision,(z-realWidth/2)*precision)] = IntArray(4).also{
                        it[0] = rgb.red
                        it[1] = rgb.green
                        it[2] = rgb.blue
                        it[3] = rgb.alpha
                    }
                }
            }
        }
        return result
    }
    fun rotateImage(bufferedImage: BufferedImage, angel: Int): BufferedImage{
        var angel = angel
        if (angel < 0) {
            // 将负数角度，纠正为正数角度
            angel += 360
        }
        val imageWidth = bufferedImage.getWidth(null)
        val imageHeight = bufferedImage.getHeight(null)

        // 计算重新绘制图片的尺寸
        val rectangle: Rectangle = calculatorRotatedSize(Rectangle(Dimension(imageWidth, imageHeight)), angel)

        // 获取原始图片的透明度
        val type = bufferedImage.colorModel.transparency
        var newImage: BufferedImage? = null
        newImage = BufferedImage(rectangle.width, rectangle.height, type)
        val graphics: Graphics2D = newImage.createGraphics()

        // 平移位置
        graphics.translate((rectangle.width - imageWidth) / 2, (rectangle.height - imageHeight) / 2)

        // 旋转角度
        graphics.rotate(Math.toRadians(angel.toDouble()), imageWidth / 2.0, imageHeight / 2.0)

        // 绘图
        graphics.drawImage(bufferedImage, null, null)
        return newImage
    }


    //计算旋转后的尺寸
    private fun calculatorRotatedSize(src: Rectangle, angel: Int): Rectangle {
        var angel = angel
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                val temp: Int = src.height
                src.height = src.width
                src.width = temp
            }
            angel %= 90
        }
        val r = Math.sqrt((src.height * src.height + src.width * src.width).toDouble()) / 2
        val len = 2 * Math.sin(Math.toRadians(angel.toDouble()) / 2) * r
        val angel_alpha = (Math.PI - Math.toRadians(angel.toDouble())) / 2
        val angel_dalta_width = Math.atan(src.height.toDouble() / src.width)
        val angel_dalta_height = Math.atan(src.width.toDouble() / src.height)
        val len_dalta_width = (len * Math.cos(Math.PI - angel_alpha - angel_dalta_width)).toInt()
        val len_dalta_height = (len * Math.cos(Math.PI - angel_alpha - angel_dalta_height)).toInt()
        val des_width: Int = src.width + len_dalta_width * 2
        val des_height: Int = src.height + len_dalta_height * 2
        return Rectangle(Dimension(des_width, des_height))
    }
}