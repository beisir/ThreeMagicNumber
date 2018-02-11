package com.hc360.dataweb.controller;

import com.hc360.dataweb.model.OrderMoveTable;
import com.hc360.dataweb.model.TaskTable;
import com.hc360.dataweb.service.UpdownExcelService;
import com.hc360.dataweb.util.ResponseJson;
import com.hc360.sso.SSOHelper;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by dell on 2017/12/6.
 */
@Controller
public class UpdownExcelController {

    @Autowired
    private UpdownExcelService udService;

    //默认单元格内容为数字时格式
    private static DecimalFormat df = new DecimalFormat("0");
    // 默认单元格格式化日期字符串
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 默认单元格格式化日期字符串
    private static SimpleDateFormat sdfn = new SimpleDateFormat("yyyy-MM-dd");
    // 格式化数字
    private static DecimalFormat nf = new DecimalFormat("0.00");

    SimpleDateFormat idDf=new SimpleDateFormat("yyMMddHHmmssSSS");

    Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    Pattern parternerP= Pattern.compile("[\\u4e00-\\u9fa5\\/]+");

    Random random = new Random();


    @RequestMapping(value="uploadExcel",method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson readExcel(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="file") MultipartFile file) throws Exception {
        ResponseJson json = new ResponseJson();
        System.out.println("文件名----------" + file.getOriginalFilename());

        Long operator = getLoginName(request);
        if(null == operator || 0==operator){
            json.setErrno(1);
            json.setMsg("请先登录再操作");
            return json;
        }

        InputStream in = file.getInputStream();
        if(file.getOriginalFilename().endsWith("xlsx")){
            //处理ecxel2007
            System.out.print("文件2007----------");
            json = readTaskExcel2007(in,operator);
            //return readTaskExcel2007(in,operator);
        }else if(file.getOriginalFilename().endsWith("xls")){
            //处理ecxel2003
            System.out.println("文件2003----------");
            json=readTaskExcel2003(in,operator);
            //return readTaskExcel2003(in,operator);
        }else {
            json.setErrno(1);
            json.setMsg("格式错误，请上传Excel文件");
        }

        return json;
    }




    @RequestMapping(value="uploadOrderExcel",method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson uploadOrderExcel(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="file") MultipartFile file) throws Exception {
        ResponseJson json = new ResponseJson();
        System.out.println("文件名----------" + file.getOriginalFilename());

       Long operator = getLoginName(request);
       if(null == operator || 0==operator){
            json.setErrno(1);
            json.setMsg("请先登录再操作");
            return json;
        }

        InputStream in = file.getInputStream();
        if(file.getOriginalFilename().endsWith("xlsx")){
            //处理ecxel2007
            System.out.print("文件2007----------");
            return readOrderExcel2007(in, operator);
        }else if(file.getOriginalFilename().endsWith("xls")){
            //处理ecxel2003
            System.out.println("文件2003----------");
            return readOrderExcel2003(in, operator);
        }

        json.setErrno(1);
        json.setMsg("格式错误，请上传Excel文件");
        return json;
    }




