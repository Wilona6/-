package com.code.classsystem.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.code.classsystem.dao.AnnouncementMapper;
import com.code.classsystem.entity.Announcement;
import com.code.classsystem.entity.Paper;
import com.code.classsystem.service.AnnouncementService;
import com.code.classsystem.vo.PaperInfoVo;
import com.code.classsystem.vo.PaperResultBinVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService {
    @Autowired
    private AnnouncementMapper announcementMapper;


    @Override
    public PageInfo<Announcement> listPage(Announcement announcement, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Announcement> data = announcementMapper.listPage(announcement);
        PageInfo<Announcement> info = new PageInfo<>(data);
        return info;
    }

    @Override
    public Integer saveOrUpdateAnnouncement(Announcement announcement) {
        if(announcement.getAnnouncementId() == null){
            return announcementMapper.insert(announcement);
        }else {
            return announcementMapper.updateById(announcement);
        }
    }


}
