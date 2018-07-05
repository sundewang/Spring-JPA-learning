package xyz.sun.bean;

/**
 * @author sundw
 * @date 2018/7/5
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "xyz.sun")
public class ConfigurationBean {
    private String name;

    private int age;

    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "ConfigurationBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", location='" + location + '\'' +
                '}';
    }
}
