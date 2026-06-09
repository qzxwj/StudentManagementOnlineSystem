package com.example.studentmanagementonlinesystem.mapper;

import com.example.studentmanagementonlinesystem.entity.CourseTeacherInfo;
import com.example.studentmanagementonlinesystem.entity.StudentCourseTeacher;
import com.example.studentmanagementonlinesystem.entity.StudentCourseTeacherInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentCourseTeacherMapper {
    @Select("""
            SELECT c.cid, c.cname, t.tid, t.tname, sct.grade, c.ccredit
            FROM sct sct INNER JOIN t t ON sct.tid = t.tid
                    INNER JOIN c c ON sct.cid = c.cid
            WHERE sct.sid = #{sid} AND sct.term = #{term}
            """)
    public List<CourseTeacherInfo> findByStudentId(@Param("sid") Integer sid,
                                                   @Param("term") String term);

    public List<StudentCourseTeacherInfo> findBySearch(@Param("sid") Integer sid,
                                                       @Param("sname") String sname,
                                                       @Param("sFuzzy") Integer sFuzzy,
                                                       @Param("cid") Integer cid,
                                                       @Param("cname") String cname,
                                                       @Param("cFuzzy") Integer cFuzzy,
                                                       @Param("tid") Integer tid,
                                                       @Param("tname") String tname,
                                                       @Param("tFuzzy") Integer tFuzzy,
                                                       @Param("lowBound") Integer lowBound,
                                                       @Param("highBound") Integer highBound,
                                                       @Param("term") String term);

    @Select("SELECT DISTINCT sct.term FROM sct sct")
    public List<String> findAllTerm();

    @Select("SELECT * FROM sct WHERE sid = #{sct.sid} AND cid = #{sct.cid} AND tid = #{sct.tid} AND term = #{sct.term}")
    public List<StudentCourseTeacher> findBySCT(@Param("sct") StudentCourseTeacher studentCourseTeacher);

    @Insert("INSERT INTO sct (sid, cid, tid, term) VALUES (#{s.sid}, #{s.cid}, #{s.tid}, #{s.term})")
    public boolean insert(@Param("s")StudentCourseTeacher studentCourseTeacher);

    @Update("UPDATE sct SET sct.grade = #{grade} WHERE sct.sid = #{sid} AND sct.tid = #{tid} AND sct.cid = #{cid} AND sct.term = #{term}")
    public boolean updateById(@Param("sid") Integer sid,
                              @Param("cid") Integer cid,
                              @Param("tid") Integer tid,
                              @Param("term") String term,
                              @Param("grade") Integer grade);

    @Delete("DELETE FROM sct WHERE sid = #{sct.sid} AND tid = #{sct.tid} AND cid = #{sct.cid}")
    public boolean deleteBySCT(@Param("sct") StudentCourseTeacher sct);

    @Delete("DELETE FROM sct WHERE sid = #{sid}")
    public int deleteBySid(@Param("sid") Integer sid);

    @Delete("DELETE FROM sct WHERE tid = #{tid}")
    public int deleteByTid(@Param("tid") Integer tid);

    @Delete("DELETE FROM sct WHERE cid = #{cid}")
    public int deleteByCid(@Param("cid") Integer cid);
}
