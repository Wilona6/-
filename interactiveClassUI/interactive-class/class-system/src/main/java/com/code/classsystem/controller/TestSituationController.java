package com.code.classsystem.controller;


import com.code.classsystem.common.shiro.util.ShiroUtils;
import com.code.classsystem.entity.Paper;
import com.code.classsystem.entity.TestSituation;
import com.code.classsystem.entity.User;
import com.code.classsystem.service.TestSituationService;
import com.code.classsystem.vo.PaperInfoVo;
import com.code.core.entity.ResponseResult;
import com.code.core.util.ResponseResultUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author coder
 * @since 2022-04-22
 */
@RestController
@RequestMapping("/testSituation")
public class TestSituationController {

    @Autowired
    private TestSituationService testSituationService;

    @PostMapping("/createPaper")
    public ResponseResult createPaper(TestSituation testSituation) {
        testSituationService.createTestSituation(testSituation);
        return ResponseResultUtil.renderSuccessMsg("成功！");
    }

    @RequestMapping("/listPage")
    public ResponseResult listPage(TestSituation testSituation, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize) {
        String userId = ShiroUtils.getUserId();
        User userEntity = ShiroUtils.getUserEntity();
        if(userEntity.getRoleId()==1){
            //学生
            testSituation.setUserId(userId);
        }
        //String userId = "68b9a2f57443803491f8bec131d1c254";
        //testSituation.setUserId(userId);
        PageInfo<TestSituation> testSituationPageInfo=testSituationService.listPage(testSituation,pageNum,pageSize);
        return ResponseResultUtil.renderSuccess(testSituationPageInfo, "成功");
    }

    @RequestMapping("/update")
    public ResponseResult update(@RequestBody TestSituation testSituation) {
        String userId = ShiroUtils.getUserId();
        //String userId = "68b9a2f57443803491f8bec131d1c254";
        testSituation.setUserId(userId);
        testSituationService.update(testSituation);
        return ResponseResultUtil.renderSuccessMsg("成功！");
    }
}

