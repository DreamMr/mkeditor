# 项目计划 #

- 我想要做的

  - 我想要能够编写markdown 文档，并且上传
  - 我想要能够看到别人的文档
  - 我想要对一些好的文档进行点赞、评论
  - 我想要对文档进行划分，例如点赞数最高的、最新的
  - 我想能够根据关键字进行查找相关的文档
  - 我想要有一个列表来查看我自己的文档

- 任务划分

  1. markdown文档的上传

  2. 显示markdown文档的显示页面

  3. 点赞

  4. 主页显示文档列表（按最新的进行排列）

  5. 十大最热榜单

  6. 关键字搜索（搜索框有提示）

  7. 登录

  8. 注册

  9. 个人中心

  10. 能够对已有的文章重新进行编辑

  11. 评论

      

      

      

- 数据库设计

  - 用户账号表（table_user)

  | 列名   | 字段名        | 数据类型    | 是否键 | 是否null | 备注     |
  | ------ | ------------- | ----------- | ------ | -------- | -------- |
  | 用户名 | user_name     | char(20)    | 主键   | not null | 不能重复 |
  | 密码   | user_password | varchar(13) | 否     | not null | MD5加密  |
  | 用户ID | user_id       | char(8)     | 否     | not null | 随机生成 |

  - 文章存储表（table_article)

    | 列名     | 字段名          | 数据类型     | 是否键 | 是否null | 备注                                       |
    | -------- | --------------- | ------------ | ------ | -------- | ------------------------------------------ |
    | 文章ID   | article_id      | char(23)     | 主键   | not null | 'a'+时间+用户ID（ayyyyMMddHHmmss12345678） |
    | 昵称     | user_name       | varchar(20)  | 外键   | not null |                                            |
    | 点赞     | article_like    | int          | 否     | not null |                                            |
    | 评论数   | article_comment | int          | 否     | not null |                                            |
    | 文章内容 | article_content | MEDIUMTEXT   | 否     | not null |                                            |
    | 摘要     | article_short   | nvarchar(50) | 否     | not null |                                            |
    | 文章标题 | article_title   | varchar(50)  | 否     | not null |                                            |

    

  - 文章评论存储表（table_article_comment)

    | 列名       | 字段名                  | 数据类型     | 是否键 | 是否null | 备注 |
    | ---------- | ----------------------- | ------------ | ------ | -------- | ---- |
    | 文章评论ID | article_comment_id      | char(23)     | 主键   | not null |      |
    | 文章ID     | article_id              | char(23)     | 外键   | not null |      |
    | 点赞数     | article_comment_like    | int          | 否     | not null |      |
    | 评论数     | article_comment_comment | int          | 否     | not null |      |
    | 内容       | comment_comment_content | varchar(100) | 否     | not null |      |
    | 昵称       | user_name               | varchar(20)  | 外键   | not null |      |

    

  - 
- 日志
  我在5月14日删掉了bootstrap-markdown-editor.js最后的label插入图片和链接标签，但是好像没有效果




