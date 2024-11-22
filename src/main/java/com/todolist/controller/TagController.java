package com.todolist.controller;

import com.todolist.dao.TagDao;
import com.todolist.entity.Tag;

import java.util.List;

public class TagController {

    /**
     * 增加一个tag
     * @param name
     * @param description
     * @return
     */
    public static int addTag(String name,String description){
        Tag tag = new Tag();
        tag.setDescription(description);
        tag.setName(name);
        tag.setColorCode(1);
        int id = TagDao.insert(tag);
        return id;
    }

    /**
     * 列出当前用户所有tag
     * @return
     */
    public static List<Tag> list(){
        List<Tag> tags = TagDao.selectTagsByUserId();
        return tags;
    }

    /**
     * 根据id查找tag
     * @param id
     * @return
     */
    public static Tag findById(int id){
        return TagDao.findById(id);
    }

    /**
     * 删除tag
     * @param id
     */
    public static void deleteTag(int id){
        //TODO
    }

    /**
     * 修改tag
     * @param id
     * @param name
     * @param description
     */
    public static void updateTag(int id,String name,String description){
        //TODO
    }

}
