## 接口文档



目前的测试文档



### 接口1

获取所有的user信息

url：http://47.112.101.204:8080/listener/user/findAll

参数：无

提交方式：Get和Post

返回数据：

+ status：自定义状态码，20000代表成功。
+ message：信息，success代表成功。
+ data：用键值对形式存放数据

 ```json
{
	"status": 20000,
	"message": "success",
	"data": {
		"redisData": "1",
		"mysqlData": [{
			"id": 1,
			"name": "张三",
			"age": 21
		}, {
			"id": 2,
			"name": "李四",
			"age": 22
		}, {
			"id": 3,
			"name": "王五",
			"age": 23
		}, {
			"id": 4,
			"name": "Tom",
			"age": 24
		}, {
			"id": 5,
			"name": "Jerry",
			"age": 25
		}, {
			"id": 6,
			"name": "Lucy",
			"age": 26
		}]
	}
}
 ```

 



### 接口2

分页查询user信息

url：http://47.112.101.204:8080/listener/user/{page}/{limit}

参数：

+ page：当前页
+ limit：每页多少数据

提交方式：Get

返回数据：

+ status：自定义状态码，20000代表成功。
+ message：信息，success代表成功。
+ data：键值对存放分页数据
  + total：总记录数
  + current：当前页数
  + pageList：当前页需要的数据
  + size：与入参limit的值一样



get请求

http://47.112.101.204:8080/listener/user/page/1/2

```json
{
    "status": 20000,
    "message": "success",
    "data": {
        "total": 8,
        "current": 1,
        "pageList": [
            {
                "id": 1,
                "name": "张三",
                "age": 21
            },
            {
                "id": 2,
                "name": "李四",
                "age": 22
            }
        ],
        "size": 2
    }
}
```





### 接口3

分页加模糊加等值查询user信息

url：http://47.112.101.204:8080/listener/user/{page}/{limit}

参数：

+ page：当前页
+ limit：每页多少数据
+ UserQuery：封装查询数据
  + name：模糊查询name属性
  + age：等值查询age属性

提交方式：Post

返回数据：

+ status：自定义状态码，20000代表成功。
+ message：信息，success代表成功。
+ data：键值对存放分页数据
  + total：总记录数
  + current：当前页数
  + pageList：当前页需要的数据
  + size：与入参limit的值一样

Post请求

![01发送一个分页模糊查询请求](api.assets/01发送一个分页模糊查询请求.png)

```json
{
    "status": 20000,
    "message": "success",
    "data": {
        "total": 2,
        "current": 1,
        "pageList": [
            {
                "id": 1,
                "name": "张三",
                "age": 21
            },
            {
                "id": 8,
                "name": "张四",
                "age": 23
            }
        ],
        "size": 2
    }
}
```





### 接口4

抛出一个异常的接口

url：http://47.112.101.204:8080/listener/user/error

提交方式：Get或Post

返回数据：

+ status：自定义状态码，20001代表失败。
+ message：信息。
+ data：无数据

```json
{
	"status": 20001,
	"message": "自定义异常!",
	"data": {}
}
```

