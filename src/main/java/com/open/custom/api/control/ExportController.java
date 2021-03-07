package com.open.custom.api.control;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.open.custom.api.request.CommonReq;
import com.open.custom.api.utils.ExportExcelUtils;
import com.open.custom.api.utils.SpringUtils;
import com.open.custom.api.utils.WordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("export")
public class ExportController {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    private static final String SUCCESS = "0";
    private static final String ERROR = "-1";

    @RequestMapping(value = "/contractWord")
    public void contractWord(HttpServletResponse response, HttpServletRequest request) throws Exception {

        response.setCharacterEncoding("GBK");
        //设置响应的内容类型
        response.setContentType("application/octet-stream");


        Map<String, Object> param = new HashMap<String, Object>();

        String req = request.getParameter("req");
        if (StringUtils.isEmpty(req)) {
            log.error("请求 不能为空");
            return;
        }
        Map<String, String> reqMap = JSON.parseObject(req, Map.class);

        String id = reqMap.get("id");
        if (StringUtils.isEmpty(id)) {
            log.error("id 不能为空");
            return;
        }

//        ContractBuilding contractBuilding = new ContractBuilding();
//        contractBuilding.setContractId(Long.parseLong(id));
//        contractBuilding.setStart(0L);
//        contractBuilding.setEnd(50L);
//        // 查询当前页实体对象
//        List<ContractBuilding> contractBuildingList = contractService.getcontractbuildinglist(contractBuilding);
//        param.put("tab1", contractBuildingList);
//
//        List<Rent> rentList = rentService.getRentPropertylist(Long.parseLong(id));
//        param.put("tab2", rentList);


        param.put("${contractNo}", reqMap.get("contractNo"));
        param.put("${floor}", reqMap.get("floor"));
        param.put("${companyName}", reqMap.get("companyName"));
        param.put("${firstPart}", reqMap.get("firstPart"));
        param.put("${firstAddress}", reqMap.get("firstAddress"));
        param.put("${firstlegalPerson}", reqMap.get("firstlegalPerson"));
        param.put("${firstTel}", reqMap.get("firstTel"));
        param.put("${secondPart}", reqMap.get("secondPart"));
        param.put("${secondAddress}", reqMap.get("secondAddress"));
        param.put("${secondPerson}", reqMap.get("secondPerson"));
        param.put("${secondTel}", reqMap.get("secondTel"));
        param.put("${rentArea}", reqMap.get("rentArea"));

        DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

        // 租赁期
        param.put("${rentStart}", reqMap.get("rentStart"));
        param.put("${rentEnd}", reqMap.get("rentEnd"));


//        param.put("${rentYearStart}", null);
//        param.put("${rentMonStart}", null);
//        param.put("${rentDayStart}", null);
//        param.put("${rentYearEnd}", null);
//        param.put("${rentMonEnd}", null);
//        param.put("${rentDayEnd}", null);
//        param.put("${rentMonSum}", null);
//
//        String rentStart = reqMap.get("rentStart");
//        Date rentStartDate = null;
//        Date rentEndDate = null;
//        if (!StringUtils.isEmpty(rentStart)) {
//            rentStartDate = df2.parse(rentStart);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(rentStartDate);
//            param.put("${rentYearStart}", calendar.get(Calendar.YEAR));
//            param.put("${rentMonStart}", calendar.get(Calendar.MONTH) + 1);
//            param.put("${rentDayStart}", calendar.get(Calendar.DAY_OF_MONTH));
//        }
//        String rentEnd = reqMap.get("rentEnd");
//        if (!StringUtils.isEmpty(rentEnd)) {
//            rentEndDate = df2.parse(rentEnd);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(rentEndDate);
//            param.put("${rentYearEnd}", calendar.get(Calendar.YEAR));
//            param.put("${rentMonEnd}", calendar.get(Calendar.MONTH) + 1);
//            param.put("${rentDayEnd}", calendar.get(Calendar.DAY_OF_MONTH));
//        }
//
//        if (rentStartDate != null && rentEndDate != null) {
//            int months = getMonths(rentStartDate, rentEndDate);
//            param.put("${rentMonSum}", months);
//        }


        // 租赁单元的交付
        param.put("${rentDeliver}", reqMap.get("rentDeliver"));


//        param.put("${rentYearDeliver}", null);
//        param.put("${rentMonDeliver}", null);
//        param.put("${rentDayDeliver}", null);
//
//        String rentDeliver = reqMap.get("rentDeliver");
//        if (!StringUtils.isEmpty(rentDeliver)) {
//            Date rentDeliverDate = df1.parse(rentDeliver);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(rentDeliverDate);
//            param.put("${rentYearDeliver}", calendar.get(Calendar.YEAR));
//            param.put("${rentMonDeliver}", calendar.get(Calendar.MONTH) + 1);
//            param.put("${rentDayDeliver}", calendar.get(Calendar.DAY_OF_MONTH));
//        }

        // 合同期内总交房租
        param.put("${rentSum}", reqMap.get("rentSum"));


        // 7.2 装修免租期
        param.put("${rentFreeStart}", reqMap.get("rentFreeStart"));
        param.put("${rentFreeEnd}", reqMap.get("rentFreeEnd"));

//        param.put("${rentFreeYearStart}", null);
//        param.put("${rentFreeMonStart}", null);
//        param.put("${rentFreeDayStart}", null);
//        param.put("${rentFreeYearEnd}", null);
//        param.put("${rentFreeMonEnd}", null);
//        param.put("${rentFreeDayEnd}", null);
//        param.put("${rentFreeMon}", null);
//
//        String rentFreeStart = reqMap.get("rentFreeStart");
//        Date rentFreeStartDate = null;
//        if (!StringUtils.isEmpty(rentFreeStart)) {
//            rentFreeStartDate = df1.parse(rentFreeStart);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(rentFreeStartDate);
//            param.put("${rentFreeYearStart}", calendar.get(Calendar.YEAR));
//            param.put("${rentFreeMonStart}", calendar.get(Calendar.MONTH) + 1);
//            param.put("${rentFreeDayStart}", calendar.get(Calendar.DAY_OF_MONTH));
//        }
//        String rentFreeEnd = reqMap.get("rentFreeEnd");
//        Date rentFreeEndDate = null;
//        if (!StringUtils.isEmpty(rentFreeEnd)) {
//            rentFreeEndDate = df1.parse(rentFreeEnd);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(rentFreeEndDate);
//            param.put("${rentFreeYearEnd}", calendar.get(Calendar.YEAR));
//            param.put("${rentFreeMonEnd}", calendar.get(Calendar.MONTH) + 1);
//            param.put("${rentFreeDayEnd}", calendar.get(Calendar.DAY_OF_MONTH));
//        }
//        if (rentFreeStartDate != null && rentFreeEndDate != null) {
//            int months = getMonths(rentFreeStartDate, rentFreeEndDate);
//            param.put("${rentFreeMon}", months);
//        }


        // 7.3运营免租期
        param.put("${operaRentFreeStart}", reqMap.get("operaRentFreeStart"));
        param.put("${operaRentFreeEnd}", reqMap.get("operaRentFreeEnd"));
        param.put("${operaRentFreeMonSum}", reqMap.get("operaRentFreeMonSum"));


//        param.put("${operaRentFreeYearStart}", null);
//        param.put("${operaRentFreeMonStart}", null);
//        param.put("${operaRentFreeDayStart}", null);
//        param.put("${operaRentFreeYearEnd}", null);
//        param.put("${operaRentFreeMonEnd}", null);
//        param.put("${operaRentFreeDayEnd}", null);
//        param.put("${operaRentFreeMonSum}", null);
//
//        String operaRentFreeStart = reqMap.get("operaRentFreeStart");
//        Date operaRentFreeStartDate = null;
//        if (!StringUtils.isEmpty(operaRentFreeStart)) {
//            operaRentFreeStartDate = df1.parse(operaRentFreeStart);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(operaRentFreeStartDate);
//            param.put("${operaRentFreeYearStart}", calendar.get(Calendar.YEAR));
//            param.put("${operaRentFreeMonStart}", calendar.get(Calendar.MONTH) + 1);
//            param.put("${operaRentFreeDayStart}", calendar.get(Calendar.DAY_OF_MONTH));
//        }
//        String operaRentFreeEnd = reqMap.get("operaRentFreeEnd");
//        Date operaRentFreeEndDate = null;
//        if (!StringUtils.isEmpty(operaRentFreeEnd)) {
//            operaRentFreeEndDate = df1.parse(operaRentFreeEnd);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(operaRentFreeEndDate);
//            param.put("${operaRentFreeYearEnd}", calendar.get(Calendar.YEAR));
//            param.put("${operaRentFreeMonEnd}", calendar.get(Calendar.MONTH) + 1);
//            param.put("${operaRentFreeDayEnd}", calendar.get(Calendar.DAY_OF_MONTH));
//        }
//        if (operaRentFreeStartDate != null && operaRentFreeEndDate != null) {
//            int months = getMonths(operaRentFreeStartDate, operaRentFreeEndDate);
//            param.put("${operaRentFreeMonSum}", months);
//        }


        // 7.5 租金应按如下期限及方式支付
        param.put("${firstRentStart}", reqMap.get("firstRentStart"));
        param.put("${firstRentEnd}", reqMap.get("firstRentEnd"));
        param.put("${firstRentMoney}", reqMap.get("firstRentMoney"));


//        param.put("${firstRentYearStart}", null);
//        param.put("${firstRentMonStart}", null);
//        param.put("${firstRentDayStart}", null);
//        param.put("${firstRentYearEnd}", null);
//        param.put("${firstRentMonEnd}", null);
//        param.put("${firstRentDayEnd}", null);
//
//        param.put("${firstRentMoney}", reqMap.get("firstRentMoney"));
//        String firstRentStart = reqMap.get("firstRentStart");
//        Date firstRentStartDate = null;
//        if (!StringUtils.isEmpty(firstRentStart)) {
//            firstRentStartDate = df1.parse(firstRentStart);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(firstRentStartDate);
//            param.put("${firstRentYearStart}", calendar.get(Calendar.YEAR));
//            param.put("${firstRentMonStart}", calendar.get(Calendar.MONTH) + 1);
//            param.put("${firstRentDayStart}", calendar.get(Calendar.DAY_OF_MONTH));
//        }
//        String firstRentEnd = reqMap.get("firstRentEnd");
//        Date firstRentEndDate = null;
//        if (!StringUtils.isEmpty(firstRentEnd)) {
//            firstRentEndDate = df1.parse(firstRentEnd);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(firstRentEndDate);
//            param.put("${firstRentYearEnd}", calendar.get(Calendar.YEAR));
//            param.put("${firstRentMonEnd}", calendar.get(Calendar.MONTH) + 1);
//            param.put("${firstRentDayEnd}", calendar.get(Calendar.DAY_OF_MONTH));
//        }


        // 9． 履约保证金
        param.put("${keepMon}", reqMap.get("keepMon"));
        param.put("${keepMoney}", reqMap.get("keepMoney"));

        // 20 ．保险
        param.put("${allLimitMoney}", reqMap.get("allLimitMoney"));
        param.put("${oneLimitMoney}", reqMap.get("oneLimitMoney"));

        // 通知联系
        param.put("${firstAddressConfirm}", reqMap.get("firstAddressConfirm"));
        param.put("${secondAddressConfirm}", reqMap.get("secondAddressConfirm"));

        // 备注
        param.put("${remark}", reqMap.get("remark"));

        // 设置Content-Disposition头 以附件形式解析
        String fileName = reqMap.get("companyName");
        if (StringUtils.isEmpty(fileName)) {
            fileName = "荟智企业中心租赁合同";
        }
        fileName = URLEncoder.encode(fileName, "UTF-8"); //解决下载文件名乱码
        response.setHeader("Content-Disposition", "attchment;filename=" + fileName + ".docx");


        ServletOutputStream outputStream = response.getOutputStream();


//        WordUtil.generateWord(param, "C:\\Users\\heart\\Desktop\\wb\\tmp\\tpl.docx", outputStream);
//        WordUtil.generateWord(param, "/templates/rentContract.docx", outputStream);
        WordUtil.generateContractWord(param, outputStream);

        outputStream.flush();
        outputStream.close();

        // 替换文档关键字
//        WordUtil.generateWord(param, "C:\\Users\\heart\\Desktop\\wb\\tmp\\tpl.docx", "C:\\Users\\heart\\Desktop\\wb\\tmp\\tpl-11.docx");

    }


