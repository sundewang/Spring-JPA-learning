package xyz.sun.controller;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ConfigurationBuilderEvent;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.event.Event;
import org.apache.commons.configuration2.event.EventListener;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.sun.bean.ConfigurationBean;
import xyz.sun.entity.Customer;
import xyz.sun.repository.CustomerCrudRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
 *
 * @author sundw
 * @date 2018/7/4
 */
@RestController
@RequestMapping("/hello")
public class Hello {
    @Autowired
    CustomerCrudRepository repository;

    @Autowired
    ConfigurationBean bean;
    static ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration> builder;

    static {
        Parameters parameters = new Parameters();
        // 不要使用resources下面的文件，它会被打包到jar中，无法修改
        File file = new File("conf/test.properties");
        System.out.println(file.getAbsolutePath());
        builder = new ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(parameters.fileBased()
                                .setFile(file));
        PeriodicReloadingTrigger trigger = new PeriodicReloadingTrigger(builder.getReloadingController(),
                null, 1, TimeUnit.SECONDS);
        trigger.start();
        builder.addEventListener(ConfigurationBuilderEvent.CONFIGURATION_REQUEST,
                new EventListener() {
                    @Override
                    public void onEvent(Event event) {
                        System.out.println(builder.getReloadingController().checkForReloading(null));
                    }
                });
    }

    @RequestMapping("/get/{lastName}")
    public List<Customer> findLastName(@PathVariable String lastName) {
        List<Customer> customer = repository.findByLastName(lastName);
        return customer;
    }

    @RequestMapping("/bean")
    public String getBean() {
        return bean.toString();
    }

    @RequestMapping("/config")
    public String getConfig() {
        String s = "";
        try {
            Configuration config = builder.getConfiguration();
            s = config.getProperty("database.url").toString();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return s;
    }

    @RequestMapping("/saveAll")
    public String saveAll() {
        String message = "";
        List<Customer> customers = generate();
        long start = System.currentTimeMillis();
        repository.saveAll(customers);
        long end = System.currentTimeMillis();
        message = String.valueOf(end-start);
        return message;
    }

    private List<Customer> generate() {
        List<Customer> customers = new ArrayList<>();
        Customer customer;
        for (int i = 10000; i < 100000; i++) {
            customer = new Customer("shen" + i, "cninfo" + i);
            customers.add(customer);
        }
        return customers;
    }
}
