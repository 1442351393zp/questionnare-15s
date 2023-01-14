package com.infoac.cas.util;


/**  
* 项目名称：zp
* 类名称：Globals   
* 类描述：  全局变量定义
* 创建人：zp 
* @version    
*
 */
public final class Globals {
	/**
	 * 前缀
	 */
	public static String CONTEXTPATH="/questionnaire";
	/**
	 * 保存用户到SESSION
	 */
	public static String USER_SESSION="USER_SESSION";
	/**
	 * 人员类型
	 */
	public static Short User_Normal=1;//正常
	public static Short User_Forbidden=0;//禁用
	public static Short User_ADMIN=-1;//超级管理员
	/**
	 *日志级别定义
	 */
	public static Short Log_Leavel_INFO=1;
	public static Short Log_Leavel_WARRING=2;
	public static Short Log_Leavel_ERROR=3;
	 /**
	  * 日志类型
	  */
	 public static Short Log_Type_LOGIN=1; //登陆
	 public static Short Log_Type_EXIT=2;  //退出
	 public static Short Log_Type_INSERT=3; //插入
	 public static Short Log_Type_DEL=4; //删除
	 public static Short Log_Type_UPDATE=5; //更新
	 public static Short Log_Type_UPLOAD=6; //上传
	 public static Short Log_Type_OTHER=7; //其他
	 
	 /**
	  * 权限等级
	  */
	 public static Short Function_Leave_ONE=0;//一级权限
	 public static Short Function_Leave_TWO=1;//二级权限
	 
	 /**
	  * 权限等级前缀
	  */
	 public static String Function_Order_ONE="ofun";//一级权限
	 public static String Function_Order_TWO="tfun";//二级权限
	 /**
	  * 定时任务
	  */
	 public static String START_ONE="0";  //开始
	 public static String STOP_ONE="1";   //结束
	 /**
	  * 返回值
	  */
	 public static String SUCCESSE = "0";  //成功
	 public static String FIAL = "1";      //失败
	 public static String FIAL_NO_SUBJECT = "2";      //wei cha dao timu
	 /**
	  * 返回信息
	  */
	 public static String MSG_TJ_SUCCESSE="添加成功";  //成功
	 public static String MSG_TJ_FAIL="添加失败";   //失败
	 public static String MSG_XG_SUCCESSE="修改成功";   //成功
	 public static String MSG_XG_FAIL="修改失败";   //失败
	 public static String MSG_CZ_SUCCESSE="操作成功";   //成功
	 public static String MSG_CZ_FAIL="操作失败";   //失败
	 public static String MSG_SC_SUCCESSE="删除成功";   //成功
	 public static String MSG_SC_FAIL="删除失败";   //失败
	public static String MSG_CX_SUCCESSE="查询成功";   //成功
	public static String MSG_CX_FAIL="查询失败";   //失败
}
