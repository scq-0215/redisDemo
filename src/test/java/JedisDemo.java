import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @Author scq
 * @date 2020/12/20 21:00
 */
//测试驱动--测试驱动--测试功能知道此处测试使用IOC+DI：spring框架和Junint框架整合
@RunWith(SpringRunner.class)

//读取spring配置文件
    @ContextConfiguration(locations = "classpath:applicationContext-redis.xml")
public class JedisDemo {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testJedis() {
        //创建一个Jedis的连接
        Jedis jedis = new Jedis("192.168.145.131", 6379);
        //密码认证 如果设置了密码，就需要进行认证
        //jedis.auth("offcn123");
        //执行redis命令
        jedis.set("mytest", "hello world, this is jedis client!");
        //从redis中取值
        String result = jedis.get("mytest");
        //打印结果
        System.out.println(result);
        //关闭连接
        jedis.close();

    }
    @Test
    public void testJedisPool() {
        //创建一连接池对象
        JedisPool jedisPool = new JedisPool("192.168.145.129", 6379);
        //从连接池中获得连接
        Jedis jedis = jedisPool.getResource();
        //密码认证 如果设置了密码，就需要进行认证
        //jedis.auth("offcn123");
        String result = jedis.get("mytest");
        System.out.println(result);
        //关闭连接
        jedis.close();

        //关闭连接池
        jedisPool.close();
    }
    @Test
    public  void testGetTemplate(){
        //初始化容器对象--加载spring配置文件
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext-redis.xml");
        RedisTemplate rediTemplate = (RedisTemplate) context.getBean("redisTemplate");
        System.out.println(rediTemplate);
    }
    @Test
    public void test2(){
      redisTemplate.boundListOps("list1").leftPushAll("1", "2", "3");
        List list1 = redisTemplate.boundListOps("list1").range(0, -1);
        System.out.println(list1);

    }
}
