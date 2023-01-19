package cn.HiaXnLib.util

import cn.HiaXnLib.Particle.RelativeLocation
import cn.HiaXnLib.Particle.util.ParticleMathUtil
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.PI

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
    fun imageToRelativeLocationList(image:BufferedImage,ignoredRGB:Int = 0X000000,precision:Double,rotate:Double = 0.0):List<RelativeLocation>{
        val result = ArrayList<RelativeLocation>()
        val height = image.height
        val width = image.width
        for (z in 0 .. width){
            for (y in 0..height){
                // 由于Relative的旋转是以X轴为对称轴的 因此使用YZ平面
                val rgb = Color(image.getRGB(z,y))
                val ignoredColor = Color(ignoredRGB)
                if (rgb != ignoredColor){
                    result.add(RelativeLocation(0.0,y*precision,z*precision))
                }
            }
        }
        ParticleMathUtil.rotationPoint(result,rotate,0.0)
        return result
    }
    /**
     * @param ignoredRGB 屏蔽的颜色 一般为0X000000
     * @param precision 精度 粒子的密集程度 建议<0.1 因为图片不是一般的大
     * @param image 被加载的图片
     * @return 一个map Key代表相对位置 value代表这个位置对应的RGB值
     */
    fun imageToRGBRelativeMap(image:BufferedImage,ignoredRGB:Int = 0X000000,precision:Double,rotate: Double = 0.0):Map<RelativeLocation,IntArray>{
        val result = HashMap<RelativeLocation,IntArray>()
        val height = image.height
        val width = image.width
        for (x in 0 .. width){
            for (y in 0..height){
                // 由于Relative的旋转是以X轴为对称轴的 因此使用YZ平面
                val rgb = Color(image.getRGB(x,y))
                val ignoredColor = Color(ignoredRGB)
                if (rgb != ignoredColor){
                    result[RelativeLocation((x - width/2)*precision,(y - height/2)*precision,0.0)]= IntArray(4).also{
                        it[0] = rgb.red
                        it[1] = rgb.green
                        it[2] = rgb.blue
                        it[3] = rgb.alpha
                    }
                }
            }
        }
        val list = ArrayList<RelativeLocation>(result.keys)
        ParticleMathUtil.rotationPoint(list,0.0,rotate)
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
    fun imageToRGBRelativeMap(image:BufferedImage,ignoredRGB:Int = 0X000000,precision:Double,newWeight:Int,newHeight:Int,rotate:Double = 0.0):Map<RelativeLocation,IntArray>{
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
        val newImage = BufferedImage(realWidth,realHeight,BufferedImage.TYPE_INT_RGB)
        newImage.graphics.drawImage(image,0,0,realWidth,realHeight,null)
        for (x in 0 until  realWidth){
            for (y in 0 until realHeight){
                // 由于Relative的旋转是以X轴为对称轴的 因此使用YZ平面
                val rgb = Color(newImage.getRGB(x,y))
                val ignoredColor = Color(ignoredRGB)
                if (rgb != ignoredColor){
                    result[RelativeLocation((x - realWidth/2)*precision,(y - realHeight/2)*precision,0.0)] = IntArray(4).also{
                        it[0] = rgb.red
                        it[1] = rgb.green
                        it[2] = rgb.blue
                        it[3] = rgb.alpha
                    }
                }
            }
        }
        val list = ArrayList<RelativeLocation>(result.keys)
        ParticleMathUtil.rotationPoint(list,0.0,rotate)
        return result
    }
}