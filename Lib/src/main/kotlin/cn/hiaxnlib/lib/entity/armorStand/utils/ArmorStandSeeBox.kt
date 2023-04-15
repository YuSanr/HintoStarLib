package cn.hiaxnlib.lib.entity.armorStand.utils

/**
 * 盔甲架的AI检测范围
 */
class ArmorStandSeeBox() {
    /**
     * 实体检测箱子的 X轴长度的一半(检测范围在+-X)
     */
    var x = 0.0
    /**
     * 实体检测箱子的 Y轴长度的一半(检测范围在+-Y)
     */
    var y = 0.0
    /**
     * 实体检测箱子的 Z轴长度的一半(检测范围在+-Z)
     */
    var z = 0.0
    /**
     * 检测实体球的半径
     */
    var r = 0.0
    /**
     * 是否采用实体球的方式检测 默认为false
     */
    var ball = false
    constructor(x:Double, y:Double, z:Double):this(){
        this.x = x
        this.y = y
        this.z = z
    }
    constructor(x:Int, y:Int, z:Int):this(){
        this.x = x + 0.0
        this.y = y + 0.0
        this.z = z + 0.0
    }
    constructor(r:Double):this(){
        ball = true
        this.r = r
        this.x = r + 0.0
        this.y = r + 0.0
        this.z = r + 0.0
    }
    constructor(r:Int):this(){
        ball = true
        this.r = r + 0.0
        this.x = r + 0.0
        this.y = r + 0.0
        this.z = r + 0.0
    }

}