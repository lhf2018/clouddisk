package bupt.iloud.Controller;

import bupt.iloud.Model.File;
import bupt.iloud.Model.Page;
import bupt.iloud.Model.PageBean;
import bupt.iloud.Model.User;
import bupt.iloud.Service.FileService;
import bupt.iloud.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;
    /*
    登陆功能
     */
    @RequestMapping("/login")
    public String login(User user, HttpSession session, HttpServletRequest request) {
        try {
            String user_name = userService.checkUser(user);
            if (user_name != null && (!"".equals(user_name))) {
                //如果登陆成功 把用户名放到session域
                session.setAttribute("user_name", user_name);
                return "redirect:/searchUserfile";
            }
            request.setAttribute("error", "用户名或密码错误");
            return "login";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "login";
    }
    /*
    用户主页
     */
    @RequestMapping("/searchUserfile")
    public String userHome(HttpSession session, Page page, HttpServletRequest request, Model mv) throws Throwable{
        List<File> list;
        PageBean pageBean=new PageBean();
        String username=(String) session.getAttribute("user_name");
        //session没有用户名说明没有登陆，让他转去主页
        if (username == null || "".equals(username)) {
            return "redirect:/login";
        }
        page.setFilepath(username);
        if (page.getCurrentpage() == 0) {
            page.setCurrentpage(1);
        }
        if(page.getPagesize()==0){
            page.setPagesize(5);
        }
        list=fileService.getUserFiles(page);
        Integer isvip= (Integer) request.getAttribute("isvip");
        if(isvip==null){
            //没有上传文件之前会调用到这里的代码，上传的时候在uploadAction里会添加isvip
            try {
                isvip=userService.isVip(username);
            }catch (Exception e){
                e.printStackTrace();
            }
            request.setAttribute("isvip",isvip);
        }
        //拿到每页的数据，每个元素就是一条记录
        pageBean.setList(list);
        pageBean.setCurrentpage(page.getCurrentpage());
        pageBean.setPagesize(page.getPagesize());
        pageBean.setTotalrecord(fileService.countUserFiles(username));

        mv.addAttribute("pagebean",pageBean);
        return "userhome";
    }
    /*
    注册功能
     */
    @RequestMapping("register")
    public String register(String usernamesignup,String passwordsignup,HttpServletRequest request){
        if("".equals(usernamesignup)||"".equals(passwordsignup)){
            request.setAttribute("usernameerror","用户名必须6-20位");
            request.setAttribute("passworderror", "密码必须6-20位");
            return "redirect:/400.html";
        }else if(	usernamesignup==null||passwordsignup==null){
            request.setAttribute("usernameerror", "用户名或密码不能为空");
            request.setAttribute("passworderror", "用户名或密码不能为空");
            return "redirect:/400.html";

        }
        else if (usernamesignup.length() > 20 || usernamesignup.length() < 6) {
            request.setAttribute("usernameerror", "用户名必须6-20位");
            return "redirect:/400.html";
        } else if (passwordsignup.length() > 20 || passwordsignup.length() < 6) {
            request.setAttribute("passworderror", "密码必须6-20位");
            return "redirect:/400.html";
        }
        User user=new User();
        user.setUsername(usernamesignup);
        user.setPassword(passwordsignup);
        try {
            userService.createUser(user);// 如果用户已注册 下层的service会抛出异常}
            // 注册成功，就在upload下分配一个私人的文件夹
            java.io.File file=new java.io.File(FileController.storePath+ java.io.File.separator+usernamesignup);
            file.mkdirs();
        }catch (IOException e){
            return "redirect:/400.html";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/400.html";
        }
        return "login";
    }
    @RequestMapping("/index")
    public String index(){
        return"index";

    }
    @RequestMapping("/help")
    public String help(){
        return"help";
    }
    @RequestMapping("/requestout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";

    }
}
