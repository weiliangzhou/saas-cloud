package com.wegoo.saasservice;

import com.wegoo.model.po.ClassInfoComment;

import java.util.List;

public interface ClassInfoCommentService {
    List<ClassInfoComment> getClassInfoCommentList(ClassInfoComment classInfoComment);

    int addClassInfoComment(ClassInfoComment classInfoComment);

    List<ClassInfoComment> getClassInfoCommentListByClassInfoId(ClassInfoComment classInfoComment);
}
