package com.todolist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    private int id;
    private String name;
    private String description;
    private int priority;
    private int colorCode;
    private int user_id;
}
