package com.code.classsystem.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.code.classsystem.entity.Announcement;

import java.util.List;

public interface AnnouncementMapper extends BaseMapper<Announcement> {

    List<Announcement> listPage(Announcement announcement);

}
