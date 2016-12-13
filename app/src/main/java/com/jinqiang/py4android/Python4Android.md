## Python脚本替换Android资源(包名,图片,文件内容)
---
>  最近要将Android项目中的gradle,图片，包名，字符串等做便捷替换，以适应不同内容的更换，于是搬出半生不熟的Python，通过一系列的文件操作达到目的。

import：

```
# -*- coding: utf-8 -*-
import os
# import pickle
# import sys
import re
import shutil
from config import local_config
from config import project_drawable
```

`config`是我的配置字典集，其中包括本地项目路径、资源路径和代码路径、各种文件中要替换的关键字等。

* 修改文件夹

```
 # 修改文件夹名字
    def search_dir(self, root_dir, search_dir_name):
        # 两个参数分别为项目路径和要查找的文件名字
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
        pass
```

* 替换项目资源图片

```
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
        
```

* 替换strings中的字段

```
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
```

* 完整项目写在github上，欢迎fork。

* ~~对于文件中字符串的替换，这里有一点：在不新建和重写文件的前提下，只在原始文件中替换单独行的字符串，如果新串的长度小于原始串的长度，会无法覆盖完全，
原串多的部分会继续写在新串后面，这个问题暂时还没想到很好的解法，如果有想法欢迎留言，感激不尽。~~
* 目前写了一个粗暴的方法，在准备替换当前行的数据时，先计算本行字符串的长度，并覆盖写入同等数目的空格，再写入新数据。