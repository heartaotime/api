package com.open.custom.api.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.open.custom.api.mapper.OpenStaticDataMapper;
import com.open.custom.api.model.OpenStaticData;
import com.open.custom.api.model.OpenStaticDataExample;
import com.open.custom.api.service.IOpenStaticDataService;
import com.open.custom.api.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Service("openStaticDataServiceImpl")
public class OpenStaticDataServiceImpl implements IOpenStaticDataService {

    @Autowired
    private OpenStaticDataMapper openStaticDataMapper;

    @Autowired
    private RedisService redisService;

    @Value("${custCacheKey.OPEN_STATIC_DATA}")
    private String OPEN_STATIC_DATA;

    @Override
    public int countByExample(OpenStaticDataExample example) {
        return openStaticDataMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(OpenStaticDataExample example) {
        return openStaticDataMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return openStaticDataMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OpenStaticData record) {
        return openStaticDataMapper.insert(record);
    }

    @Override
    public int insertSelective(OpenStaticData record) {
        return openStaticDataMapper.insertSelective(record);
    }

    @Override
    public List<OpenStaticData> selectByExampleWithBLOBs(OpenStaticDataExample example) {
        return openStaticDataMapper.selectByExampleWithBLOBs(example);
    }

    @Override
    public List<OpenStaticData> selectByExample(OpenStaticDataExample example) {
        return openStaticDataMapper.selectByExample(example);
    }

    @Override
    public OpenStaticData selectByPrimaryKey(Integer id) {
        return openStaticDataMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(OpenStaticData record, OpenStaticDataExample example) {
        return openStaticDataMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExampleWithBLOBs(OpenStaticData record, OpenStaticDataExample example) {
        return openStaticDataMapper.updateByExampleWithBLOBs(record, example);
    }

    @Override
    public int updateByExample(OpenStaticData record, OpenStaticDataExample example) {
        return openStaticDataMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(OpenStaticData record) {
        return openStaticDataMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(OpenStaticData record) {
        return openStaticDataMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int updateByPrimaryKey(OpenStaticData record) {
        return openStaticDataMapper.updateByPrimaryKey(record);
    }


    @Override
    public List<OpenStaticData> getStaticDataByCodeType(String codeType) {
        if (StringUtils.isEmpty(codeType)) {
            return null;
        }
        List<OpenStaticData> resData = new ArrayList<>();
        Object cacheData = redisService.get(OPEN_STATIC_DATA);
        if (cacheData != null) {
            List<OpenStaticData> allData = (List) cacheData;
            for (OpenStaticData item : allData) {
                if (codeType.equals(item.getCodeType())) {
                    resData.add(item);
                }
            }
        }
        return resData;
    }

    @Override
    public List<OpenStaticData> getStaticDataByCodeType(String codeType, Comparator comparator) {
        if (StringUtils.isEmpty(codeType)) {
            return null;
        }
        List<OpenStaticData> resData = new ArrayList<>();
        Object cacheData = redisService.get(OPEN_STATIC_DATA);
        if (cacheData != null) {
            List<OpenStaticData> allData = (List) cacheData;
            for (OpenStaticData item : allData) {
                if (codeType.equals(item.getCodeType())) {
                    resData.add(item);
                }
            }
        }
        if (comparator != null) {
            Collections.sort(resData, comparator);
        }
        return resData;
    }

    @Override
    public PageInfo<OpenStaticData> getStaticDataByCodeType(String codeType, int pageNum, int pageSize) {
        PageInfo<OpenStaticData> pageInfo = new PageInfo<OpenStaticData>();
        List<OpenStaticData> allData = getStaticDataByCodeType(codeType);
        if (!CollectionUtils.isEmpty(allData)) {

            int total = allData.size();
            int pageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
            int fromIndex = pageSize * (pageNum - 1);
            int toIndex = fromIndex + pageSize;
            if (toIndex >= total) {
                toIndex = total;
            }
            if (pageNum > pageCount) {
                fromIndex = 0;
                toIndex = 0;
            }

            List<OpenStaticData> openStaticData = allData.subList(fromIndex, toIndex);

            pageInfo.setTotal(total);
            pageInfo.setPageNum(pageNum);
            pageInfo.setPageSize(pageSize);
            pageInfo.setSize(openStaticData.size());
            // pageInfo.setStartRow(pageInfo.getStartRow());
            // pageInfo.setEndRow(pageInfo.getEndRow());
            pageInfo.setPages(pageCount);
            pageInfo.setPrePage(pageNum - 1);
            pageInfo.setNextPage(pageNum + 1);
            pageInfo.setIsFirstPage(pageNum == 1);
            pageInfo.setIsLastPage(pageNum == pageCount);
            pageInfo.setHasPreviousPage(pageNum != 1);
            pageInfo.setHasNextPage(pageNum < pageCount);
            // pageInfo.setNavigatePages(pageInfo.getNavigatePages());
            // pageInfo.setNavigatepageNums(pageInfo.getNavigatepageNums());
            // pageInfo.setNavigateFirstPage(pageInfo.getNavigateFirstPage());
            // pageInfo.setNavigateLastPage(pageInfo.getNavigateLastPage());
            // System.out.println(allData.subList(fromIndex, toIndex));

            pageInfo.setList(openStaticData);
        }
        return pageInfo;
    }


    @Override
    public PageInfo<OpenStaticData> getStaticDataByCodeType(String codeType, int pageNum, int pageSize, Comparator comparator) {
        PageInfo<OpenStaticData> pageInfo = new PageInfo<OpenStaticData>();
        List<OpenStaticData> allData = new ArrayList<>();

        if (comparator != null) {
            allData = getStaticDataByCodeType(codeType, comparator);
        } else {
            allData = getStaticDataByCodeType(codeType);
        }


        if (!CollectionUtils.isEmpty(allData)) {

            int total = allData.size();
            int pageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
            int fromIndex = pageSize * (pageNum - 1);
            int toIndex = fromIndex + pageSize;
            if (toIndex >= total) {
                toIndex = total;
            }
            if (pageNum > pageCount) {
                fromIndex = 0;
                toIndex = 0;
            }

            List<OpenStaticData> openStaticData = allData.subList(fromIndex, toIndex);

            pageInfo.setTotal(total);
            pageInfo.setPageNum(pageNum);
            pageInfo.setPageSize(pageSize);
            pageInfo.setSize(openStaticData.size());
            // pageInfo.setStartRow(pageInfo.getStartRow());
            // pageInfo.setEndRow(pageInfo.getEndRow());
            pageInfo.setPages(pageCount);
            pageInfo.setPrePage(pageNum - 1);
            pageInfo.setNextPage(pageNum + 1);
            pageInfo.setIsFirstPage(pageNum == 1);
            pageInfo.setIsLastPage(pageNum == pageCount);
            pageInfo.setHasPreviousPage(pageNum != 1);
            pageInfo.setHasNextPage(pageNum < pageCount);
            // pageInfo.setNavigatePages(pageInfo.getNavigatePages());
            // pageInfo.setNavigatepageNums(pageInfo.getNavigatepageNums());
            // pageInfo.setNavigateFirstPage(pageInfo.getNavigateFirstPage());
            // pageInfo.setNavigateLastPage(pageInfo.getNavigateLastPage());
            // System.out.println(allData.subList(fromIndex, toIndex));

            pageInfo.setList(openStaticData);
        }
        return pageInfo;
    }

    @Override
    public void saveData2Redis() {
        List<OpenStaticData> allData = new ArrayList<>();

        int pageNum = 1;
        int pageSize = 50;
        PageHelper.startPage(pageNum, pageSize);

        OpenStaticDataExample example = new OpenStaticDataExample();
        OpenStaticDataExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(1);
        example.setOrderByClause(" code_type, sort, id desc ");
        List<OpenStaticData> openStaticData = selectByExampleWithBLOBs(example);
        if (!CollectionUtils.isEmpty(openStaticData)) {
            // 取分页信息
            PageInfo<OpenStaticData> pageInfo = new PageInfo<>(openStaticData);
            boolean hasNextPage = pageInfo.isHasNextPage();
            allData.addAll(openStaticData);
            while (hasNextPage) {
                pageNum = pageInfo.getNextPage();
                PageHelper.startPage(pageNum, pageSize);
                openStaticData = selectByExampleWithBLOBs(example);
                allData.addAll(openStaticData);

                pageInfo = new PageInfo<>(openStaticData);
                hasNextPage = pageInfo.isHasNextPage();
            }
        }
        redisService.del(OPEN_STATIC_DATA);
        redisService.set(OPEN_STATIC_DATA, allData);
    }

//    @Override
//    public List<OpenStaticData> getStaticDataByCodeType(String codeType) {
//        if (StringUtils.isEmpty(codeType)) {
//            return null;
//        }
//        Object cacheData = redisService.hget(OPEN_STATIC_DATA, codeType);
//        List<OpenStaticData> openStaticDatas = new ArrayList<>();
//        if (cacheData == null) {
//            OpenStaticDataExample example = new OpenStaticDataExample();
//            OpenStaticDataExample.Criteria criteria = example.createCriteria();
//            criteria.andCodeTypeEqualTo(codeType);
//            criteria.andStateEqualTo(1);
//            example.setOrderByClause(" sort asc ");
//            openStaticDatas = selectByExample(example);
//            redisService.hset(OPEN_STATIC_DATA, codeType, openStaticDatas);
//        } else {
//            openStaticDatas = (List<OpenStaticData>) cacheData;
//        }
//        return openStaticDatas;
//    }
}
