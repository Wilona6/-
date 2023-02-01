package com.code.classsystem.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.code.classsystem.entity.Paper;
import com.code.classsystem.entity.TestSituation;
import com.code.classsystem.vo.PaperInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author coder
 * @since 2022-04-22
 */
public interface TestSituationMapper extends BaseMapper<TestSituation> {
    List<TestSituation> listPage(TestSituation testSituation);
    boolean update(TestSituation testSituation);

    List<TestSituation> selectByCourseIds(@Param("list") List<String> courseIds);
}