    public ResponseJson readTaskExcel2007(InputStream in,Long operator){
        System.out.println("readTask2007----------开始");
        ResponseJson json = new ResponseJson();
        try{
            ArrayList<String> qclist = new ArrayList<String>();
            ArrayList<TaskTable> rowList = new ArrayList<TaskTable>();
            TaskTable task;
            XSSFWorkbook wb = new XSSFWorkbook(in);
            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row;
            int hasTitle=0;
            for(int i = sheet.getFirstRowNum() , rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows() ; i++ ){
                System.out.println("第"+(i+1)+"行");
                String qcs=null;
                row = sheet.getRow(i);
                rowCount++;
                if(row == null){//当读取行为空时
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行不能为空！");
                    return json;
                }else if(null != row.getCell(0) && row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_STRING && row.getCell(0).getStringCellValue().contains("任务")){
                    hasTitle++;
                    continue;
                }

                task = new TaskTable();

                //任务周度
                XSSFCell cell1 = row.getCell(0);
                if(null == cell1 || cell1.getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+1+"列数据格式错误!");
                    return json;
                }else{
                    //String week = df.format(cell1.getNumericCellValue());
                    if(cell1.getNumericCellValue() != (int)cell1.getNumericCellValue() || cell1.getNumericCellValue()<0){
                        json.setErrno(1);
                        json.setMsg("第"+(i+1)+"行"+1+"列数据格式错误!");
                        return json;
                    }
                    String week = (int)cell1.getNumericCellValue()+"";
                    if(week.length() != 8 || !week.startsWith("201")){
                        json.setErrno(1);
                        json.setMsg("第"+(i+1)+"行"+1+"列数据错误,须为8位且以201开头!");
                        return json;
                    }
                    task.setTaskTime(Integer.valueOf(week));
                    qcs+=week;
                }

                //部门
                XSSFCell cell2 = row.getCell(1);
                if(null == cell2 || cell2.getCellType() != HSSFCell.CELL_TYPE_STRING){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+2+"列数据格式错误，请填写正确部门名称!");
                    return json;
                }else{
                    String nowPart = cell2.getStringCellValue().trim().replaceAll(" ","");
                    String part = cell2.getStringCellValue().trim().replaceAll(" ","").replaceAll("/","");
                    if("".equals(part) || !parternerP.matcher(part).matches()){
                        json.setErrno(1);
                        json.setMsg("第"+(i+1)+"行"+2+"列数据格式错误，请填写正确部门名称!");
                        return json;
                    }
                    task.setDepartment(nowPart);
                    qcs+=nowPart;
                }

                if(qclist.contains(qcs)){
                    json.setErrno(1);
                    json.setMsg("第"+(qclist.indexOf(qcs)+1+hasTitle)+"行与"+(i+1)+"行数据重复!");
                    return json;
                }else{
                    qclist.add(qcs);
                }

                //任务数
                XSSFCell cell3 = row.getCell(2);
                if(null == cell3 || cell3.getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+3+"列数据格式错误!");
                    return json;
                }else{
                    //cell3.getNumericCellValue() != (int)cell3.getNumericCellValue() || cell3.getNumericCellValue()<0
                    if(cell3.getNumericCellValue()<0){
                        json.setErrno(1);
                        json.setMsg("第"+(i+1)+"行"+3+"列数据错误,须为大于0的数!");
                        return json;
                    }
                    task.setTaskNum(nf.format(cell3.getNumericCellValue()));
                    task.setCreatTime(new Date());
                    task.setId(getUniqueId());
                    task.setOperatorId(new BigDecimal(operator));
                }

