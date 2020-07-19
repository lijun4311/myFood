package com.mhs66.controller.center;

import com.mhs66.WebResult;
import com.mhs66.annotation.BusinessObjectNotEmpty;
import com.mhs66.consts.FileUploadConsts;
import com.mhs66.consts.UserConsts;
import com.mhs66.controller.BaseController;
import com.mhs66.pojo.Users;
import com.mhs66.pojo.bo.UserBO;
import com.mhs66.service.IUsersService;
import com.mhs66.utils.CookieUtil;
import com.mhs66.utils.IDateUtil;
import com.mhs66.utils.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@Api(value = "用户信息接口", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
@AllArgsConstructor
public class CenterUserController extends BaseController {
    private final IUsersService centerUserService;


    @ApiOperation(value = "用户头像修改", notes = "用户头像修改", httpMethod = "POST")
    @PostMapping("uploadFace")
    @BusinessObjectNotEmpty
    public WebResult uploadFace(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "file", value = "用户头像", required = true)
                    MultipartFile file) {


        // 定义头像保存的地址
//        String fileSpace = IMAGE_USER_FACE_LOCATION;
        String fileSpace = FileUploadConsts.getImageUserFaceLocation();
        // 在路径上为每一个用户增加一个userid，用于区分不同用户上传
        String uploadPathPrefix = File.separator + userId;

        // 开始文件上传
        if (file != null) {
            FileOutputStream fileOutputStream = null;
            try {
                // 获得文件上传的文件名称
                String fileName = file.getOriginalFilename();

                if (StringUtils.isNotBlank(fileName)) {

                    // 文件重命名  imooc-face.png -> ["imooc-face", "png"]
                    String fileNameArr[] = fileName.split("\\.");

                    // 获取文件的后缀名
                    String suffix = fileNameArr[fileNameArr.length - 1];

                    if (!suffix.equalsIgnoreCase("png") &&
                            !suffix.equalsIgnoreCase("jpg") &&
                            !suffix.equalsIgnoreCase("jpeg")) {
                        return WebResult.errorMsg("图片格式不正确！");
                    }

                    // face-{userid}.png
                    // 文件名称重组 覆盖式上传，增量式：额外拼接当前时间
                    String newFileName = "face-" + userId + "." + suffix;

                    // 上传的头像最终保存的位置
                    String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFileName;
                    // 用于提供给web服务访问的地址
                    uploadPathPrefix += ("/" + newFileName);

                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null) {
                        // 创建文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    // 文件输出保存到目录
                    fileOutputStream = new FileOutputStream(outFile);
                    InputStream inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            return WebResult.errorMsg("文件不能为空！");
        }

        // 获取图片服务地址
        String imageServerUrl = FileUploadConsts.getImageServerUrl();

        // 由于浏览器可能存在缓存的情况，所以在这里，我们需要加上时间戳来保证更新后的图片可以及时刷新
        String finalUserFaceUrl = imageServerUrl + uploadPathPrefix
                + "?t=" + IDateUtil.dateToStr(LocalDateTime.now(), IDateUtil.DATE_PATTERN);

        // 更新用户头像到数据库
        Users userResult = centerUserService.updateUserFace(userId, finalUserFaceUrl);

        centerUserService.setNullProperty(userResult);
        CookieUtil.setCookieEncode(request, response, "user",
                JsonUtil.objToString(userResult));

        // TODO 后续要改，增加令牌token，会整合进redis，分布式会话

        return WebResult.ok();
    }


    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("update")
    public WebResult update(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @RequestBody @Valid UserBO centerUserBO,
            BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {

        System.out.println(centerUserBO);


        Users userResult = centerUserService.updateUserInfo(userId, centerUserBO);

        centerUserService.setNullProperty(userResult);
        CookieUtil.setCookieEncode(request, response, UserConsts.getUserPrefix(),
                JsonUtil.objToString(userResult));

        // TODO 后续要改，增加令牌token，会整合进redis，分布式会话

        return WebResult.ok();
    }


}
