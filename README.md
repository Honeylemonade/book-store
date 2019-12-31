# book-store
> Spring Data JPA,Spring security with JWT, Spring Data Redis整合Demo
>

## 前端

1. Vue.js
2. router多级路由
3. Axios统一设置请求头添加token
4. localstorage
5. echart动态绘制图表
6. 悬浮购物车

## 后端

1. Spring security
   - 采用BCrypt加密
   - 权限细粒度为HttpMethod
   - 整合JWT
   - 在sub中存储姓名，一些私密的接口设计直接从token中获取当前用户名，比如获取当期那用户的订单书籍 GET /booklist 即可，不同用户发送这个请求，所收到的结果不同
2. Redis
   - 采用Spring Data redis
   - key序列化器：StringRedisSerializer
   - value序列化器：Jackson2JsonRedisSerializer
3. Spring Data JPA
   - @Query注解自定义SQL
   - 采用JPQL
   - 多表查询返回自定义实体类
   - 进行update时，Service层需要加@Transactional和再方法加@Modifying吧，并在JPQL中声明rollback（false）

## END

未经允许，请勿转载，如有问题，请联系作者。
