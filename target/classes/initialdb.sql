-- 创建用户表
CREATE TABLE IF NOT EXISTS user (
                                    id INTEGER PRIMARY KEY AUTOINCREMENT,  -- 主键id
                                    username TEXT NOT NULL,     -- 用户名
                                    password TEXT NOT NULL,     -- 密码
                                    email TEXT ,        -- 邮箱
                                    phone TEXT, -- 电话号
                                    is_login INTEGER DEFAULT 0, -- 登陆状态 0未登录 1已登录
                                    deadlineReminderDays INTEGER DEFAULT 3 --
);

-- 创建标签表
CREATE TABLE IF NOT EXISTS tag (
                                   id INTEGER PRIMARY KEY AUTOINCREMENT,
                                   name TEXT NOT NULL,
                                   description TEXT,
                                   color_code TEXT,
                                   user_id INTEGER,
                                   FOREIGN KEY (user_id) REFERENCES user(id)
);

-- 创建任务表
CREATE TABLE IF NOT EXISTS task (
                                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                                    title TEXT NOT NULL, -- 任务名
                                    description TEXT, --任务描述
                                    start_date DATE, --开始日期
                                    due_date DATE, --结束日期
                                    start_time TIME, -- 开始时间
                                    end_time TIME, --结束时间
                                    priority INTEGER, --任务优先级 123三个等级，1最高
                                    repeat_type INTEGER, --是否重复 0不重复 1重复
                                    repeat_interval INTEGER DEFAULT 0, --重复间隔天数 0代表每天重复
                                    repeat_num INTEGER, --重复次数
                                    status INTEGER, -- 状态 0未完成 1已完成 2逾期未完成
                                    user_id INTEGER, -- 用户id
                                    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- 创建任务-标签表
CREATE TABLE IF NOT EXISTS task_tag (
                                        task_id INTEGER,
                                        tag_id INTEGER,
                                        user_id INTEGER,
                                        PRIMARY KEY (task_id, tag_id, user_id),
                                        FOREIGN KEY (task_id) REFERENCES task(id),
                                        FOREIGN KEY (tag_id) REFERENCES tag(id),
                                        FOREIGN KEY (user_id) REFERENCES user(id)
);

-- 创建任务日程表
CREATE TABLE IF NOT EXISTS task_schedule (
                                             id INTEGER PRIMARY KEY AUTOINCREMENT,
                                             task_id INTEGER,
                                             user_id INTEGER,
                                             start_time DATETIME, -- 开始时间
                                             end_time DATETIME, --结束时间
                                             FOREIGN KEY (task_id) REFERENCES task(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
    );

