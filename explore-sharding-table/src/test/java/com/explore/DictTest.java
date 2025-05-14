package com.explore;

import com.explore.entity.DictData;
import com.explore.entity.DictType;
import com.explore.mapper.DictDataMapper;
import com.explore.mapper.DictTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 字典业务 测试类
 * @author HaiQing.Yu
 * @since 2025/5/14 10:28
 */
@Slf4j
@SpringBootTest
public class DictTest {

    @Resource
    private DictTypeMapper dictTypeMapper;
    @Resource
    private DictDataMapper dictDataMapper;


    @Test
    public void createDictType() {
        DictType dictType = new DictType();
        dictType.setTypeCode("SYS_USER_SEX");
        dictType.setTypeName("用户性别");
        dictType.setRemark("系统用户性别字典");
        dictType.setCreatedTime(LocalDateTime.now());
        int insert = dictTypeMapper.insert(dictType);
        log.info("新增字典类型受影响行数: {}", insert);
    }

    @Test
    public void createDictDate() {

//        DictData dictData1 = new DictData();
//        dictData1.setTypeCode("SYS_USER_SEX");
//        dictData1.setDictCode("0");
//        dictData1.setDictValue("0");
//        dictData1.setDictLabel("未知");
//        dictData1.setSort(1);
//        dictData1.setCreatedTime(LocalDateTime.now());
//        dictDataMapper.insert(dictData1);

        DictData dictData2 = new DictData();
        dictData2.setTypeCode("SYS_USER_SEX");
        dictData2.setDictCode("1");
        dictData2.setDictValue("1");
        dictData2.setDictLabel("男");
        dictData2.setSort(2);
        dictData2.setCreatedTime(LocalDateTime.now());
        dictDataMapper.insert(dictData2);

        DictData dictData3 = new DictData();
        dictData3.setTypeCode("SYS_USER_SEX");
        dictData3.setDictCode("2");
        dictData3.setDictValue("2");
        dictData3.setDictLabel("女");
        dictData3.setSort(3);
        dictData3.setCreatedTime(LocalDateTime.now());
        dictDataMapper.insert(dictData3);

    }

    @Test
    public void queryDictType() {
        List<DictType> dictTypes = dictTypeMapper.selectList(null);
        log.info("dictTypes: {}", dictTypes);
    }

    @Test
    public void queryDictData() {
        List<DictData> dictDatas = dictDataMapper.selectList(null);
        log.info("dictDatas: {}", dictDatas);
    }

}
