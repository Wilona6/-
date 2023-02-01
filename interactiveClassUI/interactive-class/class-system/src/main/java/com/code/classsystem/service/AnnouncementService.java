package com.code.classsystem.service;

import com.code.classsystem.entity.Announcement;
import com.code.classsystem.entity.Paper;
import com.baomidou.mybatisplus.service.IService;
import com.code.classsystem.vo.PaperInfoVo;
import com.code.classsystem.vo.PaperResultBinVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author coder
 * @since 2020-04-18
 */
public interface AnnouncementService extends IService<Announcement> {

    PageInfo<Announcement> listPage(Announcement announcement, int pageNum, int pageSize);

    Integer saveOrUpdateAnnouncement(Announcement announcement);
}
