package bupt.iloud.Service;

import bupt.iloud.Mapper.FileMapper;
import bupt.iloud.Model.File;
import bupt.iloud.Model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.util.List;

@Service
public class FileService {
    @Autowired
    FileMapper fileMapper;
    public List<File> getAllFiles(Page page) throws Exception{
        page.setSearchcontent("%"+page.getSearchcontent()+"%");
        return fileMapper.getAllFiles(page);
    }
    public int countShareFiles(String searchcontent) throws Exception{
        return fileMapper.count("%"+searchcontent+"%");
    }
    public String findFilepathById(int id) throws Exception{
        return fileMapper.findFilepathById(id);
    }
    public Integer insertFile(File file) throws Exception{
        return fileMapper.insertFile(file);
    }
    public List<File> getUserFiles(Page page) throws Exception{
        return fileMapper.getUserFiles(page);
    }
    public int countUserFiles(String username) throws Exception{
        return fileMapper.countUserFiles(username);
    }
    public boolean copyFile(String file,String path){
        try {
            Files.copy(new java.io.File(file).toPath(),new java.io.File(path).toPath());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public void updateFileById(int canshare,int id) throws Exception{
        fileMapper.updateFileById(canshare,id);
    }
    public void deleteFileById(int id){
        fileMapper.deleteFileById(id);
    }
    public String findFilenameById(int id){
        return fileMapper.findFilenameById(id);
    }
}
