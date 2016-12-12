# -*- coding: utf-8 -*-
import os
# import pickle
# import sys
import re
import shutil
from config import local_config
from config import project_drawable


class ChangeResource(object):
    """docstring for ChangeResource"""

    def __init__(self):
        super(ChangeResource, self).__init__()

    # 修改文件夹名字
    def search_dir(self, root_dir, search_dir_name):
        # 两个参数分别为项目路径和要查找的文件名字
        print "开始更改新城市包名..."
        if os.path.isdir(root_dir):
            # print root_dir
            splitl = os.path.split(root_dir)  # 将路径分割为二元组
            # print splitl[1]
            if splitl[1] == search_dir_name:
                try:
                    os.rename(root_dir, splitl[0] +
                              "/" + local_config["new_city"])
                    print "文件夹 [%s] 已经改为 [%s]" \
                        % (root_dir, splitl[0] + "/" +
                            local_config["new_city"])
                except:
                    pass
            else:
                listnew = os.listdir(root_dir)
                for mdir in listnew:
                    path = root_dir + "/" + mdir
                    # print path
                    self.search_dir(path, search_dir_name)
                    pass
        else:
            print root_dir
            # 不是文件夹
            return
        # print "更改城市包名完成."
        pass

    # readlines 替换 Config中的参数
    def replace_config(self):
        config_path = local_config["config_path"]
        if os.path.isfile(config_path):
            f = open(config_path, "rb+")
            all_lines = f.readlines()
            f.seek(0)
            f.truncate()
            for line in all_lines:
                rz = re.findall(r'\"(.*)\"', line)
                # print f.tell()
                if line.find("String QQ_APPID") >= 0 and rz is not None:
                    f.write(line.replace(rz[0], local_config["qq_appid"]))
                    # f.write(line.replace(line[-12:-2],local_config["qq_appid"]))
                    # 倒数向前找到qq appid的数字位置,[-12,-2]是左闭右开的区间。
                elif line.find("String WEIBO_APPKEY") >= 0 and rz is not None:
                    f.write(line.replace(rz[0], local_config["weibo_appkey"]))
                elif line.find("String WEICHAT_APPSECRET") >= 0 \
                        and rz is not None:
                    f.write(line.replace(
                        rz[0], local_config["weichat_appsecret"]))
                elif line.find("String WEICHAT_APPID") >= 0 and rz is not None:
                    f.write(line.replace(rz[0], local_config["weichat_appid"]))
                elif line.find("String APPID") >= 0 and rz is not None:
                    f.write(line.replace(rz[0], local_config["appid"]))
                elif line.find("String REGION_CODE") >= 0 and rz is not None:
                    f.write(line.replace(rz[0], local_config["region_code"]))
                else:
                    f.write(line)
            f.close()
        else:
            print "所传Config路径格式不对"
        pass

    # readline替换config中的参数
    def replace_config_by_readline(self):
        print "开始替换config.java中的参数..."
        config_path = local_config["config_path"]
        if os.path.isfile(config_path):
            f = open(config_path, "rb+")
            f.seek(0)
            for line in open(config_path, "r"):
                front_pos = f.tell()
                line = f.readline()
                # if not line:
                # break
                rz = re.findall(r'\"(.*)\"', line)
                if line.find("String QQ_APPID") >= 0 and rz is not None:
                    f.seek(front_pos)
                    f.write(line.replace(rz[0], local_config["qq_appid"]))
                elif line.find("String WEIBO_APPKEY") >= 0 and rz is not None:
                    f.seek(front_pos)
                    f.write(line.replace(rz[0], local_config["weibo_appkey"]))
                elif line.find("String WEICHAT_APPSECRET") >= 0 \
                        and rz is not None:
                    f.seek(front_pos)
                    f.write(line.replace(
                        rz[0], local_config["weichat_appsecret"]))
                elif line.find("String WEICHAT_APPID") >= 0 and rz is not None:
                    f.seek(front_pos)
                    f.write(line.replace(rz[0], local_config["weichat_appid"]))
                elif line.find("String APPID") >= 0 and rz is not None:
                    f.seek(front_pos)
                    f.write(line.replace(rz[0], local_config["appid"]))
                elif line.find("String REGION_CODE") >= 0 and rz is not None:
                    f.seek(front_pos)
                    f.write(line.replace(rz[0], local_config["region_code"]))
            f.close()
        else:
            print "所传Config路径格式不对"
            return
        print "替换config.java参数完成"
        pass

    # 修改所有资源图片
    def replace_drawable(self):
        # 替换掉所有类型的mipmap包下的文件
        print "开始替换图片资源..."
        for pr in project_drawable:
            targetfile = self.search_drawable_in_local(project_drawable[pr])
            if targetfile is not None:
                # 递归查找
                # (filename,extension) = os.path.splitext(targetfile)
                targetdir = local_config[
                    "local_drawable_path"] + "/" + targetfile
                print "将替换的图片资源: %s " % (targetdir)
                self.search_drawable_in_project(
                    local_config["drawable_path"], targetdir)
        print "图片资源替换完成."
        pass

    # 递归替换项目中的资源
    def search_drawable_in_project(self, sourcedir, targetdir):
        if sourcedir.find(".git") >= 0:
            return
        # print sourcedir
        if os.path.isdir(sourcedir):
            # 是文件夹，检索文件夹内文件递归
            listdir = os.listdir(sourcedir)
            for ld in listdir:
                if os.path.isdir(ld) and ld.find("mipmap") < 0:
                    continue
                path = sourcedir + "/" + ld
                self.search_drawable_in_project(path, targetdir)
        else:
            # 是文件,判断是否是要复制的文件
            splitl = os.path.split(sourcedir)
            targetl = os.path.split(targetdir)
            # print "src: "+splitl[1]+" tar: "+targetl[1]
            if os.path.isfile(sourcedir) and targetl[1] == splitl[1]:
                shutil.copy(targetdir, sourcedir)
            return
        pass

    # 查找本地资源文件(一级目录)
    def search_drawable_in_local(self, source_id):
        (sourcename, srextension) = os.path.splitext(source_id)
        path = local_config["local_drawable_path"]
        if os.path.isdir(path):
            # 根据要替换的文件名找到新文件并返回
            listfile = os.listdir(path)
            for file in listfile:
                # ic_launcher.png
                (filename, extension) = os.path.splitext(file)
                if filename == sourcename:
                    return file
        else:
            # 本地资源路径非文件夹
            return
        pass

    # 修改gradle中的applicationid
    def replace_gradle(self):
        print "开始替换gradle中的参数..."
        gradle_path = local_config["gradle_path"]
        if os.path.isfile(gradle_path):
            f = open(gradle_path, "rb+")
            f.seek(0)
            for line in open(gradle_path, "r"):
                front_pos = f.tell()
                line = f.readline()
                if not line:
                    break
                rz = re.findall(r'\"(.*)\"', line)
                if line.find("applicationId") >= 0 and re is not None:
                    f.seek(front_pos)
                    f.write(line.replace(rz[0],
                                         local_config["new_package_name"]))
                    break
            f.close()
            pass
        else:
            print "替换gradle参数失败."
            return
        print "替换gradle参数完成."
        pass

    # def replace_all_packagename():
    #     pass

    def replace_strings(self):
        string_path = local_config["strings_path"]
        print "开始读取strings中的参数..."
        if os.path.isfile(string_path):
            f = open(string_path, "rb+")
            f.seek(0)
            for line in open(string_path, "r"):
                front_pos = f.tell()
                line = f.readline()
                if not line:
                    break
                rz = re.findall(r'\>(.*)\</', line)
                if line.find("app_name") >= 0 and rz:
                    # print rz[0]
                    f.seek(front_pos)
                    f.write(line.replace(rz[0], local_config["app_name"]))
                pass
            f.close()
            pass
        else:
            print "替换strings参数失败."
            return
        print "替换strings参数完成."
        pass

    def myfunc(self):
        # 搜索到文件夹/图片/字符串，再做修改
        # local_path = local_config["local_path"]
        # self.search_dir(local_path, "dali")
        # self.replace_config_by_readline()
        # self.replace_drawable()
        # self.replace_gradle()
        self.replace_strings()
        pass
