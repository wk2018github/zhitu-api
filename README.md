
## 用户

用户信息存储在zt_sys_user中。

#### zt_sys_user结构说明

|字段名|类型|描述|
|---|---|---|
|id|varchar(100)|唯一ID，必须以`USER_毫秒时间戳`为格式|
|createTime|datetime|创建时间|
|email|varchar(255)|用户邮箱|
|password|varchar(255)|用户密码|
|nickname|varchar(255)|用户昵称|
|avatarUrl|varchar(300)|头像URL|


## 项目

项目信息存储在zt_sys_project中。

#### zt_sys_project结构说明

|字段名|类型|描述|
|---|---|---|
|id|varchar(100)|唯一ID，必须以`PROJECT_毫秒时间戳`为格式|
|createTime|datetime|创建时间|
|name|varchar(255)|项目名|
|userId|varchar(100)|用户ID，外键：引用zt_sys_user.id|


## 数据集

### 数据集描述信息

所有数据集的描述信息都被统一存储在zt_sys_dataset中，每个数据集的描述信息，都对应zt_sys_dataset表中的一行。

#### zt_sys_dataset结构说明

|字段名|类型|描述|
|---|---|---|
|id|varchar(100)|唯一ID，必须以`DATASET_毫秒时间戳`为格式|
|createTime|datetime|创建时间|
|name|varchar(255)|数据集名|
|typeId|varchar(100)|类型ID，外键:引用zt_sys_dataset_local_file.id|
|userId|varchar(100)|用户ID，外键：引用zt_sys_user.id|
|projectId|varchar(100)|项目ID，外键：引用zt_sys_project.id|
|dataTable|varchar(255)|存放具体数据的数据表名，一般以`zt_data_数据集ID`命名，当typeId为remote_rdb时，该字段为null|
|rdbId|varchar(100)|关系数据库描述信息ID，外键：引用zt_sys_dataset_rdb.id，对于文件类型（local_file），该字段为null|


typeId:
+ local_file: 文件上传
+ local_rdb: 关系数据库（导入本地）
+ remote_rdb: 关系数据库（不导入本地）


### 关系数据库描述信息

所有数据集的描述信息都被统一存储在zt_sys_dataset_rdb中。

### zt_sys_dataset_rdb结构说明

|字段名|类型|描述|
|---|---|---|
|id|varchar(100)|唯一ID，必须以`RDB_毫秒时间戳`为格式|
|createTime|datetime|创建时间|
|host|varchar(100)|数据库host|
|port|int|数据库port|
|charset|varchar(100)|数据字符集|
|user|varchar(255)|数据库用户名|
|password|varchar(255)|数据库密码|
|dbName|varchar(100)|数据库名|
|tableName|varchar(100)|数据库表名|
|columnNames|varchar(500)|选取的列，多列用英文逗号分隔|


### PDF类型数据集内容信息

每个PDF数据集的内容信息都被存储在一张zt_data_数据集ID中（每个PDF数据集都分别对应一张）。
例如ID为DATASET_1428399384的PDF数据集对应的内容表为zt_data_DATASET_1428399384。
zt_data_DATASET_1428399384的行数等于DATASET_1428399384数据集包含的PDF文件数。

### zt_data_数据集ID 内容表结构说明
PDF内容表表名由“zt_data_”+“数据集ID”构成，例如一次上传5个pdf文件，数据集描述信息表zt_sys_dataset会新增一条记录，同时生成一张表（表名为“zt_data_”+“zt_sys_dataset表中刚新增的一条记录的ID”），并且pdf内容表中新增5条记录，每条记录对应一个pdf文件

|字段名|类型|描述|
|---|---|---|
|id|varchar(100)|唯一ID，必须以`PDF_毫秒时间戳`为格式|
|createTime|datetime|创建时间|
|fileName|varchar(255)|pdf文件名|
|abstract|text|pdf内容摘要，一般取前5000字|
|ftpurl|varchar(255)|pdf文件路径|


## 知识



## 任务
