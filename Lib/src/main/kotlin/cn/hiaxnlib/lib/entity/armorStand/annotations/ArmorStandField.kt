package cn.hiaxnlib.lib.entity.armorStand.annotations


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
/**
 * 这个注解主要标识转为ArmorStandData时需要额外存储的属性
 *
 * 注意 这个属性有一下要求
 *  1. 具有空构造函数
 *  2. 不具备双向链表或者类似结构
 *  3. 属性的子属性不具备违反1,2的属性
 *
 * 序列化ArmorStandData时主要采用fastjson
 * 出现不想序列化的属性时使用 @JSONField(serialize = false) (但是好像是先序列化在检测的字段 所以在具备双向链表的数据结构的属性中会StackOverFlowError)
 */
annotation class ArmorStandField
