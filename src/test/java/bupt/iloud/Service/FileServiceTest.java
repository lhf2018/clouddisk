package bupt.iloud.Service;

import bupt.iloud.Mapper.FileMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceTest {
    @Autowired
    FileMapper fileMapper;
    @Test
    public void test(){
        System.out.println(fileMapper);
    }
}
