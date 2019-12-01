package bupt.iloud.Service;

import bupt.iloud.Mapper.UserMapper;
import bupt.iloud.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public void createUser(User user) throws Exception{
        boolean found=findUser(user.getUsername());
        if(!found){
            userMapper.createUser(user);
        }else {
            throw new RuntimeException();
        }
    }
    public String checkUser(User user) throws Exception{
        return userMapper.checkUser(user);
    }
    public int findUserID(String username) throws Exception{
        return userMapper.findUser(username);
    }
    public boolean findUser(String username) throws Exception{
        Integer found=userMapper.findUser(username);
        if(found==null||found<1){
            return false;
        }else {
            return true;
        }
    }
    public int isVip(String user_name) throws Exception{
        return userMapper.isVip(user_name);
    }
}
