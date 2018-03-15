package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.interfaces.Json;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/api/file")
public class FileController {

    @Value("${common.upload-dir}")
    private String uploadDir;

    @RequestMapping(method = POST)
    @ResponseBody
    public Json upload(@RequestParam("file") MultipartFile file) throws IOException {
        String filename = DateFormatUtils.format(new Date(), "/yyyyMMdd/") + file.getOriginalFilename();
        String filePath = uploadDir + filename;
        File out = new File(filePath);
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
        }
        FileCopyUtils.copy(file.getBytes(), out);
        return Json.SUCCESS().setData("/api/file/" + filename);
    }

    @RequestMapping(value = "{day}/{file:.*}", method = GET)
    public String download(@PathVariable("day") String day,
                           @PathVariable("file") String filename,
                           HttpServletResponse response) throws IOException {
        String name = day + "/" + filename;
        File file = new File(uploadDir, name);
        if (file.exists()) {
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=" + name);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
