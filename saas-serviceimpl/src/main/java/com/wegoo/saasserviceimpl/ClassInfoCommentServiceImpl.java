package com.wegoo.saasserviceimpl;

import com.wegoo.exception.BSUtil;
import com.wegoo.model.po.ClassInfoComment;
import com.wegoo.saasdao.mapper.ClassInfoCommentMapper;
import com.wegoo.saasservice.ClassInfoCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassInfoCommentServiceImpl implements ClassInfoCommentService {
    @Autowired
    private ClassInfoCommentMapper classInfoCommentMapper;
    @Override
    public List<ClassInfoComment> getClassInfoCommentList(ClassInfoComment classInfoComment) {
        return classInfoCommentMapper.getClassInfoCommentList(classInfoComment);
    }

    @Override
    public int addClassInfoComment(ClassInfoComment classInfoComment) {
        String comment = classInfoComment.getComment();
        if(10>comment.length() || 100<comment.length()) {
            BSUtil.isTrue(false,"评论字数应在10-100字之间");
        }
        return classInfoCommentMapper.insertSelective(classInfoComment);
    }

    @Override
    public List<ClassInfoComment> getClassInfoCommentListByClassInfoId(ClassInfoComment classInfoComment) {
        return classInfoCommentMapper.getClassInfoCommentListByClassInfoId(classInfoComment);
    }
}
