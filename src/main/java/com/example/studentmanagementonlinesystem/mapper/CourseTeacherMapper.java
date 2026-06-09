package com.example.studentmanagementonlinesystem.mapper;

import com.example.studentmanagementonlinesystem.entity.Course;
import com.example.studentmanagementonlinesystem.entity.CourseTeacher;
import com.example.studentmanagementonlinesystem.entity.CourseTeacherInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseTeacherMapper {
    @Insert("INSERT INTO ct (cid, tid, term) VALUES (#{cid}, #{tid}, #{term})")
    public boolean insertCourseTeacher(@Param("cid") Integer cid,
                                       @Param("tid") Integer tid,
                                       @Param("term") String term);

    public List<CourseTeacher> findBySearch(@Param("cid") Integer cid,
                                            @Param("tid") Integer tid,
                                            @Param("term") String term);

    @Select("""
            SELECT c.cid, c.cname, c.ccredit
            FROM c c INNER JOIN ct ct ON c.cid = ct.cid
            WHERE ct.tid = #{tid}
            """)
    public List<Course> findMyCourse(@Param("tid") Integer tid,
                                     @Param("term") String term);

    public List<CourseTeacherInfo> findCourseTeacherInfo(@Param("tid") Integer tid,
                                                         @Param("tname") String tname,
                                                         @Param("tFuzzy") Integer tFuzzy,
                                                         @Param("cid") Integer cid,
                                                         @Param("cname") String cname,
                                                         @Param("cFuzzy") Integer cFuzzy);

    @Delete("DELETE FROM ct WHERE cid = #{c.cid} AND tid = #{c.tid}")
    public boolean deleteById(@Param("c") CourseTeacher courseTeacher);

    @Delete("DELETE FROM ct WHERE tid = #{tid}")
    public int deleteByTid(@Param("tid") Integer tid);

    @Delete("DELETE FROM ct WHERE cid = #{cid}")
    public int deleteByCid(@Param("cid") Integer cid);
}
