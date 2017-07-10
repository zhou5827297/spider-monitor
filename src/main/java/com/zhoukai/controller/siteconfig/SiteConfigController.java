package com.zhoukai.controller.siteconfig;

import com.zhoukai.siteconfig.SiteConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置文件控制器
 */
@RestController
@RequestMapping("siteConfig")
public class SiteConfigController {


    @Autowired
    private SiteConfigService siteConfigService;

    @RequestMapping("/getTreeNode")
    public List<SiteConfigService.TreeNode> getTreeNode(@RequestParam(required = false) String condition) {
        if (condition != null && condition.length() == 0) {
            condition = null;
        }
        List<SiteConfigService.TreeNode> treeNodes = siteConfigService.getTreeNode(condition);
        return treeNodes;
    }

    @RequestMapping("/getSiteContent")
    public String getSiteContent(@RequestParam String path) {
        return siteConfigService.getSiteContent(path);
    }

    @RequestMapping("/deleteSiteFile")
    public boolean deleteSiteFile(@RequestParam String path) {
        return siteConfigService.deleteSiteFile(path);
    }

    @RequestMapping("/saveSiteFile")
    public boolean saveSiteFile(@RequestBody Map<String, String> siteMap) {
        return siteConfigService.saveSiteFile(siteMap.get("path"), siteMap.get("content"), false);
    }

    @RequestMapping("/createSiteFile")
    public Map<String, String> createSiteFile(@RequestParam String path, @RequestParam String isDic) {
        String fileName = "";
        if (isDic != null && "1".equals(isDic)) {
            fileName = System.currentTimeMillis() + "";
        } else {
            fileName = System.currentTimeMillis() + ".json";
        }

        String realPath = path + File.separator + fileName;
        siteConfigService.saveSiteFile(realPath, "", true);
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("path", realPath);
        returnMap.put("name", fileName);
        return returnMap;
    }

    @RequestMapping("/renameSiteFile")
    public Map<String, String> renameSiteFile(@RequestParam String targetPath, @RequestParam String originPath) {
        String filePath = originPath.substring(0, originPath.lastIndexOf(File.separator));
//        String fileName = originPath.substring(originPath.lastIndexOf(File.separator) + 1);

        String realPath = filePath + File.separator + targetPath;
        siteConfigService.renameSiteFile(realPath, originPath);
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("path", realPath);
        returnMap.put("name", targetPath);
        return returnMap;
    }

    @RequestMapping("/svnSync")
    public boolean svnSync() {
        return siteConfigService.svnSync();
    }
}
