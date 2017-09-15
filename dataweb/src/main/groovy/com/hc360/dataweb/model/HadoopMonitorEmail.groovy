package com.hc360.dataweb.model

/**
 * Created by home on 2016/8/30.
 */
class HadoopMonitorEmail {
    //  hadoop_monitor_email_seq
    Integer Id;
     String fromUser;
     String toUser;
     String title;
     String content;
     String flag;
     String sysTime;

 HadoopMonitorEmail(String fromUser, String toUser, String title,String content) {
  this.content = content
  this.title = title
  this.toUser = toUser
  this.fromUser = fromUser
 }

 HadoopMonitorEmail(String fromUser, String toUser, String title) {
  this.fromUser = fromUser
  this.toUser = toUser
  this.title = title
 }

 @Override
 public String toString() {
  return "HadoopMonitorEmail{" +
          "Id=" + Id +
          ", fromUser='" + fromUser + '\'' +
          ", toUser='" + toUser + '\'' +
          ", title='" + title + '\'' +
          ", content='" + content + '\'' +
          ", flag='" + flag + '\'' +
          ", sysTime='" + sysTime + '\'' +
          '}';
 }
}