    @RequestMapping("excel")
    public void excel(HttpServletResponse response, HttpServletRequest request) throws Exception {
        response.setCharacterEncoding("GBK");
        //设置响应的内容类型
        response.setContentType("application/octet-stream");

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int pageSize = 10000;
        int curPage = 1;

        String exportReq = request.getParameter("req");
        log.info("csv exportReq={}", exportReq);
        if (StringUtils.isEmpty(exportReq)) {
            log.error("exportReq 不能为空");
            throw new RuntimeException("exportReq 不能为空");
        }
        JSONObject exportReqJson = JSON.parseObject(exportReq);

        String uuid = exportReqJson.getString("uuid");
        if (StringUtils.isEmpty(uuid)) {
            log.error("uuid 不能为空");
            throw new RuntimeException("uuid 不能为空");
        }

        String fileName = exportReqJson.getString("fileName");
        log.info("csv fileName={}", fileName);
        if (StringUtils.isEmpty(fileName)) {
            log.error("fileName 不能为空");
            throw new RuntimeException("fileName 不能为空");
        }

        String qrySrvName = exportReqJson.getString("qrySrvName");
        log.info("csv qrySrvName={}", qrySrvName);
        if (StringUtils.isEmpty(qrySrvName)) {
            log.error("qrySrvName 不能为空");
            throw new RuntimeException("qrySrvName 不能为空");
        }

        String[] qrySrvNames = qrySrvName.split("[.]");
        if (qrySrvNames == null || qrySrvNames.length != 2) {
            log.error("qrySrvName 格式错误");
            throw new RuntimeException("qrySrvName 格式错误");
        }
        String srvName = qrySrvNames[0];
        String srvMethod = qrySrvNames[1];


        String pageSizeReq = exportReqJson.getString("pageSize");
        log.info("csv pageSizeReq={}", pageSizeReq);
        if (!StringUtils.isEmpty(pageSizeReq)) {
            pageSize = Integer.parseInt(pageSizeReq);
        }

        String title = exportReqJson.getString("title");
        log.info("csv title={}", title);
        if (StringUtils.isEmpty(title)) {
            log.error("title 不能为空");
            throw new RuntimeException("title 不能为空");
        }

        LinkedHashMap<String, String> titleMap = JSON.parseObject(title, LinkedHashMap.class);
        log.info("titleMap={}", JSONObject.toJSONString(titleMap));


        String dataReq = exportReqJson.getString("data");
        log.info("csv dataReq={}", dataReq);

        String enumsStr = exportReqJson.getString("enums");
        log.info("csv enums={}", enumsStr);
        Map<String, List<Map<String, String>>> enumsMap = new HashMap<>();
        if (!StringUtils.isEmpty(enumsStr)) {
            enumsMap = JSON.parseObject(enumsStr, Map.class);
        }

        Object bean = SpringUtils.getBean(srvName);
//        Object bean = contractService;
        if (bean == null) {
            log.error("根据 srvName={} 获取服务失败", srvName);
            throw new RuntimeException("根据 srvName=" + srvName + " 获取服务失败");
        }


        Class<?> clazz = bean.getClass();
        List<Method> methods = new ArrayList<>();
        while (clazz != null) {  // 遍历所有父类字节码对象
            Method[] declaredMethods = clazz.getDeclaredMethods();
            methods.addAll(Arrays.asList(declaredMethods));
            clazz = clazz.getSuperclass();
        }
        Method qrySrvMethod = null;
        for (Method method : methods) {
            String methodName = method.getName();

            if (srvMethod.equals(methodName)) {
                qrySrvMethod = method;
                break;
            }
        }

        if (qrySrvMethod == null) {
            log.error("{} 方法未找到", qrySrvName);
            throw new RuntimeException(qrySrvName + " 方法未找到");
        }

        log.info("获取 {} 方法的请求类型", qrySrvName);
//        Class<?>[] parameterTypes = qrySrvMethod.getParameterTypes();
//        if (parameterTypes != null && parameterTypes.length > 0) {
//            Class<?> parameterType = parameterTypes[0];
//            if (!StringUtils.isEmpty(dataReq)) {
//                JSON.parseObject(dataReq, parameterType);
//            }
//        }

//        Method declaredMethod = bean.class.getDeclaredMethod("getRentSmsNotice", CommonReq.class);
        //获取到方法的参数列表
        String qryParamType = null;
        Type[] parameterTypes = qrySrvMethod.getGenericParameterTypes();
        for (Type type : parameterTypes) {
            log.info("qrySrvMethod 请求类型 {}", type);
            //只有带泛型的参数才是这种Type，所以得判断一下
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                //获取参数的类型
//                log.info("getRawType 请求类型 {}", parameterizedType.getRawType());
//                System.out.println(parameterizedType.getRawType());
                //获取参数的泛型列表
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                qryParamType = actualTypeArguments[0].getTypeName();
//                for (Type typeAc: actualTypeArguments) {
//                    System.out.println(typeAc);
//                }
            }
        }

