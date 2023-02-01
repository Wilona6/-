package com.code.classsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.code.classsystem.entity.Paper;
import com.code.classsystem.entity.TestSituation;
import com.code.classsystem.vo.PaperInfoVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author coder
 * @since 2022-04-22
 */
public interface TestSituationService extends IService<TestSituation> {

    boolean createTestSituation(TestSituation testSituation);

    PageInfo<TestSituation> listPage(TestSituation testSituation, int pageNum, int pageSize);

    boolean update(TestSituation testSituation);
}
