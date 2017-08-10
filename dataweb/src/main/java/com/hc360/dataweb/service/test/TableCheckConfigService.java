package com.hc360.dataweb.service.test;

import com.hc360.dataweb.dao.BaseDao;
import com.hc360.dataweb.dao.test.TableCheckConfigDao;
import com.hc360.dataweb.model.TableCheckConfig;
import com.hc360.dataweb.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by home on 2017/2/5.
 */
@Service
public class TableCheckConfigService extends BaseServiceImpl<TableCheckConfig, Integer> {
    @Autowired
    private TableCheckConfigDao tableCheckConfigDao;
    @Override
    public BaseDao<TableCheckConfig, Integer> getDao() {
        return tableCheckConfigDao;
    }
}
