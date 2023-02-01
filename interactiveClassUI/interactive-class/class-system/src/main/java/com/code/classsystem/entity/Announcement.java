package com.code.classsystem.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {

    @TableId(type = IdType.AUTO)
    private Long announcementId;

    private String announcementTitle;

    private String announcementDetails;

    private Integer announcementPublishers;

    private Integer type;

    private String classId;

    private String courseId;

    @TableLogic(delval = "1",value = "0")
    private Integer isDelete;
}