        log.info("qryParamType:{}", qryParamType);

        if (qryParamType == null) {
            log.error("获取请求参数VO类型失败");
            throw new RuntimeException("获取请求参数VO类型失败");
        }

        log.info("构建请求");
        CommonReq req = new CommonReq<>();
        req.setPageNum(curPage);
        req.setPageSize(pageSize);
        if (!StringUtils.isEmpty(dataReq)) {
//            req.setData(JSON.parseObject(dataReq, Map.class));
            req.setData(JSON.parseObject(dataReq, Class.forName(qryParamType)));
        }


        log.info("开始调用服务");
        log.info("req={}", JSONObject.toJSONString(req));
        Object invokeRsp = qrySrvMethod.invoke(bean, new Object[]{req});// 请求

        List list = new LinkedList();
        if (invokeRsp != null) {
            PageInfo rsp = (PageInfo) invokeRsp;
            // 获取总页数
            Integer pages = rsp.getPages();
            log.info("总共有{}页", pages);
            // 获取总行数
            Long total = rsp.getTotal();
            log.info("总共有{}行数据", total);

            list.addAll(rsp.getList());

            while (curPage < pages) {
                curPage += 1;
                log.info("继续查询,页码curPage={}", curPage);
                req.setPageNum(curPage);
                log.info("req={}", JSONObject.toJSONString(req));
                invokeRsp = qrySrvMethod.invoke(bean, new Object[]{req});// 请求
                if (invokeRsp != null) {
                    rsp = (PageInfo) invokeRsp;
                    list.addAll(rsp.getList());
                }
            }
        }

