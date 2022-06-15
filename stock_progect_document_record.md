# Stock

## 项目实现步骤

- 搭建后端开发环境
  - 设计数据库，导入设计好的数据库表单
  - 创建Maven工程，配置maven依赖、yml文件，参考已有配置文件进行修改
  - 定义springboot启动类，初步测试验证web层访问环境搭建情况
  - mybatis业务开发环境：mybatisX逆向工程生成代码
  - 完善文件工程目录结构
  - 整体后端环境测试，使用postman查看数据库访问是否正常（从controller->service->mapper）
- 前端开发环境搭建
  - node版本安装，nvm node版本控制
  - 前端项目导入，npm run dev
  - 前后端跨域问题解决：前端代理(setting.js)  或 后端crossorign注解
- 登录功能实现
  - 访问接口定义：请求接口、请求方式、请求数据、响应数据
    - 定义公共响应Vo（泛型）、响应状态码（枚举）
  - 登陆功能实现开发
    - 导入依赖：工具包（apache、google）、加密（passwordEncoder）
  - 登录接口方法定义——Controller层
  - 定义登录服务接口和实现——Service层
    - 接受参数判断、返回参数判断（null、empty）
  - 定义mapper层及sql语句——Mapper层
  - postman测试数据访问是否正常
- 验证码登录功能
  - 配置ID生成器（IdWork类 雪花算法）并交由spring管理
  - 定义web接口-controller层(获取验证码)
  - 定义生成验证码
    - 生成随机数（apache工具类）
    - ID生成器生成全局唯一id
    - 将id和随机数存入redis并设置有效时间
    - 将结果封装进map并返回给前端
  - 进行postman测试
- 完善验证码登录功能
  - 将用户输入的验证码、SessionId(rkey)封装进实体对象
  - 根据rkey从redis中获取code和用户输入的进行匹配
- 国内大盘指数功能
  - 业务分析：前端页面原型+相关表结构+接口说明+domain实体封装
  - sql分析：考虑性能，减少全表查询次数。利用索引
  - 功能实现 ：常量实体类封装----》controller——》 service——》mapper和xml
  - 接口测试+前后端联调
- 板块指数功能实现
  - 同上
- 涨幅榜功能实现
- 涨幅榜更多数据功能实现
- 涨停跌停数据统计功能
- 涨幅榜数据导入功能-easyExcel
- 股票成交量对比功能
- 个股分时涨跌幅统计功能
- 个股分时K线行情功能
- 个股日K线详情功能
- 股票国内大盘数据采集（RestTemplate）
- 股票实时数据采集
  - 股票数据采集准备
    - 股票响应数据结构说明
    - 思路分析，数据分片
  - 股票实时数据拉取实现
    - 解析工具类解析响应数据
    - 定义股票拉取服务的接口和实现类
      - 请求头响应设置、请求地址
      - 分批次查询Lists.partition(obj,num)
      - 获取响应数据：restTemplate
      - 响应数据解析
  - 股票实时数据批量保存功能
    - mapper方法和sql
    - 业务层实现调用批量插入方法
- 板块实时数据采集

- 项目集成xxljob定时任务框架
  - 集成xxljob监控平台
    - 导入admin工程
  - 定义股票数据采集执行器
  - 定义大盘数据采集任务
    - 定义采集任务（注解xxljob）
    - 任务cron表达式分析配置
    - 采集任务实现
- 股票采集服务线程池优化
  - yml参数配置
  - 定义参数实体bean
  - 配置线程池 ThreadPoolTaskExecutor
  - 数据异步采集  threadPoolTaskExecutor.execute













## 涉及技术点

- Maven
- Springboot
- MySQL
- Mybatis：mybatisX逆向工程
- redis缓存验证码
- 加密PasswordEncoder、BeansUtil复制
- easyExcels数据导出
- xxljob定时采集
- sharding-jdbc
- spring security













## 遇到问题及解决方案

【已解决】Redis环境无法加载：

- 问题描述：无法找到Beanfactory
- 原因：测试类中文件目录结构和src不一致，测试包名必须和启动类包名保持一致，不然启动类不知去哪里扫描bean



【已解决】点击验证码后验证码图片未跟随后端返回code发生变化

- 原因：前端要求返回参数为"code",后端返回为"checkCode"，无法对应



【未解决】涨幅榜更多数据功能实现时，程序查询所有数据有七千多远超数据库的总数量，暂不影响功能开发



【未解决】查询涨跌停数量时，查询数据为空，原因未找到

- 使用数据库中的日期查询，查询数据为空，使用非数据库数据查询，数据不为空

【未解决】sql模糊查询时，查询到的结果多了一个“



【已解决】周K线查询sql语句书写

```
周一的开盘价：每周开盘时间的最小时间点
周五的收盘价
一周内最大交易日期
```



【未解决】用户登录动态回显菜单栏功能

```
1-设计响应实体 meanUserDomain/loginResVo
2-获取指定用户的权限集合
```



## 编码方法总结

1-需求分析

- 接口是什么

- 需要传入的参数是什么（对应实体类设计）

- 需要返回的数据是什么（对应实体类设计）

  

2-概要设计

- 需求分解，实现概要步骤

- 过程数据传递所需实体类设计
- 数据库对应的表及SQL查询

3-编码实现

- web层
- service层
- dao层

4-测试

- Postman自测
- 前后端联调





## 参考资料

Gitee：https://gitee.com/jicaifang/stock_parent_145

Github：https://github.com/renhanlu/stock_parent