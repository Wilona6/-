package com.code.classsystem.controller;

import com.code.classsystem.entity.Announcement;
import com.code.classsystem.entity.Paper;
import com.code.classsystem.service.AnnouncementService;
import com.code.classsystem.vo.AnnouncementVo;
import com.code.classsystem.vo.PaperInfoVo;
import com.code.classsystem.vo.PaperResultBinVo;
import com.code.core.entity.ResponseResult;
import com.code.core.util.ResponseResultUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author coder
 * @since 2022-04-5
 */
@RestController
@RequestMapping("/announcement")
@Api(value = "公告接口", tags = "公告接口")
public class

AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @ApiOperation(value = "分页查询列表")
    @GetMapping("/listPage")
    public ResponseResult listPage(Announcement announcement,@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize) {
        PageInfo<Announcement> paperInfoVoList=announcementService.listPage(announcement,pageNum,pageSize);
        return ResponseResultUtil.renderSuccess(paperInfoVoList, "退分页查找公告成功");
    }

    @ApiOperation(value = "新增或编辑公告")
    @PostMapping("/saveOrUpdate")
    public ResponseResult saveOrUpdateAnnouncement(@RequestBody Announcement announcement){
        return  ResponseResultUtil.renderSuccess(announcementService.saveOrUpdateAnnouncement(announcement));
    }

    @ApiOperation(value = "根据ID查询详情")
    @GetMapping("/getById/{id}")
    public ResponseResult getAnnouncementById(@PathVariable("id") String id){
        return ResponseResultUtil.renderSuccess(announcementService.selectById(id));
    }

    @ApiOperation(value = "根据ID删除数据")
    @PostMapping("/delById/{ids}")
    public ResponseResult delPaper(@PathVariable("ids") String ids) {
        boolean b = announcementService.deleteBatchIds(Arrays.asList(ids.split(",")));
        return ResponseResultUtil.renderSuccess("成功删除");
    }



}

