package studio.thinkground.aroundhub.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Set;

@SpringBootTest()
public class redisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @DisplayName("레디스 String 값 insert, read 테스트")
    @Test
    public void insertAndReadStringInRedis() {

        int LIMIT_TIME = 3 * 60;
        String key = "testStringKey";
        String value = "testStringValue";

        ValueOperations<String, String> valueOperations =
                    redisTemplate.opsForValue();

        valueOperations.set(key, value);
        //valueOperations.set(key,value,LIMIT_TIME);

        String resultValue = (String)redisTemplate.opsForValue().get(key);

        assertThat(value).isEqualTo(resultValue);
    }

    @DisplayName("레디스 String 값 insert, read 테스트")
    @Test
    public void insertAndReadStringInRedis2() {

        String key = "testStringKey";
        String value = "testStringValue";

        redisTemplate.opsForValue().set(key, value);

        String resultValue = (String)redisTemplate.opsForValue().get(key);

        assertThat(value).isEqualTo(resultValue);
    }

    @DisplayName("레디스 String 값 업데이트 테스트")
    @Test
    public void getAndSet() {

        String key = "testStringKey";
        String value = "testStringValue3";

        redisTemplate.opsForValue().getAndSet(key, value);

        String resultValue = (String)redisTemplate.opsForValue().get(key);

        assertThat(value).isEqualTo(resultValue);
    }

    @DisplayName("레디스 delete 테스트")
    @Test
    public void deleteStringInRedis() {

        String key = "testStringKey";

        assertTrue(redisTemplate.delete(key));
    }

    @DisplayName("레디스 set 테스트")
    @Test
    void testSet() {
        // given
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        String key = "testSetKey";

        // when
        setOperations.add(key, "h", "e", "l", "l", "o");

        // then
        Set<String> members = setOperations.members(key);
        Long size = setOperations.size(key);

        assertThat(members).containsOnly("h", "e", "l", "o");
        assertThat(size).isEqualTo(4);
    }

    @DisplayName("레디스 hash 테스트")
    @Test
    void testHash() {

        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();

        String key = "testHashKey";
        String hashKey = "hello";
        String value = "world";

        hashOperations.put(key, hashKey, value);

        Object result = hashOperations.get(key, hashKey);

        assertThat(value).isEqualTo(result);
    }
}