        log.info("结束分页查询结果, 总导出行数={}", list.size());

        // 设置Content-Disposition头 以附件形式解析
        String orgFileName = fileName;
        fileName = URLEncoder.encode(fileName, "UTF-8"); //解决下载文件名乱码
        response.setHeader("Content-Disposition", "attchment;filename=" + fileName + ".xlsx");

        ServletOutputStream outputStream = response.getOutputStream();

//        String title = "楼宇管理";
//        String[] rowsName = new String[]{"楼层", "楼盘","房号", "面积", "租金周期", "租金单价", "租赁状态", "入租企业", "备注"};

        ExportExcelUtils ex = new ExportExcelUtils(orgFileName, titleMap, list);
        try {
            ex.export(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        outputStream.flush();
        outputStream.close();

    }


    private int getMonths(Date date1, Date date2) {
        int iMonth = 0;
        int flag = 0;
        try {
            Calendar objCalendarDate1 = Calendar.getInstance();
            objCalendarDate1.setTime(date1);
            Calendar objCalendarDate2 = Calendar.getInstance();
            objCalendarDate2.setTime(date2);
            if (objCalendarDate2.equals(objCalendarDate1)) return 0;
            if (objCalendarDate1.after(objCalendarDate2)) {
                Calendar temp = objCalendarDate1;
                objCalendarDate1 = objCalendarDate2;
                objCalendarDate2 = temp;
            }
            if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1.get(Calendar.DAY_OF_MONTH)) flag = 1;
            if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR))
                iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR))
                        * 12 + objCalendarDate2.get(Calendar.MONTH) - flag)
                        - objCalendarDate1.get(Calendar.MONTH);
            else
                iMonth = objCalendarDate2.get(Calendar.MONTH)
                        - objCalendarDate1.get(Calendar.MONTH) - flag;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iMonth;
    }


    public static void main(String[] args) throws Exception {
//        Class<ContractService> contractServiceClass = ContractService.class;
//
//        ContractServiceImpl contractService = new ContractServiceImpl();
//        contractService.getRentSmsNotice();

        //获取参数泛型
        //通过反射获取到方法
//        Method[] declaredMethods = ContractService.class.getDeclaredMethods();
//        for (Method declaredMethod : declaredMethods) {
//            System.out.println(declaredMethod.getName());
//        }
//        Method declaredMethod = ContractService.class.getDeclaredMethod("getRentSmsNotice", CommonReq.class);
//        //获取到方法的参数列表
//        Type[] parameterTypes = declaredMethod.getGenericParameterTypes();
//        for (Type type : parameterTypes) {
//            System.out.println(type);
//            //只有带泛型的参数才是这种Type，所以得判断一下
//            if (type instanceof ParameterizedType) {
//                ParameterizedType parameterizedType = (ParameterizedType) type;
//                //获取参数的类型
//                System.out.println(parameterizedType.getRawType());
//                //获取参数的泛型列表
//                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
//                for (Type typeAc : actualTypeArguments) {
//                    System.out.println(typeAc);
//                }
//            }
//        }
    }
}
