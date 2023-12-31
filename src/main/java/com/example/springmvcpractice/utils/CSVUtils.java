package com.example.springmvcpractice.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.springmvcpractice.models.Product;
import com.example.springmvcpractice.models.Users;
import com.example.springmvcpractice.repositories.AdminRepositories;
import com.example.springmvcpractice.repositories.ProductRepositories;
import com.example.springmvcpractice.repositories.UsersRepositories;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

@Component
public class CSVUtils {
    private UsersRepositories usersRepositories;
    private ProductRepositories productRepositories;
    private AdminRepositories adminRepositories;

    @Autowired
    public CSVUtils(UsersRepositories usersRepositories, ProductRepositories productRepositories, AdminRepositories adminRepositories) {
        this.usersRepositories = usersRepositories;
        this.productRepositories = productRepositories;
        this.adminRepositories = adminRepositories;
    }

    @PostConstruct
    public void readUserCSV(){

//        user database seeding
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/wikiwoo/Desktop/DECAGON/week-7/practicing springboot/SpringMVCPractice/src/main/java/com/example/springmvcpractice/users.csv"))) {
            String line;
            boolean lineOne = false;
            while ((line=bufferedReader.readLine())!=null){
                String[]user = line.split(",");
                if (lineOne) {
                    Users user1 = Users.builder()
                            .fullName(user[1])
                            .imageUrl(user[3])
                            .password(BCrypt.withDefaults()
                                    .hashToString(12, user[2].trim().toCharArray()))
                            .username(user[0])
                            .balance(new BigDecimal(2500000))
                            .build();
                    usersRepositories.save(user1);
                }
                lineOne = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        admin database seeding
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/wikiwoo/Desktop/DECAGON/week-7/practicing springboot/SpringMVCPractice/src/main/java/com/example/springmvcpractice/admin.csv"))) {
            String line="";
            boolean lineOne = false;
            while ((line = bufferedReader.readLine()) != null) {
                String[] user = line.split(",");
                if (lineOne) {
                   Users admin = Users.builder()
                            .fullName(user[1])
                            .password(BCrypt.withDefaults()
                                    .hashToString(12, user[2].trim().toCharArray()))
                            .username(user[0])
                            .role(user[3])
                            .build();
                    adminRepositories.save(admin);

                }
                lineOne = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        product database seeding
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/wikiwoo/Desktop/DECAGON/week-7/practicing springboot/SpringMVCPractice/src/main/java/com/example/springmvcpractice/products.csv"))){
            String line;
            boolean lineOne = false;
            while ((line=bufferedReader.readLine())!=null){
                String[]product = line.split(",");
                if (lineOne) {
                    Product product1 = Product.builder()
                            .categories(product[0])
                            .price(new BigDecimal(product[1]))
                            .productName(product[2])
                            .quantity(Long.parseLong(product[3]))
                            .image(product[4])
                            .build();
                    productRepositories.save(product1);
                }
                lineOne = true;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
