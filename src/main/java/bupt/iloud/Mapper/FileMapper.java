package bupt.iloud.Mapper;

import bupt.iloud.Model.File;
import bupt.iloud.Model.Page;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FileMapper {
    @Select("SELECT * FROM file where canshare=1 and filename like #{searchcontent} limit #{startindex},#{pagesize}")
    public List<File> getAllFiles(Page page) throws Exception;

    /*统计文件数*/
    @Select("SELECT COUNT(id) totalrecord from file where canshare=1 and filename like #{searchcontent}")
    public int count(String searchcontent) throws Exception;
    @Select("select file.filepath from file where id=#{value}")
    public String findFilepathById(int id) throws Exception;
    /*插入文件*/
    @Insert("insert into icloud.file (filename,filepath,filesize,createtime,canshare,user_id,MD5) values(#{filename},#{filepath},#{filesize},#{createtime},#{canshare},#{user_id},#{MD5})")
    public Integer insertFile(File file) throws Exception;

    /* 查询用户的文件*/
    @Select("SELECT * FROM file WHERE filepath=#{filepath} order by createtime desc LIMIT #{startindex},#{pagesize}")
    public List<File> getUserFiles(Page page) throws Exception;
    /*统计用户文件*/
    @Select("SELECT COUNT(id) totalrecord FROM file WHERE filepath=#{username}")
    public int countUserFiles(String username) throws Exception;

    @Update("update file set canshare=#{canshare} where id=#{id}")
    public void updateFileById(int canshare,int id) throws Exception;

    @Delete("delete from file where id=#{value}")
    public void deleteFileById(int id);
    @Select("select file.filename from file where id=#{value}")
    public String findFilenameById(int id);


}
