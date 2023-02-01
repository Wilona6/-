package com.code.classsystem.vo;

import com.code.classsystem.entity.Announcement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementVo extends Announcement {
    private int page;

    private int limit;

}
