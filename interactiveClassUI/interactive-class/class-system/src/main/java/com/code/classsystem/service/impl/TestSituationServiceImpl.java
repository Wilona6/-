package com.code.classsystem.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.code.classsystem.common.shiro.util.ShiroUtils;
import com.code.classsystem.dao.*;
import com.code.classsystem.entity.Course;
import com.code.classsystem.entity.HomeWork;
import com.code.classsystem.entity.Paper;
import com.code.classsystem.entity.TestSituation;
import com.code.classsystem.service.TestSituationService;
import com.code.classsystem.vo.PaperInfoVo;
import com.code.core.util.DateUtils;
import com.code.core.util.UUIDUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author coder
 * @since 2022-04-22
 */
@Service
public class TestSituationServiceImpl extends ServiceImpl<TestSituationMapper, TestSituation> implements TestSituationService {

    @Autowired
    private TestSituationMapper testSituationMapper;
    @Autowired
    private PaperMapper paperMapper;
    @Autowired
    private HomeWorkMapper homeWorkMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean createTestSituation(TestSituation testSituation) {
        return this.insert(testSituation);
    }

    @Override
    public PageInfo<TestSituation> listPage(TestSituation testSituation, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TestSituation> paperInfoVoList=testSituationMapper.listPage(testSituation);
        for (TestSituation situation : paperInfoVoList) {
            Paper paper = paperMapper.selectById(situation.getPaperId());
            HomeWork homeWork = homeWorkMapper.selectById(situation.getHomeworkId());
            Course course = courseMapper.selectById(situation.getCourseId());
            if(course==null){
                situation.setCourseName("无");
            }else{
                situation.setCourseName(course.getCourseName());
            }
            if(paper==null){
                situation.setPaperName("未上传");
                situation.setQuestionUrl("无");
            }else{
                situation.setPaperName(paper.getPaperName());
            }
            if(homeWork==null){
                situation.setWorkTitle("未上传");
                situation.setHomeworkUrl("无");
            }else{
                situation.setWorkTitle(homeWork.getWorkTitle());
            }
            situation.setUsername(userMapper.getUserInfoByUserId(situation.getUserId()).getName());
        }
        return new PageInfo<>(paperInfoVoList);
    }

    @Override
    public boolean update(TestSituation testSituation) {
        if(testSituation.getHomeworkId()!=null){
            //上传作业的
            TestSituation testSituation1 = new TestSituation();
            testSituation1.setHomeworkId(testSituation.getHomeworkId());
            testSituation1.setUserId(testSituation.getUserId());
            List<TestSituation> testSituations = testSituationMapper.listPage(testSituation1);
            if(testSituations.size()!=0){
                //有这个人的作业
                return testSituationMapper.update(testSituation);
            }else{
                return this.insert(testSituation);
            }
        }

        if(testSituation.getPaperId()!=null){
            //上传试卷的
            TestSituation testSituation2 = new TestSituation();
            testSituation2.setPaperId(testSituation.getPaperId());
            testSituation2.setUserId(testSituation.getUserId());
            List<TestSituation> testSituations = testSituationMapper.listPage(testSituation2);
            if(testSituations.size()!=0){
                //有这个人的作业
                return testSituationMapper.update(testSituation);
            }else{
                return this.insert(testSituation);
            }
        }
        if(testSituation.getCourseId()==null){
            //标记学习的
            TestSituation testSituation2 = new TestSituation();
            testSituation2.setUserId(testSituation.getUserId());
            testSituation2.setResId(testSituation.getResId());
            List<TestSituation> testSituations = testSituationMapper.listPage(testSituation2);
            if(testSituations.size()==0){
                //有这个人的作业
                return this.insert(testSituation);
            }
        }
        return true;
    }
}
