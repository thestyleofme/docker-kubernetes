package com.imooc.course.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.imooc.course.dto.CourseDTO;
import com.imooc.course.mapper.CourseMapper;
import com.imooc.course.thrift.ServiceProvider;
import com.imooc.thrift.user.UserInfo;
import com.imooc.thrift.user.dto.TeacherDTO;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ServiceProvider serviceProvider;

    @Override
    public List<CourseDTO> courseList() {
        List<CourseDTO> courseDTOList = courseMapper.courseList();
        if (courseDTOList != null) {
            for (CourseDTO courseDTO : courseDTOList) {
                Integer userId = courseMapper.getCourseTeacher(courseDTO.getId());
                if (userId != null) {
                    try {
                        UserInfo userInfo = serviceProvider.getUserService().getTeacherById(userId);
                        courseDTO.setTeacher(trans2TeacherDto(userInfo));
                    } catch (TException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }
        return courseDTOList;
    }

    private TeacherDTO trans2TeacherDto(UserInfo userInfo) {
        TeacherDTO teacherDTO = new TeacherDTO();
        BeanUtils.copyProperties(userInfo, teacherDTO);
        return teacherDTO;
    }
}