                rowList.add(task);
            }//end for i
            //插入数据
            udService.insertTasks(rowList);

            json.setErrno(0);
            json.setMsg("ok");
        }catch(Exception e){
            e.printStackTrace();
            json.setErrno(1);
            json.setMsg("服务异常，请稍后再试");
            json.setData(e.getMessage());
        }
        return json;
    }



    public ResponseJson readTaskExcel2003(InputStream in,Long operator){
        System.out.println("read2003----------开始");
        ResponseJson json = new ResponseJson();
        try{
            ArrayList<String> qclist = new ArrayList<String>();
            ArrayList<TaskTable> rowList = new ArrayList<TaskTable>();
            TaskTable task;
            HSSFWorkbook wb = new HSSFWorkbook(in);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;
            int hasTitle=0;
            for(int i = sheet.getFirstRowNum() , rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows() ; i++ ){
                System.out.println("第"+(i+1)+"行");
                String qcs=null;
                row = sheet.getRow(i);
                rowCount++;
                if(row == null){//当读取行为空时
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行不能为空！");
                    return json;
                }else if(null != row.getCell(0) && row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_STRING && row.getCell(0).getStringCellValue().contains("任务")){
                    hasTitle++;
                    continue;
                }

                task = new TaskTable();

                //任务周度
                HSSFCell cell1 = row.getCell(0);
                if(null == cell1 || cell1.getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+1+"列数据格式错误!");
                    return json;
                }else{
                    //String week = df.format(cell1.getNumericCellValue());
                    if(cell1.getNumericCellValue() != (int)cell1.getNumericCellValue() || cell1.getNumericCellValue()<0){
                        json.setErrno(1);
                        json.setMsg("第"+(i+1)+"行"+1+"列数据格式错误!");
                        return json;
                    }
                    String week = (int)cell1.getNumericCellValue()+"";
                    if(week.length() != 8 || !week.startsWith("201")){
                        json.setErrno(1);
                        json.setMsg("第"+(i+1)+"行"+1+"列数据错误,须为8位且以201开头!");
                        return json;
                    }
                    task.setTaskTime(Integer.valueOf(week));
                    qcs+=week;
                }

                //部门
                HSSFCell cell2 = row.getCell(1);
                if(null == cell2 || cell2.getCellType() != HSSFCell.CELL_TYPE_STRING){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+2+"列数据格式错误，请填写正确部门名称!");
                    return json;
                }else{
                    String nowPart = cell2.getStringCellValue().trim().replaceAll(" ","");
                    String part = cell2.getStringCellValue().trim().replaceAll(" ","").replaceAll("/","");
                    if("".equals(part) || !parternerP.matcher(part).matches()){
                        json.setErrno(1);
                        json.setMsg("第"+(i+1)+"行"+2+"列数据格式错误，请填写正确部门名称!");
                        return json;
                    }
                    task.setDepartment(nowPart);
                    qcs+=nowPart;
                }

                if(qclist.contains(qcs)){
                    json.setErrno(1);
                    json.setMsg("第"+(qclist.indexOf(qcs)+1+hasTitle)+"行与"+(i+1)+"行数据重复!");
                    return json;
                }else{
                    qclist.add(qcs);
                }

                //任务数
                HSSFCell cell3 = row.getCell(2);
                if(null == cell3 || cell3.getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+3+"列数据格式错误,须为大于0的数!");
                    return json;
                }else{
                    //cell3.getNumericCellValue() != (int)cell3.getNumericCellValue() || cell3.getNumericCellValue()<0
                    if(cell3.getNumericCellValue()<0){
                        json.setErrno(1);
                        json.setMsg("第"+(i+1)+"行"+3+"列数据错误,须为大于0的数!");
                        return json;
                    }
                    task.setTaskNum(nf.format(cell3.getNumericCellValue()));
                    task.setCreatTime(new Date());
                    task.setId(getUniqueId());
                    task.setOperatorId(new BigDecimal(operator));
                }

                rowList.add(task);
            }//end for i
            //插入数据
            udService.insertTasks(rowList);

            json.setErrno(0);
            json.setMsg("ok");
        }catch(Exception e){
            e.printStackTrace();
            json.setErrno(1);
            json.setMsg("服务异常，请稍后再试");
            json.setData(e.getMessage());
        }
        return json;
    }




    public ResponseJson readOrderExcel2007(InputStream in,Long operator){
        System.out.println("readTask2007----------开始");
        ResponseJson json = new ResponseJson();
        try{
            ArrayList<String> qclist = new ArrayList<String>();
            ArrayList<OrderMoveTable> rowList = new ArrayList<OrderMoveTable>();
            OrderMoveTable order;
            XSSFWorkbook wb = new XSSFWorkbook(in);
            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row;
            int hasTitle=0;
            System.out.println("共几行---"+sheet.getPhysicalNumberOfRows());
            for(int i = sheet.getFirstRowNum() , rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows() ; i++ ){
                System.out.println("第"+(i+1)+"行");
                row = sheet.getRow(i);
                rowCount++;
                if(row == null){//当读取行为空时
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行不能为空！");
                    return json;
                }else if(null != row.getCell(0) && row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_STRING && row.getCell(0).getStringCellValue().contains("划转")){
                    hasTitle++;
                    continue;
                }

                order = new OrderMoveTable();
                StringBuffer qcs=new StringBuffer("");

                //划转时间
                XSSFCell cell1 = row.getCell(0);
                if(null == cell1 || cell1.getCellType() != HSSFCell.CELL_TYPE_STRING){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+1+"列数据格式错误!");
                    return json;
                }else{
                    String moveT = cell1.getStringCellValue().trim();

                    if(moveT.length()!=10 || !pattern.matcher(moveT).matches()){
                        json.setErrno(1);
                        json.setMsg("第"+(i+1)+"行"+1+"列数据错误,须为 yyyy-MM-dd 格式!");
                        return json;
                    }
                    order.setMoveDate(moveT);
                    qcs.append(moveT);
                }

                //订单编号
                XSSFCell cell2 = row.getCell(1);
                if(null == cell2 || cell2.getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+2+"列数据格式错误，请填写正确订单编号!");
                    return json;
                }else{
                    qcs.append(cell2.getNumericCellValue());
                    Long oid= Long.valueOf(df.format(cell2.getNumericCellValue()));
                    order.setOrderId(oid);
                }

                //划出签单业务员
                String fromwho = null;
                XSSFCell cell3 = row.getCell(2);
                if(null == cell3 || cell3.getCellType() != HSSFCell.CELL_TYPE_STRING){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+3+"列数据格式错误，请填写正确划出业务员名称!");
                    return json;
                }else{
                    fromwho=cell3.getStringCellValue().trim().replaceAll(" ","");
                    /*if("1".equals(SSOHelper.queryHCWorker(fromwho))){
                        json.setErrno(1);
                        json.setMsg("第"+(i+1)+"行"+3+"列 划出签单业务员 非MIS用户");
                        return json;
                    }*/
                    qcs.append(fromwho);
                    order.setFromWho(fromwho);
                }


                //划入签单业务员
                String towho=null;
                XSSFCell cell4 = row.getCell(3);
                if(null == cell4 || cell4.getCellType() != HSSFCell.CELL_TYPE_STRING){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+4+"列数据格式错误，请填写正确划入业务员名称!");
                    return json;
                }else{
                    towho=cell4.getStringCellValue().trim().replaceAll(" ","");
                    if(towho.equalsIgnoreCase(fromwho)){
                        json.setErrno(1);
                        json.setMsg("第"+(i+1)+"行数据错误，划入业务员名称不能与划出业务员名称相同!");
                        return json;
                    }
                    /*if("1".equals(SSOHelper.queryHCWorker(towho))){
                        json.setErrno(1);
                        json.setMsg("第"+(i+1)+"行"+4+"列 划入签单业务员 非MIS用户");
                        return json;
                    }*/
                    qcs.append(towho);
                    order.setToWho(towho);
                }


                //所属部门
                XSSFCell cell5 = row.getCell(4);
                if(null == cell5 || cell5.getCellType() != HSSFCell.CELL_TYPE_STRING){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+5+"列数据格式错误，请填写正确部门名称!");
                    return json;
                }else{
                    String nowPart = cell5.getStringCellValue().trim().replaceAll(" ","");
                    String part = cell5.getStringCellValue().trim().replaceAll(" ","").replaceAll("/","");
                    if("".equals(part) || !parternerP.matcher(part).matches()){
                        json.setErrno(1);
                        json.setMsg("第"+(i+1)+"行"+5+"列数据格式错误，请填写正确部门名称!");
                        return json;
                    }
                    qcs.append(nowPart);

                    order.setDepartment(nowPart);
                    System.out.println("部门---"+order.getDepartment());
                }


                //划转金额
                XSSFCell cell6 = row.getCell(5);
                if(null == cell6 || cell6.getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+6+"列数据格式错误,请填写正确划转金额!");
                    return json;
                }else{
                    qcs.append(cell6.getNumericCellValue());
                    order.setAmount(nf.format(cell6.getNumericCellValue()));
                }

                //客户划给谁
                XSSFCell cell7 = row.getCell(6);
                if(null == cell7 || cell7.getCellType() != HSSFCell.CELL_TYPE_STRING){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+7+"列数据格式错误，请填写正确划入业务员名称!");
                    return json;
                }else{
                    String ctw = cell7.getStringCellValue().trim().replaceAll(" ", "");
                    if(!"订单划转".equals(ctw) && !"流水划转".equals(ctw)){
                        json.setErrno(1);
                        json.setMsg("第"+(i+ 1)+"行"+7+"列数据错误，请填写 订单划转或流水划转!");
                        return json;
                    }
                    qcs.append(ctw);
                    order.setClientToWho(ctw);
                }

                if(qclist.contains(qcs.toString())){
                    json.setErrno(1);
                    json.setMsg("第"+(qclist.indexOf(qcs.toString())+1+hasTitle)+"行与"+(i+1)+"行数据重复!");
                    System.out.println();
                    return json;
                }else{
                    qclist.add(qcs.toString());
                }
                order.setCreatTime(new Date());
                order.setOperatorId(new BigDecimal(operator));
                order.setId(getUniqueId());
                rowList.add(order);
            }//end for i
            //插入数据
            udService.insertOrders(rowList);

            json.setErrno(0);
            json.setMsg("ok");
        }catch(Exception e){
            e.printStackTrace();
            json.setErrno(1);
            json.setMsg("服务异常，请稍后再试");
            json.setData(e.getMessage());
        }
        return json;
    }







    public ResponseJson readOrderExcel2003(InputStream in,Long operator){
        System.out.println("read2003----------开始");
        ResponseJson json = new ResponseJson();
        try{
            ArrayList<String> qclist = new ArrayList<String>();
            ArrayList<OrderMoveTable> rowList = new ArrayList<OrderMoveTable>();
            OrderMoveTable order;
            HSSFWorkbook wb = new HSSFWorkbook(in);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;
            int hasTitle=0;
            System.out.print("read2003----------row---begin");
            System.out.println("共几行---"+sheet.getPhysicalNumberOfRows());
            for(int i = sheet.getFirstRowNum() , rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows() ; i++ ){
                System.out.println("第"+(i+1)+"行");
                row = sheet.getRow(i);
                rowCount++;
                if(row == null){//当读取行为空时
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行不能为空！");
                    return json;
                }else if(null != row.getCell(0) && row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_STRING && row.getCell(0).getStringCellValue().contains("划转")){
                    hasTitle++;
                    continue;
                }

                order = new OrderMoveTable();
                StringBuffer qcs=new StringBuffer("");

                //划转时间
                HSSFCell cell1 = row.getCell(0);
                if(null == cell1 || cell1.getCellType() != HSSFCell.CELL_TYPE_STRING){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+1+"列数据格式错误!");
                    return json;
                }else{
                    String moveT = cell1.getStringCellValue().trim();

                    if(moveT.length()!=10 || !pattern.matcher(moveT).matches()){
                        json.setErrno(1);
                        json.setMsg("第"+(i+1)+"行"+1+"列数据错误,须为 yyyy-MM-dd 格式!");
                        return json;
                    }
                    qcs.append(moveT);
                    order.setMoveDate(moveT);
                }

                //订单编号
                HSSFCell cell2 = row.getCell(1);
                if(null == cell2 || cell2.getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+2+"列数据格式错误，请填写正确订单编号!");
                    return json;
                }else{
                    //qcs=cell2.getStringCellValue().trim().replaceAll(" ","");
                    //order.setOrderId(qcs);
                    qcs.append(cell2.getNumericCellValue());
                    Long oid= Long.valueOf(df.format(cell2.getNumericCellValue()));
                    order.setOrderId(oid);
                }

                //划出签单业务员
                String fromwho = null;
                HSSFCell cell3 = row.getCell(2);
                if(null == cell3 || cell3.getCellType() != HSSFCell.CELL_TYPE_STRING){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+3+"列数据格式错误，请填写正确划出业务员名称!");
                    return json;
                }else{
                    fromwho=cell3.getStringCellValue().trim().replaceAll(" ","");
                    /*if("1".equals(SSOHelper.queryHCWorker(fromwho))){
                        json.setErrno(1);
                        json.setMsg("第"+(i+1)+"行"+3+"列 划出签单业务员 非MIS用户");
                        return json;
                    }*/
                    qcs.append(fromwho);
                    order.setFromWho(fromwho);
                }


                //划入签单业务员
                String towho=null;
                HSSFCell cell4 = row.getCell(3);
                if(null == cell4 || cell4.getCellType() != HSSFCell.CELL_TYPE_STRING){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+4+"列数据格式错误，请填写正确划入业务员名称!");
                    return json;
                }else{
                    towho=cell4.getStringCellValue().trim().replaceAll(" ","");
                    if(towho.equalsIgnoreCase(fromwho)){
                        json.setErrno(1);
                        json.setMsg("第"+(i+1)+"行数据错误，划入业务员名称不能与划出业务员名称相同!");
                        return json;
                    }
                    /*if("1".equals(SSOHelper.queryHCWorker(towho))){
                        json.setErrno(1);
                        json.setMsg("第"+(i+1)+"行"+4+"列 划入签单业务员 非MIS用户");
                        return json;
                    }*/
                    qcs.append(towho);
                    order.setToWho(towho);
                }


                //所属部门
                HSSFCell cell5 = row.getCell(4);
                if(null == cell5 || cell5.getCellType() != HSSFCell.CELL_TYPE_STRING){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+5+"列数据格式错误，请填写正确部门名称!");
                    return json;
                }else{
                    String nowPart = cell5.getStringCellValue().trim().replaceAll(" ","");
                    String part = cell5.getStringCellValue().trim().replaceAll(" ","").replaceAll("/","");
                    if("".equals(part) || !parternerP.matcher(part).matches()){
                        json.setErrno(1);
                        json.setMsg("第"+(i+1)+"行"+5+"列数据格式错误，请填写正确部门名称!");
                        return json;
                    }
                    qcs.append(nowPart);
                    order.setDepartment(nowPart);
                    System.out.println("部门---"+order.getDepartment());
                }


                //划转金额
                HSSFCell cell6 = row.getCell(5);
                if(null == cell6 || cell6.getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+6+"列数据格式错误,请填写正确划转金额!");
                    return json;
                }else{
                    qcs.append(cell6.getNumericCellValue());
                    order.setAmount(nf.format(cell6.getNumericCellValue()));
                }

                //客户划给谁
                HSSFCell cell7 = row.getCell(6);
                if(null == cell7 || cell7.getCellType() != HSSFCell.CELL_TYPE_STRING){
                    json.setErrno(1);
                    json.setMsg("第"+(i+1)+"行"+7+"列数据格式错误，请填写正确划入业务员名称!");
                    return json;
                }else{
                    String ctw = cell7.getStringCellValue().trim().replaceAll(" ", "");
                    if(!"订单划转".equals(ctw) && !"流水划转".equals(ctw)){
                        json.setErrno(1);
                        json.setMsg("第"+(i+ 1)+"行"+7+"列数据错误，请填写 订单划转或流水划转!");
                        return json;
                    }
                    order.setClientToWho(ctw);
                    qcs.append(ctw);

                }

                if(qclist.contains(qcs.toString())){
                    json.setErrno(1);
                    json.setMsg("第"+(qclist.indexOf(qcs.toString())+1+hasTitle)+"行与"+(i+1)+"行数据重复!");
                    return json;
                }else{
                    qclist.add(qcs.toString());
                }

                order.setCreatTime(new Date());
                order.setOperatorId(new BigDecimal(operator));
                order.setId(getUniqueId());
                rowList.add(order);
            }//end for i
            //插入数据
            udService.insertOrders(rowList);

            json.setErrno(0);
            json.setMsg("ok");


        }catch(Exception e){
            e.printStackTrace();
            json.setErrno(1);
            json.setMsg("服务异常，请稍后再试");
            json.setData(e.getMessage());
        }
        return json;
    }



    public static void writeExcel(ArrayList<ArrayList<Object>> result,String path){
        if(result == null){
            return;
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet1");
        for(int i = 0 ;i < result.size() ; i++){
            HSSFRow row = sheet.createRow(i);
            if(result.get(i) != null){
                for(int j = 0; j < result.get(i).size() ; j ++){
                    HSSFCell cell = row.createCell(j);
                    cell.setCellValue(result.get(i).get(j).toString());
                }
            }
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try
        {
            wb.write(os);
        } catch (IOException e){
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        File file = new File(path);//Excel文件生成后存储的位置。
        OutputStream fos  = null;
        try
        {
            fos = new FileOutputStream(file);
            fos.write(content);
            os.close();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //判断整数（int）
    private boolean isInteger(String str) {
        if (null == str || "".equals(str.trim())) {
            return false;
        }
        //Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        Pattern pattern = Pattern.compile("\\d+");
        return pattern.matcher(str).matches();
    }

    //判断浮点数（double和float）
    private boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }




    private String getUniqueId() {

        String str=idDf.format(new Date())+random.nextInt(10)+random.nextInt(10)+random.nextInt(10);
        return str;
    }


    private Long getLoginName(HttpServletRequest request){
        Long operatorId = null;
        Cookie[] cookies = request.getCookies();//获取cookie数组
        if (null!=cookies) {
            for(Cookie cookie : cookies){
                if("dataUser".equalsIgnoreCase(cookie.getName())){
                    operatorId= Long.valueOf(cookie.getValue());
                }
            }
        }
        return operatorId;
    }




}
